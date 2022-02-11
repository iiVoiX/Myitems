// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.task;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.builder.task.TaskPassiveEffect;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class TaskPassiveEffectManager extends HandlerManager {
    private BukkitTask taskLoadPassiveEffect;

    protected TaskPassiveEffectManager(final MyItems plugin) {
        super(plugin);
        this.reloadTaskLoadPassiveEffect();
    }

    public final void reloadTaskLoadPassiveEffect() {
        if (this.taskLoadPassiveEffect != null) {
            this.taskLoadPassiveEffect.cancel();
        }
        this.taskLoadPassiveEffect = this.createTaskLoadPassiveEffect();
    }

    private final BukkitTask createTaskLoadPassiveEffect() {
        final MainConfig mainConfig = MainConfig.getInstance();
        final BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        final Runnable runnable = new TaskPassiveEffect(this.plugin);
        final int delay = 2;
        final int period = mainConfig.getPassivePeriodEffect();
        final BukkitTask task = scheduler.runTaskTimer(this.plugin, runnable, 2L, period);
        return task;
    }
}
