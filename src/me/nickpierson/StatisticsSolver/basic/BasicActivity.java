package me.nickpierson.StatisticsSolver.basic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BasicActivity extends Activity {
	
	private BasicView view;
	private BasicModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		view = new BasicView(this);
		model = new BasicModel();
		BasicPresenter.create(model, view);
		
		setContentView(view.getView());
	}
	
	public void keypadPress(View button){
		view.keypadPress((Button) button);
	}
	
	public void backSpace(View button){
		view.backSpace();
	}
	
	public void donePress(View button){
		view.donePress();
	}
}
