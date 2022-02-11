// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.event;

import com.praya.agarthalib.utility.MathUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CombatPreCriticalEvent extends Event implements Cancellable {
    private static final HandlerList handlers;

    static {
        handlers = new HandlerList();
    }

    private final LivingEntity attacker;
    private final LivingEntity victims;
    private boolean cancel;
    private double chance;

    public CombatPreCriticalEvent(final LivingEntity attacker, final LivingEntity victims, final double chance) {
        this.cancel = false;
        this.attacker = attacker;
        this.victims = victims;
        this.chance = MathUtil.limitDouble(chance, 0.0, 100.0);
    }

    public static final HandlerList getHandlerList() {
        return CombatPreCriticalEvent.handlers;
    }

    public final LivingEntity getAttacker() {
        return this.attacker;
    }

    public final LivingEntity getVictims() {
        return this.victims;
    }

    public final double getChance() {
        return this.chance;
    }

    public final void setChance(final double chance) {
        this.chance = MathUtil.limitDouble(chance, 0.0, 100.0);
    }

    public final boolean isCritical() {
        return MathUtil.chanceOf(this.chance);
    }

    public HandlerList getHandlers() {
        return CombatPreCriticalEvent.handlers;
    }

    public boolean isCancelled() {
        return this.cancel;
    }

    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
}
