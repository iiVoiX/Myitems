// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.support;

import api.praya.combatstamina.builder.event.PlayerStaminaRegenChangeEvent;
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

public class ListenerPlayerStaminaRegenChange extends HandlerEvent implements Listener {
    public ListenerPlayerStaminaRegenChange(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void eventPlayerStaminaMaxChange(final PlayerStaminaRegenChangeEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        if (!event.isCancelled()) {
            final Player player = event.getPlayer();
            final PlayerStaminaRegenChangeEvent.StaminaRegenModifierEnum staminaRegenType = PlayerStaminaRegenChangeEvent.StaminaRegenModifierEnum.BONUS;
            final LoreStatsArmor statsBuild = statsManager.getLoreStatsArmor(player);
            final SocketGemsProperties socketBuild = socketManager.getSocketProperties(player);
            final double staminaRegenStats = statsBuild.getStaminaRegen();
            final double staminaRegenSocket = socketBuild.getStaminaRegen();
            final double staminaRegenBase = event.getOriginalStaminaRegen(staminaRegenType);
            final double staminaRegenResult = staminaRegenStats + staminaRegenSocket + staminaRegenBase;
            event.setStaminaRegen(staminaRegenType, staminaRegenResult);
        }
    }
}
