package me.nickpierson.StatisticsSolver.basic;

import me.nickpierson.StatisticsSolver.R;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

public class BasicView {

	private RelativeLayout view;

	public BasicView(BasicActivity activity) {
		view = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.basic, null);
	}

	public RelativeLayout getView() {
		return view;
	}

}
