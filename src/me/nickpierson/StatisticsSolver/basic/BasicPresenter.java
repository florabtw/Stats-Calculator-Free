package me.nickpierson.StatisticsSolver.basic;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.thecellutioncenter.mvplib.ActionListener;
import com.thecellutioncenter.mvplib.DataActionListener;

public class BasicPresenter {

	public static void create(final BasicModel model, final BasicView view) {
		showEmptyResults(model, view);

		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				if (!view.isKeyPadVisible()) {
					view.showKeypad();
				}
			}
		}, BasicView.Types.EDITTEXT_CLICKED);

		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				model.validateInput(view.getInput());
			}
		}, BasicView.Types.DONE_CLICKED);

		model.addListener(new DataActionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void fire(HashMap<Enum<?>, ?> data) {
				view.showResults((LinkedHashMap<String, Double>) data.get(BasicModel.Keys.RESULTS));
			}
		}, BasicModel.Types.VALID_INPUT);

		model.addListener(new DataActionListener() {

			@Override
			public void fire(HashMap<Enum<?>, ?> data) {
				showEmptyResults(model, view);
				view.showNumberErrorToast((Integer) data.get(BasicModel.Keys.INVALID_ITEM));
			}
		}, BasicModel.Types.INVALID_NUMBER);

		model.addListener(new DataActionListener() {

			@Override
			public void fire(HashMap<Enum<?>, ?> data) {
				showEmptyResults(model, view);
				view.showFrequencyErrorToast((Integer) data.get(BasicModel.Keys.INVALID_ITEM));
			}
		}, BasicModel.Types.INVALID_FREQUENCY);
	}

	private static void showEmptyResults(final BasicModel model, final BasicView view) {
		view.showResults(model.getEmptyResults());
	}

}
