// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.plugin;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import core.praya.agarthalib.builder.face.Agartha;
import core.praya.agarthalib.builder.plugin.*;
import core.praya.agarthalib.utility.ServerUtil;
import core.praya.agarthalib.utility.SortUtil;
import core.praya.agarthalib.utility.SystemUtil;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PluginPropertiesManager extends HandlerManager {
    private final PluginPropertiesResourceBuild pluginPropertiesResource;
    private final PluginPropertiesStreamBuild pluginPropertiesStream;

    public PluginPropertiesManager(final MyItems plugin) {
        super(plugin);
        this.pluginPropertiesResource = PluginPropertiesBuild.getPluginPropertiesResource(plugin, plugin.getPluginType(), plugin.getPluginVersion());
        this.pluginPropertiesStream = PluginPropertiesBuild.getPluginPropertiesStream(plugin);
    }

    public final PluginPropertiesResourceBuild getPluginPropertiesResource() {
        return this.pluginPropertiesResource;
    }

    public final PluginPropertiesStreamBuild getPluginPropertiesStream() {
        return this.pluginPropertiesStream;
    }

    public final boolean isActivated() {
        return this.getPluginPropertiesStream().isActivated();
    }

    public final String getName() {
        return this.getPluginPropertiesStream().getName();
    }

    public final String getCompany() {
        return this.getPluginPropertiesStream().getCompany();
    }

    public final String getAuthor() {
        return this.getPluginPropertiesStream().getAuthor();
    }

    public final String getWebsite() {
        return this.getPluginPropertiesStream().getWebsite();
    }

    public final List<String> getDevelopers() {
        return this.getPluginPropertiesStream().getDevelopers();
    }

    public final List<String> getListType() {
        return (List<String>) SortUtil.toArray(this.getPluginPropertiesStream().getTypeProperties().keySet());
    }

    public final PluginTypePropertiesBuild getTypeProperties(final String type) {
        if (type != null) {
            for (final String key : this.getListType()) {
                if (key.equalsIgnoreCase(type)) {
                    return this.getPluginPropertiesStream().getTypeProperties().get(key);
                }
            }
        }
        return null;
    }

    public final boolean isTypeExists(final String type) {
        return this.getTypeProperties(type) != null;
    }

    public final boolean isLatestVersion() {
        final String type = this.plugin.getPluginType();
        final String versionLatest = this.getPluginTypeVersion(type);
        final String versionCurrent = this.plugin.getPluginVersion();
        return versionLatest.equalsIgnoreCase(versionCurrent);
    }

    public final String getPluginTypeVersion(final String type) {
        final PluginTypePropertiesBuild typeProperties = this.getTypeProperties(type);
        return (typeProperties != null) ? typeProperties.getVersion() : this.getPluginPropertiesResource().getVersion();
    }

    public final String getPluginTypeWebsite(final String type) {
        final PluginTypePropertiesBuild typeProperties = this.getTypeProperties(type);
        return (typeProperties != null) ? typeProperties.getWebsite() : this.getPluginPropertiesResource().getWebsite();
    }

    @Deprecated
    public final String getLatestVersion(final String type) {
        return this.getPluginTypeVersion(type);
    }

    @Deprecated
    public final List<String> getAnnouncement(final String type) {
        final PluginTypePropertiesBuild typeProperties = this.getTypeProperties(type);
        return (typeProperties != null) ? typeProperties.getAnnouncement() : new ArrayList<String>();
    }

    @Deprecated
    public final List<String> getChangelog(final String type) {
        final PluginTypePropertiesBuild typeProperties = this.getTypeProperties(type);
        return (typeProperties != null) ? typeProperties.getChangelog() : new ArrayList<String>();
    }

    @Deprecated
    public final List<String> getReasonBanned() {
        return (List<String>) SortUtil.toArray(this.getPluginPropertiesStream().getBannedProperties().keySet());
    }

    @Deprecated
    public final boolean isReasonBannedExists(final String reason) {
        return this.getBannedProperties(reason) != null;
    }

    @Deprecated
    public final PluginBannedPropertiesBuild getBannedProperties(final String reason) {
        for (final String key : this.getPluginPropertiesStream().getBannedProperties().keySet()) {
            if (key.equalsIgnoreCase(reason)) {
                return this.getPluginPropertiesStream().getBannedProperties().get(key);
            }
        }
        return null;
    }

    public final void check() {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        if (!this.isActivated()) {
            final String message = lang.getText("Plugin_Deactivated");
            this.disable(message);
            return;
        }
        if (!this.checkPluginName() || !this.checkPluginAuthor()) {
            final String message = lang.getText("Plugin_Information_Not_Match");
            this.disable(message);
        }
    }

    private final boolean checkPluginName() {
        return this.getName() == null || this.getName().equalsIgnoreCase(this.getPluginPropertiesResource().getName());
    }

    private final boolean checkPluginAuthor() {
        return this.getAuthor() == null || this.getAuthor().equalsIgnoreCase(this.getPluginPropertiesResource().getAuthor());
    }

    private final void disable(final String message) {
        if (message != null) {
            SystemUtil.sendMessage(message);
        }
        this.plugin.getPluginLoader().disablePlugin(this.plugin);
    }

    @Deprecated
    public final boolean isBanned() {
        return this.getBannedReason() != null;
    }

    @Deprecated
    public final String getBannedReason() {
        final String serverIP = ServerUtil.getIP();
        for (final String reason : this.pluginPropertiesStream.getBannedProperties().keySet()) {
            for (final String ip : this.pluginPropertiesStream.getBannedProperties().get(reason).getServers()) {
                if (ip.equalsIgnoreCase(serverIP)) {
                    return reason;
                }
            }
        }
        return null;
    }
}
