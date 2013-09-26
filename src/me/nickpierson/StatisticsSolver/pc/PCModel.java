package me.nickpierson.StatisticsSolver.pc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import me.nickpierson.StatisticsSolver.utils.BaseModel;
import android.util.SparseArray;

public class PCModel extends BaseModel {
	
	SparseArray<BigInteger> factCache = new SparseArray<BigInteger>();

	public BigInteger calculateFact(int num) {
		BigInteger cachedValue = factCache.get(num);
		if(cachedValue != null){
			return cachedValue;
		}
		
		if (num > 1000) {
			/* TODO: Approximate */
		} else if (num < 0) {
			return BigInteger.valueOf(0);
		}

		BigInteger answer = BigInteger.valueOf(1);

		for (int i = num; i > 1; i--) {
			answer = answer.multiply(BigInteger.valueOf(i));
		}

		factCache.put(num, answer);
		return answer;
	}

	private BigInteger approximateFactorial(int num) {
		return BigDecimal.valueOf((Math.sqrt(Math.PI * 2 * num) * Math.pow((num / Math.E), num))).toBigInteger();
	}

	public BigInteger calculatePermutation(int n, int r) {
		if (n < r) {
			return BigInteger.valueOf(0);
		}

		return calculateFact(n).divide(calculateFact(n - r));
	}

	public BigInteger calculateCombination(int n, int r) {
		if (n < r) {
			return BigInteger.valueOf(0);
		}

		return calculateFact(n).divide(calculateFact(r).multiply(calculateFact(n - r)));
	}

	public BigInteger calculateIndistinctPerm(int nVal, String input) {
		ArrayList<Double> nVals = convertInput(input);

		if (!isValidInput(nVal, nVals)) {
			return BigInteger.valueOf(0);
		}

		BigInteger denominator = BigInteger.valueOf(1);
		for (double val : nVals) {
			denominator = denominator.multiply(calculateFact((int) val));
		}

		return calculateFact(nVal).divide(denominator);
	}

	private boolean isValidInput(long nVal, ArrayList<Double> nVals) {
		long sum = 0;
		for (double val : nVals) {
			sum += val;

			if (val != (int) Integer.valueOf((int) val)) {
				return false;
			}
		}

		if (sum > nVal) {
			return false;
		} else {
			return true;
		}
	}

}
