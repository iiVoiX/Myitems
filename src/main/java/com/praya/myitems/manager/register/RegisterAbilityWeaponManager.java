// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.manager.register;

import api.praya.myitems.builder.ability.AbilityWeapon;
import com.praya.agarthalib.utility.ServerUtil;
import com.praya.agarthalib.utility.TextUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.ability.weapon.*;
import com.praya.myitems.builder.handler.HandlerManager;
import core.praya.agarthalib.enums.main.VersionNMS;

import java.util.Collection;
import java.util.HashMap;

public class RegisterAbilityWeaponManager extends HandlerManager {
    private final HashMap<String, AbilityWeapon> mapAbilityWeapon;

    protected RegisterAbilityWeaponManager(final MyItems plugin) {
        super(plugin);
        this.mapAbilityWeapon = new HashMap<String, AbilityWeapon>();
        this.setup();
    }

    public final Collection<String> getAbilityIDs() {
        return this.mapAbilityWeapon.keySet();
    }

    public final Collection<AbilityWeapon> getAllAbilityWeapon() {
        return this.mapAbilityWeapon.values();
    }

    public final AbilityWeapon getAbilityWeapon(final String ability) {
        if (ability != null) {
            for (final String key : this.getAbilityIDs()) {
                if (key.equalsIgnoreCase(ability)) {
                    return this.mapAbilityWeapon.get(key);
                }
            }
        }
        return null;
    }

    public final boolean isExists(final String ability) {
        return this.getAbilityWeapon(ability) != null;
    }

    public final AbilityWeapon getAbilityWeaponByKeyLore(final String keyLore) {
        if (keyLore != null) {
            final String coloredKeyLore = TextUtil.colorful(keyLore);
            for (final AbilityWeapon key : this.getAllAbilityWeapon()) {
                if (TextUtil.colorful(key.getKeyLore()).equalsIgnoreCase(coloredKeyLore)) {
                    return key;
                }
            }
        }
        return null;
    }

    public final boolean isKeyLoreExists(final String keyLore) {
        return this.getAbilityWeaponByKeyLore(keyLore) != null;
    }

    public final boolean register(final AbilityWeapon abilityWeapon) {
        if (abilityWeapon != null) {
            final String id = abilityWeapon.getID();
            if (!this.isExists(id)) {
                this.mapAbilityWeapon.put(id, abilityWeapon);
                return true;
            }
        }
        return false;
    }

    public final boolean unregister(final String ability) {
        final AbilityWeapon abilityWeapon = this.getAbilityWeapon(ability);
        if (abilityWeapon != null) {
            final String id = abilityWeapon.getID();
            this.mapAbilityWeapon.remove(id);
            return true;
        }
        return false;
    }

    private final void setup() {
        final AbilityWeapon abilityWeaponAirShock = AbilityWeaponAirShock.getInstance();
        final AbilityWeapon abilityWeaponBlind = AbilityWeaponBlind.getInstance();
        final AbilityWeapon abilityWeaponBubbleDeflector = AbilityWeaponBubbleDeflector.getInstance();
        final AbilityWeapon abilityWeaponCannibalism = AbilityWeaponCannibalism.getInstance();
        final AbilityWeapon abilityWeaponConfuse = AbilityWeaponConfuse.getInstance();
        final AbilityWeapon abilityWeaponCurse = AbilityWeaponCurse.getInstance();
        final AbilityWeapon abilityWeaponDarkFlame = AbilityWeaponDarkFlame.getInstance();
        final AbilityWeapon abilityWeaponDarkImpact = AbilityWeaponDarkImpact.getInstance();
        final AbilityWeapon abilityWeaponFlame = AbilityWeaponFlame.getInstance();
        final AbilityWeapon abilityWeaponFlameWheel = AbilityWeaponFlameWheel.getInstance();
        final AbilityWeapon abilityWeaponFreeze = AbilityWeaponFreeze.getInstance();
        final AbilityWeapon abilityWeaponHarm = AbilityWeaponHarm.getInstance();
        final AbilityWeapon abilityWeaponHungry = AbilityWeaponHungry.getInstance();
        final AbilityWeapon abilityWeaponLightning = AbilityWeaponLightning.getInstance();
        final AbilityWeapon abilityWeaponPoison = AbilityWeaponPoison.getInstance();
        final AbilityWeapon abilityWeaponRoots = AbilityWeaponRoots.getInstance();
        final AbilityWeapon abilityWeaponSlowness = AbilityWeaponSlow.getInstance();
        final AbilityWeapon abilityWeaponTired = AbilityWeaponTired.getInstance();
        final AbilityWeapon abilityWeaponVampirism = AbilityWeaponVampirism.getInstance();
        final AbilityWeapon abilityWeaponVenomBlast = AbilityWeaponVenomBlast.getInstance();
        final AbilityWeapon abilityWeaponVenomSpread = AbilityWeaponVenomSpread.getInstance();
        final AbilityWeapon abilityWeaponWeakness = AbilityWeaponWeak.getInstance();
        final AbilityWeapon abilityWeaponWither = AbilityWeaponWither.getInstance();
        this.register(abilityWeaponAirShock);
        this.register(abilityWeaponBlind);
        this.register(abilityWeaponBubbleDeflector);
        this.register(abilityWeaponCannibalism);
        this.register(abilityWeaponConfuse);
        this.register(abilityWeaponCurse);
        this.register(abilityWeaponDarkFlame);
        this.register(abilityWeaponDarkImpact);
        this.register(abilityWeaponFlame);
        this.register(abilityWeaponFlameWheel);
        this.register(abilityWeaponFreeze);
        this.register(abilityWeaponHarm);
        this.register(abilityWeaponHungry);
        this.register(abilityWeaponLightning);
        this.register(abilityWeaponPoison);
        this.register(abilityWeaponRoots);
        this.register(abilityWeaponSlowness);
        this.register(abilityWeaponTired);
        this.register(abilityWeaponVampirism);
        this.register(abilityWeaponVenomBlast);
        this.register(abilityWeaponVenomSpread);
        this.register(abilityWeaponWeakness);
        this.register(abilityWeaponWither);
        if (ServerUtil.isCompatible(VersionNMS.V1_9_R2)) {
            final AbilityWeapon abilityWeaponBadLuck = AbilityWeaponBadLuck.getInstance();
            final AbilityWeapon abilityWeaponLevitation = AbilityWeaponLevitation.getInstance();
            this.register(abilityWeaponBadLuck);
            this.register(abilityWeaponLevitation);
        }
    }
}
