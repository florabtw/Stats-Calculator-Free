package me.nickpierson.StatsCalculatorFree.basic;

import me.nickpierson.StatsCalculator.basic.BasicActivity;
import me.nickpierson.StatsCalculator.basic.BasicModel;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FreeBasicActivity extends BasicActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new FreeBasicView(this);
		model = new BasicModel(this);
		FreeBasicPresenter.create(this, model, view);

		setContentView(view.getView());
	}

	@Override
	public void keypadPress(View button) {
		((FreeBasicView) view).keypadPress((Button) button);
	}

	@Override
	public void backSpace(View button) {
		((FreeBasicView) view).backspace();
	}
}
