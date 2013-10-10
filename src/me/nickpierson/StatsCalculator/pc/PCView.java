package me.nickpierson.StatsCalculator.pc;

import java.util.ArrayList;

import me.nickpierson.StatsCalculator.R;
import me.nickpierson.StatsCalculator.utils.KeypadHelper;
import me.nickpierson.StatsCalculator.utils.MyConstants;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
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
	private TextView tvNsTitle;
	private TextView tvNPermRTitle;
	private TextView tvNChooseRTitle;
	private TextView tvIndistinctTitle;

	public PCView(PCActivity activity) {
		this.activity = activity;
		view = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.perm_comb, null);
		svResults = (ScrollView) LayoutInflater.from(activity).inflate(R.layout.perm_comb_results, null);
		tlKeypad = (TableLayout) LayoutInflater.from(activity).inflate(R.layout.keypad, null);
		flFrame = (FrameLayout) view.findViewById(R.id.pc_flFrame);
		tvNsTitle = (TextView) view.findViewById(R.id.pc_ns_title);
		tvNFact = (TextView) svResults.findViewById(R.id.pc_tvNFact);
		tvRFact = (TextView) svResults.findViewById(R.id.pc_tvRFact);
		tvNPermR = (TextView) svResults.findViewById(R.id.pc_tvNPermR);
		tvNChooseR = (TextView) svResults.findViewById(R.id.pc_tvNChooseR);
		tvIndistinct = (TextView) svResults.findViewById(R.id.pc_tvIndistinct);
		tvNPermRTitle = (TextView) svResults.findViewById(R.id.pc_results_tvNPermR);
		tvNChooseRTitle = (TextView) svResults.findViewById(R.id.pc_results_tvNCombR);
		tvIndistinctTitle = (TextView) svResults.findViewById(R.id.pc_results_tvIndistinctTitle);
		ImageButton btnBackspace = (ImageButton) tlKeypad.findViewById(R.id.keypad_backspace);
		Button btnMultiply = (Button) tlKeypad.findViewById(R.id.keypad_times);

		subscriptNPermRTitle();
		subscriptNChooseRTitle();
		subscriptIndisctinctTitle();
		subscriptNsTitle();

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

		btnMultiply.setEnabled(false);
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

	private void subscriptNPermRTitle() {
		SpannableStringBuilder string = new SpannableStringBuilder(tvNPermRTitle.getText());
		subscriptText(string, 0, 1);
		subscriptText(string, 2, 3);
		tvNPermRTitle.setText(string);
	}

	private void subscriptNChooseRTitle() {
		SpannableStringBuilder string = new SpannableStringBuilder(tvNChooseRTitle.getText());
		subscriptText(string, 0, 1);
		subscriptText(string, 2, 3);
		tvNChooseRTitle.setText(string);
	}

	private void subscriptIndisctinctTitle() {
		SpannableStringBuilder string = new SpannableStringBuilder(tvIndistinctTitle.getText());
		subscriptText(string, 6, 7);
		subscriptText(string, 9, 10);
		subscriptText(string, 12, 13);
		tvIndistinctTitle.setText(string);
	}

	private void subscriptNsTitle() {
		SpannableStringBuilder nsTitle = new SpannableStringBuilder(tvNsTitle.getText());
		subscriptText(nsTitle, 1, 2);
		subscriptText(nsTitle, 4, 5);
		subscriptText(nsTitle, 7, 8);
		tvNsTitle.setText(nsTitle);
	}

	private void subscriptText(SpannableStringBuilder nsTitle, int start, int end) {
		nsTitle.setSpan(new SubscriptSpan(), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		nsTitle.setSpan(new RelativeSizeSpan(.6f), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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

	public ArrayList<String> getResults() {
		ArrayList<String> results = new ArrayList<String>();
		results.add(tvNFact.getText().toString());
		results.add(tvRFact.getText().toString());
		results.add(tvNPermR.getText().toString());
		results.add(tvNChooseR.getText().toString());
		results.add(tvIndistinct.getText().toString());
		return results;
	}

	public void setResults(ArrayList<String> results) {
		setNFactorial(results.get(0));
		setRFactorial(results.get(1));
		setPermutation(results.get(2));
		setCombination(results.get(3));
		setIndistinct(results.get(4));
	}

	public View getView() {
		return view;
	}
}
