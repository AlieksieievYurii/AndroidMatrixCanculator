package com.whitedeveloper.matrix.ListView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.whitedeveloper.matrix.tags.Action;
import com.whitedeveloper.matrix.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    private final ArrayList<ItemMatrices> arrayList;
    private final LayoutInflater layoutInflater;

    ListViewAdapter(Context context, ArrayList<ItemMatrices> arrayList) {
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

    @SuppressLint("DefaultLocale")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View item = view;
        if(item == null)
            item = layoutInflater.inflate(R.layout.item_list_view_saving,viewGroup,false);

        TextView tvNameSaving = item.findViewById(R.id.tv_name_saving);
        TextView tvNameAction = item.findViewById(R.id.tv_action);
        TextView tvResult = item.findViewById(R.id.tv_result);

        tvNameSaving.setText(arrayList.get(i).getNameSaving());
        tvNameAction.setText(arrayList.get(i).getAction().toString());

        if(arrayList.get(i).getAction() == Action.DETERMINATION)
            tvResult.setText(arrayList.get(i).getResultItem());
        else
            tvResult.setText(String.format("%dx%d",arrayList.get(i).getCountRows(),arrayList.get(i).getCountColumns()));

        return item;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position)
    {
        return arrayList.get(position).getAction().getTypeMenu();
    }
}
