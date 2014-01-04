package me.nickpierson.StatsCalculatorFree.utils;

import me.nickpierson.StatsCalculator.utils.DefaultAdapter;
import android.content.Context;

public class FreeDefaultAdapter extends DefaultAdapter {

	public FreeDefaultAdapter(Context context, int resource, int titleId, int resultId) {
		super(context, resource, titleId, resultId);
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
