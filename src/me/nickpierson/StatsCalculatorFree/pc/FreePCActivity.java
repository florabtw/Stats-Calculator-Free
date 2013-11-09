package me.nickpierson.StatsCalculatorFree.pc;

import me.nickpierson.StatsCalculator.pc.PCActivity;
import me.nickpierson.StatsCalculator.pc.PCModel;
import me.nickpierson.StatsCalculator.pc.PCView;
import android.os.Bundle;

public class FreePCActivity extends PCActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		model = new PCModel();
		view = new PCView(this);
		FreePCPresenter.create(model, view);

		setContentView(view.getView());
	}

}
