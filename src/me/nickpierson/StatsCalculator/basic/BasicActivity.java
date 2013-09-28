package me.nickpierson.StatsCalculator.basic;

import me.nickpierson.StatsCalculator.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class BasicActivity extends Activity {

	private BasicView view;
	private BasicModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new BasicView(this);
		model = new BasicModel();
		BasicPresenter.create(model, view);

		setContentView(view.getView());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.basic, menu);
		return true;
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
