package me.nickpierson.StatsCalculator.basic;

import java.util.ArrayList;
import java.util.HashMap;

import me.nickpierson.StatsCalculator.utils.MyConstants;

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

		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				view.showSaveListPopup();
			}
		}, BasicView.Types.SAVE_LIST);

		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				model.saveList(view.getInput());
			}
		}, BasicView.Types.SAVE_CLICKED);

		model.addListener(new ActionListener() {

			@Override
			public void fire() {
				view.showToast(MyConstants.SAVE_SUCCESSFUL);
			}
		}, BasicModel.Types.SAVE_SUCCESSFUL);

		model.addListener(new ActionListener() {

			@Override
			public void fire() {
				view.showToast(MyConstants.SAVE_FAILED);
			}
		}, BasicModel.Types.SAVE_FAILED);
	}

	private static void showEmptyResults(final BasicModel model, final BasicView view) {
		view.showResults(model.getEmptyResults());
	}

}
