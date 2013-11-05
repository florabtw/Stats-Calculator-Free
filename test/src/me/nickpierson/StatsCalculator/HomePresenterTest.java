package me.nickpierson.StatsCalculator;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import me.nickpierson.StatsCalculator.utils.MyConstants;
import me.nickpierson.StatsCalculatorFree.FreeHomeActivity;
import me.nickpierson.StatsCalculatorFree.basic.FreeBasicActivity;
import me.nickpierson.StatsCalculatorFree.pc.FreePCActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.thecellutioncenter.mvplib.ActionListener;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class HomePresenterTest {

	private HomeModel model;
	private HomeView view;
	private FreeHomeActivity activity;
	private ArgumentCaptor<ActionListener> listener;

	@Before
	public void setup() {
		model = mock(HomeModel.class);
		view = mock(HomeView.class);
		activity = mock(FreeHomeActivity.class);

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

		verify(activity, times(2)).startActivity(new Intent(activity, FreeBasicActivity.class));
	}

	@Test
	public void whenPermCombButtonIsClicked_ThenPermCombCalculatorIsOpened() {
		createPresenter();

		verify(view).addListener(listener.capture(), eq(HomeView.Types.PERM_COMB_BUTTON));

		listener.getValue().fire();

		verify(activity, times(2)).startActivity(new Intent(activity, FreePCActivity.class));
	}

	@Test
	public void whenContactDeveloperMenuItemSelected_ThenEmailIntentIsShown() {
		Uri emailUri = Uri.fromParts("mailto", MyConstants.DEVELOPER_EMAIL, null);
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, emailUri);
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, MyConstants.EMAIL_SUBJECT);
		Intent testIntent = Intent.createChooser(emailIntent, MyConstants.EMAIL_CHOOSER_TITLE);

		createPresenter();

		verify(view).addListener(listener.capture(), eq(HomeView.Types.MENU_CONTACT));

		listener.getValue().fire();

		verify(activity).startActivity(testIntent);
	}

	@Test
	public void whenRateThisAppMenuItemIsSelected_ThenUserIsDirectedToPlayStore() {
		when(activity.getApplicationContext()).thenReturn(mock(Context.class));
		when(activity.getApplicationContext().getPackageName()).thenReturn("StatsCalculator");
		Uri uri = Uri.parse("market://details?id=" + activity.getApplicationContext().getPackageName());
		Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);

		HomePresenter.create(activity, model, view);

		verify(view).addListener(listener.capture(), eq(HomeView.Types.MENU_RATE));

		listener.getValue().fire();

		verify(activity).startActivity(rateAppIntent);
	}

	@Test
	public void whenRateThisAppMenuItemIsSelectedWithNoPlayStore_ThenUserIsShownError() {
		when(activity.getApplicationContext()).thenReturn(mock(Context.class));
		when(activity.getApplicationContext().getPackageName()).thenReturn("StatsCalculator");
		Uri uri = Uri.parse("market://details?id=" + activity.getApplicationContext().getPackageName());
		Intent rateAppIntent = new Intent(Intent.ACTION_VIEW, uri);
		doThrow(new ActivityNotFoundException()).when(activity).startActivity(rateAppIntent);

		HomePresenter.create(activity, model, view);

		verify(view).addListener(listener.capture(), eq(HomeView.Types.MENU_RATE));

		listener.getValue().fire();

		verify(view).showToast(MyConstants.RATE_ERROR);
	}
}