package me.nickpierson.StatisticsSolver.basic;

import android.app.Activity;
import android.os.Bundle;

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
}
