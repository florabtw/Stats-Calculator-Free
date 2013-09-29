package me.nickpierson.StatsCalculator.basic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import me.nickpierson.StatsCalculator.utils.MyConstants;
import android.app.Activity;

import com.thecellutioncenter.mvplib.DataActionHandler;

public class BasicModel extends DataActionHandler {

	private Activity activity;

	public enum Types {
		VALID_INPUT, INVALID_NUMBER, SAVE_SUCCESSFUL, SAVE_FAILED, LOAD_ERROR, DELETE_ERROR;
	}

	public enum Keys {
		INVALID_ITEM, VALIDATED_LIST;
	}

	public BasicModel(Activity activity) {
		this.activity = activity;
	}

	public LinkedHashMap<String, Double> getEmptyResults() {
		LinkedHashMap<String, Double> emptyMap = new LinkedHashMap<String, Double>();
		emptyMap.put(MyConstants.SIZE, 0.0);
		emptyMap.put(MyConstants.SUM, 0.0);
		emptyMap.put(MyConstants.ARITH_MEAN, 0.0);
		emptyMap.put(MyConstants.MEDIAN, 0.0);
		emptyMap.put(MyConstants.MODE, null);
		emptyMap.put(MyConstants.RANGE, 0.0);
		emptyMap.put(MyConstants.POP_VAR, 0.0);
		emptyMap.put(MyConstants.SAMPLE_VAR, 0.0);
		emptyMap.put(MyConstants.POP_DEV, 0.0);
		emptyMap.put(MyConstants.SAMPLE_DEV, 0.0);
		return emptyMap;
	}

	public void validateInput(String input) {
		HashMap<Enum<?>, Object> results = new HashMap<Enum<?>, Object>();

		if (input.length() == 0) {
			eventInvalid(results, 1);
			return;
		}

		String[] values = input.split(",");
		for (int i = 0; i < values.length; i++) {
			String currVal = values[i];

			if (currVal.length() == 0) {
				continue;
			} else if (currVal.contains("x")) {
				if (!isValidFreqItem(currVal)) {
					eventInvalid(results, i + 1);
					return;
				}
			} else if (!isValidDouble(currVal)) {
				eventInvalid(results, i + 1);
				return;
			}
		}

		results.put(Keys.VALIDATED_LIST, convertList(input));
		dataEvent(Types.VALID_INPUT, results);
	}

	private void eventInvalid(HashMap<Enum<?>, Object> results, int i) {
		results.put(Keys.INVALID_ITEM, i);
		dataEvent(Types.INVALID_NUMBER, results);
	}

	private boolean isValidFreqItem(String val) {
		String[] values = val.split("x");
		if (values.length != 2) {
			return false;
		}

		if (!isValidDouble(values[0]) || !isValidFrequency(values[1])) {
			return false;
		}

		return true;
	}

	private boolean isValidFrequency(String string) {
		if (!isValidInteger(string)) {
			return false;
		}

		int value = Integer.valueOf(string);
		if (value > MyConstants.MAX_FREQUENCY) {
			return false;
		}

		return true;
	}

