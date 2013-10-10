package me.nickpierson.StatsCalculator.pc;

import java.util.ArrayList;

import me.nickpierson.StatsCalculator.utils.KeypadActivity;
import me.nickpierson.StatsCalculator.utils.MyConstants;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class PCActivity extends ActionBarActivity implements KeypadActivity {

	private PCModel model;
	private PCView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		model = new PCModel();
		view = new PCView(this);
		PCPresenter.create(model, view);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(view.getView());
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(MyConstants.RESULTS_KEY, view.getResults());
		outState.putBoolean(MyConstants.KEYPAD_KEY, view.isKeypadVisible());
		super.onSaveInstanceState(outState);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			view.setResults((ArrayList<String>) savedInstanceState.getSerializable(MyConstants.RESULTS_KEY));

			if (savedInstanceState.getBoolean(MyConstants.KEYPAD_KEY)) {
				view.showKeypad();
			}
		}
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public void keypadPress(View button) {
		view.keypadPress((Button) button);
	}

	@Override
	public void backSpace(View button) {
		view.backSpace();
	}

	@Override
	public void donePress(View button) {
		view.donePress();
	}

	@Override
	public void onBackPressed() {
		if (view.isKeypadVisible()) {
			view.showResults();
		} else {
			super.onBackPressed();
		}
	}

}
