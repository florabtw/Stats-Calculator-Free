package me.nickpierson.StatsCalculatorFree.reference;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class FreeBasicReferenceActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FreeBasicReferenceView view = new FreeBasicReferenceView(this);

		setContentView(view.getView());
	}

}
