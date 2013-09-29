package me.nickpierson.StatsCalculator.basic.reference;

import android.app.Activity;
import android.os.Bundle;

public class BasicReferenceActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		BasicReferenceView view = new BasicReferenceView(this);
		
		setContentView(view.getView());
	}

}
