/*
 * Copyright (c) 2015 VoxelBox <http://engine.thevoxelbox.com>.
 * All Rights Reserved.
 */
package com.afterkraft.kraftrpg.api.module;


import com.afterkraft.kraftrpg.api.RpgCommon;

public abstract class AbstractModule implements IModule {

    private boolean running;

    @Override
    public final void start() {
        if (this.running) {
            return;
        }
        this.running = true;
        RpgCommon.getLogger().info("Initializing " + getClass().getName());
        initialize();
    }

    protected abstract void initialize();

    @Override
    public final boolean isRunning() {
        return this.running;
    }

    @Override
    public final void stop() {
        if (!this.running) {
            return;
        }
        this.running = false;
        RpgCommon.getLogger().info("Deinitializing " + getClass().getName());
        deinitialize();
    }

    protected abstract void deinitialize();

}
