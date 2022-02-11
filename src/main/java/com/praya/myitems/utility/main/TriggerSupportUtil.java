// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.utility.main;

import api.praya.agarthalib.main.AgarthaLibAPI;
import api.praya.agarthalib.manager.plugin.SupportManagerAPI;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.entity.Player;

public class TriggerSupportUtil {
    public static final void updateSupport(final Player player) {
        final AgarthaLibAPI agarthaLibAPI = AgarthaLibAPI.getInstance();
        final SupportManagerAPI supportManagerAPI = agarthaLibAPI.getPluginManagerAPI().getSupportManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final boolean enableMaxHealth = mainConfig.isStatsEnableMaxHealth();
        if (enableMaxHealth) {
            PlayerUtil.setMaxHealth(player);
        }
    }
}
