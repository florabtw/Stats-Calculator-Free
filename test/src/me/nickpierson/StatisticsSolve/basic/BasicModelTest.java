package me.nickpierson.StatisticsSolve.basic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import me.nickpierson.StatisticsSolver.basic.BasicModel;
import me.nickpierson.StatisticsSolver.utils.BasicResult;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class BasicModelTest {

	public BasicModel model;
	private double DELTA = .000001;

	@Before
	public void setup() {
		model = new BasicModel();
	}

	@Test
	public void convertInputReturnsCorrectListOfIntegers_WithCorrectInput() {
		String input = "45,89,.334,99.999999,0,-.12,45.00001";
		ArrayList<Double> expectedReturn = makeValidList(45,89,.334,99.999999,0,-.12,45.00001);

		assertEquals(expectedReturn, model.convertInput(input));
	}

	@Test
	public void convertInputReturnsNull_WhenMoreThanOnePeriodIsInOneNumber() {
		String input = "35.8,34.5.6,71";
		String input1 = "37.89,34..8,2";
		String input2 = "90.1,23,...9";

		assertEquals(null, model.convertInput(input));
		assertEquals(null, model.convertInput(input1));
		assertEquals(null, model.convertInput(input2));
	}

	@Test
	public void convertInputReturnsNull_WhenNegativeSignIsMisplaced() {
		String input = "-45.6,98.1,9-1";
		String input1 = "-32.1,11,.-1";
		String input2 = "34,82,95-";

		assertEquals(null, model.convertInput(input));
		assertEquals(null, model.convertInput(input1));
		assertEquals(null, model.convertInput(input2));
	}

	@Test
	public void calculateResults_CalculatesCorrectResult() {
		ArrayList<Double> sampleInput = makeValidList(45, 68.1, 29.4, -54, -.19, 3.0001);
		BasicResult expectedResult = new BasicResult();
		expectedResult.sum = 91.3101;
		expectedResult.size = 6;
		expectedResult.mean = 15.21835;
		expectedResult.median = 16.20005;
		expectedResult.mode = null;
		expectedResult.range = 122.1;
		expectedResult.popVariance = 1510.402939;
		expectedResult.sampleVariance = 1812.483527;
		expectedResult.popDeviation = 38.863902;
		expectedResult.sampleDeviation = 42.573272;
		
		BasicResult actualResult = model.calculateResults(sampleInput);
		
		assertEquals(expectedResult.sum, actualResult.sum, DELTA);
		assertEquals(expectedResult.size, actualResult.size);
		assertEquals(expectedResult.mean, actualResult.mean, DELTA);
		assertEquals(expectedResult.median, actualResult.median, DELTA);
		assertEquals(expectedResult.mode, actualResult.mode);
		assertEquals(expectedResult.range, actualResult.range, DELTA);
		assertEquals(expectedResult.popVariance, actualResult.popVariance, DELTA);
		assertEquals(expectedResult.sampleVariance, actualResult.sampleVariance, DELTA);
		assertEquals(expectedResult.popDeviation, expectedResult.popDeviation, DELTA);
		assertEquals(expectedResult.sampleDeviation, actualResult.sampleDeviation, DELTA);
	}

	private ArrayList<Double> makeValidList(double... args) {
		ArrayList<Double> validList = new ArrayList<Double>();
		for (double val : args) {
			validList.add(val);
		}
		return validList;
	}

}
