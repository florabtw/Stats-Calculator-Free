package me.nickpierson.StatisticsSolver;

import java.util.HashMap;

import me.nickpierson.StatisticsSolver.basic.BasicActivity;

import android.content.Intent;

import com.thecellutioncenter.mvplib.DataActionListener;

public class HomePresenter {

	public static void create(final HomeActivity activity, HomeModel model, final HomeView view) {
		view.addListener(new DataActionListener() {
			
			@Override
			public void fire(HashMap<Enum<?>, ?> data) {
				openCorrespondingCalculator(activity, view, (Integer) data.get(HomeView.Types.BUTTON_CLICKED));
			}
		}, HomeView.Types.BUTTON_CLICKED);

	}
	
	public static void openCorrespondingCalculator(HomeActivity activity, HomeView view, int buttonID){
		Intent intent;
		
		switch (buttonID) {
		case R.id.home_btnFirst:
			intent = new Intent(activity, BasicActivity.class);
			break;
			
		default:
			view.showToast(activity.getString(R.string.buttonError));
			return;
		}
		
		activity.startActivity(intent);
	}

}
