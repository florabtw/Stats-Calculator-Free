package me.nickpierson.StatisticsSolver;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class HomeActivity extends Activity {

	private HomeView view;
	private HomeModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		view = new HomeView(this);
		model = new HomeModel();
		HomePresenter.create(this, model, view);
		
		setContentView(view.getView());
	}
	
	public void buttonClicked(View button){
		view.buttonClicked(button);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
