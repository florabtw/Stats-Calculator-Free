package me.nickpierson.StatisticsSolver.pc;

import java.util.ArrayList;
import java.util.HashMap;

import me.nickpierson.StatisticsSolver.utils.BaseModel;

public class PCModel extends BaseModel {

	HashMap<Long, Long> factCache = new HashMap<Long, Long>();

	public long calculateFact(long num) {
		Long fact = factCache.get(num);
		if (fact != null) {
			return fact;
		}
		
		if (num < 0) {
			return 0;
		} else if (num <= 1) {
			return 1;
		}

		long answer = num * calculateFact(num - 1);
		factCache.put(num, answer);
		return answer;
	}

	public long calculatePermutation(long n, long r) {
		if (n < r) {
			return 0;
		}

		return calculateFact(n) / calculateFact(n - r);
	}

	public long calculateCombination(long n, long r) {
		if (n < r) {
			return 0;
		}

		return calculateFact(n) / (calculateFact(r) * calculateFact(n - r));
	}

	public long calculateIndistinctPerm(long nVal, String input) {
		ArrayList<Double> nVals = convertInput(input);

		if (isInvalidInput(nVal, nVals)) {
			return 0;
		}

		long denominator = 1;
		for (double val : nVals) {
			denominator *= calculateFact((long) val);
		}

		return calculateFact(nVal) / denominator;
	}

	private boolean isInvalidInput(long nVal, ArrayList<Double> nVals) {
		long sum = 0;
		for (double val : nVals) {
			sum += val;
		}

		if (sum > nVal) {
			return true;
		} else {
			return false;
		}
	}

}
