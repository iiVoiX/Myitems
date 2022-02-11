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
import com.praya.myitems.manager.game.ItemSetManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class ListenerPlayerSwapHandItems extends HandlerEvent implements Listener {
    public ListenerPlayerSwapHandItems(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void triggerEquipmentChangeEvent(final PlayerSwapHandItemsEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final ItemSetManager itemSetManager = gameManager.getItemSetManager();
        if (!event.isCancelled()) {
            final Player player = event.getPlayer();
            final ItemStack itemMainHand = event.getMainHandItem();
            final ItemStack itemOffHand = event.getOffHandItem();
            if (itemSetManager.isItemSet(itemMainHand) || itemSetManager.isItemSet(itemOffHand)) {
                new BukkitRunnable() {
                    public void run() {
                        itemSetManager.updateItemSet(player);
                    }
                }.runTaskLater(this.plugin, 0L);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void playerSwapHandItemsEvent(final PlayerSwapHandItemsEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (!event.isCancelled()) {
            final Player player = event.getPlayer();
            final ItemStack itemMainHand = event.getMainHandItem();
            final ItemStack itemOffHand = event.getOffHandItem();
            final boolean enableItemUniversal = mainConfig.isStatsEnableItemUniversal();
            final boolean enableGradeCalculation = mainConfig.isPassiveEnableGradeCalculation();
            boolean isShieldMainHand;
            if (EquipmentUtil.isSolid(itemMainHand)) {
                final Material materialMainHand = itemMainHand.getType();
                final Collection<PassiveEffectEnum> passiveEffectsMainHand = passiveEffectManager.getPassiveEffects(itemMainHand);
                isShieldMainHand = materialMainHand.toString().equalsIgnoreCase("SHIELD");
                passiveEffectManager.reloadPassiveEffect(player, passiveEffectsMainHand, enableGradeCalculation);
            } else {
                isShieldMainHand = false;
            }
            boolean isShieldOffHand;
            if (EquipmentUtil.isSolid(itemOffHand)) {
                final Material materialOffHand = itemOffHand.getType();
                final Collection<PassiveEffectEnum> passiveEffectsOffHand = passiveEffectManager.getPassiveEffects(itemOffHand);
                isShieldOffHand = materialOffHand.toString().equalsIgnoreCase("SHIELD");
                passiveEffectManager.reloadPassiveEffect(player, passiveEffectsOffHand, enableGradeCalculation);
            } else {
                isShieldOffHand = false;
            }
            if (enableItemUniversal || isShieldMainHand || isShieldOffHand) {
                new BukkitRunnable() {
                    public void run() {
                        TriggerSupportUtil.updateSupport(player);
                    }
                }.runTaskLater(this.plugin, 0L);
            }
        }
    }
}
