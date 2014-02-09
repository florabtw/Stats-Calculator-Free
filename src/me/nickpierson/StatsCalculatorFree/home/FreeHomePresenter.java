package me.nickpierson.StatsCalculatorFree.home;

import me.nickpierson.StatsCalculator.home.HomeModel;
import me.nickpierson.StatsCalculator.home.HomePresenter;
import me.nickpierson.StatsCalculator.home.HomeView;
import me.nickpierson.StatsCalculatorFree.basic.FreeBasicActivity;
import me.nickpierson.StatsCalculatorFree.pc.FreePCActivity;
import me.nickpierson.StatsCalculatorFree.utils.FreeConstants;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.thecellutioncenter.mvplib.ActionListener;

public class FreeHomePresenter extends HomePresenter {

	public static void create(final Activity activity, HomeModel model, final FreeHomeView view) {
		setup(activity, model, view);

		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				activity.startActivity(new Intent(activity, FreeBasicActivity.class));
			}
		}, HomeView.Types.DESCRIPTIVE_BUTTON);

		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				activity.startActivity(new Intent(activity, FreePCActivity.class));
			}
		}, HomeView.Types.PERM_COMB_BUTTON);

		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				Uri uri = Uri.parse(FreeConstants.UPGRADE_URL);
				Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);

				try {
					activity.startActivity(rateAppIntent);
				} catch (Exception e) {
					view.showToast(FreeConstants.UPGRADE_ERROR);
				}
			}
		}, FreeHomeView.FreeTypes.MENU_UPGRADE);
	}
}
