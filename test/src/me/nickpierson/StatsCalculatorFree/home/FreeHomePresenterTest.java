package me.nickpierson.StatsCalculatorFree.home;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import me.nickpierson.StatsCalculator.home.HomePresenterTest;
import me.nickpierson.StatsCalculator.home.HomeView;
import me.nickpierson.StatsCalculatorFree.basic.FreeBasicActivity;
import me.nickpierson.StatsCalculatorFree.pc.FreePCActivity;
import me.nickpierson.StatsCalculatorFree.utils.FreeConstants;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class FreeHomePresenterTest extends HomePresenterTest {

	private FreeHomeView freeView;

	@Override
	@Before
	public void setup() {
		super.setup();

		freeView = mock(FreeHomeView.class);
	}

	public void createPresenter() {
		FreeHomePresenter.create(activity, model, freeView);
	}

	@Test
	public void whenBasicButtonIsClicked_ThenBasicCalculatorIsOpened() {
		createPresenter();

		verify(freeView).addListener(listener.capture(), eq(HomeView.Types.DESCRIPTIVE_BUTTON));

		listener.getValue().fire();

		verify(activity, times(2)).startActivity(new Intent(activity, FreeBasicActivity.class));
	}

	@Test
	public void whenPermCombButtonIsClicked_ThenPermCombCalculatorIsOpened() {
		createPresenter();

		verify(freeView).addListener(listener.capture(), eq(HomeView.Types.PERM_COMB_BUTTON));

		listener.getValue().fire();

		verify(activity, times(2)).startActivity(new Intent(activity, FreePCActivity.class));
	}

	@Test
	public void whenProVersionMenuOptionIsPressed_ThenTheUserIsRedirectedToThePlayStore() {
		Uri uri = Uri.parse(FreeConstants.UPGRADE_URL);
		Intent upgradeIntent = new Intent(Intent.ACTION_VIEW, uri);
		createPresenter();

		verify(freeView).addListener(listener.capture(), eq(FreeHomeView.FreeTypes.MENU_UPGRADE));

		listener.getValue().fire();

		verify(activity).startActivity(upgradeIntent);
	}

	@Test
	public void whenProVersionMenuOptionIsPressedWithNoPlayStore_ThenTheUserIsShownAnError() {
		Uri uri = Uri.parse(FreeConstants.UPGRADE_URL);
		Intent upgradeIntent = new Intent(Intent.ACTION_VIEW, uri);
		doThrow(new ActivityNotFoundException()).when(activity).startActivity(upgradeIntent);
		createPresenter();

		verify(freeView).addListener(listener.capture(), eq(FreeHomeView.FreeTypes.MENU_UPGRADE));

		listener.getValue().fire();

		verify(freeView).showToast(FreeConstants.UPGRADE_ERROR);
	}
}
