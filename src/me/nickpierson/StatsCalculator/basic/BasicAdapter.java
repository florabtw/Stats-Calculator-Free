package me.nickpierson.StatsCalculator.basic;

import java.text.DecimalFormat;
import java.util.LinkedHashMap;

import me.nickpierson.StatsCalculator.R;
import me.nickpierson.StatsCalculator.utils.MyConstants;
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
			String stringAnswer = answer.toString();
			if (answer > MyConstants.MAX_PLAIN_FORMAT) {
				DecimalFormat format = new DecimalFormat(MyConstants.DECIMAL_FORMAT_LARGE);
				stringAnswer = format.format(answer);
			} else {
				DecimalFormat format = new DecimalFormat();
				format.setMaximumFractionDigits(MyConstants.DECIMAL_PLACES_LARGE);
				stringAnswer = format.format(answer);
			}

			tvAnswer.setText(stringAnswer);
		}

		return item;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}
}
