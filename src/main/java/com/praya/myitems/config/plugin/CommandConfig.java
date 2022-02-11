// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.config.plugin;

import com.praya.agarthalib.utility.FileUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerCommand;
import com.praya.myitems.manager.plugin.DataManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.builder.command.CommandBuild;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CommandConfig extends HandlerCommand {
    private final HashMap<String, CommandBuild> mapCommand;

    public CommandConfig(final MyItems plugin) {
        super(plugin);
        this.mapCommand = new HashMap<String, CommandBuild>();
        this.setup();
    }

    public final Collection<String> getCommandIDs() {
        return this.mapCommand.keySet();
    }

    public final Collection<CommandBuild> getCommandBuilds() {
        return this.mapCommand.values();
    }

    public final CommandBuild getCommand(final String id) {
        for (final String key : this.getCommandIDs()) {
            if (key.equalsIgnoreCase(id)) {
                return this.mapCommand.get(key);
            }
        }
        return null;
    }

    public final void setup() {
        this.reset();
        this.loadConfig();
    }

    private final void reset() {
        this.mapCommand.clear();
    }

    private final void loadConfig() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final DataManager dataManager = pluginManager.getDataManager();
        final String path = dataManager.getPath("Path_File_Command");
        final FileConfiguration config = FileUtil.getFileConfigurationResource(this.plugin, path);
        for (final String key : config.getKeys(false)) {
            if (key.equalsIgnoreCase("Command")) {
                final ConfigurationSection idSection = config.getConfigurationSection(key);
                for (final String id : idSection.getKeys(false)) {
                    final ConfigurationSection mainDataSection = idSection.getConfigurationSection(id);
                    final List<String> aliases = new ArrayList<String>();
                    String permission = null;
                    for (final String mainData : mainDataSection.getKeys(false)) {
                        if (mainData.equalsIgnoreCase("Permission")) {
                            permission = mainDataSection.getString(mainData);
                        } else {
                            if (!mainData.equalsIgnoreCase("Aliases")) {
                                continue;
                            }
                            if (mainDataSection.isString(mainData)) {
                                aliases.add(mainDataSection.getString(mainData));
                            } else {
                                if (!mainDataSection.isList(mainData)) {
                                    continue;
                                }
                                aliases.addAll(mainDataSection.getStringList(mainData));
                            }
                        }
                    }
                    final CommandBuild commandBuild = new CommandBuild(id, permission, aliases);
                    this.mapCommand.put(id, commandBuild);
                }
            }
        }
    }
}
