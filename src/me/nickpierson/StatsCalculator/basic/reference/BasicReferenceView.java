package me.nickpierson.StatsCalculator.basic.reference;

import me.nickpierson.StatsCalculator.R;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public class BasicReferenceView {

	LinearLayout view;
	private ListView lvList;

	public BasicReferenceView(BasicReferenceActivity activity) {
		view = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.basic_reference, null);
		lvList = (ListView) view.findViewById(R.id.basic_reference_lvList);

		String[] titles = activity.getResources().getStringArray(R.array.reference_headers);
		TypedArray images = activity.getResources().obtainTypedArray(R.array.reference_images);

		BasicReferenceAdapter listAdapter = new BasicReferenceAdapter(activity, R.layout.basic_reference_list_item);

		for (int i = 0; i < titles.length; i++) {
			listAdapter.add(new ReferenceListItem(images.getResourceId(i, -1), titles[i]));
		}

		lvList.setAdapter(listAdapter);

		images.recycle();
	}

	public View getView() {
		return view;
	}

}
