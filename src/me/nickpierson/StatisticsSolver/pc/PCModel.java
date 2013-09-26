package me.nickpierson.StatisticsSolver.pc;

import java.util.ArrayList;

import me.nickpierson.StatisticsSolver.utils.BaseModel;

public class PCModel extends BaseModel {

	/* TODO: Cache & test large values */
	public int calculateFact(int num) {
		if (num < 0) {
			return 0;
		}

		int total = 1;
		for (int i = num; i > 1; i--) {
			total *= i;
		}

		return total;
	}

	public int calculatePermutation(int n, int r) {
		if (n < r) {
			return 0;
		}

		return calculateFact(n) / calculateFact(n - r);
	}

	public int calculateCombination(int n, int r) {
		if (n < r) {
			return 0;
		}

		return calculateFact(n) / (calculateFact(r) * calculateFact(n - r));
	}

	public int calculateIndistinctPerm(int nVal, String input) {
		ArrayList<Double> nVals = convertInput(input);
		
		if (isInvalidInput(nVal, nVals)) {
			return 0;
		}
		
		int denominator = 1;
		for (double val : nVals) {
			denominator *= calculateFact((int) val);
		}

		return calculateFact(nVal) / denominator;
	}

	private boolean isInvalidInput(int nVal, ArrayList<Double> nVals) {
		int sum = 0;
		for(double val : nVals){
			sum += val;
		}
		
		if(sum > nVal){
			return true;
		} else {
			return false;
		}
	}

}
