package Templates;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class myMapInt<U> {
    Map<U, Integer> map;
    int dummy;

    public myMapInt(Map<U, Integer> m) {
        map = m;
        dummy = 0;
    }

    public int size() {
        return map.size();
    }

    public Integer get(U key) {
        return map.getOrDefault(key, dummy);
    }

    public void put(U key, Integer val) {
        map.put(key, val);
    }

    public Integer remove(Object key) {
        return map.remove(key);
    }

    public boolean containsKey(U key) {
        return map.containsKey(key);
    }

    public boolean containsValue(Integer val) {
        return map.containsValue(val);
    }

    public void add(U key, int val) {
        int newVal = get(key) + val;
        if (newVal == dummy) {
            remove(key);
        } else {
            put(key, newVal);
        }
    }

    public Set<Map.Entry<U, Integer>> entrySet() {
        return map.entrySet();
    }

    public Set<U> keySet() {
        return map.keySet();
    }

    public Collection<Integer> values() {
        return map.values();
    }

    @Override
    public String toString() {
        return map.toString();
    }
}