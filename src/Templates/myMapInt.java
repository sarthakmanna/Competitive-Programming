package Templates;

import java.util.Map;

public class myMapInt<U> {
    Map<U, Integer> map;
    int dummy;

    public myMapInt(Map<U, Integer> m) {
        map = m;
        dummy = 0;
    }

    public Integer get(U key) {
        return map.getOrDefault(key, dummy);
    }

    public void put(U key, Integer val) {
        map.put(key, val);
    }

    public void remove(U key) {
        map.remove(key);
    }

    public void add(U key, int val) {
        int newVal = get(key) + val;
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
