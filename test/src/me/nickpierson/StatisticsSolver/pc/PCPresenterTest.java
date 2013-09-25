package me.nickpierson.StatisticsSolver.pc;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.thecellutioncenter.mvplib.ActionListener;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class PCPresenterTest {

	private PCView view;
	private PCModel model;
	ArgumentCaptor<ActionListener> listener;

	@Before
	public void setup() {
		view = mock(PCView.class);
		model = mock(PCModel.class);

		listener = ArgumentCaptor.forClass(ActionListener.class);

		when(view.getNVal()).thenReturn(5);
		when(view.getRVal()).thenReturn(6);
		when(view.getNVals()).thenReturn("2,4,6");
		when(model.calculateFact(any(Integer.class))).thenReturn(1);
		when(model.calculateCombination(any(Integer.class), any(Integer.class))).thenReturn(2);
		when(model.calculatePermutation(any(Integer.class), any(Integer.class))).thenReturn(3);
		when(model.calculateIndistinctPerm(any(Integer.class), any(String.class))).thenReturn(4);
	}

	public void createPresenter() {
		PCPresenter.create(model, view);
	}

	@Test
	public void whenCalculateButtonIsPressed_ThenValuesAreSetToDefault() {
		createPresenter();

		verify(view).addListener(listener.capture(), eq(PCView.Types.CALCULATE_PRESSED));

		listener.getValue().fire();

		verify(view, times(2)).displayDefaultValues();
	}

	@Test
	public void whenCalculateButtonIsPressedWithAtLeastNVal_ThenViewIsShownWithNFactorial() {
		createPresenter();
		when(view.hasNVal()).thenReturn(true);

		verify(view).addListener(listener.capture(), eq(PCView.Types.CALCULATE_PRESSED));

		listener.getValue().fire();

		verify(view, times(2)).displayDefaultValues();
		verifyNFact();
	}

	@Test
	public void whenCalculateButtonIsPressedWithAtLeastRVal_ThenViewIsShownWithRFactorial() {
		createPresenter();
		when(view.hasRVal()).thenReturn(true);

		verify(view).addListener(listener.capture(), eq(PCView.Types.CALCULATE_PRESSED));

		listener.getValue().fire();

		verify(view, times(2)).displayDefaultValues();
		verifyRFact();
	}

	@Test
	public void whenCalculateButtonIsPressedWithNAndRVal_ThenCorrectValuesAreShown() {
		createPresenter();
		when(view.hasNVal()).thenReturn(true);
		when(view.hasRVal()).thenReturn(true);

		verify(view).addListener(listener.capture(), eq(PCView.Types.CALCULATE_PRESSED));

		listener.getValue().fire();

		verify(view, times(2)).displayDefaultValues();
		verifyNFact();
		verifyRFact();
		verifyPermutationAndCombination();
	}

	@Test
	public void whenCalculateButtonIsPressedWithNValAndNVals_ThenCorrectValuesAreShown() {
		createPresenter();
		when(view.hasNVal()).thenReturn(true);
		when(view.hasNVals()).thenReturn(true);

		verify(view).addListener(listener.capture(), eq(PCView.Types.CALCULATE_PRESSED));

		listener.getValue().fire();

		verify(view, times(2)).displayDefaultValues();
		verifyNFact();
		verifyIndisctinctPermutation();
	}

	@Test
	public void whenCalculateButtonIsPressedWithAllVals_ThenAllValsAreDisplayed() {
		createPresenter();
		when(view.hasNVal()).thenReturn(true);
		when(view.hasRVal()).thenReturn(true);
		when(view.hasNVals()).thenReturn(true);

		verify(view).addListener(listener.capture(), eq(PCView.Types.CALCULATE_PRESSED));

		listener.getValue().fire();

		verify(view, times(2)).displayDefaultValues();
		verifyNFact();
		verifyRFact();
		verifyPermutationAndCombination();
		verifyIndisctinctPermutation();
	}

	private void verifyIndisctinctPermutation() {
		verify(model).calculateIndistinctPerm(view.getNVal(), view.getNVals());
		verify(view).displayIndistinct(model.calculateIndistinctPerm(any(Integer.class), any(String.class)));
	}

	private void verifyPermutationAndCombination() {
		verify(model).calculatePermutation(view.getNVal(), view.getRVal());
		verify(view).displayPermutation(model.calculatePermutation(any(Integer.class), any(Integer.class)));
		verify(model).calculateCombination(view.getNVal(), view.getRVal());
		verify(view).displayCombination(model.calculateCombination(any(Integer.class), any(Integer.class)));
	}

	private void verifyNFact() {
		verify(model).calculateFact(view.getNVal());
		verify(view).displayNFactorial(model.calculateFact(any(Integer.class)));
	}

	private void verifyRFact() {
		verify(model).calculateFact(view.getRVal());
		verify(view).displayRFactorial(model.calculateFact(any(Integer.class)));
	}
}
