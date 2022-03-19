// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.placeholder;

import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;
import com.praya.myitems.MyItems;
import com.praya.myitems.manager.plugin.PlaceholderManager;
import org.bukkit.plugin.Plugin;

public class ReplacerMVDWPlaceholderAPIBuild {
    private final MyItems plugin;
    private final String placeholder;

    public ReplacerMVDWPlaceholderAPIBuild(final MyItems plugin, final String placeholder) {
        this.plugin = plugin;
        this.placeholder = placeholder;
    }

    public final String getPlaceholder() {
        return this.placeholder;
    }

    public final void register() {
        final PlaceholderManager placeholderManager = this.plugin.getPluginManager().getPlaceholderManager();
        final String identifier = this.getPlaceholder() + "_*";
        PlaceholderAPI.registerPlaceholder(this.plugin, identifier, new PlaceholderReplacer() {
            public String onPlaceholderReplace(final PlaceholderReplaceEvent event) {
                return placeholderManager.getReplacement(event.getPlayer(), event.getPlaceholder().split(ReplacerMVDWPlaceholderAPIBuild.this.getPlaceholder() + "_")[1]);
            }
        });
    }
}
