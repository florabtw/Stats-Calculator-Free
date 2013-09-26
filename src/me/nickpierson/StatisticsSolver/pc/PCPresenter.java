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
					int n = view.getNVal();
					view.displayNFactorial(model.calculateFact(n));

					if (view.hasRVal()) {
						int r = view.getRVal();
						view.displayPermutation(model.calculatePermutation(n, r));
						view.displayCombination(model.calculateCombination(n, r));
					}

					if (view.hasNVals()) {
						view.displayIndistinct(model.calculateIndistinctPerm(n, view.getNVals()));
					}
				}

				if (view.hasRVal()) {
					view.displayRFactorial(model.calculateFact(view.getRVal()));
				}
			}
		}, PCView.Types.CALCULATE_PRESSED);
	}
}
