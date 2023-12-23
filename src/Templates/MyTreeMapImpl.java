package Templates;

import java.util.*;

public class MyTreeMapImpl extends MyTreeMap implements Map<Long, Long> {
    @Override
    public boolean containsKey(Object key) {
        return containsKey((long) key);
    }

    @Override
    public boolean containsValue(Object value) {
        for (long itr : values()) {
            if (itr == (long) value) return true;
        }
        return false;
    }

    @Override
    public Long get(Object key) {
        return get((long) key);
    }

    @Override
    public Long put(Long key, Long value) {
        return put(key, value);
    }

    @Override
    public Long remove(Object key) {
        Map.Entry<Long, Long> pair = floor((long) key);
        return remove((long) key) ? pair.getValue() : null;
    }

    @Override
    public void putAll(Map<? extends Long, ? extends Long> m) {
        m.entrySet().forEach(entry -> put(entry.getKey(), entry.getValue()));
    }

    @Override
    public void clear() {
        super.root = null;
    }

    @Override
    public Set<Long> keySet() {
        LinkedHashSet<Long> keys = new LinkedHashSet<>();
        super.getIteratorOverNodes().forEachRemaining(node -> keys.add(node.key));
        return keys;
    }

    @Override
    public Collection<Long> values() {
        List<Long> values = new ArrayList<>();
        super.getIteratorOverNodes().forEachRemaining(node -> values.add(node.value));
        return values;
    }

    @Override
    public Set<Entry<Long, Long>> entrySet() {
        LinkedHashSet<Entry<Long, Long>> entries = new LinkedHashSet<>();
        super.getIteratorOverNodes().forEachRemaining(node -> entries.add(new AbstractMap.SimpleEntry<>(node.key, node.value)));
        return entries;
    }
}
