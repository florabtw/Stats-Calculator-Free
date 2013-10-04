package me.nickpierson.StatsCalculator.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;

public class KeypadHelper {

	public void keypadPress(EditText etInput, char character) {
		etInput.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, getKeyEvent(character)));
	}

	public void backspace(EditText etInput) {
		etInput.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
	}

	public void longPressBackspace(EditText etInput) {
		int cursorPosition = etInput.getSelectionEnd();
		int textLength = etInput.getText().length();
		String remainingText = etInput.getText().subSequence(cursorPosition, textLength).toString();

		etInput.setText(remainingText);
	}

	public void watchEditText(EditText etInput) {
		etInput.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				for (int i = 0; i < s.toString().length(); i++) {
					if (!isAcceptableChar(s.charAt(i))) {
						s.replace(i, i + 1, "");
					}
				}
			}
		});
	}

	protected boolean isAcceptableChar(char c) {
		if (Character.isDigit(c)) {
			return true;
		} else if (c == '.' || c == ',' || c == '-' || c == 'x') {
			return true;
		}

		return false;
	}

	/*
	 * TODO: Figure out why this is necessary. I just ran this on API 8 phone
	 * and it worked just fine.
	 */
	@SuppressLint("NewApi")
	public void disableSoftInputFromAppearing(EditText etInput) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			etInput.setRawInputType(InputType.TYPE_CLASS_TEXT);
			etInput.setTextIsSelectable(true);
		} else {
			etInput.setRawInputType(InputType.TYPE_NULL);
			etInput.setFocusable(true);
		}
	}

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
