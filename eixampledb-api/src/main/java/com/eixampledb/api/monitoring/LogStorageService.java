package com.eixampledb.api.monitoring;


public interface LogStorageService {

    public abstract void infoMessage(String info);
    public abstract void errorMessage(String error);
    public abstract void warningMessage(String warning);


}
