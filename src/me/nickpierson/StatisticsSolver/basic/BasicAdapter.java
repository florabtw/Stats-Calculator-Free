package me.nickpierson.StatisticsSolver.basic;

import java.util.LinkedHashMap;

import me.nickpierson.StatisticsSolver.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BasicAdapter extends ArrayAdapter<LinkedHashMap.Entry<String, Double>> {

	private int resource;

	public BasicAdapter(Context context, int resource) {
		super(context, resource);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = LayoutInflater.from(getContext()).inflate(resource, null);

		TextView tvTitle = (TextView) item.findViewById(R.id.basic_tvResultTitle);
		TextView tvAnswer = (TextView) item.findViewById(R.id.basic_tvResultAnswer);

		tvTitle.setText(getItem(position).getKey());

		Double answer = getItem(position).getValue();
		if (answer == null) {
			tvAnswer.setText("None");
		} else {
			tvAnswer.setText(getItem(position).getValue().toString());
		}

		return item;
	}
}
