package com.afterkraft.kraftrpg.api.module;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Context {

    public static Context getRootContext() {
        return Root.INSTANCE;
    }

    private final String name;
    private final Context parent;
    private final List<Context> children = Lists.newArrayList();
    private final Map<Class<?>, Object> singles = Maps.newHashMap();
    private final Set<Contextable> contextable = Sets.newHashSet();

    private Context(String name) {
        this.name = name;
        this.parent = null;
    }

    public Context(String name, Context parent) {
        this.name = name;
        this.parent = parent;
    }

    public String getName() {
        return this.name;
    }

    protected void addChild(Context c) {
        this.children.add(c);
    }

    protected void removeChild(Context c) {
        this.children.remove(c);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> cls) {
        if (this.singles.containsKey(cls)) {
            return (T) this.singles.get(cls);
        }
        T p = this.parent.get(cls);
        if (p != null) {
            return p;
        }
        for (Context c : this.children) {
            p = c.get(cls);
            if (p != null) {
                return p;
            }
        }
        return null;
    }

    public void putObject(Object c) {
        this.singles.put(c.getClass(), c);
        if(c instanceof Contextable) {
            this.contextable.add((Contextable) c);
        }
    }

    public void putObject(Class<?> prov, Object obj) {
        this.singles.put(prov, obj);
        if(obj instanceof Contextable) {
            this.contextable.add((Contextable) obj);
        }
    }

    public void put(Contextable c) {
        this.contextable.add(c);
        if (c.getClass().isAnnotationPresent(Singleton.class)) {
            this.singles.put(c.getClass(), c);
        }
    }

    public boolean has(Class<?> prov) {
        return this.singles.containsKey(prov);
    }

    public void release() {
        for (Context c : this.children) {
            c.release();
        }
        this.children.clear();
        this.singles.clear();
        for (Contextable c : this.contextable) {
            c.release();
        }
        this.contextable.clear();
        this.parent.removeChild(this);
    }

    public void release(Contextable c) {
        if(c == null) return;
        c.release();
        this.contextable.remove(c);
        if(this.singles.containsKey(c.getClass()) && this.singles.get(c.getClass()).equals(c)) {
            this.singles.remove(c.getClass());
        }
    }

    private static final class Root {
        private static final Context INSTANCE = new Context("root");
    }
}
