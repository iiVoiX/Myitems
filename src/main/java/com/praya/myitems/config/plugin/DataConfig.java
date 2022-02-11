// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.plugin;

import com.praya.agarthalib.utility.FileUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerConfig;
import core.praya.agarthalib.builder.main.DataBuild;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class DataConfig extends HandlerConfig {
    private final HashMap<String, DataBuild> mapData;

    public DataConfig(final MyItems plugin) {
        super(plugin);
        this.mapData = new HashMap<String, DataBuild>();
        this.setup();
    }

    public final Collection<String> getDataIDs() {
        return this.mapData.keySet();
    }

    public final Collection<DataBuild> getDataBuilds() {
        return this.mapData.values();
    }

    public final DataBuild getData(final String id) {
        if (id != null) {
            for (final String key : this.getDataIDs()) {
                if (key.equalsIgnoreCase(id)) {
                    return this.mapData.get(key);
                }
            }
        }
        return null;
    }

    public final void setup() {
        this.reset();
        this.loadConfig();
    }

    private final void reset() {
        this.mapData.clear();
    }

    private final void loadConfig() {
        final FileConfiguration config = FileUtil.getFileConfigurationResource(this.plugin, "Resources/data.yml");
        for (final String path : config.getKeys(true)) {
            final String key = path.replace(".", "_");
            if (config.isString(path)) {
                final String text = config.getString(path);
                final List<String> list = new ArrayList<String>();
                list.add(text);
                final DataBuild dataBuild = new DataBuild(key, list);
                this.mapData.put(key, dataBuild);
            } else {
                if (!config.isList(path)) {
                    continue;
                }
                final List<String> list2 = config.getStringList(path);
                final DataBuild dataBuild2 = new DataBuild(key, list2);
                this.mapData.put(key, dataBuild2);
            }
        }
    }
}
