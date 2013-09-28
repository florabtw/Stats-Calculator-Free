package me.nickpierson.StatisticsSolver.basic;

import java.util.ArrayList;
import java.util.HashMap;

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
				ArrayList<Double> validList = (ArrayList<Double>) data.get(BasicModel.Keys.VALIDATED_LIST);
				view.showResults(model.calculateResults(validList));
			}
		}, BasicModel.Types.VALID_INPUT);

		model.addListener(new DataActionListener() {

			@Override
			public void fire(HashMap<Enum<?>, ?> data) {
				showEmptyResults(model, view);
				view.showErrorToast((Integer) data.get(BasicModel.Keys.INVALID_ITEM));
			}
		}, BasicModel.Types.INVALID_NUMBER);
	}

	private static void showEmptyResults(final BasicModel model, final BasicView view) {
		view.showResults(model.getEmptyResults());
	}

}
