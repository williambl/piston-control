package com.williambl.pistoncontrol;

import com.google.common.collect.Sets;

import java.util.*;

public class IterableHashSet <T> implements Set<T> {
    private final Set<T> set;
    private final List<T> list;

    public IterableHashSet() {
        this.set = new HashSet<>();
        this.list = new ArrayList<>();
    }

    public IterableHashSet(Collection<T> collection) {
        this.set = new HashSet<>(collection);
        this.list = new ArrayList<>(collection);
    }

    public IterableHashSet(T entry) {
        this.set = Sets.newHashSet(entry);
        this.list = Collections.singletonList(entry);
    }

    public boolean intersects(IterableHashSet<T> other) {
        IterableHashSet<T> smallerSet;
        IterableHashSet<T> largerSet;

        if(this.size() > other.size()) {
            largerSet = this;
            smallerSet = other;
        } else {
            largerSet = other;
            smallerSet = this;
        }

        for (T entry : smallerSet.list)
            if (largerSet.contains(entry))
                return true;

        return false;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        //make new object so this object isn't modifiable
        return new ArrayList<>(list).iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public boolean add(T t) {
        if (t == null) return false;
        boolean modified = set.add(t);
        if (modified) list.add(t);
        return modified;
    }

    @Override
    public boolean remove(Object o) {
        boolean modified = set.remove(o);
        if (modified) list.remove(o);
        return modified;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        list.addAll(Sets.difference(set, new HashSet<T>(c)));
        return set.addAll(c);
    }

    @Override
    public void clear() {
        set.clear();
        list.clear();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = set.removeAll(c);
        if (modified) list.removeAll(c);
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = set.retainAll(c);
        if (modified) list.retainAll(c);
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return set.containsAll(c);
    }

    @Override
    public Object[] toArray(Object[] a) {
        return list.toArray(a);
    }
}
