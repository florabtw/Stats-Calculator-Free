package me.nickpierson.StatisticsSolver;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class HomeView {

	private RelativeLayout view;

	public HomeView(HomeActivity homeActivity) {
		view = (RelativeLayout) LayoutInflater.from(homeActivity).inflate(R.layout.home, null);
	}

	public View getView() {
		return view;
	}

}
