package me.nickpierson.StatsCalculator;

import me.nickpierson.StatsCalculator.basic.BasicActivity;
import me.nickpierson.StatsCalculator.pc.PCActivity;
import android.content.Intent;

import com.thecellutioncenter.mvplib.ActionListener;

public class HomePresenter {

	public static void create(final HomeActivity activity, HomeModel model, final HomeView view) {
		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				activity.startActivity(new Intent(activity, BasicActivity.class));
			}
		}, HomeView.Types.DESCRIPTIVE_BUTTON);

		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				activity.startActivity(new Intent(activity, PCActivity.class));
			}
		}, HomeView.Types.PERM_COMB_BUTTON);
	}
}
