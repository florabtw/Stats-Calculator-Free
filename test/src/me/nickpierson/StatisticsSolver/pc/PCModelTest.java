package me.nickpierson.StatisticsSolver.pc;

import static org.junit.Assert.assertEquals;

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
		assertEquals(120, model.calculateFact(5));
		assertEquals(479001600, model.calculateFact(12));
		assertEquals(1, model.calculateFact(1));
		assertEquals(1, model.calculateFact(0));
		assertEquals(0, model.calculateFact(-1));
	}

	@Test
	public void permutationReturnCorrectValue() {
		assertEquals(60, model.calculatePermutation(5, 3));
		assertEquals(151200, model.calculatePermutation(10, 6));
		assertEquals(5040, model.calculatePermutation(7, 7));
		assertEquals(5, model.calculatePermutation(5, 1));
		assertEquals(1, model.calculatePermutation(6, 0));
		assertEquals(0, model.calculatePermutation(3, 4));
	}

	@Test
	public void combinationReturnsCorrectValue() {
		assertEquals(10, model.calculateCombination(5, 3));
		assertEquals(210, model.calculateCombination(10, 6));
		assertEquals(1, model.calculateCombination(7, 7));
		assertEquals(5, model.calculateCombination(5, 1));
		assertEquals(1, model.calculateCombination(6, 0));
		assertEquals(0, model.calculateCombination(3, 4));
	}

	@Test
	public void convertStringReturnsCorrectValues() {

	}

	@Test
	public void indistinctPermutationReturnsCorrectValue() {
		assertEquals(60, model.calculateIndistinctPerm(6, "3,2"));
		assertEquals(2, model.calculateIndistinctPerm(2, "1,1"));
		assertEquals(1, model.calculateIndistinctPerm(1, "1"));
		assertEquals(120, model.calculateIndistinctPerm(5, "0"));
		assertEquals(0, model.calculateIndistinctPerm(5, "3,3"));
	}

}
