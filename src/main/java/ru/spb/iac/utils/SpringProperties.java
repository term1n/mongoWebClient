package ru.spb.iac.utils;

import org.springframework.beans.*;
import org.springframework.beans.factory.config.*;

import java.util.*;

/**
 * Created by manaev on 06.11.14.
 */
public class SpringProperties extends PropertyPlaceholderConfigurer {
    private Map<String, String> propertiesMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
        super.processProperties(beanFactory, props);

        propertiesMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String valueStr = resolvePlaceholder(keyStr, props, SYSTEM_PROPERTIES_MODE_FALLBACK);
            propertiesMap.put(keyStr, valueStr);
        }
    }

    public Map<String, String> getPropertiesMap() {
        return propertiesMap;
    }
}