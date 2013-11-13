package me.nickpierson.StatsCalculatorFree.basic;

import me.nickpierson.StatsCalculator.basic.BasicAdapter;
import android.content.Context;

public class FreeBasicAdapter extends BasicAdapter {

	public FreeBasicAdapter(Context context, int resource) {
		super(context, resource);
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
