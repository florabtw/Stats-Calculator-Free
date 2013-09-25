package me.nickpierson.StatisticsSolver.utils;

import android.view.KeyEvent;

public class MyConstants {
	
	public static final String SIZE = "Size";
	public static final String SUM = "Sum";
	public static final String MEAN = "Mean";
	public static final String MEDIAN = "Median";
	public static final String MODE = "Mode";
	public static final String RANGE = "Range";
	public static final String POP_VAR = "Population Variance";
	public static final String SAMPLE_VAR = "Sample Variance";
	public static final String POP_DEV = "Population Std. Deviation";
	public static final String SAMPLE_DEV = "Sample Std. Deviation";

	public static final String BASIC = "Descriptive Stats";
	public static final String PERM_COMB = "Permutations & Combinations";
	public static final String PROB_DISTS = "Probability Distributions";

	public static final String[] descriptions = { BASIC, PERM_COMB, PROB_DISTS };

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
		case 'x':
			return KeyEvent.KEYCODE_X;
		default:
			return -1;
		}
	}
}
