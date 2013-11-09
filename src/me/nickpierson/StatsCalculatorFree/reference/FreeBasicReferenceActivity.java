package me.nickpierson.StatsCalculatorFree.reference;

import me.nickpierson.StatsCalculator.reference.BasicReferenceView;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class FreeBasicReferenceActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		BasicReferenceView view = new BasicReferenceView(this);

		setContentView(view.getView());
	}
}
