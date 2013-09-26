package me.nickpierson.StatisticsSolver.pc;

import java.math.BigInteger;

import me.nickpierson.StatisticsSolver.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thecellutioncenter.mvplib.ActionHandler;

public class PCView extends ActionHandler {

	public enum Types {
		CALCULATE_PRESSED;
	}

	LinearLayout view;
	private EditText etNVal;
	private EditText etRVal;
	private EditText etNVals;
	private TextView tvNFact;
	private TextView tvRFact;
	private TextView tvNPermR;
	private TextView tvNChooseR;
	private TextView tvIndistinct;
	private Button btnCalculate;

	public PCView(PCActivity pcActivity) {
		view = (LinearLayout) LayoutInflater.from(pcActivity).inflate(R.layout.perm_comb, null);
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

	public void displayDefaultValues() {
		tvNFact.setText(R.string.pc_default_value);
		tvRFact.setText(R.string.pc_default_value);
		tvNPermR.setText(R.string.pc_default_value);
		tvNChooseR.setText(R.string.pc_default_value);
		tvIndistinct.setText(R.string.pc_default_value);
	}

	public void displayNFactorial(BigInteger nFact) {
		tvNFact.setText(String.valueOf(nFact));
	}

	public void displayRFactorial(BigInteger rFact) {
		tvRFact.setText(rFact.toString());
	}

	public void displayPermutation(BigInteger permutation) {
		tvNPermR.setText(permutation.toString());
	}

	public void displayCombination(BigInteger combination) {
		tvNChooseR.setText(combination.toString());
	}

	public void displayIndistinct(BigInteger indistinct) {
		tvIndistinct.setText(indistinct.toString());
	}

	public boolean hasNVal() {
		return etNVal.getText().length() > 0;
	}

	public boolean hasRVal() {
		return etRVal.getText().length() > 0;
	}

	public boolean hasNVals() {
		return etNVals.getText().length() > 0;
	}

	public int getNVal() {
		return Integer.valueOf(etNVal.getText().toString());
	}

	public int getRVal() {
		return Integer.valueOf(etRVal.getText().toString());
	}

	public String getNVals() {
		return etNVals.getText().toString();
	}

	public View getView() {
		return view;
	}
}
