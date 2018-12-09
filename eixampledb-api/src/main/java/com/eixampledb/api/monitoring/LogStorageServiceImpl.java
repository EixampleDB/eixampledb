package com.eixampledb.api.monitoring;


import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Service
public class LogStorageServiceImpl implements LogStorageService{

    private Logger logger = LogManager.getLogger(LogStorageServiceImpl.class);

    private String[] messages;


    @Override
    public void infoMessage(String info) {

        logger.traceEntry();

        logger.info(info);

        logger.traceExit();
    }

    @Override
    public void errorMessage(String error) {

        logger.traceEntry();

        logger.error(error);

        logger.traceExit();
    }

    @Override
    public void warningMessage(String warning) {

        logger.traceEntry();

        logger.warn(warning);

        logger.traceExit();
    }



}
