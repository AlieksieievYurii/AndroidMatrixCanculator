package com.whitedeveloper.matrix;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Objects;

public class HiddenKeyboard
{
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
