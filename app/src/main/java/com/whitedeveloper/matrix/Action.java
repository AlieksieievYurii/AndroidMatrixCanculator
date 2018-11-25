package com.whitedeveloper.matrix;

public enum Action
{
    ADDITION("addition"),
    SUBTRACTION("subtraction"),
    MULTIPLICATION("multiplication"),
    DETERMINATION("determination"),
    TRANSPOSING("transposing"),
    INVERSION("inversion");

    private String nameAction;

    Action(String nameAction) {
        this.nameAction = nameAction;
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
}
