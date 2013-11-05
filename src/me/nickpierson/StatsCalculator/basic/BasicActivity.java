package me.nickpierson.StatsCalculator.basic;

import me.nickpierson.StatsCalculator.R;
import me.nickpierson.StatsCalculator.utils.KeypadActivity;
import me.nickpierson.StatsCalculator.utils.MyConstants;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class BasicActivity extends ActionBarActivity implements KeypadActivity {

	private BasicView view;
	private BasicModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new BasicView(this);
		model = new BasicModel(this);
		BasicPresenter.create(this, model, view);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(view.getView());
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putDoubleArray(MyConstants.RESULTS_KEY, model.getResults());
		outState.putBoolean(MyConstants.KEYPAD_KEY, view.isKeyPadVisible());
		outState.putInt(MyConstants.SCROLL_POSITION_KEY, view.getScrollPosition());
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			double[] results = savedInstanceState.getDoubleArray(MyConstants.RESULTS_KEY);
			model.setResults(results);
			view.showResults(results);

			view.setScrollPosition(savedInstanceState.getInt(MyConstants.SCROLL_POSITION_KEY));

			if (savedInstanceState.getBoolean(MyConstants.KEYPAD_KEY)) {
				view.showKeypad();
			}
		}
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.basic, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings_save_list:
			view.menuSaveList();
			return true;
		case R.id.settings_load_list:
			view.menuLoadList();
			return true;
		case R.id.settings_reference:
			view.menuReference();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void keypadPress(View button) {
		view.keypadPress((Button) button);
	}

	@Override
	public void backSpace(View button) {
		view.backspace();
	}

	@Override
	public void donePress(View button) {
		view.donePress();
	}

	@Override
	public void onBackPressed() {
		if (view.isKeyPadVisible()) {
			view.showResults();
		} else {
			super.onBackPressed();
		}
	}
}
