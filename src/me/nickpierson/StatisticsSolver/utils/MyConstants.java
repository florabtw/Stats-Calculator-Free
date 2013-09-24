package me.nickpierson.StatisticsSolver.utils;

import android.view.KeyEvent;

public class MyConstants {

	public static final String BASIC = "Basic (Mean, Median, Mode, Range, Variance, Standard Deviation)";
	public static final String PERM_COMB = "Permutation / Combination";
	public static final String DENSITY_TABLE = "Density Table";
	public static final String BINOMIAL = "Binomial Distribution";
	public static final String NEGATIVE_BINOMIAL = "Negative Binomial Distribution";
	public static final String POISSON = "Poisson Distribution";

	public static final String[] descriptions = { BASIC, PERM_COMB, DENSITY_TABLE, BINOMIAL, NEGATIVE_BINOMIAL, POISSON };

	public static int getKeyEvent(char c) {
		switch (c) {
		case '0':
			return KeyEvent.KEYCODE_0;
		case '1':
			return KeyEvent.KEYCODE_1;
		case '2':
			return KeyEvent.KEYCODE_2;
		case '3':
			return KeyEvent.KEYCODE_3;
		case '4':
			return KeyEvent.KEYCODE_4;
		case '5':
			return KeyEvent.KEYCODE_5;
		case '6':
			return KeyEvent.KEYCODE_6;
		case '7':
			return KeyEvent.KEYCODE_7;
		case '8':
			return KeyEvent.KEYCODE_8;
		case '9':
			return KeyEvent.KEYCODE_9;
		case '-':
			return KeyEvent.KEYCODE_MINUS;
		case '.':
			return KeyEvent.KEYCODE_PERIOD;
		case ',':
			return KeyEvent.KEYCODE_COMMA;
		default:
			return -1;
		}
	}
}
