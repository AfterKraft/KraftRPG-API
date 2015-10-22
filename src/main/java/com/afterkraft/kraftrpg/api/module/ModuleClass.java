/*
 * Copyright (c) 2015 VoxelBox <http://engine.thevoxelbox.com>.
 * All Rights Reserved.
 */
package com.afterkraft.kraftrpg.api.module;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

public class ModuleClass {

    private final Class<? extends IModule> moduleClass;
    private final Class<? extends IModule> providedInterface;
    private final List<Class<? extends IModule>> dependencies;

    @SuppressWarnings("unchecked")
    public ModuleClass(Class<? extends IModule> cls) {
        this.moduleClass = cls;
        this.dependencies = Lists.newArrayList();
        if (cls.isAnnotationPresent(DependsOn.class)) {
            DependsOn depend = cls.getAnnotation(DependsOn.class);
            for (Class<?> d : depend.value()) {
                this.dependencies.add((Class<? extends IModule>) d);
            }
        }
        if (cls.isAnnotationPresent(Provides.class)) {
            Provides provided = cls.getAnnotation(Provides.class);
            // validate as interface?
            this.providedInterface = provided.value();
        } else {
            this.providedInterface = null;
        }
    }

    public boolean isAbstract() {
        return Modifier.isAbstract(this.moduleClass.getModifiers());
    }

    public Class<? extends IModule> getModuleClass() {
        return this.moduleClass;
    }

    public Iterable<Class<? extends IModule>> getDependencies() {
        return this.dependencies;
    }

    public Optional<Class<? extends IModule>> getProvidedClass() {
        return Optional.ofNullable(this.providedInterface);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ModuleClass)) {
            return false;
        }
        ModuleClass mc = (ModuleClass) o;
        return mc.moduleClass.equals(this.moduleClass);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("class", this.moduleClass.getName()).toString();
    }

    @Override
    public int hashCode() {
        return this.moduleClass.hashCode();
    }

}
