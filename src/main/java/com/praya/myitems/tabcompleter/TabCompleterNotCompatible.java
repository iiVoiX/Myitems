// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.tabcompleter;

import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.TabCompleterUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerTabCompleter;
import com.praya.myitems.manager.plugin.LanguageManager;
import core.praya.agarthalib.enums.branch.SoundEnum;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class TabCompleterNotCompatible extends HandlerTabCompleter implements TabCompleter {
    public TabCompleterNotCompatible(final MyItems plugin) {
        super(plugin);
    }

    public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args) {
        final LanguageManager lang = this.plugin.getPluginManager().getLanguageManager();
        final String message = lang.getText(sender, "MyItems_Not_Compatible");
        final List<String> tabList = new ArrayList<String>();
        SenderUtil.sendMessage(sender, message);
        SenderUtil.playSound(sender, SoundEnum.BLOCK_WOOD_BUTTON_CLICK_ON);
        return (List<String>) TabCompleterUtil.returnList(tabList, args);
    }
}
