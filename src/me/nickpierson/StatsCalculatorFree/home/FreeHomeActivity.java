package me.nickpierson.StatsCalculatorFree.home;

import me.nickpierson.StatsCalculator.R;
import me.nickpierson.StatsCalculator.home.HomeActivity;
import me.nickpierson.StatsCalculator.home.HomeModel;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class FreeHomeActivity extends HomeActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		view = new FreeHomeView(this);
		model = new HomeModel();
		FreeHomePresenter.create(this, model, (FreeHomeView) view);

		setContentView(view.getView());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.free_home, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.menu_home_upgrade) {
			((FreeHomeView) view).menuUpgrade();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
	}
}
