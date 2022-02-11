// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.builder.event;

import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PowerShootCastEvent extends PowerPreCastEvent {
    private static final HandlerList handlers;

    static {
        handlers = new HandlerList();
    }

    private final ProjectileEnum projectile;
    private boolean cancel;
    private double cooldown;
    private double speed;

    public PowerShootCastEvent(final Player player, final PowerEnum power, final PowerClickEnum click, final ItemStack item, final String lore, final ProjectileEnum projectile, final double cooldown) {
        super(player, power, click, item, lore);
        this.cancel = false;
        this.projectile = projectile;
        this.cooldown = cooldown;
        this.speed = 3.0;
    }

    public static HandlerList getHandlerList() {
        return PowerShootCastEvent.handlers;
    }

    public final ProjectileEnum getProjectile() {
        return this.projectile;
    }

    public final double getCooldown() {
        return this.cooldown;
    }

    public final void setCooldown(final double cooldown) {
        this.cooldown = cooldown;
    }

    public final double getSpeed() {
        return this.speed;
    }

    public final void setSpeed(final double speed) {
        this.speed = speed;
    }

    @Override
    public HandlerList getHandlers() {
        return PowerShootCastEvent.handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
}
