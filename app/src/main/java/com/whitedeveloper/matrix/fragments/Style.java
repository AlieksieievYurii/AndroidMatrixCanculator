package com.whitedeveloper.matrix.fragments;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import com.whitedeveloper.matrix.R;

public class Style {
    public static void setStyleEditViewForGridLayout(Context context, EditText editText) {
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(new ViewGroup.LayoutParams(
                   ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.setGravity(Gravity.FILL_HORIZONTAL);

            int margin = dpToPx(4,context);
            layoutParams.setMargins(margin, margin, margin, margin);

            editText.setMaxLines(1);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setSingleLine( true);
            editText.setMinWidth(dpToPx(25,context));
            editText.setBackground(ContextCompat.getDrawable(context,R.drawable.background_edit_text));
            int padding = dpToPx(2,context);
            editText.setPadding(padding, padding, padding, padding);
            editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            editText.setLayoutParams(layoutParams);
        }



    public static void setStyleTextViewForGridLayout(Context context, TextView textView) {
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        layoutParams.setGravity(Gravity.FILL_HORIZONTAL);
        int margin = dpToPx(4,context);
        layoutParams.setMargins(margin,margin,margin,margin);

        textView.setMaxLines(1);
        textView.setMinWidth(dpToPx(25,context));
        textView.setBackground(ContextCompat.getDrawable(context,R.drawable.background_edit_text));
        textView.setTextColor(ContextCompat.getColor(context,R.color.textColor));
        textView.setTextSize(dpToPx(7,context));

        int padding = dpToPx(2,context);
        textView.setPadding(padding, padding, padding, padding);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setLayoutParams(layoutParams);
    }

    private static int dpToPx(float dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
