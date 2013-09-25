package me.nickpierson.StatisticsSolver.pc;

import me.nickpierson.StatisticsSolver.R;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class PCView {

	LinearLayout view;
	
	public PCView(PCActivity pcActivity) {
		view = (LinearLayout) LayoutInflater.from(pcActivity).inflate(R.layout.perm_comb, null);
	}

	public View getView() {
		return view;
	}

}
