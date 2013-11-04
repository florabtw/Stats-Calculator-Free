package me.nickpierson.StatsCalculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class HomeActivity extends ActionBarActivity {

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.home_contact_developer:
			view.menuContact();
			return true;
		case R.id.home_rate_app:
			return true;
		default:
			return false;
		}
	}
}
