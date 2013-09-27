package me.nickpierson.StatisticsSolver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class HomeAdapter extends ArrayAdapter<String> {

	private int resource;

	public HomeAdapter(Context context, int resource) {
		super(context, resource);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = LayoutInflater.from(getContext()).inflate(resource, null);

		Button btnGo = (Button) item.findViewById(R.id.home_btnListItem);
		TextView tvDescription = (TextView) item.findViewById(R.id.home_tvListItem);

		String itemDescription = getItem(position);
		tvDescription.setText(itemDescription);
		btnGo.setTag(itemDescription);

		return item;
	}

	@Override
	public void addAll(String... strings) {
		for (String string : strings) {
			add(string);
		}
	}
}
