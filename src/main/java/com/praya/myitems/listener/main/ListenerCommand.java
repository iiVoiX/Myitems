// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class ListenerCommand extends HandlerEvent implements Listener {
    public ListenerCommand(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler
    public void onCommand(final PlayerCommandPreprocessEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        final Player player = event.getPlayer();
        final ItemStack itemBefore = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
        final Collection<PassiveEffectEnum> passiveEffectBefore = passiveEffectManager.getPassiveEffects(itemBefore);
        final int idBefore = EquipmentUtil.isSolid(itemBefore) ? itemBefore.hashCode() : 0;
        final boolean enableGradeCalculation = mainConfig.isPassiveEnableGradeCalculation();
        new BukkitRunnable() {
            public void run() {
                if (player.isOnline()) {
                    final ItemStack itemAfter = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                    final Collection<PassiveEffectEnum> passiveEffectAfter = passiveEffectManager.getPassiveEffects(itemAfter);
                    final int idAfter = EquipmentUtil.isSolid(itemAfter) ? itemAfter.hashCode() : 0;
                    if (idBefore != idAfter) {
                        passiveEffectManager.reloadPassiveEffect(player, passiveEffectBefore, enableGradeCalculation);
                        passiveEffectManager.reloadPassiveEffect(player, passiveEffectAfter, enableGradeCalculation);
                    }
                }
            }
        }.runTaskLater(this.plugin, 1L);
    }
}
