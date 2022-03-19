// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.support;

import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import com.sucy.skill.api.event.PlayerLevelUpEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ListenerPlayerLevelUp extends HandlerEvent implements Listener {
    public ListenerPlayerLevelUp(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void eventPlayerLevelUp(final PlayerLevelUpEvent event) {
        final Player player = event.getPlayerData().getPlayer();
        TriggerSupportUtil.updateSupport(player);
    }
}
