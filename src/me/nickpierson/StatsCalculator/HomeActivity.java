package me.nickpierson.StatsCalculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class HomeActivity extends ActionBarActivity {

	private HomeView view;
	private HomeModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new HomeView(this);
		model = new HomeModel();
		HomePresenter.create(this, model, view);

		setContentView(view.getView());
	}
}
