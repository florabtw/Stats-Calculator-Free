package me.nickpierson.StatsCalculator.basic;

import me.nickpierson.StatsCalculator.R;
import me.nickpierson.StatsCalculator.utils.KeypadActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class BasicActivity extends Activity implements KeypadActivity {

	private BasicView view;
	private BasicModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new BasicView(this);
		model = new BasicModel(this);
		BasicPresenter.create(this, model, view);

		setContentView(view.getView());
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
}
