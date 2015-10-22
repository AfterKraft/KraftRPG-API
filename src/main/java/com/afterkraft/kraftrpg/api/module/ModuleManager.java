/*
 * Copyright (c) 2015 VoxelBox <http://engine.thevoxelbox.com>.
 * All Rights Reserved.
 */
package com.afterkraft.kraftrpg.api.module;

import com.afterkraft.kraftrpg.api.RpgCommon;
import com.afterkraft.kraftrpg.api.util.graph.DataNode;
import com.afterkraft.kraftrpg.api.util.graph.GraphOperations;
import com.afterkraft.kraftrpg.api.util.graph.directed.DirectedGraph;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thevoxelbox.genesis.config.Configuration;
import com.thevoxelbox.genesis.config.ConfigurationManager;
import com.thevoxelbox.genesis.game.Game;
import com.thevoxelbox.launch.scanner.AnnotationScanner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ModuleManager {

    private final Game gameInstance;

    private final Map<Class<? extends IModule>, ModuleClass> modcls;
    private final Map<Class<? extends IModule>, Class<? extends IModule>> generics;
    private final Map<Class<? extends IModule>, IModule> modules;
    private final Map<Class<? extends IModule>, Context> modContexts;
    private List<ModuleClass> loadOrder;

    public ModuleManager(Game game) {
        this.gameInstance = game;
        this.modcls = Maps.newHashMap();
        this.modules = Maps.newHashMap();
        this.generics = Maps.newHashMap();
        this.modContexts = Maps.newHashMap();
    }

    public void use(Class<? extends IModule> moduleClass) {
        if (!this.modcls.containsKey(moduleClass)) {
            this.modcls.put(moduleClass, new ModuleClass(moduleClass));
        }
        ModuleClass module = this.modcls.get(moduleClass);
        for (Class<? extends IModule> dependency : module.getDependencies()) {
            if (!this.modcls.containsKey(dependency)) {
                use(dependency);
            }
        }
        if (module.getProvidedClass().isPresent()) {
            this.generics.put(module.getProvidedClass().get(), moduleClass);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends IModule> Optional<T> getModule(Class<T> cls) {
        return Optional.ofNullable((T) this.modules.get(cls));
    }

    public synchronized void init() {
        this.gameInstance.getContext().putObject(this);
        this.gameInstance.getContext().putObject(Game.class, this.gameInstance);
        this.gameInstance.getContext().putObject(AnnotationScanner.getInstance());

        DirectedGraph<DataNode<ModuleClass>> dependencyGraph = createModuleGraph();
        if (GraphOperations.hasCycle(dependencyGraph)) {
            throw new IllegalStateException("Modules have cyclical dependencies");
        }

        createLoadOrderFromGraph(dependencyGraph);

        for (ModuleClass mod : this.loadOrder) {
            try {
                IModule obj = mod.getModuleClass().newInstance();
                this.modules.put(mod.getModuleClass(), obj);
                if (mod.getProvidedClass().isPresent()) {
                    Class<? extends IModule> prov = mod.getProvidedClass().get();
                    if (this.gameInstance.getContext().has(prov)) {
                        RpgCommon.getLogger().warn("Multiple modules attempting to provide %s, contenders are %s and %s", prov.getName(),
                                                       this.gameInstance.getContext().get(prov).getClass().getName(), mod.getModuleClass().getName());
                    }
                    this.gameInstance.getContext().putObject(prov, obj);
                    this.modules.put(prov, obj);
                }
                this.gameInstance.getContext().putObject(mod.getModuleClass(), obj);
                Context modContext = new Context(mod.getModuleClass().getSimpleName(), this.gameInstance.getContext());
                this.modContexts.put(mod.getModuleClass(), modContext);
                for (Field f : obj.getClass().getDeclaredFields()) {
                    if (Modifier.isStatic(f.getModifiers())) {
                        continue;
                    }
                    if (f.isAnnotationPresent(ContextBased.class)) {
                        if (!f.isAccessible()) {
                            f.setAccessible(true);
                        }
                        if (f.getType().isAnnotationPresent(Configuration.class)) {
                            Object config = ConfigurationManager.getInstance().init(f.getType());
                            f.set(obj, config);
                            continue;
                        }
                        if (Context.class.equals(f.getType())) {
                            f.set(obj, modContext);
                            continue;
                        }
                        if (this.gameInstance.getContext().has(f.getType())) {
                            f.set(obj, this.gameInstance.getContext().get(f.getType()));
                        } else {
                            RpgCommon.getLogger().warn("Module " + mod.getModuleClass().getName()
                                                           + " has unfulfilled contextbased fields of type " + f.getType().getName());
                        }
                    }
                }
                obj.start();
            } catch (Exception e) {
                RpgCommon.getLogger().error(e, "Error instanciating module: " + mod.getModuleClass().getName());
            }
        }
    }

    private DirectedGraph<DataNode<ModuleClass>> createModuleGraph() {
        DirectedGraph<DataNode<ModuleClass>> dependencyGraph = new DirectedGraph<>();
        Map<ModuleClass, DataNode<ModuleClass>> moduleNodes = Maps.newHashMap();
        for (Map.Entry<Class<? extends IModule>, ModuleClass> mc : this.modcls.entrySet()) {
            if (Modifier.isAbstract(mc.getKey().getModifiers())) {
                continue;
            }
            DataNode<ModuleClass> node = new DataNode<>(mc.getValue());
            moduleNodes.put(mc.getValue(), node);
            dependencyGraph.add(node);
        }
        for (ModuleClass mc : this.modcls.values()) {
            DataNode<ModuleClass> node = moduleNodes.get(mc);
            for (Class<? extends IModule> dep : mc.getDependencies()) {
                if (Modifier.isAbstract(dep.getModifiers())) {
                    if (this.generics.containsKey(dep)) {
                        Class<? extends IModule> prov = this.generics.get(dep);
                        ModuleClass dmc = this.modcls.get(prov);
                        DataNode<ModuleClass> dnode = moduleNodes.get(dmc);
                        dependencyGraph.addEdge(node, dnode);
                        continue;
                    }
                    RpgCommon.getLogger().error(mc.getModuleClass().getName() + " has unfulfilled abstract dependency " + dep.getName());
                    continue;
                }
                ModuleClass dmc = this.modcls.get(dep);
                DataNode<ModuleClass> dnode = moduleNodes.get(dmc);
                dependencyGraph.addEdge(node, dnode);
            }
        }
        return dependencyGraph;
    }

    private void createLoadOrderFromGraph(DirectedGraph<DataNode<ModuleClass>> dependencyGraph) {
        this.loadOrder = Lists.newArrayList();
        while (dependencyGraph.getNodeCount() != 0) {
            DataNode<ModuleClass> next = null;
            for (DataNode<ModuleClass> n : dependencyGraph.getNodes()) {
                if (n.getEdgeCount() == 0) {
                    next = n;
                }
            }
            if (next == null) {
                // Shouldn't be possible since we guaranteed we have no cycles
                throw new IllegalStateException("Modules have no proper order");
            }
            this.loadOrder.add(next.getData());
            dependencyGraph.delete(next);
        }
    }

    public synchronized void shutdown() {
        RpgCommon.getLogger().info("Shutting down...");
        this.gameInstance.getContext().release();
        for (int i = this.loadOrder.size() - 1; i >= 0; i--) {
            ModuleClass next = this.loadOrder.get(i);
            IModule module = this.modules.get(next.getModuleClass());
            module.stop();
            this.modContexts.get(module.getClass()).release();
        }
        for (IModule module : this.modules.values()) {
            if (module.isRunning()) {
                this.modContexts.get(module.getClass()).release();
                module.stop();
            }
        }
        this.modContexts.clear();
        this.modules.clear();
        this.modcls.clear();
        this.generics.clear();
        RpgCommon.getLogger().flushAll();
        RpgCommon.getLogger().shutdown();
        System.exit(0);
    }
}