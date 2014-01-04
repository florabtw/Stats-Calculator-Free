package me.nickpierson.StatsCalculatorFree.basic;

import me.nickpierson.StatsCalculator.R;
import me.nickpierson.StatsCalculator.basic.BasicActivity;
import me.nickpierson.StatsCalculator.basic.BasicModel;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.free_basic, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int itemId = item.getItemId();
		if (itemId == R.id.menu_reference) {
			((FreeBasicView) view).menuReference();
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
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
