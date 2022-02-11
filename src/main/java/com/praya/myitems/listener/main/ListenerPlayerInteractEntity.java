// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class ListenerPlayerInteractEntity extends HandlerEvent implements Listener {
    public ListenerPlayerInteractEntity(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void eventPutItemFrame(final PlayerInteractEntityEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final ItemSetManager itemSetManager = gameManager.getItemSetManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (!event.isCancelled()) {
            final Player player = event.getPlayer();
            final Entity entity = event.getRightClicked();
            final boolean enableGradeCalculation = mainConfig.isPassiveEnableGradeCalculation();
            if (entity instanceof ItemFrame) {
                final ItemStack itemMainHand = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                final ItemStack itemOffHand = Bridge.getBridgeEquipment().getEquipment(player, Slot.OFFHAND);
                final Collection<PassiveEffectEnum> mainPassiveEffects = passiveEffectManager.getPassiveEffects(itemMainHand);
                final Collection<PassiveEffectEnum> offPassiveEffects = passiveEffectManager.getPassiveEffects(itemOffHand);
                new BukkitRunnable() {
                    public void run() {
                        if (itemSetManager.isItemSet(itemMainHand) || itemSetManager.isItemSet(itemOffHand)) {
                            itemSetManager.updateItemSet(player);
                        }
                        passiveEffectManager.reloadPassiveEffect(player, mainPassiveEffects, enableGradeCalculation);
                        passiveEffectManager.reloadPassiveEffect(player, offPassiveEffects, enableGradeCalculation);
                    }
                }.runTaskLater(this.plugin, 1L);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void triggerSupport(final PlayerInteractEntityEvent event) {
        if (!event.isCancelled()) {
            final Entity entity = event.getRightClicked();
            if (entity instanceof ItemFrame) {
                final Player player = event.getPlayer();
                new BukkitRunnable() {
                    public void run() {
                        TriggerSupportUtil.updateSupport(player);
                    }
                }.runTaskLater(this.plugin, 2L);
            }
        }
    }
}
