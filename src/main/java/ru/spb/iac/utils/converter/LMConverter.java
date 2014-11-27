package ru.spb.iac.utils.converter;

import java.util.*;

/**
 * Created by manaev on 26.11.14.
 */
public interface LMConverter<C,K,V> {
    public List<C> convert(Map<K,V> map);
    public Map<K,V> endure(List<C> list);
}
