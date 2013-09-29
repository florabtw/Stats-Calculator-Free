package me.nickpierson.StatsCalculator.pc;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import me.nickpierson.StatsCalculator.utils.MyConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.thecellutioncenter.mvplib.ActionListener;
import com.thecellutioncenter.mvplib.DataActionListener;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class PCPresenterTest {

	private PCView view;
	private PCModel model;
	ArgumentCaptor<ActionListener> listener;
	ArgumentCaptor<DataActionListener> dataListener;

	@Before
	public void setup() {
		view = mock(PCView.class);
		model = mock(PCModel.class);

		listener = ArgumentCaptor.forClass(ActionListener.class);
		dataListener = ArgumentCaptor.forClass(DataActionListener.class);

		when(view.getNVal()).thenReturn("nval");
		when(view.getRVal()).thenReturn("rval");
		when(view.getNVals()).thenReturn("nval,nval");
		when(model.calculateFact(any(Integer.class))).thenReturn(BigInteger.ONE);
		when(model.calculatePermutation(any(Integer.class), any(Integer.class))).thenReturn(BigInteger.valueOf(2));
		when(model.calculateCombination(any(Integer.class), any(Integer.class))).thenReturn(BigInteger.valueOf(3));
		when(model.calculateIndistinct(any(Integer.class), anyListOf(Integer.class))).thenReturn(BigInteger.valueOf(4));
	}

	public void createPresenter() {
		PCPresenter.create(model, view);
	}

	@Test
	public void whenCalculateButtonIsPressed_ThenDisplayedValuesAreCleared() {
		createPresenter();

		verify(view).addListener(listener.capture(), eq(PCView.Types.CALCULATE_PRESSED));

		listener.getValue().fire();

		verify(view).showDefaultValues();
	}

	@Test
	public void whenCalculateButtonIsPressed_ThenModelValidatesInput() {
		createPresenter();

		verify(view).addListener(listener.capture(), eq(PCView.Types.CALCULATE_PRESSED));

		listener.getValue().fire();

		verify(model).validateInput(view.getNVal(), view.getRVal(), view.getNVals());
	}

	@Test
	public void whenGoButtonOnKeyboardIsPressed_ThenModelValidatesInputAndKeyboardIsDismissed() {
		createPresenter();

		verify(view).addListener(listener.capture(), eq(PCView.Types.KEYBOARD_GO));

		listener.getValue().fire();

		verify(view).showDefaultValues();
		verify(model).validateInput(view.getNVal(), view.getRVal(), view.getNVals());
		verify(view).dismissNValsKeyboard();
	}

	@Test
	public void whenOnlyNValueIsShownValid_ThenRelatedValuesAreDisplayed() {
		createPresenter();
		HashMap<Enum<?>, Integer> map = new HashMap<Enum<?>, Integer>();
		int testNVal = 6;
		map.put(PCModel.Keys.N_VALUE, testNVal);

		verify(model).addListener(dataListener.capture(), eq(PCModel.Types.ONLY_VALID_N));

		dataListener.getValue().fire(map);

		verifyNFact(testNVal);
		verify(view).setRFactorial(MyConstants.NOT_APPLICABLE);
		verify(view).setPermutation(MyConstants.NOT_APPLICABLE);
		verify(view).setCombination(MyConstants.NOT_APPLICABLE);
		verify(view).setIndistinct(MyConstants.NOT_APPLICABLE);
	}

	@Test
	public void whenOnlyRValueIsShownValid_ThenRelatedValuesAreDisplayed() {
		createPresenter();
		HashMap<Enum<?>, Integer> map = new HashMap<Enum<?>, Integer>();
		int testRVal = 5;
		map.put(PCModel.Keys.R_VALUE, testRVal);

		verify(model).addListener(dataListener.capture(), eq(PCModel.Types.ONLY_VALID_R));

		dataListener.getValue().fire(map);

		verifyRFact(testRVal);
		verify(view).setNFactorial(MyConstants.NOT_APPLICABLE);
		verify(view).setPermutation(MyConstants.NOT_APPLICABLE);
		verify(view).setCombination(MyConstants.NOT_APPLICABLE);
		verify(view).setIndistinct(MyConstants.NOT_APPLICABLE);
	}

	@Test
	public void whenRAndNValueAreShownValid_ThenRelatedValuesAreDisplayed() {
		createPresenter();
		HashMap<Enum<?>, Integer> map = new HashMap<Enum<?>, Integer>();
		int testNVal = 6;
		int testRVal = 5;
		map.put(PCModel.Keys.N_VALUE, testNVal);
		map.put(PCModel.Keys.R_VALUE, testRVal);

		verify(model).addListener(dataListener.capture(), eq(PCModel.Types.VALID_N_AND_R));

		dataListener.getValue().fire(map);

		verifyNFact(testNVal);
		verifyRFact(testRVal);
		verifyPermutation(testNVal, testRVal);
		verifyCombination(testNVal, testRVal);
		verify(view).setIndistinct(MyConstants.NOT_APPLICABLE);
	}

	@Test
	public void whenNAndNValuesAreValid_ThenRelatedValuesAreDisplayed() {
		createPresenter();
		HashMap<Enum<?>, Object> map = new HashMap<Enum<?>, Object>();
		ArrayList<Integer> testNVals = new ArrayList<Integer>();
		int testNVal = 6;
		testNVals.add(2);
		testNVals.add(3);

		map.put(PCModel.Keys.N_VALUE, testNVal);
		map.put(PCModel.Keys.N_VALUES, testNVals);

		verify(model).addListener(dataListener.capture(), eq(PCModel.Types.VALID_N_AND_NS));

		dataListener.getValue().fire(map);

		verifyNFact(testNVal);
		verifyIndistinct(testNVals, testNVal);
		verify(view).setRFactorial(MyConstants.NOT_APPLICABLE);
		verify(view).setPermutation(MyConstants.NOT_APPLICABLE);
		verify(view).setCombination(MyConstants.NOT_APPLICABLE);
	}

	@Test
	public void whenAllValuesArePresent_ThenRelatedValuesAreShown() {
		createPresenter();
		HashMap<Enum<?>, Object> map = new HashMap<Enum<?>, Object>();
		ArrayList<Integer> testNVals = new ArrayList<Integer>();
		int testNVal = 6;
		int testRVal = 5;
		testNVals.add(7);
		testNVals.add(8);
		map.put(PCModel.Keys.N_VALUE, testNVal);
		map.put(PCModel.Keys.R_VALUE, testRVal);
		map.put(PCModel.Keys.N_VALUES, testNVals);

		verify(model).addListener(dataListener.capture(), eq(PCModel.Types.ALL_VALUES_VALID));

		dataListener.getValue().fire(map);

		verifyNFact(testNVal);
		verifyRFact(testRVal);
		verifyPermutation(testNVal, testRVal);
		verifyCombination(testNVal, testRVal);
		verifyIndistinct(testNVals, testNVal);
	}

	@Test
	public void numbersOverOneBillionAreFormatted() {
		when(model.calculateFact(any(Integer.class))).thenReturn(BigInteger.valueOf(1000000001));
		when(model.calculatePermutation(any(Integer.class), any(Integer.class))).thenReturn(BigInteger.valueOf(1000000001));
		when(model.calculateCombination(any(Integer.class), any(Integer.class))).thenReturn(BigInteger.valueOf(1000000001));
		when(model.calculateIndistinct(any(Integer.class), anyListOf(Integer.class))).thenReturn(BigInteger.valueOf(1000000001));
		createPresenter();
		HashMap<Enum<?>, Object> map = new HashMap<Enum<?>, Object>();
		ArrayList<Integer> testNVals = new ArrayList<Integer>();
		int testNVal = 6;
		int testRVal = 5;
		testNVals.add(7);
		testNVals.add(8);
		map.put(PCModel.Keys.N_VALUE, testNVal);
		map.put(PCModel.Keys.R_VALUE, testRVal);
		map.put(PCModel.Keys.N_VALUES, testNVals);

		verify(model).addListener(dataListener.capture(), eq(PCModel.Types.ALL_VALUES_VALID));

		dataListener.getValue().fire(map);

		verify(view).setNFactorial("1E9");
		verify(view).setRFactorial("1E9");
		verify(view).setPermutation("1E9");
		verify(view).setCombination("1E9");
		verify(view).setIndistinct("1E9");
	}

	private void verifyNFact(int testNVal) {
		verify(model).calculateFact(testNVal);
		verify(view).setNFactorial(model.calculateFact(testNVal).toString());
	}

	private void verifyRFact(int testRVal) {
		verify(model).calculateFact(testRVal);
		verify(view).setRFactorial(model.calculateFact(testRVal).toString());
	}

	private void verifyCombination(int testNVal, int testRVal) {
		verify(model).calculateCombination(testNVal, testRVal);
		verify(view).setCombination(model.calculateCombination(testNVal, testRVal).toString());
	}

	private void verifyPermutation(int testNVal, int testRVal) {
		verify(model).calculatePermutation(testNVal, testRVal);
		verify(view).setPermutation(model.calculatePermutation(testNVal, testRVal).toString());
	}

	private void verifyIndistinct(ArrayList<Integer> testNVals, int testNVal) {
		verify(model).calculateIndistinct(testNVal, testNVals);
		verify(view).setIndistinct(model.calculateIndistinct(testNVal, testNVals).toString());
	}

	@Test
	public void PCPresenterHandlesInvalidInputCorrectly() {
		createPresenter();

		verify(model).addListener(listener.capture(), eq(PCModel.Types.INPUT_OVER_MAX_VALUE));

		listener.getValue().fire();

		verify(view).showToast(MyConstants.MESSAGE_INPUT_OVER_MAX);
	}
}
