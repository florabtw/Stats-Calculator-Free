package me.nickpierson.StatsCalculatorFree.basic;

import me.nickpierson.StatsCalculator.basic.BasicView;
import me.nickpierson.StatsCalculator.utils.Constants;
import me.nickpierson.StatsCalculator.utils.KeypadHelper;
import android.app.Activity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.nickpierson.me.StatsCalculator.R;

public class FreeBasicView extends BasicView {

	private KeypadHelper keypadHelper;

	public FreeBasicView(Activity activity) {
		super(activity);

		resultsAdapter = new FreeBasicAdapter(activity, R.layout.basic_result_item);

		resultsAdapter.addMultiple(Constants.BASIC_TITLES);
		lvResults.setAdapter(resultsAdapter);

		keypadHelper = new KeypadHelper();
		ImageButton btnBackspace = (ImageButton) tlKeypad.findViewById(R.id.keypad_backspace);

		keypadHelper.disableSoftInputFromAppearing(etInput);
		keypadHelper.watchEditText(etInput);

		btnBackspace.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				keypadHelper.longPressBackspace(etInput);
				return true;
			}
		});
	}

	@Override
	public void showResults() {
		flFrame.removeAllViews();
		flFrame.addView(lvResults);
	}

	public void keypadPress(Button button) {
		/* Skips MVP */
		keypadHelper.keypadPress(etInput, button.getText().charAt(0));
	}

	public void backspace() {
		/* Skips MVP */
		keypadHelper.backspace(etInput);
	}
}