	private boolean isValidInteger(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean isValidDouble(String string) {
		try {
			Double.valueOf(string);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private ArrayList<Double> convertList(String input) {
		ArrayList<Double> convertedList = new ArrayList<Double>();
		for (String value : input.split(",")) {
			if (value.length() == 0) {
				continue;
			} else if (value.contains("x")) {
				String[] freqItem = value.split("x");
				for (int i = 0; i < Integer.valueOf(freqItem[1]); i++) {
					convertedList.add(Double.valueOf(freqItem[0]));
				}
			} else {
				convertedList.add(Double.valueOf(value));
			}
		}

		return convertedList;
	}

	public LinkedHashMap<String, Double> calculateResults(List<Double> numberList) {
		Collections.sort(numberList);

		LinkedHashMap<String, Double> result = new LinkedHashMap<String, Double>();
		result.put(MyConstants.SIZE, (double) numberList.size());
		result.put(MyConstants.SUM, calculateSum(numberList));
		result.put(MyConstants.ARITH_MEAN, result.get(MyConstants.SUM) / result.get(MyConstants.SIZE));
		result.put(MyConstants.GEO_MEAN, calculateGeoMean(numberList));
		result.put(MyConstants.MEDIAN, calculateMedian(numberList, result.get(MyConstants.SIZE)));
		result.put(MyConstants.MODE, calculateMode(numberList));
		result.put(MyConstants.RANGE, calculateRange(numberList));
		result.put(MyConstants.POP_VAR, calculatePopVariance(numberList, result.get(MyConstants.ARITH_MEAN), result.get(MyConstants.SIZE)));
		result.put(MyConstants.SAMPLE_VAR, calculateSampleVariance(numberList, result.get(MyConstants.ARITH_MEAN), result.get(MyConstants.SIZE)));
		result.put(MyConstants.POP_DEV, Math.sqrt(result.get(MyConstants.POP_VAR)));
		result.put(MyConstants.SAMPLE_DEV, Math.sqrt(result.get(MyConstants.SAMPLE_VAR)));
		result.put(MyConstants.COEFF_VAR, result.get(MyConstants.SAMPLE_DEV) / result.get(MyConstants.ARITH_MEAN));
		result.put(MyConstants.SKEWNESS, calculateSkewness(numberList, result.get(MyConstants.ARITH_MEAN), result.get(MyConstants.POP_DEV)));
		result.put(MyConstants.KURTOSIS, calculateKurtosis(numberList, result.get(MyConstants.ARITH_MEAN), result.get(MyConstants.POP_DEV)));

		return result;
	}

	private Double calculateKurtosis(List<Double> numberList, double mean, double stdDev) {
		return calculateKurtOrSkew(4, numberList, mean, stdDev);
	}

	private Double calculateSkewness(List<Double> numberList, double mean, double stdDev) {
		return calculateKurtOrSkew(3, numberList, mean, stdDev);
	}

	private Double calculateKurtOrSkew(int power, List<Double> numberList, double mean, double stdDev) {
		double sum = 0;
		for (double number : numberList) {
			sum += Math.pow(number - mean, power);
		}

		double denom = Math.pow(stdDev, power) * (numberList.size());

		return sum / denom;
	}

	private Double calculateGeoMean(List<Double> numberList) {
		double value = 1;
		for (double number : numberList) {
			if (number < 0) {
				return Double.NaN;
			}

			value *= number;
		}

		return Math.pow(value, 1.0 / numberList.size());
	}

	private double calculateSum(List<Double> numberList) {
		double sum = 0;
		for (double num : numberList) {
			sum += num;
		}
		return sum;
	}

	private double calculateMedian(List<Double> numberList, double length) {
		int size = (int) length;

		if (size % 2 == 1) {
			int index = (int) Math.floor(size / 2.0);
			return numberList.get(index);
		} else {
			int half = size / 2;
			return (numberList.get(half - 1) + numberList.get(half)) / 2;
		}
	}

	private Double calculateMode(List<Double> numberList) {
		HashMap<Double, Integer> freqs = new HashMap<Double, Integer>();
		for (double num : numberList) {
			Integer freq = freqs.get(num);
			freqs.put(num, freq == null ? 1 : freq + 1);
		}

		double mode = 0;
		int max = 0;
		boolean isSimilarMax = true;
		for (Map.Entry<Double, Integer> entry : freqs.entrySet()) {
			int freq = entry.getValue();
			if (freq > max) {
				isSimilarMax = false;
				max = freq;
				mode = entry.getKey();
			} else if (freq == max) {
				isSimilarMax = true;
			}
		}

		if (isSimilarMax) {
			return null;
		} else {
			return mode;
		}
	}

	private double calculateRange(List<Double> numberList) {
		double max = Double.MIN_VALUE, min = Double.MAX_VALUE;
		for (double num : numberList) {
			if (num > max) {
				max = num;
			}
			if (num < min) {
				min = num;
			}
		}

		return max - min;
	}

	private double calculatePopVariance(List<Double> numberList, double average, double size) {
		double numerator = calculateVarianceNumerator(numberList, average);
		return numerator / size;
	}

	private double calculateSampleVariance(List<Double> numberList, double average, double size) {
		double numerator = calculateVarianceNumerator(numberList, average);
		return numerator / (size - 1);
	}

	private double calculateVarianceNumerator(List<Double> numberList, double average) {
		double sum = 0;
		for (double num : numberList) {
			sum += Math.pow(num - average, 2);
		}
		return sum;
	}

	public void saveList(String name, String input) {
		File outputFile = new File(activity.getFilesDir(), name);
		if (outputFile.exists()) {
			event(Types.SAVE_FAILED);
			return;
		}

		try {
			FileOutputStream output = new FileOutputStream(outputFile);
			output.write(input.getBytes());
			output.close();
		} catch (Exception e) {
			event(Types.SAVE_FAILED);
			return;
		}

		event(Types.SAVE_SUCCESSFUL);
	}

	public String[] getSavedLists() {
		File internalDir = activity.getFilesDir();
		return internalDir.list();
	}

	public String loadList(String listName) {
		File listFile = new File(activity.getFilesDir(), listName);

		StringBuilder list = new StringBuilder();
		try {
			FileInputStream input = new FileInputStream(listFile);
			byte[] bytes = new byte[input.available()];

			input.read(bytes);

			for (byte b : bytes) {
				list.append((char) b);
			}

			input.close();
		} catch (IOException e) {
			event(Types.LOAD_ERROR);
			e.printStackTrace();
		}

		return list.toString();
	}

	public void deleteList(String listName) {
		File file = new File(activity.getFilesDir(), listName);
		boolean isDeleted = file.delete();
		if (!isDeleted) {
			event(Types.DELETE_ERROR);
		}
	}
}
