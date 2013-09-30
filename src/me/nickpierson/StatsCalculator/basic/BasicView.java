package me.nickpierson.StatsCalculator.basic;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import me.nickpierson.StatsCalculator.R;
import me.nickpierson.StatsCalculator.utils.MyConstants;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Build;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.thecellutioncenter.mvplib.DataActionHandler;

public class BasicView extends DataActionHandler {

	public enum Types {
		DONE_CLICKED, EDITTEXT_CLICKED, MENU_SAVE, SAVE_LIST, LOAD_LIST, MENU_LOAD_OR_DELETE, DELETE_LIST, MENU_REFERENCE;
	}

	public enum Keys {
		LIST_NAME;
	}

	private RelativeLayout view;
	private FrameLayout flContent;
	private ListView lvResults;
	private TableLayout tlKeypad;
	private EditText etInput;
	private BasicActivity activity;
	private BasicAdapter resultsAdapter;

	public BasicView(BasicActivity activity) {
		this.activity = activity;
		view = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.basic, null);
		flContent = (FrameLayout) view.findViewById(R.id.basic_flContent);
		etInput = (EditText) view.findViewById(R.id.basic_etInput);
		disableSoftInputFromAppearing();

		lvResults = (ListView) LayoutInflater.from(activity).inflate(R.layout.basic_results, null);
		resultsAdapter = new BasicAdapter(activity, R.layout.basic_result_item);
		lvResults.setAdapter(resultsAdapter);

		flContent.addView(lvResults);

		etInput.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				event(Types.EDITTEXT_CLICKED);
				return false;
			}
		});

		tlKeypad = (TableLayout) LayoutInflater.from(activity).inflate(R.layout.keypad, null);

		Button btnBackspace = (Button) tlKeypad.findViewById(R.id.keypad_backspace);
		btnBackspace.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				etInput.setText(etInput.getText().subSequence(etInput.getSelectionEnd(), etInput.getText().length()));
				return true;
			}
		});
	}

	/*
	 * TODO: Figure out why this is necessary. I just ran this on API 8 phone
	 * and it worked just fine.
	 */
	@SuppressLint("NewApi")
	private void disableSoftInputFromAppearing() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			etInput.setRawInputType(InputType.TYPE_CLASS_TEXT);
			etInput.setTextIsSelectable(true);
		} else {
			etInput.setRawInputType(InputType.TYPE_NULL);
			etInput.setFocusable(true);
		}
	}

	public void showResults(LinkedHashMap<String, Double> result) {
		resultsAdapter.clear();
		for (Entry<String, Double> entry : result.entrySet()) {
			resultsAdapter.add(entry);
		}

		flContent.removeAllViews();
		flContent.addView(lvResults);
	}

	public void showKeypad() {
		flContent.removeAllViews();
		flContent.addView(tlKeypad);
	}

	public void showToast(String message) {
		Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
	}

	public void showErrorToast(int errorItem) {
		Toast.makeText(activity, String.format(MyConstants.DESCRIPTIVE_NUMBER_ERROR, errorItem), Toast.LENGTH_SHORT).show();
	}

	public void showSaveListPopup() {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

		View alertView = LayoutInflater.from(activity).inflate(R.layout.save_list_dialog, null);
		final EditText etName = (EditText) alertView.findViewById(R.id.save_list_etListName);

		alertBuilder.setView(alertView);
		alertBuilder.setPositiveButton(R.string.save, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				eventWithName(Types.SAVE_LIST, etName.getText().toString());
			}
		});
		alertBuilder.setNegativeButton(R.string.cancel, null);

		alertBuilder.show();
	}

	public void showLoadListPopup(final String[] savedLists) {
		AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);
		alertBuilder.setSingleChoiceItems(savedLists, 0, null);
		alertBuilder.setPositiveButton(R.string.load, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ListView lv = ((AlertDialog) dialog).getListView();
				String name = (String) lv.getAdapter().getItem(lv.getCheckedItemPosition());
				eventWithName(Types.LOAD_LIST, name);
			}
		});
		alertBuilder.setNegativeButton(R.string.delete, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ListView lv = ((AlertDialog) dialog).getListView();
				String name = (String) lv.getAdapter().getItem(lv.getCheckedItemPosition());
				eventWithName(Types.DELETE_LIST, name);
			}
		});
		alertBuilder.setNeutralButton(R.string.cancel, null);

		alertBuilder.show();
	}

	private void eventWithName(Types type, String name) {
		HashMap<Enum<?>, String> result = new HashMap<Enum<?>, String>();
		result.put(Keys.LIST_NAME, name);
		dataEvent(type, result);
	}

	public void menuSaveList() {
		event(Types.MENU_SAVE);
	}

	public void menuLoadList() {
		event(Types.MENU_LOAD_OR_DELETE);
	}

	public void menuReference() {
		event(Types.MENU_REFERENCE);
	}

	public void keypadPress(Button button) {
		/* Skips MVP */
		etInput.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, MyConstants.getKeyEvent(button.getText().charAt(0))));
	}

	public void backSpace() {
		/* Skips MVP */
		etInput.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
	}

	public void donePress() {
		event(Types.DONE_CLICKED);
	}

	public boolean isKeyPadVisible() {
		return tlKeypad.isShown();
	}

	public RelativeLayout getView() {
		return view;
	}

	public String getInput() {
		return etInput.getText().toString();
	}

	public void setInputText(String list) {
		etInput.setText(list);
	}

	public void selectInput(String string) {
		int startIndex = etInput.getText().toString().indexOf(string);
		
		/* Just in case */
		if (startIndex > 0) {
			etInput.setSelection(startIndex, startIndex + string.length());
		}
	}
}
