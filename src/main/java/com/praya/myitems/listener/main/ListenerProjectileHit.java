// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import com.praya.agarthalib.utility.ProjectileUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ListenerProjectileHit extends HandlerEvent implements Listener {
    public ListenerProjectileHit(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler
    public void onProjectileHit(final ProjectileHitEvent event) {
        final Entity projectile = event.getEntity();
        if (ProjectileUtil.isDisappear(projectile) && !projectile.isDead()) {
            projectile.remove();
        }
    }
}
