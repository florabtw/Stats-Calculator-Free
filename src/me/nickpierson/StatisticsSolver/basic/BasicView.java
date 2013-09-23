package me.nickpierson.StatisticsSolver.basic;

import me.nickpierson.StatisticsSolver.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class BasicView {

	private RelativeLayout view;
	private FrameLayout flContent;
	private ScrollView svResults;
	private LinearLayout llKeypad;
	private EditText etInput;

	public BasicView(BasicActivity activity) {
		view = (RelativeLayout) LayoutInflater.from(activity).inflate(R.layout.basic, null);
		flContent = (FrameLayout) view.findViewById(R.id.basic_flContent);
		etInput = (EditText) view.findViewById(R.id.basic_etInput);
		
		svResults = (ScrollView) LayoutInflater.from(activity).inflate(R.layout.basic_results, null);
		llKeypad = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.keypad, null);
		
		flContent.addView(svResults);
		
		etInput.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				flContent.removeAllViews();
				flContent.addView(llKeypad);
			}
		});
	}

	public RelativeLayout getView() {
		return view;
	}

}
