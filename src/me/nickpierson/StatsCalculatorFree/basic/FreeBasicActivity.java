package me.nickpierson.StatsCalculatorFree.basic;

import me.nickpierson.StatsCalculator.basic.BasicActivity;
import me.nickpierson.StatsCalculator.basic.BasicModel;
import me.nickpierson.StatsCalculator.basic.BasicView;
import android.os.Bundle;

public class FreeBasicActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new BasicView(this);
		model = new BasicModel(this);
		FreeBasicPresenter.create(this, model, view);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(view.getView());
	}
}
