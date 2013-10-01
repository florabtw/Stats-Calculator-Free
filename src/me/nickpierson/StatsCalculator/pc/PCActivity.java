package me.nickpierson.StatsCalculator.pc;

import me.nickpierson.StatsCalculator.utils.KeypadActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PCActivity extends Activity implements KeypadActivity {

	private PCModel model;
	private PCView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		model = new PCModel();
		view = new PCView(this);
		PCPresenter.create(model, view);

		setContentView(view.getView());
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

}
