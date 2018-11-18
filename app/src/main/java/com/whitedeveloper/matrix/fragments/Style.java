package com.whitedeveloper.matrix.fragments;

import android.content.Context;
import android.content.res.TypedArray;

import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import com.whitedeveloper.matrix.R;

public class Style {
    public static void setStyleEditViewForGridLayout(Context context, EditText editText) {
        TypedArray typedArray = context.obtainStyledAttributes(R.style.style_edt_element_matrix, R.styleable.styleable_edt_matrix);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(new ViewGroup.LayoutParams(
                typedArray.getLayoutDimension(R.styleable.styleable_edt_matrix_android_layout_width, 0),
                typedArray.getLayoutDimension(R.styleable.styleable_edt_matrix_android_layout_height, 0)));
        layoutParams.setGravity(Gravity.FILL_HORIZONTAL);

        int margin = typedArray.getLayoutDimension(R.styleable.styleable_edt_matrix_android_layout_margin, 0);
        layoutParams.setMargins(margin, margin, margin, margin);

        editText.setMaxLines(typedArray.getInt(R.styleable.styleable_edt_matrix_android_maxLines, 1));
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);//TODO Modification
        editText.setSingleLine(typedArray.getBoolean(R.styleable.styleable_edt_matrix_android_singleLine, true));
        editText.setMinWidth(typedArray.getDimensionPixelOffset(R.styleable.styleable_edt_matrix_android_minWidth, 0));
        editText.setBackground(typedArray.getDrawable(R.styleable.styleable_edt_matrix_android_background));
        int padding = typedArray.getDimensionPixelOffset(R.styleable.styleable_edt_matrix_android_padding, 0);
        editText.setPadding(padding, padding, padding, padding);
        editText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        typedArray.recycle();
        editText.setLayoutParams(layoutParams);
    }

    public static void setStyleTextViewForGridLayout(Context context, TextView textView) {
        TypedArray typedArray = context.obtainStyledAttributes(R.style.style_edt_element_matrix, R.styleable.styleable_edt_matrix);
        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(new ViewGroup.LayoutParams(
                typedArray.getLayoutDimension(R.styleable.styleable_edt_matrix_android_layout_width, 0),
                typedArray.getLayoutDimension(R.styleable.styleable_edt_matrix_android_layout_height, 0)));
        layoutParams.setGravity(Gravity.FILL_HORIZONTAL);

        int margin = typedArray.getLayoutDimension(R.styleable.styleable_edt_matrix_android_layout_margin, 0);
        layoutParams.setMargins(margin, margin, margin, margin);

        textView.setMaxLines(typedArray.getInt(R.styleable.styleable_edt_matrix_android_maxLines, 1));
        textView.setMinWidth(typedArray.getDimensionPixelOffset(R.styleable.styleable_edt_matrix_android_minWidth, 0));
        textView.setBackground(typedArray.getDrawable(R.styleable.styleable_edt_matrix_android_background));
        textView.setTextColor(context.getResources().getColor(R.color.textColor));
        int padding = typedArray.getDimensionPixelOffset(R.styleable.styleable_edt_matrix_android_padding, 0);
        textView.setPadding(padding, padding, padding, padding);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        typedArray.recycle();
        textView.setLayoutParams(layoutParams);
    }
}
