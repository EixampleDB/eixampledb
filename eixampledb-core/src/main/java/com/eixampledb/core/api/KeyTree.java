package com.eixampledb.core.api;

import java.util.TreeSet;
import java.util.NavigableSet;
import java.util.Collection;

public class KeyTree {

    private TreeSet<String> keys;

    public KeyTree() {
        this.keys = new TreeSet<>();
    }

    // add when not present
    public boolean add(String key) {
        return keys.add(key);
    }

    // add when not present
    public boolean add(Collection<String> key) {
        return keys.addAll(key);
    }

    // delete if present
    public boolean delete(String key) {
        return keys.remove(key);
    }

    public void clear(){
        keys.clear();
    }

    public NavigableSet<String> withPrefix(String prefixS){
        String prefixE = "";

        int i;
        // iterate over the prefix
        for(i = prefixS.length()-1; i >= 0; i--){
            // if we dont find the biggest char posible, we get the next biggest one to replace it
            if(prefixS.charAt(i) != '\uffff'){
                prefixE = prefixS.substring(0,i)+(char)(prefixS.charAt(i)+1)+prefixS.substring(i+1);
                break;
            }
        }
        if(i==-1){ // this happens if all char is prefix were the biggest char posible
            return keys.tailSet(prefixS,true);
        }
        else{       // we set the boundaries to find all keys between the given prefix and the result of last iteration
            return keys.subSet(prefixS,true,prefixE,false);
        }
    }
}