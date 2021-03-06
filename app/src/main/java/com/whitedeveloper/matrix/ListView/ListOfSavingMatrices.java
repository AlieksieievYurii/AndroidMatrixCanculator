package com.whitedeveloper.matrix.ListView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.whitedeveloper.matrix.R;
import com.whitedeveloper.matrix.instance.SavingInstance;
import com.whitedeveloper.matrix.tags.TagKeys;

import java.util.ArrayList;


public class ListOfSavingMatrices extends AlertDialog {

    public interface CallBack {
        void selectedItem(String nameSaving);

        void selectedMatrixA(String nameSaving);

        void selectedMatrixB(String nameSaving);

        void selectedMatrix(String nameSaving);

        void removeItem(String nameSaving);

        void showItem(String nameSaving);
    }

    private CallBack callBack;
    private ArrayList<ItemMatrices> arrayList;
    private ListViewAdapter arrayAdapter;

    ListOfSavingMatrices(@NonNull Context context, CallBack callBack) {

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
        final SwipeMenuListView lvSaving = findViewById(R.id.ls_saving);
        lvSaving.setMenuCreator(new MenuCreator(getContext()));

        arrayList = new ArrayList<>();
        arrayList.addAll(SavingInstance.getAllSaving(getContext()));

        arrayAdapter = new ListViewAdapter(getContext(), arrayList);
        lvSaving.setAdapter(arrayAdapter);

        lvSaving.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (menu.getViewType()) {
                    case TagKeys.TYPE_MENU_A_B_R:
                        enterListenerABR(index, position);
                        break;
                    case TagKeys.TYPE_MENU_M_R:
                        enterListenerMR(index, position);
                        break;
                }

                return true;
            }

            private void enterListenerABR(int index, int position) {
                switch (index) {
                    case 0:
                        callBack.selectedMatrixA(arrayList.get(position).getNameSaving());
                        break;
                    case 1:
                        callBack.selectedMatrixB(arrayList.get(position).getNameSaving());
                        break;
                    case 2:
                        callBack.removeItem(arrayList.get(position).getNameSaving());
                        arrayList.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        break;
                    case 3:
                        callBack.showItem(arrayList.get(position).getNameSaving());
                        break;
                }
            }

            private void enterListenerMR(int index, int position) {
                switch (index) {
                    case 0:
                        callBack.selectedMatrix(arrayList.get(position).getNameSaving());
                        break;
                    case 1:
                        callBack.removeItem(arrayList.get(position).getNameSaving());
                        arrayList.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        break;
                    case 2:
                        callBack.showItem(arrayList.get(position).getNameSaving());
                        break;
                }
            }
        });

        lvSaving.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                callBack.selectedItem(arrayList.get(i).getNameSaving());
            }
        });


        lvSaving.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

    }
}
