// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.builder.abs;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import com.praya.myitems.builder.passive.buff.*;
import com.praya.myitems.builder.passive.debuff.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public abstract class PassiveEffect {
    protected final PassiveEffectEnum buffEnum;
    protected final int grade;

    public PassiveEffect(final PassiveEffectEnum buffEnum, final int grade) {
        this.buffEnum = buffEnum;
        this.grade = grade;
    }

    public static final PassiveEffect getPassiveEffect(final PassiveEffectEnum buffEnum, final int grade) {
        switch (buffEnum) {
            case ABSORB: {
                return new BuffAbsorb(grade);
            }
            case FIRE_RESISTANCE: {
                return new BuffFireResistance(grade);
            }
            case HASTE: {
                return new BuffHaste(grade);
            }
            case HEALTH_BOOST: {
                return new BuffHealthBoost(grade);
            }
            case INVISIBILITY: {
                return new BuffInvisibility(grade);
            }
            case JUMP: {
                return new BuffJump(grade);
            }
            case LUCK: {
                return new BuffLuck(grade);
            }
            case PROTECTION: {
                return new BuffProtection(grade);
            }
            case REGENERATION: {
                return new BuffRegeneration(grade);
            }
            case SATURATION: {
                return new BuffSaturation(grade);
            }
            case SPEED: {
                return new BuffSpeed(grade);
            }
            case STRENGTH: {
                return new BuffStrength(grade);
            }
            case VISION: {
                return new BuffVision(grade);
            }
            case WATER_BREATHING: {
                return new BuffWaterBreathing(grade);
            }
            case BLIND: {
                return new DebuffBlind(grade);
            }
            case CONFUSE: {
                return new DebuffConfuse(grade);
            }
            case FATIGUE: {
                return new DebuffFatigue(grade);
            }
            case SLOW: {
                return new DebuffSlow(grade);
            }
            case STARVE: {
                return new DebuffStarve(grade);
            }
            case TOXIC: {
                return new DebuffToxic(grade);
            }
            case GLOW: {
                return new DebuffGlow(grade);
            }
            case UNLUCK: {
                return new DebuffUnluck(grade);
            }
            case WEAK: {
                return new DebuffWeak(grade);
            }
            case WITHER: {
                return new DebuffWither(grade);
            }
            default: {
                return null;
            }
        }
    }

    public abstract void cast(final Player p0);

    public final PassiveEffectEnum getPassiveEffectEnum() {
        return this.buffEnum;
    }

    public final int getGrade() {
        return this.grade;
    }

    public final PotionEffectType getPotion() {
        return this.buffEnum.getPotion();
    }
}
