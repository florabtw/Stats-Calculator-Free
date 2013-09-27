package me.nickpierson.StatisticsSolver.pc;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class PCModelTest {

	private PCModel model;

	@Before
	public void setup() {
		model = new PCModel();
	}

	@Test
	public void factorialReturnsCorrectValue() {
		assertEquals(BigInteger.valueOf(120), model.calculateFact(5));
		assertEquals(BigInteger.valueOf(479001600), model.calculateFact(12));
		assertEquals(BigInteger.valueOf(1), model.calculateFact(1));
		assertEquals(BigInteger.valueOf(1), model.calculateFact(0));
		assertEquals(BigInteger.valueOf(0), model.calculateFact(-1));

		/* TODO: Test Approximate numbers */
	}

	@Test
	public void permutationReturnCorrectValue() {
		assertEquals(BigInteger.valueOf(60), model.calculatePermutation(5, 3));
		assertEquals(BigInteger.valueOf(151200), model.calculatePermutation(10, 6));
		assertEquals(BigInteger.valueOf(5040), model.calculatePermutation(7, 7));
		assertEquals(BigInteger.valueOf(5), model.calculatePermutation(5, 1));
		assertEquals(BigInteger.valueOf(1), model.calculatePermutation(6, 0));
		assertEquals(BigInteger.valueOf(0), model.calculatePermutation(3, 4));

		/* TODO: Test Approximate numbers */
	}

	@Test
	public void combinationReturnsCorrectValue() {
		assertEquals(BigInteger.valueOf(10), model.calculateCombination(5, 3));
		assertEquals(BigInteger.valueOf(210), model.calculateCombination(10, 6));
		assertEquals(BigInteger.valueOf(1), model.calculateCombination(7, 7));
		assertEquals(BigInteger.valueOf(5), model.calculateCombination(5, 1));
		assertEquals(BigInteger.valueOf(1), model.calculateCombination(6, 0));
		assertEquals(BigInteger.valueOf(0), model.calculateCombination(3, 4));

		/* TODO: Test Approximate numbers */
	}

	@Test
	public void indistinctPermutationReturnsCorrectValue() {
		assertEquals(BigInteger.valueOf(60), model.calculateIndistinctPerm(6, "3,2"));
		assertEquals(BigInteger.valueOf(2), model.calculateIndistinctPerm(2, "1,1,"));
		assertEquals(BigInteger.valueOf(1), model.calculateIndistinctPerm(1, "1"));
		assertEquals(BigInteger.valueOf(120), model.calculateIndistinctPerm(5, "0"));
		assertEquals(BigInteger.valueOf(0), model.calculateIndistinctPerm(5, "3,3"));

		/* TODO: Test Approximate numbers */

	}

	@Test
	public void willOverFlowReturnsCorrectValues() {
		>>>>>>>>>>>
	}

}
