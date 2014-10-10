package ru.spb.iac.utils;

import org.springframework.stereotype.Service;

/**
 * @author ismakaev
 *         Date: 10.10.14
 */
@Service("commonUtils")
public class CommonUtils {
    public String convertToKey(String host, Integer port){
        return host + ":" + String.valueOf(port);
    }
}
