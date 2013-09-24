package me.nickpierson.StatisticsSolver.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nickpierson.StatisticsSolver.utils.BasicResult;

public class BasicModel {

	private int previousErrorIndex;

	public ArrayList<Double> convertInput(String input) {
		ArrayList<Double> result = new ArrayList<Double>();
		String[] numbers = input.split(",");
		for (int i = 0; i < numbers.length; i++) {
			if (numbers[i].length() == 0) {
				continue;
			}

			try {
				result.add(Double.valueOf(numbers[i]));
			} catch (NumberFormatException e) {
				previousErrorIndex = i;
				return null;
			}
		}

		if (result.size() == 0) {
			return null;
		} else {
			return result;
		}
	}

	public BasicResult calculateResults(List<Double> numberList) {
		BasicResult result = new BasicResult();
		result.size = numberList.size();
		result.sum = calculateSum(numberList);
		result.mean = result.sum / result.size;
		result.median = calculateMedian(numberList, result.size);
		result.mode = calculateMode(numberList);
		result.range = calculateRange(numberList);
		result.popVariance = calculatePopVariance(numberList, result.mean, result.size);
		result.sampleVariance = calculateSampleVariance(numberList, result.mean, result.size);
		result.popDeviation = Math.sqrt(result.popVariance);
		result.sampleDeviation = Math.sqrt(result.sampleVariance);
		return result;
	}

	private double calculateSum(List<Double> numberList) {
		double sum = 0;
		for (double num : numberList) {
			sum += num;
		}
		return sum;
	}

	private double calculateMedian(List<Double> numberList, int size) {
		Collections.sort(numberList);
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
		for (Map.Entry<Double, Integer> entry : freqs.entrySet()) {
			int freq = entry.getValue();
			if (freq > max) {
				max = freq;
				mode = entry.getKey();
			}
		}

		if (max == 1) {
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
		}

		for (double num : numberList) {
			if (num < min) {
				min = num;
			}
		}

		return max - min;
	}

	private double calculatePopVariance(List<Double> numberList, double average, int size) {
		double numerator = calculateVarianceNumerator(numberList, average);
		return numerator / size;
	}

	private double calculateSampleVariance(List<Double> numberList, double average, int size) {
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

	public int getPreviousErrorIndex() {
		return previousErrorIndex;
	}

}
