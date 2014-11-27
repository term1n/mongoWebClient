package ru.spb.iac.utils.converter.impl;

import org.springframework.stereotype.*;
import ru.spb.iac.ui.models.database.*;
import ru.spb.iac.utils.converter.*;

import java.util.*;

/**
 * Created by manaev on 26.11.14.
 */
@Service
public class LmConverterImpl implements LMConverter<DatabaseInfo,String,List<String>> {
    @Override
    public List<DatabaseInfo> convert(Map<String, List<String>> map) {
        List<DatabaseInfo> list = new ArrayList<DatabaseInfo>();
        for(Map.Entry<String, List<String>> entry: map.entrySet()){
            list.add(new DatabaseInfo(entry.getKey(),entry.getValue()));
        }
        return list;
    }

    @Override
    public Map<String, List<String>> endure(List<DatabaseInfo> list) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for(DatabaseInfo entry: list){
            map.put(entry.getDbName(),entry.getCollectionNames());
        }
        return map;
    }
}
