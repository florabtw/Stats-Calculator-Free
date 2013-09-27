package me.nickpierson.StatisticsSolver.pc;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.nickpierson.StatisticsSolver.utils.MyConstants;
import android.util.SparseArray;

import com.thecellutioncenter.mvplib.DataActionHandler;

public class PCModel extends DataActionHandler {

	public enum Types {
		ONLY_VALID_N, ONLY_VALID_R, VALID_N_AND_R, VALID_N_AND_NS, ALL_VALUES_VALID, INPUT_OVER_MAX_VALUE;
	}

	public enum Keys {
		N_VALUE, R_VALUE, N_VALUES;
	}

	SparseArray<BigInteger> factCache = new SparseArray<BigInteger>();

	public void validateInput(String nVal, String rVal, String nVals) {
		HashMap<Enum<?>, Object> returnValues = new HashMap<Enum<?>, Object>();

		if (isValid(nVal) && isValid(rVal)) {
			int n = convert(nVal);
			int r = convert(rVal);

			returnValues.put(Keys.N_VALUE, n);
			returnValues.put(Keys.R_VALUE, r);

			if (isValidList(nVals, n)) {
				ArrayList<Integer> nList = new ArrayList<Integer>();
				nList = convertList(nVals);
				returnValues.put(Keys.N_VALUES, nList);
				dataEvent(Types.ALL_VALUES_VALID, returnValues);
			} else {
				dataEvent(Types.VALID_N_AND_R, returnValues);
			}
		} else if (isValid(nVal)) {
			int n = convert(nVal);

			returnValues.put(Keys.N_VALUE, n);

			if (isValidList(nVals, n)) {
				addNList(nVals, returnValues);
				dataEvent(Types.VALID_N_AND_NS, returnValues);
			} else {
				dataEvent(Types.ONLY_VALID_N, returnValues);
			}
		} else if (isValid(rVal)) {
			int r = convert(rVal);
			returnValues.put(Keys.R_VALUE, r);
			dataEvent(Types.ONLY_VALID_R, returnValues);
		}
	}

	private void addNList(String nVals, HashMap<Enum<?>, Object> returnValues) {
		ArrayList<Integer> nList = new ArrayList<Integer>();
		nList = convertList(nVals);
		returnValues.put(Keys.N_VALUES, nList);
	}

	private ArrayList<Integer> convertList(String string) {
		ArrayList<Integer> result = new ArrayList<Integer>();

		String[] nVals = string.split(",");
		for (String val : nVals) {
			result.add(convert(val));
		}

		return result;
	}

	private int convert(String nVal) {
		return Integer.valueOf(nVal);
	}

	private boolean isValidList(String list, int n) {
		String[] nVals = list.split(",");
		int sum = 0;

		for (String val : nVals) {
			if (!isValid(val)) {
				return false;
			}
			sum += convert(val);
		}

		if (sum > n) {
			return false;
		}

		return true;
	}

	private boolean isValid(String string) {
		return string.length() != 0 && !isTooBig(string);
	}

	private boolean isTooBig(String input) {
		try {
			int n = Integer.parseInt(input);

			if (n > MyConstants.PC_MAX_INPUT) {
				event(Types.INPUT_OVER_MAX_VALUE);
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException e) {
			return true;
		}
	}

	public BigInteger calculateFact(int num) {
		BigInteger cachedValue = factCache.get(num);
		if (cachedValue != null) {
			return cachedValue;
		}

		if (num < 0) {
			return BigInteger.ZERO;
		}

		BigInteger answer = BigInteger.ONE;

		for (int i = num; i > 1; i--) {
			answer = answer.multiply(BigInteger.valueOf(i));
		}

		factCache.put(num, answer);
		return answer;
	}

	public BigInteger calculatePermutation(int n, int r) {
		if (n < r) {
			return BigInteger.ZERO;
		}

		return calculateFact(n).divide(calculateFact(n - r));
	}

	public BigInteger calculateCombination(int n, int r) {
		if (n < r) {
			return BigInteger.ZERO;
		}

		return calculateFact(n).divide(calculateFact(r).multiply(calculateFact(n - r)));
	}

	public BigInteger calculateIndistinct(int n, List<Integer> nVals) {
		BigInteger denom = BigInteger.ONE;
		for (int val : nVals) {
			denom = denom.multiply(calculateFact(val));
		}

		return calculateFact(n).divide(denom);
	}
}
