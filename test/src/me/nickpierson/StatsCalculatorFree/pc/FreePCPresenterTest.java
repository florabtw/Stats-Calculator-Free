package me.nickpierson.StatsCalculatorFree.pc;

import me.nickpierson.StatsCalculator.pc.PCPresenterTest;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class FreePCPresenterTest extends PCPresenterTest {

	@Override
	public void createPresenter() {
		FreePCPresenter.create(model, view);
	}

}
