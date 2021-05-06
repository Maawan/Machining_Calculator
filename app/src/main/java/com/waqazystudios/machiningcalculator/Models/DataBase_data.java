package com.waqazystudios.machiningcalculator.Models;

public class DataBase_data {
    private String title;
    private float value;
    private String units;
    public DataBase_data(){

    }
    public DataBase_data(String title , float value , String units){
        this.title = title;
        this.value = value;
        this.units = units;
    }

    public String getTitle() {
        return title;
    }

    public float getValue() {
        return value;
    }

    public String getUnits() {
        return units;
    }
}
