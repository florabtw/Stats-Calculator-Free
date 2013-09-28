package me.nickpierson.StatsCalculator;

import java.util.HashMap;

import me.nickpierson.StatsCalculator.R;
import me.nickpierson.StatsCalculator.basic.BasicActivity;
import me.nickpierson.StatsCalculator.pc.PCActivity;
import me.nickpierson.StatsCalculator.utils.MyConstants;
import android.content.Intent;

import com.thecellutioncenter.mvplib.DataActionListener;

public class HomePresenter {

	public static void create(final HomeActivity activity, HomeModel model, final HomeView view) {
		view.addListener(new DataActionListener() {

			@Override
			public void fire(HashMap<Enum<?>, ?> data) {
				openCorrespondingCalculator(activity, view, (String) data.get(HomeView.Types.BUTTON_CLICKED));
			}
		}, HomeView.Types.BUTTON_CLICKED);
	}

	public static void openCorrespondingCalculator(HomeActivity activity, HomeView view, String description) {
		Intent intent;

		if (description.equals(MyConstants.BASIC)) {
			intent = new Intent(activity, BasicActivity.class);
		} else if (description.equals(MyConstants.PERM_COMB)) {
			intent = new Intent(activity, PCActivity.class);
		} else {
			view.showToast(activity.getString(R.string.buttonError));
			return;
		}

		activity.startActivity(intent);
	}

}
