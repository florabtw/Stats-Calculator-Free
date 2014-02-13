package me.nickpierson.StatsCalculatorFree.basic;

import me.nickpierson.StatsCalculator.basic.BasicView;
import me.nickpierson.StatsCalculator.utils.Constants;
import me.nickpierson.StatsCalculator.utils.KeypadHelper;
import me.nickpierson.StatsCalculatorFree.utils.FreeDefaultAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.nickpierson.me.StatsCalculator.R;

public class FreeBasicView extends BasicView {

	private KeypadHelper keypadHelper;

	public FreeBasicView(Activity activity) {
		super(activity, new FreeDefaultAdapter(activity, R.layout.basic_result_item, R.id.basic_tvResultTitle, R.id.basic_tvResultAnswer));

		lvResults = (ListView) LayoutInflater.from(activity).inflate(R.layout.results_list, null);
		lvResults.setAdapter(resultsAdapter);
		resultsAdapter.addMultiple(Constants.BASIC_TITLES);

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

	public void menuReference() {
		event(Types.MENU_REFERENCE);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public int getDialogTheme() {
		return AlertDialog.THEME_HOLO_LIGHT;
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
