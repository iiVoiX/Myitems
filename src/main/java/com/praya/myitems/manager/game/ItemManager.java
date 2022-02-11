// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.game;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.config.game.ItemConfig;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public class ItemManager extends HandlerManager {
    private final ItemConfig itemConfig;

    protected ItemManager(final MyItems plugin) {
        super(plugin);
        this.itemConfig = new ItemConfig(plugin);
    }

    public final ItemConfig getItemConfig() {
        return this.itemConfig;
    }

    public final Collection<String> getItemIDs() {
        return this.getItemConfig().getItemIDs();
    }

    public final ItemStack getItem(final String nameid) {
        return this.getItemConfig().getItem(nameid);
    }

    public final Collection<ItemStack> getItems() {
        final Collection<ItemStack> items = new ArrayList<ItemStack>();
        for (final String id : this.getItemIDs()) {
            final ItemStack item = this.getItem(id);
            if (item != null) {
                items.add(item);
            }
        }
        return items;
    }

    public final boolean isExist(final String id) {
        return this.getItem(id) != null;
    }

    public final String getRawName(final String nameid) {
        for (final String key : this.getItemIDs()) {
            if (key.equalsIgnoreCase(nameid)) {
                return key;
            }
        }
        return null;
    }
}
