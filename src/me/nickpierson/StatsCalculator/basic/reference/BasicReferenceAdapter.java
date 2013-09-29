package me.nickpierson.StatsCalculator.basic.reference;

import me.nickpierson.StatsCalculator.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BasicReferenceAdapter extends ArrayAdapter<ReferenceListItem> {

	private int resource;

	public BasicReferenceAdapter(Context context, int resource) {
		super(context, resource);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View listItemView = LayoutInflater.from(getContext()).inflate(resource, null);

		TextView title = (TextView) listItemView.findViewById(R.id.basic_reference_tvTitle);
		ImageView ivFormula = (ImageView) listItemView.findViewById(R.id.basic_reference_ivFormula);

		ReferenceListItem listItem = getItem(position);
		title.setText(listItem.getTitle());
		ivFormula.setBackgroundResource(listItem.getImageId());

		return listItemView;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
