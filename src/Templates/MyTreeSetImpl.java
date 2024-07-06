package Templates;

import java.util.*;

public class MyTreeSetImpl extends MyTreeMap implements Set<Long> {
    public MyTreeSetImpl() {
        super();
    }

    public MyTreeSetImpl(boolean allowDupli) {
        super(allowDupli);
    }

    public MyTreeSetImpl(Comparator<Long> com) {
        super(com);
    }

    public MyTreeSetImpl(boolean allowDupli, Comparator<Long> com) {
        super(allowDupli, com);
    }

    @Override
    public boolean contains(Object o) {
        return super.containsKey((long) o);
    }

    private int index;

    @Override
    public Object[] toArray() {
        Object[] ret = new Object[super.size()];
        index = 0;
        super.getIteratorOverNodes().forEachRemaining(node -> ret[index++] = node.value);
        return ret;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(Long key) {
        return super.add(key);
    }

    @Override
    public boolean remove(Object o) {
        return super.remove((long) o);
    }

    private boolean containsAll;

    @Override
    public boolean containsAll(Collection<?> c) {
        containsAll = true;
        c.forEach(key -> containsAll = super.containsKey((long) key) && containsAll);
        return containsAll;
    }

    private boolean addAll;

    @Override
    public boolean addAll(Collection<? extends Long> c) {
        addAll = false;
        c.forEach(key -> addAll = super.add(key) || addAll);
        return addAll;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        ArrayList<Long> retainedElements = new ArrayList<>();
        c.forEach(key -> {
            if (super.containsKey((long) key)) retainedElements.add((long) key);
        });
        boolean listChanged = retainedElements.size() != size();
        clear();
        addAll(retainedElements);
        return listChanged;
    }

    private boolean removeAll;

    @Override
    public boolean removeAll(Collection<?> c) {
        removeAll = false;
        c.forEach(key -> removeAll = remove(key) || removeAll);
        return removeAll;
    }

    @Override
    public Iterator<Long> iterator() {
        Iterator<TreeMapNode> iterator = getIteratorOverNodes();
        return new Iterator<Long>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Long next() {
                return iterator.next().key;
            }
        };
    }

    @Override
    public void clear() {
        super.root = null;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }
}
