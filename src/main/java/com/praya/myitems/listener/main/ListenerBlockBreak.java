// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import com.praya.agarthalib.utility.BlockUtil;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ListenerBlockBreak extends HandlerEvent implements Listener {
    public ListenerBlockBreak(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler
    public void eventBlockBreak(final BlockBreakEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        if (!event.isCancelled()) {
            if (BlockUtil.isSet(event.getBlock())) {
                event.setCancelled(true);
                return;
            }
            final Player player = event.getPlayer();
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            if (EquipmentUtil.hasLore(item)) {
                final int durability = (int) statsManager.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.CURRENT);
                if (!statsManager.durability(player, item, durability, true)) {
                    event.setCancelled(true);
                    statsManager.sendBrokenCode(player, Slot.MAINHAND);
                }
            }
        }
    }
}
