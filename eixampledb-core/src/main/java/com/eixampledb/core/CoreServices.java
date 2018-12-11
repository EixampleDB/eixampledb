package com.eixampledb.core;


public class CoreServices {
    public static final int NUM_TYPE = 1;

    public String operation_increment(String key, String value){

        if(value.contains(".")){
            value = (Double.parseDouble(value)+1.) + "";
        }else{
            value = (Long.parseLong(value)+1) + "";
        }

        return value;
    }

    public String operation_decrement(String key, String value){

        if(value.contains(".")){
            value = (Double.parseDouble(value)-1.) + "";
        }else{
            value = (Long.parseLong(value)-1) + "";
        }

        return value;
    }
}
