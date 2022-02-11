// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.passive.debuff;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.myitems.builder.abs.PassiveEffect;
import com.praya.myitems.config.plugin.MainConfig;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class DebuffToxic extends PassiveEffect {
    private static final PassiveEffectEnum debuff;

    static {
        debuff = PassiveEffectEnum.TOXIC;
    }

    public DebuffToxic() {
        super(DebuffToxic.debuff, 1);
    }

    public DebuffToxic(final int grade) {
        super(DebuffToxic.debuff, grade);
    }

    @Override
    public final void cast(final Player player) {
        final MainConfig mainConfig = MainConfig.getInstance();
        final PotionEffectType potionType = this.getPotion();
    }
}
