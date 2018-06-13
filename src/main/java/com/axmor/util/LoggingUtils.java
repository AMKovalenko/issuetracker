package com.axmor.util;

import org.slf4j.Logger;

public class LoggingUtils {

    public static void startMethodDebug(Logger logger, String methodName, Object params){
       logger.debug("-------======= Started method {}. =======-------\n Input params {}", methodName, params);
       if (params != null){
           logger.debug("Input params {}", params);
       }
    }

    public static void finishMethodDebug(Logger logger, String methodName, Object result){
        logger.debug("-------======= Finished method {}. =======-------\n ", methodName);
        if (result != null){
            logger.debug("Result: {}", result);
        }
    }

}
