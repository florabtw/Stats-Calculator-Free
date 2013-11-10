package me.nickpierson.StatsCalculatorFree.pc;

import me.nickpierson.StatsCalculator.pc.PCActivity;
import me.nickpierson.StatsCalculator.pc.PCModel;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FreePCActivity extends PCActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		model = new PCModel();
		view = new FreePCView(this);
		FreePCPresenter.create(model, view);

		setContentView(view.getView());
	}

	@Override
	public void keypadPress(View button) {
		((FreePCView) view).keypadPress((Button) button);
	}

	@Override
	public void backSpace(View button) {
		((FreePCView) view).backSpace();
	}

}
