// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.main;

import api.praya.myitems.manager.game.GameManagerAPI;
import api.praya.myitems.manager.player.PlayerManagerAPI;
import com.praya.myitems.MyItems;
import org.bukkit.plugin.java.JavaPlugin;

public class MyItemsAPI {
    private final GameManagerAPI gameManagerAPI;
    private final PlayerManagerAPI playerManagerAPI;

    private MyItemsAPI(final MyItems plugin) {
        this.gameManagerAPI = new GameManagerAPI(plugin);
        this.playerManagerAPI = new PlayerManagerAPI(plugin);
    }

    public static final MyItemsAPI getInstance() {
        return MyItemsAPIHelper.instance;
    }

    public final GameManagerAPI getGameManagerAPI() {
        return this.gameManagerAPI;
    }

    public final PlayerManagerAPI getPlayerManagerAPI() {
        return this.playerManagerAPI;
    }

    private static class MyItemsAPIHelper {
        private static final MyItemsAPI instance;

        static {
            final MyItems plugin = (MyItems) JavaPlugin.getProvidingPlugin(MyItems.class);
            instance = new MyItemsAPI(plugin);
        }
    }
}
