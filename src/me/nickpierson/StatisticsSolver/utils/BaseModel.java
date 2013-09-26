package me.nickpierson.StatisticsSolver.utils;

import java.util.ArrayList;

public abstract class BaseModel {

	private int previousErrorIndex;

	public ArrayList<Double> convertInput(String input) {
		ArrayList<Double> result = new ArrayList<Double>();
		String[] numbers = input.split(",");
		for (int i = 0; i < numbers.length; i++) {
			if (numbers[i].length() == 0) {
				continue;
			}

			if (hasMultiplier(numbers[i]) && isWellFormed(numbers[i])) {
				double num = getNumber(numbers[i]);
				int multiplier = getMultiplier(numbers[i]);

				for (int j = 0; j < multiplier; j++) {
					result.add(num);
				}
			} else if (isWellFormed(numbers[i])) {
				result.add(Double.valueOf(numbers[i]));
			} else {
				previousErrorIndex = i;
				return null;
			}
		}

		if (result.size() == 0) {
			previousErrorIndex = 0;
			return null;
		} else {
			return result;
		}
	}

	private boolean isWellFormed(String string) {
		if (hasMultiplier(string)) {
			String[] values = string.split("x");

			if (values.length != 2 || values[0].length() < 1 || values[1].length() < 1) {
				return false;
			}

			try {
				Double.valueOf(values[0]);
				Integer.valueOf(values[1]);
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			try {
				Double.valueOf(string);
			} catch (NumberFormatException e) {
				return false;
			}
		}

		return true;
	}

	private boolean hasMultiplier(String string) {
		return string.contains("x");
	}

	private double getNumber(String value) {
		return Double.valueOf(value.substring(0, value.indexOf('x')));
	}

	private int getMultiplier(String value) {
		return Integer.valueOf(value.substring(value.indexOf('x') + 1, value.length()));
	}

	public int getPreviousErrorIndex() {
		return previousErrorIndex;
	}

}