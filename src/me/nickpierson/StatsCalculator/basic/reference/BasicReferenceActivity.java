package me.nickpierson.StatsCalculator.basic.reference;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class BasicReferenceActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		BasicReferenceView view = new BasicReferenceView(this);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setContentView(view.getView());
	}

}
