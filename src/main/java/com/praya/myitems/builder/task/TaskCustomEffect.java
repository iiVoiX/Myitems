// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.task;

import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerTask;
import com.praya.myitems.utility.customeffect.CustomEffectFreeze;
import org.bukkit.entity.Player;

public class TaskCustomEffect extends HandlerTask implements Runnable {
    public TaskCustomEffect(final MyItems plugin) {
        super(plugin);
    }

    @Override
    public void run() {
        for (final Player loopPlayer : PlayerUtil.getOnlinePlayers()) {
            CustomEffectFreeze.cast(loopPlayer);
        }
    }
}
