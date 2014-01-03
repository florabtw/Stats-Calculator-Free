package me.nickpierson.StatsCalculatorFree.pc;

import me.nickpierson.StatsCalculator.pc.PCAdapter;
import android.content.Context;

public class FreePCAdapter extends PCAdapter {

	public FreePCAdapter(Context context, int resource) {
		super(context, resource);
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
