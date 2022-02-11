// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.support;

import api.praya.agarthalib.builder.event.PlayerHealthMaxChangeEvent;
import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.SocketManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ListenerPlayerHealthMaxChange extends HandlerEvent implements Listener {
    public ListenerPlayerHealthMaxChange(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void eventPlayerHealthMaxChange(final PlayerHealthMaxChangeEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final boolean enableMaxHealth = mainConfig.isStatsEnableMaxHealth();
        if (!event.isCancelled() && enableMaxHealth) {
            final Player player = event.getPlayer();
            final LoreStatsArmor statsBuild = statsManager.getLoreStatsArmor(player);
            final SocketGemsProperties socketBuild = socketManager.getSocketProperties(player);
            final double healthStats = statsBuild.getHealth();
            final double healthSocket = socketBuild.getHealth();
            final double healthBase = event.getMaxHealth();
            final double healthResult = healthStats + healthSocket + healthBase;
            event.setMaxHealth(healthResult);
        }
    }
}
