package me.nickpierson.StatsCalculator.utils;

import android.view.View;

public interface KeypadActivity {

	/* Keypad input character */
	abstract void keypadPress(View button);

	/* Keypad backspace button */
	abstract void backSpace(View button);

	/* Keypad done button */
	abstract void donePress(View button);

}
