package me.nickpierson.StatisticsSolver.pc;

import me.nickpierson.StatisticsSolver.R;
import android.view.LayoutInflater;
import android.view.View;
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

	public PCView(PCActivity pcActivity) {
		view = (LinearLayout) LayoutInflater.from(pcActivity).inflate(R.layout.perm_comb, null);
		tvNFact = (TextView) view.findViewById(R.id.pc_tvNFact);
		tvRFact = (TextView) view.findViewById(R.id.pc_tvRFact);
		tvNPermR = (TextView) view.findViewById(R.id.pc_tvNPermR);
		tvNChooseR = (TextView) view.findViewById(R.id.pc_tvNChooseR);
		tvIndistinct = (TextView) view.findViewById(R.id.pc_tvIndistinct);

		etNVal = (EditText) view.findViewById(R.id.pc_etNVal);
		etRVal = (EditText) view.findViewById(R.id.pc_etRVal);
		etNVals = (EditText) view.findViewById(R.id.pc_etNVals);
	}

	public void displayDefaultValues() {
		tvNFact.setText("n/a");
		tvRFact.setText("n/a");
		tvNPermR.setText("n/a");
		tvNChooseR.setText("n/a");
		tvIndistinct.setText("n/a");
	}

	public void displayNFactorial(int nFact) {
		tvNFact.setText(nFact);
	}

	public void displayRFactorial(int rFact) {
		tvRFact.setText(rFact);
	}

	public void displayPermutation(int permutation) {
		tvNPermR.setText(permutation);
	}

	public void displayCombination(int combination) {
		tvNChooseR.setText(combination);
	}

	public void displayIndistinct(int indistinct) {
		tvIndistinct.setText(indistinct);
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
