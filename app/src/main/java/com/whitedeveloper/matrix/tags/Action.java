package com.whitedeveloper.matrix.tags;

public enum Action
{
    ADDITION("addition",TagKeys.TYPE_MENU_A_B_R),
    SUBTRACTION("subtraction", TagKeys.TYPE_MENU_A_B_R),
    MULTIPLICATION("multiplication",TagKeys.TYPE_MENU_A_B_R),
    DETERMINATION("determination",TagKeys.TYPE_MENU_M_R),
    TRANSPOSING("transposing",TagKeys.TYPE_MENU_M_R),
    INVERSION("inversion",TagKeys.TYPE_MENU_M_R),
    SEPARATION("separation",TagKeys.TYPE_MENU_M_R),
    MULTIPLICATION_BY_NUMBER("multiplication by number",TagKeys.TYPE_MENU_M_R);

    private String nameAction;
    private int typeMenu;

    Action(String nameAction, int typeMenu) {
        this.nameAction = nameAction;
        this.typeMenu = typeMenu;
    }

    @Override
    public String toString() {
        return nameAction;
    }

    public boolean equals(String nameAction){
        return this.nameAction.equals(nameAction);
    }

    public static Action findByName(String name){
        for(Action v : values()){
            if(v.nameAction.equals(name)){
                return v;
            }
        }
        return null;
    }

    public int getTypeMenu() {
        return typeMenu;
    }
}
