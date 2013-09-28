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
		}, BasicView.Types.MENU_SAVE);

		view.addListener(new DataActionListener() {

			@Override
			public void fire(HashMap<Enum<?>, ?> data) {
				model.saveList((String) data.get(BasicView.Keys.LIST_NAME), view.getInput());
			}
		}, BasicView.Types.SAVE_LIST);

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

		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				String[] lists = model.getSavedLists();
				if (lists != null) {
					view.showLoadListPopup(model.getSavedLists());
				}
			}
		}, BasicView.Types.MENU_LOAD_OR_DELETE);

		view.addListener(new DataActionListener() {

			@Override
			public void fire(HashMap<Enum<?>, ?> data) {
				String listName = (String) data.get(BasicView.Keys.LIST_NAME);
				view.setInputText(model.loadList(listName));
			}
		}, BasicView.Types.LOAD_LIST);

		model.addListener(new ActionListener() {

			@Override
			public void fire() {
				view.showToast(MyConstants.LIST_LOAD_ERROR);
			}
		}, BasicModel.Types.LOAD_ERROR);

		view.addListener(new DataActionListener() {

			@Override
			public void fire(HashMap<Enum<?>, ?> data) {
				String listName = (String) data.get(BasicView.Keys.LIST_NAME);
				model.deleteList(listName);
			}
		}, BasicView.Types.DELETE_LIST);

		model.addListener(new ActionListener() {

			@Override
			public void fire() {
				view.showToast(MyConstants.LIST_DELETE_ERROR);
			}
		}, BasicModel.Types.DELETE_ERROR);
	}

	private static void showEmptyResults(final BasicModel model, final BasicView view) {
		view.showResults(model.getEmptyResults());
	}

}
