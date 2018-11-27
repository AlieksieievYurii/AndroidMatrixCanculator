package com.whitedeveloper.matrix.ListView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.whitedeveloper.matrix.R;
import com.whitedeveloper.matrix.instance.SavingInstance;

import java.util.ArrayList;


public class ListOfSavingMatrices extends Dialog {

    public interface CallBack {
        void selectedItem(String name);

        void removeItem(String name);

        void showItem(String name);
    }

    private SwipeMenuListView lvSaving;
    private CallBack callBack;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;

    public ListOfSavingMatrices(@NonNull Context context, CallBack callBack) {

        super(context);
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog_list_saving);
        init();

    }

    private void init() {
        lvSaving = findViewById(R.id.ls_saving);
        lvSaving.setMenuCreator(new MenuCreator(getContext()));

        arrayList = new ArrayList<>();
        arrayList.addAll(SavingInstance.getAllSaving(getContext()));

        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, arrayList);
        lvSaving.setAdapter(arrayAdapter);

        lvSaving.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        callBack.removeItem(arrayList.get(position));
                        arrayList.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        break;

                    case 1:
                        callBack.showItem(arrayList.get(position));
                        break;
                }
                return false;
            }
        });

        lvSaving.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                callBack.selectedItem(arrayList.get(i));
            }
        });


        lvSaving.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

    }
}
