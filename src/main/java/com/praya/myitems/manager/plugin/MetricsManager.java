// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.plugin;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.metrics.BStats;

public class MetricsManager extends HandlerManager {
    private final BStats metricsBStats;

    protected MetricsManager(final MyItems plugin) {
        super(plugin);
        this.metricsBStats = new BStats(plugin);
    }

    public final BStats getMetricsBStats() {
        return this.metricsBStats;
    }
}
