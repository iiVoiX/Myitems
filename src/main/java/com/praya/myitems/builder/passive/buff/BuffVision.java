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

public class BuffVision extends PassiveEffect {
    private static final PassiveEffectEnum buff;

    static {
        buff = PassiveEffectEnum.VISION;
    }

    public BuffVision() {
        super(BuffVision.buff, 1);
    }

    public BuffVision(final int grade) {
        super(BuffVision.buff, grade);
    }

    @Override
    public final void cast(final Player player) {
        final MyItems plugin = (MyItems) JavaPlugin.getPlugin((Class) MyItems.class);
        final GameManager gameManager = plugin.getGameManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (!passiveEffectManager.isPassiveEffectCooldown(BuffVision.buff, player)) {
            final PotionEffectType potionType = this.getPotion();
            final long cooldown = this.getCooldown();
            final int duration = this.getDuration();
            final boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
            passiveEffectManager.setPassiveEffectCooldown(BuffVision.buff, player, cooldown);
        }
    }

    private final int getDuration() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return mainConfig.getPassivePeriodEffect() * this.grade + 20;
    }

    private final long getCooldown() {
        final MainConfig mainConfig = MainConfig.getInstance();
        return MathUtil.convertTickToMilis(mainConfig.getPassivePeriodEffect()) * PassiveEffectEnum.HASTE.getMaxGrade();
    }
}
