package Templates;

import java.util.Map;

public class myMapLong<U> {
    Map<U, Long> map;
    long dummy;

    public myMapLong(Map<U, Long> m) {
        map = m;
        dummy = 0;
    }

    public Long get(U key) {
        return map.getOrDefault(key, dummy);
    }

    public void put(U key, Long val) {
        map.put(key, val);
    }

    public void remove(U key) {
        map.remove(key);
    }

    public void add(U key, long val) {
        long newVal = get(key) + val;
        if (newVal == dummy) {
            remove(key);
        } else {
            put(key, newVal);
        }
    }

    @Override
    public String toString() {
        return map.toString();
    }
}

