package com.whitedeveloper.matrix.ListView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;

public class MenuCreator implements SwipeMenuCreator
{

    private final Context context;

    public MenuCreator(Context context) {
        this.context = context;
    }

    @Override
    public void create(SwipeMenu menu)
    {
        menu.addMenuItem(getItemRemoving());
        menu.addMenuItem(getItemShowing());
    }

    private SwipeMenuItem getItemShowing()
    {
        SwipeMenuItem swipeMenuItem = new SwipeMenuItem(context);
        swipeMenuItem.setBackground(new ColorDrawable(Color.GREEN));
        swipeMenuItem.setWidth(200);
        swipeMenuItem.setTitle("Show");
        swipeMenuItem.setTitleSize(12);
        swipeMenuItem.setTitleColor(Color.WHITE);
        return swipeMenuItem;
    }

    private SwipeMenuItem getItemRemoving()
    {
        SwipeMenuItem swipeMenuItem = new SwipeMenuItem(context);
                swipeMenuItem.setBackground(new ColorDrawable(Color.RED));
                swipeMenuItem.setWidth(200);
                swipeMenuItem.setTitle("Remove");
                swipeMenuItem.setTitleSize(12);
                swipeMenuItem.setTitleColor(Color.WHITE);
        return swipeMenuItem;
    }
}
