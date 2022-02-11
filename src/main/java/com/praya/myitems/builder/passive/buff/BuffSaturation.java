// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.passive.buff;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.abs.PassiveEffect;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.PassiveEffectManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

public class BuffSaturation extends PassiveEffect {
    private static final PassiveEffectEnum buff;

    static {
        buff = PassiveEffectEnum.SATURATION;
    }

    public BuffSaturation() {
        super(BuffSaturation.buff, 1);
    }

    public BuffSaturation(final int grade) {
        super(BuffSaturation.buff, grade);
    }

    @Override
    public final void cast(final Player player) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (!passiveEffectManager.isPassiveEffectCooldown(BuffSaturation.buff, player)) {
            final PotionEffectType potionType = BuffSaturation.buff.getPotion();
            final long cooldown = this.getCooldown();
            final int duration = this.getDuration();
            final boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
            passiveEffectManager.setPassiveEffectCooldown(BuffSaturation.buff, player, cooldown);
        }
    }

    private final int getDuration() {
        final int duration = this.grade * 5 / BuffSaturation.buff.getMaxGrade();
        return MathUtil.limitInteger(duration, 1, duration);
    }

    private final long getCooldown() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return MathUtil.convertTickToMilis(mainConfig.getPassivePeriodEffect()) * BuffSaturation.buff.getMaxGrade();
    }
}
