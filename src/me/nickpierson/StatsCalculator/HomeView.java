package me.nickpierson.StatsCalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.thecellutioncenter.mvplib.DataActionHandler;

public class HomeView extends DataActionHandler {

	public enum Types {
		DESCRIPTIVE_BUTTON, PERM_COMB_BUTTON;
	}

	private RelativeLayout view;
	private HomeActivity activity;
	private Button btnPermComb;
	private Button btnDescriptive;

	public HomeView(HomeActivity activity) {
		this.activity = activity;

		view = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.home, null);
		btnDescriptive = (Button) view.findViewById(R.id.home_btnDescriptive);
		btnPermComb = (Button) view.findViewById(R.id.home_btnPermComb);

		btnDescriptive.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				event(Types.DESCRIPTIVE_BUTTON);
			}
		});

		btnPermComb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				event(Types.PERM_COMB_BUTTON);
			}
		});
	}

	public RelativeLayout getView() {
		return view;
	}

	public void showToast(String message) {
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}

}
