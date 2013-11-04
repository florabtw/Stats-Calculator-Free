package me.nickpierson.StatsCalculator;

import me.nickpierson.StatsCalculator.basic.BasicActivity;
import me.nickpierson.StatsCalculator.pc.PCActivity;
import me.nickpierson.StatsCalculator.utils.MyConstants;
import android.content.Intent;
import android.net.Uri;

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

		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				Uri emailUri = Uri.fromParts("mailto", MyConstants.DEVELOPER_EMAIL, null);
				Intent emailIntent = new Intent(Intent.ACTION_SENDTO, emailUri);
				emailIntent.putExtra(Intent.EXTRA_SUBJECT, MyConstants.EMAIL_SUBJECT);
				activity.startActivity(Intent.createChooser(emailIntent, MyConstants.EMAIL_CHOOSER_TITLE));
			}
		}, HomeView.Types.MENU_CONTACT);
	}
}
