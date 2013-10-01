package me.nickpierson.StatsCalculator.pc;

import me.nickpierson.StatsCalculator.R;
import me.nickpierson.StatsCalculator.utils.KeypadHelper;
import me.nickpierson.StatsCalculator.utils.MyConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thecellutioncenter.mvplib.ActionHandler;

public class PCView extends ActionHandler {

	public enum Types {
		DONE_PRESSED, EDITTEXT_CLICKED
	}

	private LinearLayout view;
	private EditText etNVal;
	private EditText etRVal;
	private EditText etNVals;
	private TextView tvNFact;
	private TextView tvRFact;
	private TextView tvNPermR;
	private TextView tvNChooseR;
	private TextView tvIndistinct;
	private PCActivity activity;
	private Toast toast;
	private KeypadHelper keypadHelper;
	private ScrollView svResults;
	private TableLayout tlKeypad;
	private FrameLayout flFrame;

	public PCView(PCActivity activity) {
		this.activity = activity;
		view = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.perm_comb, null);
		svResults = (ScrollView) LayoutInflater.from(activity).inflate(R.layout.perm_comb_results, null);
		tlKeypad = (TableLayout) LayoutInflater.from(activity).inflate(R.layout.keypad, null);
		flFrame = (FrameLayout) view.findViewById(R.id.pc_flFrame);
		tvNFact = (TextView) svResults.findViewById(R.id.pc_tvNFact);
		tvRFact = (TextView) svResults.findViewById(R.id.pc_tvRFact);
		tvNPermR = (TextView) svResults.findViewById(R.id.pc_tvNPermR);
		tvNChooseR = (TextView) svResults.findViewById(R.id.pc_tvNChooseR);
		tvIndistinct = (TextView) svResults.findViewById(R.id.pc_tvIndistinct);
		Button btnBackspace = (Button) tlKeypad.findViewById(R.id.keypad_backspace);

		etNVal = (EditText) view.findViewById(R.id.pc_etNVal);
		etRVal = (EditText) view.findViewById(R.id.pc_etRVal);
		etNVals = (EditText) view.findViewById(R.id.pc_etNVals);

		setEditTextClickListener(etNVal);
		setEditTextClickListener(etRVal);
		setEditTextClickListener(etNVals);

		keypadHelper = new KeypadHelper();

		keypadHelper.disableSoftInputFromAppearing(etNVal);
		keypadHelper.disableSoftInputFromAppearing(etRVal);
		keypadHelper.disableSoftInputFromAppearing(etNVals);

		flFrame.addView(svResults);

		btnBackspace.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				EditText etSelected = getSelectedEditText();
				if (etSelected != null) {
					keypadHelper.longPressBackspace(etSelected);
				}
				return true;
			}
		});
	}

	private void setEditTextClickListener(EditText editText) {
		editText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				event(Types.EDITTEXT_CLICKED);
				return false;
			}
		});
	}

	public void showDefaultValues() {
		setNFactorial(MyConstants.NOT_APPLICABLE);
		setRFactorial(MyConstants.NOT_APPLICABLE);
		setPermutation(MyConstants.NOT_APPLICABLE);
		setCombination(MyConstants.NOT_APPLICABLE);
		setIndistinct(MyConstants.NOT_APPLICABLE);
	}

	public void showKeypad() {
		flFrame.removeAllViews();
		flFrame.addView(tlKeypad);
	}

	public void showResults() {
		flFrame.removeAllViews();
		flFrame.addView(svResults);
	}

	public void setNFactorial(String text) {
		tvNFact.setText(text);
	}

	public void setRFactorial(String text) {
		tvRFact.setText(text);
	}

	public void setPermutation(String text) {
		tvNPermR.setText(text);
	}

	public void setCombination(String text) {
		tvNChooseR.setText(text);
	}

	public void setIndistinct(String text) {
		tvIndistinct.setText(text);
	}

	public void showToast(String message) {
		try {
			toast.getView().isShown();
			toast.setText(message);
		} catch (Exception e) {
			toast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
		}
		toast.show();
	}

	public void keypadPress(Button button) {
		/* Skips MVP */
		EditText etSelected = getSelectedEditText();

		if (etSelected != null) {
			keypadHelper.keypadPress(etSelected, button.getText().charAt(0));
		}
	}

	public void backSpace() {
		/* Skips MVP */
		EditText etSelected = getSelectedEditText();

		if (etSelected != null) {
			keypadHelper.backspace(etSelected);
		}
	}

	public void donePress() {
		event(Types.DONE_PRESSED);
	}

	private EditText getSelectedEditText() {
		EditText etSelected = null;

		if (etNVal.isFocused()) {
			etSelected = etNVal;
		} else if (etRVal.isFocused()) {
			etSelected = etRVal;
		} else if (etNVals.isFocused()) {
			etSelected = etNVals;
		}

		return etSelected;
	}

	public boolean isKeypadVisible() {
		return tlKeypad.isShown();
	}

	public String getNVal() {
		return etNVal.getText().toString();
	}

	public String getRVal() {
		return etRVal.getText().toString();
	}

	public String getNVals() {
		return etNVals.getText().toString();
	}

	public View getView() {
		return view;
	}
}
