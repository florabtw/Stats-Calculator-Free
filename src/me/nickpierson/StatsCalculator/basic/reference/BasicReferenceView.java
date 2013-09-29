package me.nickpierson.StatsCalculator.basic.reference;

import me.nickpierson.StatsCalculator.R;
import me.nickpierson.StatsCalculator.utils.MyConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

public class BasicReferenceView {

	ListView view;

	int[] images = { R.drawable.arith_mean, R.drawable.geo_mean, R.drawable.sample_var, R.drawable.sample_dev, R.drawable.coeff_var, R.drawable.skewness,
			R.drawable.kurtosis };
	String[] titles = { MyConstants.ARITH_MEAN, MyConstants.GEO_MEAN, MyConstants.SAMPLE_VAR, MyConstants.SAMPLE_DEV, MyConstants.REF_COEFF_VAR,
			MyConstants.REF_SKEWNESS, MyConstants.REF_KURTOSIS };

	public BasicReferenceView(BasicReferenceActivity activity) {
		view = (ListView) LayoutInflater.from(activity).inflate(R.layout.basic_reference, null);

		BasicReferenceAdapter listAdapter = new BasicReferenceAdapter(activity, R.layout.basic_reference_list_item);

		for (int i = 0; i < titles.length; i++) {
			listAdapter.add(new ReferenceListItem(images[i], titles[i]));
		}

		view.setAdapter(listAdapter);
	}

	public View getView() {
		return view;
	}

}
