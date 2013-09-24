package me.nickpierson.StatisticsSolve.basic;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import me.nickpierson.StatisticsSolver.basic.BasicModel;
import me.nickpierson.StatisticsSolver.basic.BasicPresenter;
import me.nickpierson.StatisticsSolver.basic.BasicView;
import me.nickpierson.StatisticsSolver.utils.BasicResult;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import com.thecellutioncenter.mvplib.ActionListener;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class BasicPresenterTest {

	BasicView view;
	BasicModel model;

	ArgumentCaptor<ActionListener> listener;

	@Before
	public void setup() {
		view = mock(BasicView.class);
		model = mock(BasicModel.class);

		listener = ArgumentCaptor.forClass(ActionListener.class);
	}

	public void createPresenter() {
		BasicPresenter.create(model, view);
	}

	@Test
	public void whenEditTextIsClicked_ThenKeypadIsShown() {
		createPresenter();

		verify(view).addListener(listener.capture(), eq(BasicView.Types.EDITTEXT_CLICKED));

		listener.getValue().fire();

		verify(view).showKeypad();
	}

	@Test
	public void whenDoneIsClicked_ThenInputIsCleaned() {
		createPresenter();

		verify(view).addListener(listener.capture(), eq(BasicView.Types.DONE_CLICKED));

		listener.getValue().fire();

		verify(model).convertInput(view.getInput());
	}

	@Test
	public void whenDoneIsClicked_ThenResultsAreCalculated() {
		createPresenter();

		verify(view).addListener(listener.capture(), eq(BasicView.Types.DONE_CLICKED));

		listener.getValue().fire();

		verify(model).calculateResults(anyListOf(Double.class));
	}

	@Test
	public void whenDoneIsClicked_ThenResultsAreDisplayed() {
		createPresenter();
		ArrayList<Double> validList = new ArrayList<Double>();
		validList.add(5.6);
		when(model.convertInput(any(String.class))).thenReturn(validList);

		verify(view).addListener(listener.capture(), eq(BasicView.Types.DONE_CLICKED));

		listener.getValue().fire();

		verify(view).showResults(any(BasicResult.class));
	}

	@Test
	public void whenDoneIsClickedWithBadInput_ThenToastIsShown() {
		createPresenter();
		when(model.convertInput(any(String.class))).thenReturn(null);

		verify(view).addListener(listener.capture(), eq(BasicView.Types.DONE_CLICKED));

		listener.getValue().fire();

		verify(view).showToast(any(String.class));
	}

}
