package me.nickpierson.StatisticsSolver.basic;

import me.nickpierson.StatisticsSolver.R;
import me.nickpierson.StatisticsSolver.utils.BasicResult;
import me.nickpierson.StatisticsSolver.utils.MyConstants;
import android.annotation.SuppressLint;
import android.os.Build;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thecellutioncenter.mvplib.ActionHandler;

public class BasicView extends ActionHandler {

	public enum Types {
		DONE_CLICKED, EDITTEXT_CLICKED;
	}

	private RelativeLayout view;
	private FrameLayout flContent;
	private ScrollView svResults;
	private TableLayout tlKeypad;
	private EditText etInput;
	private BasicActivity activity;

	public BasicView(BasicActivity activity) {
		this.activity = activity;
		view = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.basic, null);
		flContent = (FrameLayout) view.findViewById(R.id.basic_flContent);
		etInput = (EditText) view.findViewById(R.id.basic_etInput);

		svResults = (ScrollView) LayoutInflater.from(activity).inflate(R.layout.basic_results, null);
		tlKeypad = (TableLayout) LayoutInflater.from(activity).inflate(R.layout.keypad, null);

		flContent.addView(svResults);

		etInput.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				event(Types.EDITTEXT_CLICKED);
				return false;
			}
		});

		disableSoftInputFromAppearing();
	}

	@SuppressLint("NewApi")
	private void disableSoftInputFromAppearing() {
		if (Build.VERSION.SDK_INT >= 11) {
			etInput.setRawInputType(InputType.TYPE_CLASS_TEXT);
			etInput.setTextIsSelectable(true);
		} else {
			etInput.setRawInputType(InputType.TYPE_NULL);
			etInput.setFocusable(true);
		}
	}

	public void showResults(BasicResult results) {
		((TextView) svResults.findViewById(R.id.basic_tvSizeResult)).setText(String.valueOf(results.size));
		((TextView) svResults.findViewById(R.id.basic_tvSumResult)).setText(String.valueOf(results.sum));
		((TextView) svResults.findViewById(R.id.basic_tvMeanResult)).setText(String.valueOf(results.mean));
		((TextView) svResults.findViewById(R.id.basic_tvMedianResult)).setText(String.valueOf(results.median));
		((TextView) svResults.findViewById(R.id.basic_tvModeResult)).setText(String.valueOf(results.mode));
		((TextView) svResults.findViewById(R.id.basic_tvRangeResult)).setText(String.valueOf(results.range));
		((TextView) svResults.findViewById(R.id.basic_tvVarSampleResult)).setText(String.valueOf(results.sampleVariance));
		((TextView) svResults.findViewById(R.id.basic_tvVarPopResult)).setText(String.valueOf(results.popVariance));
		((TextView) svResults.findViewById(R.id.basic_tvStdDevSampleResult)).setText(String.valueOf(results.sampleDeviation));
		((TextView) svResults.findViewById(R.id.basic_tvStdDevPopResult)).setText(String.valueOf(results.popDeviation));

		flContent.removeAllViews();
		flContent.addView(svResults);
	}

	public void showKeypad() {
		flContent.removeAllViews();
		flContent.addView(tlKeypad);
	}

	public void showToast(String message) {
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
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
}
