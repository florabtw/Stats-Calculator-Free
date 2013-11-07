package me.nickpierson.StatsCalculatorFree.pc;

import me.nickpierson.StatsCalculator.pc.PCModel;
import me.nickpierson.StatsCalculator.pc.PCPresenter;
import me.nickpierson.StatsCalculator.pc.PCView;

public class FreePCPresenter extends PCPresenter {

	public static void create(final PCModel model, final PCView view) {
		setup(model, view);
	}
}
