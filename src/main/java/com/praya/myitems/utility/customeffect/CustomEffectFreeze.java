// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.utility.customeffect;

import api.praya.myitems.builder.passive.PassiveEffectTypeEnum;
import com.praya.agarthalib.utility.EntityUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.utility.main.CustomEffectUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.enums.branch.ParticleEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Collection;

public class CustomEffectFreeze {
    protected static final PassiveEffectTypeEnum customEffect;

    static {
        customEffect = PassiveEffectTypeEnum.FREEZE;
    }

    public static final void cast(final LivingEntity livingEntity) {
        if (EntityUtil.isPlayer(livingEntity)) {
            effect(PlayerUtil.parse(livingEntity));
        }
        display(livingEntity);
    }

    public static final void cast(final Player player) {
        effect(player);
        display(player);
    }

    public static final void effect(final Player player) {
        if (CustomEffectUtil.isRunCustomEffect(player, CustomEffectFreeze.customEffect)) {
            if (player.getWalkSpeed() >= 0.05f) {
                CustomEffectUtil.setSpeedBase(player, player.getWalkSpeed());
                player.setWalkSpeed(0.0f);
            }
        } else if (CustomEffectUtil.hasSpeedBase(player)) {
            final float baseSpeed = CustomEffectUtil.getSpeedBase(player);
            CustomEffectUtil.removeSpeedBase(player);
            player.setWalkSpeed(baseSpeed);
        }
    }

    public static final void display(final LivingEntity livingEntity) {
        final MainConfig mainConfig = MainConfig.getInstance();
        if (CustomEffectUtil.isRunCustomEffect(livingEntity, CustomEffectFreeze.customEffect)) {
            final Location loc = livingEntity.getLocation();
            final Collection<Player> players = PlayerUtil.getNearbyPlayers(loc, mainConfig.getEffectRange());
            Bridge.getBridgeParticle().playParticle(players, ParticleEnum.CLOUD, loc, 10, 0.25, 0.25, 0.25, 0.10000000149011612);
            Bridge.getBridgeSound().playSound(players, loc, SoundEnum.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
        }
    }
}
