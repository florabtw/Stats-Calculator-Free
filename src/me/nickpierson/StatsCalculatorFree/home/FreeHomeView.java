package me.nickpierson.StatsCalculatorFree.home;

import me.nickpierson.StatsCalculator.home.HomeView;
import android.app.Activity;

public class FreeHomeView extends HomeView {

	public enum FreeTypes {
		MENU_UPGRADE;
	}

	public FreeHomeView(Activity activity) {
		super(activity);
	}

	public void menuUpgrade() {
		event(FreeTypes.MENU_UPGRADE);
	}

	public void showUpgradeDetails() {
		// TODO Auto-generated method stub
		
	}
}
