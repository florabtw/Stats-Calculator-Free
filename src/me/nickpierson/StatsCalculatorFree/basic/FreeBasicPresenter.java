package me.nickpierson.StatsCalculatorFree.basic;

import me.nickpierson.StatsCalculator.basic.BasicModel;
import me.nickpierson.StatsCalculator.basic.BasicPresenter;
import me.nickpierson.StatsCalculator.basic.BasicView;
import me.nickpierson.StatsCalculatorFree.basic.reference.FreeBasicReferenceActivity;
import android.app.Activity;
import android.content.Intent;

import com.thecellutioncenter.mvplib.ActionListener;

public class FreeBasicPresenter extends BasicPresenter {

	public static void create(final Activity activity, final BasicModel model, final BasicView view) {
		setup(activity, model, view);

		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				activity.startActivity(new Intent(activity, FreeBasicReferenceActivity.class));
			}
		}, BasicView.Types.MENU_REFERENCE);
	}

}
