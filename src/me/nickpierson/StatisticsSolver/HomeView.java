package me.nickpierson.StatisticsSolver;

import java.util.HashMap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.thecellutioncenter.mvplib.DataActionHandler;

public class HomeView extends DataActionHandler {

	public enum Types {
		BUTTON_CLICKED;
	}
	
	private LinearLayout view;
	private HomeActivity activity;
	private Button btnFirst;

	public HomeView(HomeActivity activity) {
		this.activity = activity;
		view = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.home, null);
		btnFirst = (Button) view.findViewById(R.id.home_btnBasic);
		
		btnFirst.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				HashMap<Enum<?>, Integer> map = new HashMap<Enum<?>, Integer>();
				map.put(Types.BUTTON_CLICKED, btnFirst.getId());
				dataEvent(Types.BUTTON_CLICKED, map);
			}
		});
	}

	public LinearLayout getView() {
		return view;
	}

	public void showToast(String message) {
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}

}
