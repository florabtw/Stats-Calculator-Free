package me.nickpierson.StatsCalculator;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import me.nickpierson.StatsCalculator.basic.BasicActivity;
import me.nickpierson.StatsCalculator.pc.PCActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.content.Intent;

import com.thecellutioncenter.mvplib.ActionListener;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class HomePresenterTest {

	private HomeModel model;
	private HomeView view;
	private HomeActivity activity;
	private ArgumentCaptor<ActionListener> listener;

	@Before
	public void setup() {
		model = mock(HomeModel.class);
		view = mock(HomeView.class);
		activity = mock(HomeActivity.class);

		listener = ArgumentCaptor.forClass(ActionListener.class);
	}

	public void createPresenter() {
		HomePresenter.create(activity, model, view);
	}

	@Test
	public void whenBasicButtonIsClicked_ThenBasicCalculatorIsOpened() {
		createPresenter();

		verify(view).addListener(listener.capture(), eq(HomeView.Types.DESCRIPTIVE_BUTTON));

		listener.getValue().fire();

		verify(activity, times(2)).startActivity(new Intent(activity, BasicActivity.class));
	}

	@Test
	public void whenPermCombButtonIsClicked_ThenPermCombCalculatorIsOpened() {
		createPresenter();

		verify(view).addListener(listener.capture(), eq(HomeView.Types.PERM_COMB_BUTTON));

		listener.getValue().fire();

		verify(activity, times(2)).startActivity(new Intent(activity, PCActivity.class));
	}
}