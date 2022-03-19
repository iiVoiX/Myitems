// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.support;

import api.praya.combatstamina.builder.event.PlayerStaminaMaxChangeEvent;
import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.SocketManager;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ListenerPlayerStaminaMaxChange extends HandlerEvent implements Listener {
    public ListenerPlayerStaminaMaxChange(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void eventPlayerStaminaMaxChange(final PlayerStaminaMaxChangeEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        if (!event.isCancelled()) {
            final Player player = event.getPlayer();
            final PlayerStaminaMaxChangeEvent.StaminaMaxModifierEnum staminaMaxType = PlayerStaminaMaxChangeEvent.StaminaMaxModifierEnum.BONUS;
            final LoreStatsArmor statsBuild = statsManager.getLoreStatsArmor(player);
            final SocketGemsProperties socketBuild = socketManager.getSocketProperties(player);
            final double staminaMaxStats = statsBuild.getStaminaMax();
            final double staminaMaxSocket = socketBuild.getStaminaMax();
            final double staminaMaxBase = event.getOriginalMaxStamina(staminaMaxType);
            final double staminaMaxResult = staminaMaxStats + staminaMaxSocket + staminaMaxBase;
            event.setMaxStamina(staminaMaxType, staminaMaxResult);
        }
    }
}
