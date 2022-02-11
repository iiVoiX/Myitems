// 
// Decompiled by Procyon v0.5.36
// 

package api.praya.myitems.manager.game;

import api.praya.myitems.builder.item.ItemGenerator;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerManager;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.ItemGeneratorManager;

import java.util.Collection;

public class ItemGeneratorManagerAPI extends HandlerManager {
    protected ItemGeneratorManagerAPI(final MyItems plugin) {
        super(plugin);
    }

    public final Collection<String> getItemGeneratorNames() {
        return this.getItemGeneratorManager().getItemGeneratorIDs();
    }

    public final Collection<ItemGenerator> getItemGenerators() {
        return this.getItemGeneratorManager().getItemGenerators();
    }

    public final ItemGenerator getItemGenerator(final String nameid) {
        return this.getItemGeneratorManager().getItemGenerator(nameid);
    }

    public final boolean isItemGeneratorExists(final String nameid) {
        return this.getItemGeneratorManager().isItemGeneratorExists(nameid);
    }

    private final ItemGeneratorManager getItemGeneratorManager() {
        final GameManager gameManager = this.plugin.getGameManager();
        final ItemGeneratorManager itemGeneratorManager = gameManager.getItemGeneratorManager();
        return itemGeneratorManager;
    }
}
