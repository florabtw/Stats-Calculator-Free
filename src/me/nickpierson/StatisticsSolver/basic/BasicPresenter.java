package me.nickpierson.StatisticsSolver.basic;

import java.util.ArrayList;

import com.thecellutioncenter.mvplib.ActionListener;

public class BasicPresenter {

	public static void create(final BasicModel model, final BasicView view) {
		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				view.showKeypad();
			}
		}, BasicView.Types.EDITTEXT_CLICKED);

		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				ArrayList<Double> input = model.convertInput(view.getInput());
				if(input == null){
					view.showToast("Could not parse input. Please fix item #" + model.getPreviousErrorIndex());
				} else {
					view.showResults(model.calculateResults(input));
				}
			}
		}, BasicView.Types.DONE_CLICKED);
	}

}
