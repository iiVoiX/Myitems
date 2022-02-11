// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.passive.debuff;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.agarthalib.utility.ServerUtil;
import com.praya.myitems.builder.abs.PassiveEffect;
import com.praya.myitems.config.plugin.MainConfig;
import core.praya.agarthalib.enums.main.VersionNMS;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class DebuffGlow extends PassiveEffect {
    private static final PassiveEffectEnum debuff;

    static {
        debuff = PassiveEffectEnum.GLOW;
    }

    public DebuffGlow() {
        super(DebuffGlow.debuff, 1);
    }

    public DebuffGlow(final int grade) {
        super(DebuffGlow.debuff, grade);
    }

    @Override
    public final void cast(final Player player) {
        final MainConfig mainConfig = MainConfig.getInstance();
        if (ServerUtil.isCompatible(VersionNMS.V1_9_R2)) {
            final PotionEffectType potionType = this.getPotion();
            final boolean isEnableParticle = mainConfig.isMiscEnableParticlePotion();
        }
    }
}
