package me.nickpierson.StatsCalculator.basic;

import me.nickpierson.StatsCalculator.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class BasicActivity extends Activity {

	private BasicView view;
	private BasicModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new BasicView(this);
		model = new BasicModel(this);
		BasicPresenter.create(model, view);

		setContentView(view.getView());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.basic, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.settings_save_list:
			view.saveList();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/* Keypad input character */
	public void keypadPress(View button) {
		view.keypadPress((Button) button);
	}

	/* Keypad backspace button */
	public void backSpace(View button) {
		view.backSpace();
	}

	/* Keypad done button */
	public void donePress(View button) {
		view.donePress();
	}
}
