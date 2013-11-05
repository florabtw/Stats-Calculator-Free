package me.nickpierson.StatsCalculatorFree.basic;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import me.nickpierson.StatsCalculator.basic.BasicPresenterTest;
import me.nickpierson.StatsCalculator.basic.BasicView;
import me.nickpierson.StatsCalculatorFree.basic.reference.FreeBasicReferenceActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.content.Intent;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class FreeBasicPresenterTest extends BasicPresenterTest {

	@Override
	public void createPresenter() {
		FreeBasicPresenter.create(activity, model, view);
	}

	@Test
	public void whenMenuReferenceGuideIsClicked_ThenReferenceGuideIsShown() {
		createPresenter();

		verify(view).addListener(listener.capture(), eq(BasicView.Types.MENU_REFERENCE));

		listener.getValue().fire();

		verify(activity, times(2)).startActivity(new Intent(activity, FreeBasicReferenceActivity.class));
	}
}
