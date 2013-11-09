package me.nickpierson.StatsCalculatorFree.basic;

import me.nickpierson.StatsCalculator.basic.BasicPresenterTest;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class FreeBasicPresenterTest extends BasicPresenterTest {

	@Override
	public void createPresenter() {
		FreeBasicPresenter.create(activity, model, view);
	}
}
