package Templates;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class MyMapLong<U> {
    Map<U, Long> map;
    long dummy;

    public MyMapLong(Map<U, Long> m) {
        map = m;
        dummy = 0;
    }

    public int size() {
        return map.size();
    }

    public Long get(U key) {
        return map.getOrDefault(key, dummy);
    }

    public void put(U key, Long val) {
        map.put(key, val);
    }

    public Long remove(Object key) {
        return map.remove(key);
    }

    public boolean containsKey(U key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Long val) {
        return map.containsValue(val);
    }

    public void add(U key, long val) {
        long newVal = get(key) + val;
        if (newVal == dummy) {
            remove(key);
        } else {
            put(key, newVal);
        }
    }

    public Set<Map.Entry<U, Long>> entrySet() {
        return map.entrySet();
    }

    public Set<U> keySet() {
        return map.keySet();
    }

    public Collection<Long> values() {
        return map.values();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}