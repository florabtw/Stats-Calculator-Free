package me.nickpierson.StatisticsSolver;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.HashMap;

import me.nickpierson.StatisticsSolver.utils.MyConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;

import android.content.Intent;

import com.thecellutioncenter.mvplib.DataActionListener;

@RunWith(RobolectricTestRunner.class)
public class HomePresenterTest {

	private HomeModel model;
	private HomeView view;
	private HomeActivity activity;

	@Before
	public void setup() {
		model = mock(HomeModel.class);
		view = mock(HomeView.class);
		activity = mock(HomeActivity.class);
	}

	public void createPresenter() {
		HomePresenter.create(activity, model, view);
	}

	@Test
	public void ifAnErrorOccursOnButtonPress_ThenToastIsShown() {
		buttonSetup("Not a real description");

		/* Doesn't actually test that the correct string is retrieved.
		 * The any() is for clarity since no matter what integer is put there,
		 * it would pass the test.
		 */
		verify(view).showToast(activity.getString(any(Integer.class)));
	}
	
	@Test
	public void ifAnErrorOccursOnButtonPress_ThenNoActivityIsOpened() {
		buttonSetup("Not a real description");
		
		verify(activity, never()).startActivity(any(Intent.class));
	}

	@Test
	public void whenBasicButtonIsClicked_ThenBasicCalculatorIsOpened() {
		buttonSetup(MyConstants.BASIC);

		verify(activity).startActivity(any(Intent.class));
	}

	private void buttonSetup(String description) {
		createPresenter();
		ArgumentCaptor<DataActionListener> listener = ArgumentCaptor.forClass(DataActionListener.class);
		HashMap<Enum<?>, String> map = new HashMap<Enum<?>, String>();
		map.put(HomeView.Types.BUTTON_CLICKED, description);
		
		verify(view).addListener(listener.capture(), eq(HomeView.Types.BUTTON_CLICKED));
		
		listener.getValue().fire(map);
	}
}