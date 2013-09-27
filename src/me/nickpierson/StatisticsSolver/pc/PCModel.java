package me.nickpierson.StatisticsSolver.pc;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import android.util.SparseArray;

import com.thecellutioncenter.mvplib.DataActionHandler;

public class PCModel extends DataActionHandler {

	public enum Types {
		ONLY_VALID_N, ONLY_VALID_R, VALID_N_AND_R, VALID_N_AND_NS, ALL_VALUES_VALID;
	}

	public enum Keys {
		N_VALUE, R_VALUE, N_VALUES;
	}

	SparseArray<BigInteger> factCache = new SparseArray<BigInteger>();

	public void validateInput(String nVal, String rVal, String nVals) {

	}

	public BigInteger calculateFact(int num) {
		BigInteger cachedValue = factCache.get(num);
		if (cachedValue != null) {
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

	public BigInteger calculateIndistinct(int n, List<Integer> nVals) {
		return BigInteger.valueOf(0);
	}

	private boolean isValidInput(int nVal, ArrayList<Double> nVals) {
		int sum = 0;
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

	public boolean willOverflow(String input) {
		double dblVal = Double.valueOf(input);
		int intVal = Integer.valueOf(input);

		if (dblVal != intVal) {
			return false;
		} else {
			return true;
		}
	}
}
