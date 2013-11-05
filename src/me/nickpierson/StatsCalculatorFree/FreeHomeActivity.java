package me.nickpierson.StatsCalculatorFree;

import me.nickpierson.StatsCalculator.HomeActivity;
import me.nickpierson.StatsCalculator.HomeModel;
import me.nickpierson.StatsCalculator.HomeView;
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
