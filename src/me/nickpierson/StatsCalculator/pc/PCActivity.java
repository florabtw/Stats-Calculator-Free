package me.nickpierson.StatsCalculator.pc;

import android.app.Activity;
import android.os.Bundle;

public class PCActivity extends Activity {

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

}
