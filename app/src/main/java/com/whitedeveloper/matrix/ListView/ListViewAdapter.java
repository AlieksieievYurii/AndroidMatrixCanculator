package com.whitedeveloper.matrix.ListView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.whitedeveloper.matrix.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<ItemMatrices> arrayList;
    private final LayoutInflater layoutInflater;

    public ListViewAdapter(Context context, ArrayList<ItemMatrices> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View item = view;

        if(item == null)
        {
            item = layoutInflater.inflate(R.layout.item_list_view_saving,viewGroup,false);
        }

        return item;
    }
}
