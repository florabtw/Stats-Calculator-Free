package me.nickpierson.StatisticsSolver.pc;

import com.thecellutioncenter.mvplib.ActionListener;

public class PCPresenter {

	public static void create(final PCModel model, final PCView view) {
		view.displayDefaultValues();

		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				view.displayDefaultValues();

				if (view.hasNVal()) {
					view.displayNFactorial(model.calculateFact(view.getNVal()));
				}

				if (view.hasRVal()) {
					view.displayRFactorial(model.calculateFact(view.getRVal()));
				}

				if (view.hasNVal() && view.hasRVal()) {
					view.displayPermutation(model.calculatePermutation(view.getNVal(), view.getRVal()));
					view.displayCombination(model.calculateCombination(view.getNVal(), view.getRVal()));
				}

				if (view.hasNVal() && view.hasNVals()) {
					view.displayIndistinct(model.calculateIndistinctPerm(view.getNVal(), view.getNVals()));
				}
			}
		}, PCView.Types.CALCULATE_PRESSED);
	}

}
