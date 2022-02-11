// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.tabcompleter;

import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.TabCompleterUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerTabCompleter;
import com.praya.myitems.manager.plugin.CommandManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class TabCompleterLoreRemove extends HandlerTabCompleter implements TabCompleter {
    public TabCompleterLoreRemove(final MyItems plugin) {
        super(plugin);
    }

    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final CommandManager commandManager = pluginManager.getCommandManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final List<String> tabList = new ArrayList<String>();
        SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
        if (SenderUtil.isPlayer(sender)) {
            final Player player = PlayerUtil.parse(sender);
            final ItemStack item = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
            final ItemMeta meta = item.getItemMeta();
            if (args.length == 1) {
                if (meta.hasLore()) {
                    if (commandManager.checkPermission(sender, "Lore_Remove")) {
                        for (int t = 1; t <= meta.getLore().size(); ++t) {
                            tabList.add(String.valueOf(t));
                        }
                    }
                } else {
                    final String message = lang.getText(sender, "MyItems_RemoveLore_Empty");
                    tabList.add("");
                    SenderUtil.sendMessage(sender, message);
                }
            }
        }
        return (List<String>) TabCompleterUtil.returnList(tabList, args);
    }
}
