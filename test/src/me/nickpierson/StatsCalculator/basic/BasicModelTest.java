package me.nickpierson.StatsCalculator.basic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import me.nickpierson.StatsCalculator.utils.MyConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.app.Activity;

import com.thecellutioncenter.mvplib.ActionListener;
import com.thecellutioncenter.mvplib.DataActionListener;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class BasicModelTest {

	public BasicModel model;
	private double DELTA = .000001;

	private DataActionListener validDataListener;
	private DataActionListener invalidDataListener;
	private ActionListener validListener;
	private ActionListener invalidListener;
	private Activity activity;

	@Before
	public void setup() {
		activity = mock(Activity.class);
		model = new BasicModel(activity);

		validDataListener = mock(DataActionListener.class);
		invalidDataListener = mock(DataActionListener.class);
		validListener = mock(ActionListener.class);
		invalidListener = mock(ActionListener.class);
	}

	@Test
	public void modelReturnsEmptyHashMapOnRequest() {
		LinkedHashMap<String, Double> emptyMap = model.getEmptyResults();
		assertEquals(emptyMap.get(MyConstants.SIZE), 0.0, DELTA);
		assertEquals(emptyMap.get(MyConstants.SUM), 0.0, DELTA);
		assertEquals(emptyMap.get(MyConstants.MEAN), 0.0, DELTA);
		assertEquals(emptyMap.get(MyConstants.MEDIAN), 0.0, DELTA);
		assertEquals(emptyMap.get(MyConstants.MODE), null);
		assertEquals(emptyMap.get(MyConstants.RANGE), 0.0, DELTA);
		assertEquals(emptyMap.get(MyConstants.POP_VAR), 0.0, DELTA);
		assertEquals(emptyMap.get(MyConstants.SAMPLE_VAR), 0.0, DELTA);
		assertEquals(emptyMap.get(MyConstants.POP_DEV), 0.0, DELTA);
		assertEquals(emptyMap.get(MyConstants.SAMPLE_DEV), 0.0, DELTA);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void validateInputNotifiesCorrectly_GivenValidInput() {
		HashMap<Enum<?>, ArrayList<Double>> validMap1 = new HashMap<Enum<?>, ArrayList<Double>>();
		HashMap<Enum<?>, ArrayList<Double>> validMap2 = new HashMap<Enum<?>, ArrayList<Double>>();
		String validInput1 = "500,30x3,59.0233";
		String validInput2 = "55.5,,31.3x2,-3,.2,-.23";
		String validInput3 = "60.1123x100000";
		ArrayList<Double> validList1 = makeValidList(500, 30, 30, 30, 59.0233);
		ArrayList<Double> validList2 = makeValidList(55.5, 31.3, 31.3, -3, .2, -.23);
		validMap1.put(BasicModel.Keys.VALIDATED_LIST, validList1);
		validMap2.put(BasicModel.Keys.VALIDATED_LIST, validList2);
		addAllListeners();

		model.validateInput(validInput1);
		model.validateInput(validInput2);
		model.validateInput(validInput3);

		verify(validDataListener).fire(validMap1);
		verify(validDataListener).fire(validMap2);
		verify(validDataListener, times(3)).fire((HashMap<Enum<?>, ?>) any(Object.class));
		verify(invalidDataListener, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void validateInputNotifiesCorrectly_GivenInvalidInput() {
		HashMap<Enum<?>, Integer> invalidMapFirstPos = new HashMap<Enum<?>, Integer>();
		HashMap<Enum<?>, Integer> invalidMapThirdPos = new HashMap<Enum<?>, Integer>();
		String invalidInputFirstPos1 = "";
		String invalidInputFirstPos2 = "23x1.5";
		String invalidInputFirstPos3 = "23x100001";
		String invalidInputFirstPos4 = "x5,27";
		String invalidInputFirstPos5 = "5x,27";
		String invalidInputThirdPos1 = "23,52x2,56..8,9";
		String invalidInputThirdPos2 = "23,25.6,23..5x2,7";
		String invalidInputThirdPos3 = "23,25.6,25-5,7";
		String invalidInputThirdPos4 = "23,25.6,25xx5,7";
		invalidMapFirstPos.put(BasicModel.Keys.INVALID_ITEM, 1);
		invalidMapThirdPos.put(BasicModel.Keys.INVALID_ITEM, 3);
		addAllListeners();

		model.validateInput(invalidInputFirstPos1);
		model.validateInput(invalidInputFirstPos2);
		model.validateInput(invalidInputFirstPos3);
		model.validateInput(invalidInputFirstPos4);
		model.validateInput(invalidInputFirstPos5);
		model.validateInput(invalidInputThirdPos1);
		model.validateInput(invalidInputThirdPos2);
		model.validateInput(invalidInputThirdPos3);
		model.validateInput(invalidInputThirdPos4);

		verify(invalidDataListener, times(5)).fire(invalidMapFirstPos);
		verify(invalidDataListener, times(4)).fire(invalidMapThirdPos);
		verify(validDataListener, never()).fire((HashMap<Enum<?>, ?>) any(Object.class));
	}

	public void addAllListeners() {
		model.addListener(validDataListener, BasicModel.Types.VALID_INPUT);
		model.addListener(invalidDataListener, BasicModel.Types.INVALID_NUMBER);
	}

	@Test
	public void calculateResults_CalculatesCorrectResult() {
		ArrayList<Double> sampleInput = makeValidList(45, 68.1, 29.4, -54, -.19, 3.0001);
		LinkedHashMap<String, Double> expectedResult = new LinkedHashMap<String, Double>();
		expectedResult.put(MyConstants.SUM, 91.3101);
		expectedResult.put(MyConstants.SIZE, 6.0);
		expectedResult.put(MyConstants.MEAN, 15.21835);
		expectedResult.put(MyConstants.MEDIAN, 16.20005);
		expectedResult.put(MyConstants.MODE, null);
		expectedResult.put(MyConstants.RANGE, 122.1);
		expectedResult.put(MyConstants.POP_VAR, 1510.402939);
		expectedResult.put(MyConstants.SAMPLE_VAR, 1812.483527);
		expectedResult.put(MyConstants.POP_DEV, 38.863902);
		expectedResult.put(MyConstants.SAMPLE_DEV, 42.573272);

		LinkedHashMap<String, Double> actualResult = model.calculateResults(sampleInput);
		assertEquals(expectedResult.get(MyConstants.SUM), actualResult.get(MyConstants.SUM));
		assertEquals(expectedResult.get(MyConstants.SIZE), actualResult.get(MyConstants.SIZE));
		assertEquals(expectedResult.get(MyConstants.MEAN), actualResult.get(MyConstants.MEAN), DELTA);
		assertEquals(expectedResult.get(MyConstants.MEDIAN), actualResult.get(MyConstants.MEDIAN), DELTA);
		assertEquals(expectedResult.get(MyConstants.MODE), actualResult.get(MyConstants.MODE));
		assertEquals(expectedResult.get(MyConstants.RANGE), actualResult.get(MyConstants.RANGE), DELTA);
		assertEquals(expectedResult.get(MyConstants.POP_VAR), actualResult.get(MyConstants.POP_VAR), DELTA);
		assertEquals(expectedResult.get(MyConstants.SAMPLE_VAR), actualResult.get(MyConstants.SAMPLE_VAR), DELTA);
		assertEquals(expectedResult.get(MyConstants.POP_DEV), actualResult.get(MyConstants.POP_DEV), DELTA);
		assertEquals(expectedResult.get(MyConstants.SAMPLE_DEV), actualResult.get(MyConstants.SAMPLE_DEV), DELTA);
	}

	private ArrayList<Double> makeValidList(double... args) {
		ArrayList<Double> validList = new ArrayList<Double>();
		for (double val : args) {
			validList.add(val);
		}
		return validList;
	}

	@Test
	public void saveListNotifiesCorrectly_GivenValidInput() throws FileNotFoundException {
		addAllListListeners();
		String testName = "testName.txt";
		String input = "34x3,72.1,1337.H4CK3R";
		File testFile = new File(testName);

		model.saveList(testName, input);

		verify(validListener).fire();
		verify(invalidListener, never()).fire();

		testFile.delete();
	}

	@Test
	public void saveListNotifiesCorrectly_GivenInvalidInput() throws IOException {
		addAllListListeners();
		String testName = "someName.txt";
		String input = "OLLEH";
		File alreadyExists = new File(testName);
		alreadyExists.createNewFile();

		File testFile = new File(testName);
		FileOutputStream fakeStream = new FileOutputStream(testFile);
		when(activity.openFileOutput(testName, 0)).thenReturn(fakeStream);

		model.saveList(testName, input);

		verify(invalidListener).fire();
		verify(validListener, never()).fire();

		testFile.delete();
		alreadyExists.delete();
	}

	private void addAllListListeners() {
		model.addListener(validListener, BasicModel.Types.SAVE_SUCCESSFUL);
		model.addListener(invalidListener, BasicModel.Types.SAVE_FAILED);
	}
}
