// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import api.praya.myitems.builder.passive.PassiveEffectTypeEnum;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.utility.main.CustomEffectUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class ListenerEntityRegainHealth extends HandlerEvent implements Listener {
    public ListenerEntityRegainHealth(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler
    public void eventEntityRegainHealth(final EntityRegainHealthEvent event) {
        if (!event.isCancelled() && Bridge.getBridgeLivingEntity().isLivingEntity(event.getEntity())) {
            final LivingEntity entity = (LivingEntity) event.getEntity();
            if (CustomEffectUtil.isRunCustomEffect(entity, PassiveEffectTypeEnum.CURSE)) {
                event.setCancelled(true);
            }
        }
    }
}
