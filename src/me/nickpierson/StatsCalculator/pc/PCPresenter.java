package me.nickpierson.StatsCalculator.pc;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import me.nickpierson.StatsCalculator.utils.MyConstants;

import com.thecellutioncenter.mvplib.ActionListener;
import com.thecellutioncenter.mvplib.DataActionListener;

public class PCPresenter {

	public static void create(final PCModel model, final PCView view) {
		view.addListener(new ActionListener() {

			@Override
			public void fire() {
				view.showDefaultValues();
				model.validateInput(view.getNVal(), view.getRVal(), view.getNVals());
			}
		}, PCView.Types.CALCULATE_PRESSED);

		model.addListener(new DataActionListener() {

			@Override
			public void fire(HashMap<Enum<?>, ?> data) {
				int n = (Integer) data.get(PCModel.Keys.N_VALUE);

				setNFactorial(model, view, n);
				view.setRFactorial(MyConstants.NOT_APPLICABLE);
				view.setPermutation(MyConstants.NOT_APPLICABLE);
				view.setCombination(MyConstants.NOT_APPLICABLE);
				view.setIndistinct(MyConstants.NOT_APPLICABLE);
			}
		}, PCModel.Types.ONLY_VALID_N);

		model.addListener(new DataActionListener() {

			@Override
			public void fire(HashMap<Enum<?>, ?> data) {
				int r = (Integer) data.get(PCModel.Keys.R_VALUE);

				view.setNFactorial(MyConstants.NOT_APPLICABLE);
				setRFactorial(model, view, r);
				view.setPermutation(MyConstants.NOT_APPLICABLE);
				view.setCombination(MyConstants.NOT_APPLICABLE);
				view.setIndistinct(MyConstants.NOT_APPLICABLE);
			}
		}, PCModel.Types.ONLY_VALID_R);

		model.addListener(new DataActionListener() {

			@Override
			public void fire(HashMap<Enum<?>, ?> data) {
				int n = (Integer) data.get(PCModel.Keys.N_VALUE);
				int r = (Integer) data.get(PCModel.Keys.R_VALUE);

				setNFactorial(model, view, n);
				setRFactorial(model, view, r);
				setPermAndComb(model, view, n, r);
				view.setIndistinct(MyConstants.NOT_APPLICABLE);
			}
		}, PCModel.Types.VALID_N_AND_R);

		model.addListener(new DataActionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void fire(HashMap<Enum<?>, ?> data) {
				int n = (Integer) data.get(PCModel.Keys.N_VALUE);
				ArrayList<Integer> nVals = (ArrayList<Integer>) data.get(PCModel.Keys.N_VALUES);

				setNFactorial(model, view, n);
				view.setRFactorial(MyConstants.NOT_APPLICABLE);
				view.setPermutation(MyConstants.NOT_APPLICABLE);
				view.setCombination(MyConstants.NOT_APPLICABLE);
				setIndistinct(model, view, n, nVals);
			}
		}, PCModel.Types.VALID_N_AND_NS);

		model.addListener(new DataActionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void fire(HashMap<Enum<?>, ?> data) {
				int n = (Integer) data.get(PCModel.Keys.N_VALUE);
				int r = (Integer) data.get(PCModel.Keys.R_VALUE);
				ArrayList<Integer> nVals = (ArrayList<Integer>) data.get(PCModel.Keys.N_VALUES);

				setNFactorial(model, view, n);
				setRFactorial(model, view, r);
				setPermAndComb(model, view, n, r);
				setIndistinct(model, view, n, nVals);
			}
		}, PCModel.Types.ALL_VALUES_VALID);

		model.addListener(new ActionListener() {

			@Override
			public void fire() {
				view.showToast(MyConstants.MESSAGE_INPUT_OVER_MAX);
			}
		}, PCModel.Types.INPUT_OVER_MAX_VALUE);
	}

	private static void setNFactorial(final PCModel model, final PCView view, int n) {
		view.setNFactorial(getFormattedNumber(model.calculateFact(n)));
	}

	private static void setRFactorial(final PCModel model, final PCView view, int r) {
		view.setRFactorial(getFormattedNumber(model.calculateFact(r)));
	}

	private static void setPermAndComb(final PCModel model, final PCView view, int n, int r) {
		view.setPermutation(getFormattedNumber(model.calculatePermutation(n, r)));
		view.setCombination(getFormattedNumber(model.calculateCombination(n, r)));
	}

	private static void setIndistinct(final PCModel model, final PCView view, int n, ArrayList<Integer> nVals) {
		view.setIndistinct(getFormattedNumber(model.calculateIndistinct(n, nVals)));
	}

	private static String getFormattedNumber(BigInteger number) {
		String stringValue;
		if (number.compareTo(BigInteger.valueOf(MyConstants.MAX_PLAIN_FORMAT)) == 1) {
			stringValue = new DecimalFormat(MyConstants.DECIMAL_FORMAT).format(number);
		} else {
			stringValue = number.toString();
		}
		return stringValue;
	}
}
