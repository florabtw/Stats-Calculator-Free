package me.nickpierson.StatisticsSolver.pc;

import me.nickpierson.StatisticsSolver.R;
import me.nickpierson.StatisticsSolver.utils.MyConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.thecellutioncenter.mvplib.ActionHandler;

public class PCView extends ActionHandler {

	public enum Types {
		CALCULATE_PRESSED;
	}

	private ScrollView view;
	private EditText etNVal;
	private EditText etRVal;
	private EditText etNVals;
	private TextView tvNFact;
	private TextView tvRFact;
	private TextView tvNPermR;
	private TextView tvNChooseR;
	private TextView tvIndistinct;
	private Button btnCalculate;
	private PCActivity activity;

	public PCView(PCActivity activity) {
		this.activity = activity;
		view = (ScrollView) LayoutInflater.from(activity).inflate(R.layout.perm_comb, null);
		tvNFact = (TextView) view.findViewById(R.id.pc_tvNFact);
		tvRFact = (TextView) view.findViewById(R.id.pc_tvRFact);
		tvNPermR = (TextView) view.findViewById(R.id.pc_tvNPermR);
		tvNChooseR = (TextView) view.findViewById(R.id.pc_tvNChooseR);
		tvIndistinct = (TextView) view.findViewById(R.id.pc_tvIndistinct);
		btnCalculate = (Button) view.findViewById(R.id.pc_btnCalculate);
		btnCalculate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				event(Types.CALCULATE_PRESSED);
			}
		});

		etNVal = (EditText) view.findViewById(R.id.pc_etNVal);
		etRVal = (EditText) view.findViewById(R.id.pc_etRVal);
		etNVals = (EditText) view.findViewById(R.id.pc_etNVals);
	}
	
	public void showDefaultValues() {
		setNFactorial(MyConstants.NOT_APPLICABLE);
		setRFactorial(MyConstants.NOT_APPLICABLE);
		setPermutation(MyConstants.NOT_APPLICABLE);
		setCombination(MyConstants.NOT_APPLICABLE);
		setIndistinct(MyConstants.NOT_APPLICABLE);
	}

	public void setNFactorial(String text) {
		tvNFact.setText(text);
	}

	public void setRFactorial(String text) {
		tvRFact.setText(text);
	}

	public void setPermutation(String text) {
		tvNPermR.setText(text);
	}

	public void setCombination(String text) {
		tvNChooseR.setText(text);
	}

	public void setIndistinct(String text) {
		tvIndistinct.setText(text);
	}

	public String getNVal() {
		return etNVal.getText().toString();
	}

	public String getRVal() {
		return etRVal.getText().toString();
	}

	public String getNVals() {
		return etNVals.getText().toString();
	}

	public View getView() {
		return view;
	}

	public void showToast(String message) {
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}
}
