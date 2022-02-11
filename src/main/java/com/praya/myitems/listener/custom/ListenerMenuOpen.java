// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.custom;

import api.praya.agarthalib.builder.event.MenuOpenEvent;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.menu.MenuSocket;
import com.praya.myitems.menu.MenuStats;
import core.praya.agarthalib.builder.menu.Menu;
import core.praya.agarthalib.builder.menu.MenuExecutor;
import core.praya.agarthalib.builder.menu.MenuGUI;
import core.praya.agarthalib.enums.branch.SoundEnum;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ListenerMenuOpen extends HandlerEvent implements Listener {
    public ListenerMenuOpen(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void menuOpenEvent(final MenuOpenEvent event) {
        final Menu menu = event.getMenu();
        final Player player = event.getPlayer();
        final String id = menu.getID();
        if (!event.isCancelled() && menu instanceof MenuGUI) {
            final MenuGUI menuGUI = (MenuGUI) menu;
            if (id.startsWith("MyItems")) {
                SenderUtil.playSound(player, SoundEnum.ENTITY_EXPERIENCE_ORB_PICKUP);
            }
            if (id.equalsIgnoreCase("MyItems Socket")) {
                final MenuExecutor executor = menu.getExecutor();
                if (executor instanceof MenuSocket) {
                    final MenuSocket executorSocket = (MenuSocket) executor;
                    executorSocket.updateSocketMenu(menuGUI, player);
                }
            } else if (id.equalsIgnoreCase("MyItems Stats")) {
                final MenuExecutor executor = menu.getExecutor();
                if (executor instanceof MenuStats) {
                    final MenuStats executorStats = (MenuStats) executor;
                    executorStats.updateStatsMenu(menuGUI, player);
                }
            }
        }
    }
}
