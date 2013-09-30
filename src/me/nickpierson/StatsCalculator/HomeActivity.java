package me.nickpierson.StatsCalculator;

import android.app.Activity;
import android.os.Bundle;

public class HomeActivity extends Activity {

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
