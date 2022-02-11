// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.placeholder;

import com.praya.myitems.MyItems;
import com.praya.myitems.manager.plugin.PlaceholderManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReplacerPlaceholderAPIBuild extends PlaceholderExpansion {
    @Override
    public @NotNull String getAuthor() {
        return "VoChiDanh";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "myitems";
    }

    @Override
    public @NotNull String getVersion() {
        return "6.4.6-SNAPSHOT";
    }



    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player p, @NotNull String identifier) {
        if (p == null) {
            return "Player not online";
        }
        return null;
    }
}
