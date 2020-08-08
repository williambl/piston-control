package com.williambl.pistoncontrol;

import net.minecraft.block.Block;
import net.minecraft.tag.Tag;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class InverseBlockTagMap implements Map<Block, IterableHashSet<Tag<Block>>> {
    private Map<Block, IterableHashSet<Tag<Block>>> map;

    public InverseBlockTagMap() {
        this.map = new HashMap<>();
    }

    public InverseBlockTagMap(Block key, IterableHashSet<Tag<Block>> value) {
        this();
        this.map.put(key, value);
    }

    public InverseBlockTagMap(Map<Block, IterableHashSet<Tag<Block>>> map) {
        this.map = map;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        if ((value instanceof IterableHashSet))
            return map.containsValue(value);

        if (value instanceof Tag)
            for (IterableHashSet<Tag<Block>> set : map.values())
                if (set.contains(value))
                    return true;

        return false;
    }

    public boolean containsValue(IterableHashSet<Tag<Block>> value) {
        return map.containsValue(value);
    }

    public boolean containsValue(Tag<Block> subValue) {
        for (IterableHashSet<Tag<Block>> set : map.values())
            if (set.contains(subValue))
                return true;

        return false;
    }

    @Override
    public IterableHashSet<Tag<Block>> get(Object key) {
        return map.get(key);
    }

    @Override
    public IterableHashSet<Tag<Block>> put(Block key, IterableHashSet<Tag<Block>> value) {
        if (containsKey(key))
            get(key).addAll(value);
        else
            map.put(key, value);
        return value;
    }

    @Override
    public IterableHashSet<Tag<Block>> remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends Block, ? extends IterableHashSet<Tag<Block>>> m) {
        for (Map.Entry<? extends Block, ? extends IterableHashSet<Tag<Block>>> entry : m.entrySet())
            map.put(entry.getKey(), entry.getValue());
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<Block> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<IterableHashSet<Tag<Block>>> values() {
        return map.values();
    }

    @Override
    public Set<Entry<Block, IterableHashSet<Tag<Block>>>> entrySet() {
        return map.entrySet();
    }


}
