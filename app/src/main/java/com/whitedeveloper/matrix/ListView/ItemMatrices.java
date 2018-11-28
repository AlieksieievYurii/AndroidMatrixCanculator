package com.whitedeveloper.matrix.ListView;


import com.whitedeveloper.matrix.Action;

public class ItemMatrices
{
    private String nameSaving;
    private Action action;
    private String resultItem;
    private int countRows;
    private int countColumns;

    public String getNameSaving() {
        return nameSaving;
    }

    public void setNameSaving(String nameSaving) {
        this.nameSaving = nameSaving;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getResultItem() {
        return resultItem;
    }

    public void setResultItem(String resultItem) {
        this.resultItem = resultItem;
    }

    public int getCountRows() {
        return countRows;
    }

    public void setCountRows(int countRows) {
        this.countRows = countRows;
    }

    public int getCountColumns() {
        return countColumns;
    }

    public void setCountColumns(int countColumns) {
        this.countColumns = countColumns;
    }

    public ItemMatrices(String nameSaving, Action action, int countRows, int countColumns) {
        this.nameSaving = nameSaving;
        this.action = action;
        this.countRows = countRows;
        this.countColumns = countColumns;
    }

    public ItemMatrices() {
    }

    public ItemMatrices(String nameSaving, Action action, String resultItem) {
        this.nameSaving = nameSaving;
        this.action = action;
        this.resultItem = resultItem;
    }
}
