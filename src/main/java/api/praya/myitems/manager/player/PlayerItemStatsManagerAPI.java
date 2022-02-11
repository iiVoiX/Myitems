// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.manager.player;

import api.praya.myitems.builder.item.ItemStatsArmor;
import api.praya.myitems.builder.item.ItemStatsWeapon;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.manager.player.PlayerItemStatsManager;
import com.praya.myitems.manager.player.PlayerManager;
import org.bukkit.entity.Player;

public class PlayerItemStatsManagerAPI extends HandlerManager {
    protected PlayerItemStatsManagerAPI(final MyItems plugin) {
        super(plugin);
    }

    public final ItemStatsWeapon getItemStatsWeapon(final Player player) {
        return this.getPlayerItemStatsManager().getItemStatsWeapon(player);
    }

    public final ItemStatsArmor getItemStatsArmor(final Player player) {
        return this.getPlayerItemStatsManager().getItemStatsArmor(player);
    }

    private final PlayerItemStatsManager getPlayerItemStatsManager() {
        final PlayerManager playerManager = this.plugin.getPlayerManager();
        final PlayerItemStatsManager playerItemStatsManager = playerManager.getPlayerItemStatsManager();
        return playerItemStatsManager;
    }
}
