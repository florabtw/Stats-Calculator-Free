package me.nickpierson.StatsCalculator;

import java.util.HashMap;

import me.nickpierson.StatsCalculator.R;
import me.nickpierson.StatsCalculator.utils.MyConstants;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.thecellutioncenter.mvplib.DataActionHandler;

public class HomeView extends DataActionHandler {

	public enum Types {
		BUTTON_CLICKED;
	}

	private ListView lvHome;
	private HomeActivity activity;
	private HomeAdapter adapter;

	public HomeView(HomeActivity activity) {
		this.activity = activity;

		lvHome = (ListView) LayoutInflater.from(activity).inflate(R.layout.home, null);
		adapter = new HomeAdapter(activity, R.layout.home_list_item);
		lvHome.setAdapter(adapter);
		adapter.addAll(MyConstants.descriptions);
	}
	
	public void buttonClicked(View button) {
		HashMap<Enum<?>, String> map = new HashMap<Enum<?>, String>();
		map.put(Types.BUTTON_CLICKED, (String) button.getTag());
		dataEvent(Types.BUTTON_CLICKED, map);
	}

	public ListView getView() {
		return lvHome;
	}

	public void showToast(String message) {
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}

}
