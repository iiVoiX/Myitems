// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.abs;

import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.myitems.builder.specialpower.*;
import org.bukkit.entity.LivingEntity;

public abstract class SpecialPower {
    protected final PowerSpecialEnum specialEnum;

    public SpecialPower(final PowerSpecialEnum specialEnum) {
        this.specialEnum = specialEnum;
    }

    public static final SpecialPower getSpecial(final PowerSpecialEnum specialEnum) {
        switch (specialEnum) {
            case AMATERASU: {
                return new SpecialPowerAmaterasu();
            }
            case BLINK: {
                return new SpecialPowerBlink();
            }
            case FISSURE: {
                return new SpecialPowerFissure();
            }
            case ICE_SPIKES: {
                return new SpecialPowerIceSpikes();
            }
            case NERO_BEAM: {
                return new SpecialPowerNeroBeam();
            }
            default: {
                return null;
            }
        }
    }

    public abstract void cast(final LivingEntity p0);

    public final PowerSpecialEnum getSpecialEnum() {
        return this.specialEnum;
    }
}
