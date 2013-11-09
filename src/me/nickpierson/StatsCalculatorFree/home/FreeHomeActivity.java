package me.nickpierson.StatsCalculatorFree.home;

import me.nickpierson.StatsCalculator.home.HomeActivity;
import me.nickpierson.StatsCalculator.home.HomeModel;
import me.nickpierson.StatsCalculator.home.HomeView;
import android.os.Bundle;

public class FreeHomeActivity extends HomeActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new HomeView(this);
		model = new HomeModel();
		FreeHomePresenter.create(this, model, view);

		setContentView(view.getView());
	}
}
