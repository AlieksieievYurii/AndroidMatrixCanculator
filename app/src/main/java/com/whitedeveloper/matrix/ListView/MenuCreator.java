package com.whitedeveloper.matrix.ListView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.whitedeveloper.matrix.R;
import com.whitedeveloper.matrix.tags.TagKeys;

public class MenuCreator implements SwipeMenuCreator {

    private final Context context;

    MenuCreator(Context context) {
        this.context = context;
    }

    @Override
    public void create(SwipeMenu menu) {
        switch (menu.getViewType()) {
            case TagKeys.TYPE_MENU_A_B_R:
                menu.addMenuItem(getItemMatrixA());
                menu.addMenuItem(getItemMatrixB());
                break;
            case TagKeys.TYPE_MENU_M_R:
                menu.addMenuItem(getItemMatrix());
                break;
            case TagKeys.TYPE_MENU_M:
                menu.addMenuItem(getItemMatrix());
                break;
        }
        menu.addMenuItem(getItemRemoving());
        menu.addMenuItem(getItemShowing());

    }

    private SwipeMenuItem getItemShowing() {
        SwipeMenuItem swipeMenuItem = new SwipeMenuItem(context);
        swipeMenuItem.setBackground(new ColorDrawable(ContextCompat.getColor(context, R.color.green)));
        swipeMenuItem.setWidth(200);
        swipeMenuItem.setTitle(context.getString(R.string.show));
        swipeMenuItem.setTitleSize(12);
        swipeMenuItem.setTitleColor(Color.WHITE);
        return swipeMenuItem;
    }

    private SwipeMenuItem getItemRemoving() {
        SwipeMenuItem swipeMenuItem = new SwipeMenuItem(context);
        swipeMenuItem.setBackground(new ColorDrawable(ContextCompat.getColor(context, R.color.red_item)));
        swipeMenuItem.setWidth(200);
        swipeMenuItem.setTitle(context.getString(R.string.remove));
        swipeMenuItem.setTitleSize(12);
        swipeMenuItem.setTitleColor(Color.WHITE);
        return swipeMenuItem;
    }

    private SwipeMenuItem getItemMatrixA() {
        SwipeMenuItem swipeMenuItem = new SwipeMenuItem(context);
        swipeMenuItem.setBackground(new ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent)));
        swipeMenuItem.setWidth(200);
        swipeMenuItem.setTitle(context.getString(R.string.item_matrix_a));
        swipeMenuItem.setTitleSize(12);
        swipeMenuItem.setTitleColor(Color.WHITE);
        return swipeMenuItem;
    }

    private SwipeMenuItem getItemMatrixB() {
        SwipeMenuItem swipeMenuItem = new SwipeMenuItem(context);
        swipeMenuItem.setBackground(new ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent)));
        swipeMenuItem.setWidth(200);
        swipeMenuItem.setTitle(context.getString(R.string.item_matrix_b));
        swipeMenuItem.setTitleSize(12);
        swipeMenuItem.setTitleColor(Color.WHITE);
        return swipeMenuItem;
    }

    private SwipeMenuItem getItemMatrix() {
        SwipeMenuItem swipeMenuItem = new SwipeMenuItem(context);
        swipeMenuItem.setBackground(new ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent)));
        swipeMenuItem.setWidth(200);
        swipeMenuItem.setTitle(context.getString(R.string.item_matrix));
        swipeMenuItem.setTitleSize(12);
        swipeMenuItem.setTitleColor(Color.WHITE);
        return swipeMenuItem;
    }
}
