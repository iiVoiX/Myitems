// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems;

import com.praya.agarthalib.utility.PluginUtil;
import com.praya.agarthalib.utility.ServerEventUtil;
import com.praya.agarthalib.utility.ServerUtil;
import com.praya.myitems.command.*;
import com.praya.myitems.listener.custom.*;
import com.praya.myitems.listener.main.*;
import com.praya.myitems.listener.support.*;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.player.PlayerManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.myitems.manager.register.RegisterManager;
import com.praya.myitems.manager.task.TaskManager;
import com.praya.myitems.tabcompleter.*;
import com.praya.myitems.utility.main.AntiBugUtil;
import core.praya.agarthalib.builder.face.Agartha;
import core.praya.agarthalib.enums.main.VersionNMS;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class MyItems extends JavaPlugin implements Agartha {
    private final String type = "Premium";
    private final String placeholder = "myitems";
    private PluginManager pluginManager;
    private PlayerManager playerManager;
    private GameManager gameManager;
    private TaskManager taskManager;
    private RegisterManager registerManager;

    public String getPluginName() {
        return this.getName();
    }

    public String getPluginType() {
        return "Premium";
    }

    public String getPluginVersion() {
        return this.getDescription().getVersion();
    }

    public String getPluginPlaceholder() {
        return "myitems";
    }

    public String getPluginWebsite() {
        return this.getPluginManager().getPluginPropertiesManager().getWebsite();
    }

    public String getPluginLatest() {
        return this.getPluginManager().getPluginPropertiesManager().getPluginTypeVersion(this.getPluginType());
    }

    public List<String> getPluginDevelopers() {
        return this.getPluginManager().getPluginPropertiesManager().getDevelopers();
    }

    public final PluginManager getPluginManager() {
        return this.pluginManager;
    }

    public final GameManager getGameManager() {
        return this.gameManager;
    }

    public final PlayerManager getPlayerManager() {
        return this.playerManager;
    }

    public final TaskManager getTaskManager() {
        return this.taskManager;
    }

    public final RegisterManager getRegisterManager() {
        return this.registerManager;
    }

    public void onEnable() {
        this.setPluginManager();
        this.setPlayerManager();
        this.setGameManager();
        this.setTaskManager();
        this.setRegisterManager();
        this.getPluginManager().getDependencyManager().getDependencyConfig().setup();
        this.getPluginManager().getHookManager().getHookConfig().setup();
        this.setup();
        this.registerCommand();
        this.registerTabComplete();
        this.registerEvent();
        this.registerPlaceholder();
    }

    private final void setPluginManager() {
        (this.pluginManager = new PluginManager(this)).initialize();
    }

    private final void setGameManager() {
        this.gameManager = new GameManager(this);
    }

    private final void setPlayerManager() {
        this.playerManager = new PlayerManager(this);
    }

    private final void setTaskManager() {
        this.taskManager = new TaskManager(this);
    }

    private final void setRegisterManager() {
        this.registerManager = new RegisterManager(this);
    }

    private final void setup() {
        this.gameManager.getAbilityWeaponManager().getAbilityWeaponConfig().setup();
        this.gameManager.getElementManager().getElementConfig().setup();
        this.gameManager.getPowerManager().getPowerCommandManager().getPowerCommandConfig().setup();
        this.gameManager.getPowerManager().getPowerSpecialManager().getPowerSpecialConfig().setup();
        this.gameManager.getSocketManager().getSocketConfig().setup();
        this.gameManager.getItemManager().getItemConfig().setup();
        this.gameManager.getItemTypeManager().getItemTypeConfig().setup();
        this.gameManager.getItemTierManager().getItemTierConfig().setup();
        this.gameManager.getItemGeneratorManager().getItemGeneratorConfig().setup();
        this.gameManager.getItemSetManager().getItemSetConfig().setup();
    }

    private final void registerPlaceholder() {
        this.getPluginManager().getPlaceholderManager().registerAll();
    }

    private final void registerCommand() {
        final CommandExecutor commandMyItems = new CommandMyItems(this);
        final CommandExecutor commandAttributes = new CommandAttributes(this);
        final CommandExecutor commandEnchant = new CommandEnchant(this);
        final CommandExecutor commandEnchantAdd = new CommandEnchantAdd(this);
        final CommandExecutor commandEnchantClear = new CommandEnchantClear(this);
        final CommandExecutor commandEnchantRemove = new CommandEnchantRemove(this);
        final CommandExecutor commandItemName = new CommandItemName(this);
        final CommandExecutor commandLore = new CommandLore(this);
        final CommandExecutor commandLoreAdd = new CommandLoreAdd(this);
        final CommandExecutor commandLoreClear = new CommandLoreClear(this);
        final CommandExecutor commandLoreInsert = new CommandLoreInsert(this);
        final CommandExecutor commandLoreRemove = new CommandLoreRemove(this);
        final CommandExecutor commandLoreSet = new CommandLoreSet(this);
        final CommandExecutor commandNBTClear = new CommandNBTClear(this);
        final CommandExecutor commandSocket = new CommandSocket(this);
        final CommandExecutor commandUnbreakable = new CommandUnbreakable(this);
        final CommandExecutor commandNotCompatible = new CommandNotCompatible(this);
        final CommandExecutor commandFlag = ServerUtil.isCompatible(VersionNMS.V1_8_R3) ? new CommandFlag(this) : commandNotCompatible;
        final CommandExecutor commandAddFlag = ServerUtil.isCompatible(VersionNMS.V1_8_R3) ? new CommandFlagAdd(this) : commandNotCompatible;
        final CommandExecutor commandRemoveFlag = ServerUtil.isCompatible(VersionNMS.V1_8_R3) ? new CommandFlagRemove(this) : commandNotCompatible;
        final CommandExecutor commandClearFlag = ServerUtil.isCompatible(VersionNMS.V1_8_R3) ? new CommandFlagClear(this) : commandNotCompatible;
        this.getCommand("MyItems").setExecutor(commandMyItems);
        this.getCommand("ItemAtt").setExecutor(commandAttributes);
        this.getCommand("Enchant").setExecutor(commandEnchant);
        this.getCommand("EnchantAdd").setExecutor(commandEnchantAdd);
        this.getCommand("EnchantRemove").setExecutor(commandEnchantRemove);
        this.getCommand("EnchantClear").setExecutor(commandEnchantClear);
        this.getCommand("ItemName").setExecutor(commandItemName);
        this.getCommand("Lore").setExecutor(commandLore);
        this.getCommand("LoreSet").setExecutor(commandLoreSet);
        this.getCommand("LoreInsert").setExecutor(commandLoreInsert);
        this.getCommand("LoreAdd").setExecutor(commandLoreAdd);
        this.getCommand("LoreRemove").setExecutor(commandLoreRemove);
        this.getCommand("LoreClear").setExecutor(commandLoreClear);
        this.getCommand("NBTClear").setExecutor(commandNBTClear);
        this.getCommand("Socket").setExecutor(commandSocket);
        this.getCommand("Unbreakable").setExecutor(commandUnbreakable);
        this.getCommand("Flag").setExecutor(commandFlag);
        this.getCommand("FlagAdd").setExecutor(commandAddFlag);
        this.getCommand("FlagRemove").setExecutor(commandRemoveFlag);
        this.getCommand("FlagClear").setExecutor(commandClearFlag);
    }

    private final void registerTabComplete() {
        final TabCompleter tabCompleterMyItems = new TabCompleterMyItems(this);
        final TabCompleter tabCompleterAttributes = new TabCompleterAttributes(this);
        final TabCompleter tabCompleterEnchantmentAdd = new TabCompleterEnchantmentAdd(this);
        final TabCompleter tabCompleterEnchantmentRemove = new TabCompleterEnchantmentRemove(this);
        final TabCompleter tabCompleterLoreRemove = new TabCompleterLoreRemove(this);
        final TabCompleter tabCompleterSocket = new TabCompleterSocket(this);
        final TabCompleter tabCompleterUnbreakable = new TabCompleterUnbreakable(this);
        final TabCompleter tabCompleterNotCompatible = new TabCompleterNotCompatible(this);
        final TabCompleter tabCompleterFlagAdd = ServerUtil.isCompatible(VersionNMS.V1_8_R3) ? new TabCompleterFlagAdd(this) : tabCompleterNotCompatible;
        final TabCompleter tabCompleterFlagRemove = ServerUtil.isCompatible(VersionNMS.V1_8_R3) ? new TabCompleterFlagRemove(this) : tabCompleterNotCompatible;
        this.getCommand("MyItems").setTabCompleter(tabCompleterMyItems);
        this.getCommand("ItemAtt").setTabCompleter(tabCompleterAttributes);
        this.getCommand("EnchantAdd").setTabCompleter(tabCompleterEnchantmentAdd);
        this.getCommand("EnchantRemove").setTabCompleter(tabCompleterEnchantmentRemove);
        this.getCommand("LoreRemove").setTabCompleter(tabCompleterLoreRemove);
        this.getCommand("Socket").setTabCompleter(tabCompleterSocket);
        this.getCommand("Unbreakable").setTabCompleter(tabCompleterUnbreakable);
        this.getCommand("FlagAdd").setTabCompleter(tabCompleterFlagAdd);
        this.getCommand("FlagRemove").setTabCompleter(tabCompleterFlagRemove);
    }

    private final void registerEvent() {
        final Listener listenerBlockBreak = new ListenerBlockBreak(this);
        final Listener listenerBlockPhysic = new ListenerBlockPhysic(this);
        final Listener listenerCommand = new ListenerCommand(this);
        final Listener listenerPlayerDropItem = new ListenerPlayerDropItem(this);
        final Listener listenerEntityDamage = new ListenerEntityDamage(this);
        final Listener listenerEntityDamageByEntity = new ListenerEntityDamageByEntity(this);
        final Listener listenerEntityDeath = new ListenerEntityDeath(this);
        final Listener listenerEntityRegainHealth = new ListenerEntityRegainHealth(this);
        final Listener listenerHeldItem = new ListenerHeldItem(this);
        final Listener listenerInventoryClick = new ListenerInventoryClick(this);
        final Listener listenerInventoryDrag = new ListenerInventoryDrag(this);
        final Listener listenerInventoryOpen = new ListenerInventoryOpen(this);
        final Listener listenerPlayerItemDamage = new ListenerPlayerItemDamage(this);
        final Listener listenerPlayerInteract = new ListenerPlayerInteract(this);
        final Listener listenerPlayerInteractEntity = new ListenerPlayerInteractEntity(this);
        final Listener listenerPlayerJoin = new ListenerPlayerJoin(this);
        final Listener listenerPlayerRespawn = new ListenerPlayerRespawn(this);
        final Listener listenerEntityShootBowEvent = new ListenerEntityShootBow(this);
        final Listener listenerProjectileHit = new ListenerProjectileHit(this);
        final Listener listenerCombatCriticalDamage = new ListenerCombatCriticalDamage(this);
        final Listener listenerMenuClose = new ListenerMenuClose(this);
        final Listener listenerMenuOpen = new ListenerMenuOpen(this);
        final Listener listenerPowerCommandCast = new ListenerPowerCommandCast(this);
        final Listener listenerPowerPreCast = new ListenerPowerPreCast(this);
        final Listener listenerPowerShootCast = new ListenerPowerShootCast(this);
        final Listener listenerPowerSpecialCast = new ListenerPowerSpecialCast(this);
        final Listener listenerPlayerHealthMaxChange = new ListenerPlayerHealthMaxChange(this);
        ServerEventUtil.registerEvent(this, listenerBlockBreak);
        ServerEventUtil.registerEvent(this, listenerBlockPhysic);
        ServerEventUtil.registerEvent(this, listenerCommand);
        ServerEventUtil.registerEvent(this, listenerEntityDamage);
        ServerEventUtil.registerEvent(this, listenerEntityDamageByEntity);
        ServerEventUtil.registerEvent(this, listenerPlayerDropItem);
        ServerEventUtil.registerEvent(this, listenerEntityDeath);
        ServerEventUtil.registerEvent(this, listenerEntityRegainHealth);
        ServerEventUtil.registerEvent(this, listenerHeldItem);
        ServerEventUtil.registerEvent(this, listenerInventoryClick);
        ServerEventUtil.registerEvent(this, listenerInventoryDrag);
        ServerEventUtil.registerEvent(this, listenerInventoryOpen);
        ServerEventUtil.registerEvent(this, listenerPlayerItemDamage);
        ServerEventUtil.registerEvent(this, listenerPlayerInteract);
        ServerEventUtil.registerEvent(this, listenerPlayerInteractEntity);
        ServerEventUtil.registerEvent(this, listenerPlayerJoin);
        ServerEventUtil.registerEvent(this, listenerPlayerRespawn);
        ServerEventUtil.registerEvent(this, listenerEntityShootBowEvent);
        ServerEventUtil.registerEvent(this, listenerProjectileHit);
        ServerEventUtil.registerEvent(this, listenerCombatCriticalDamage);
        ServerEventUtil.registerEvent(this, listenerMenuClose);
        ServerEventUtil.registerEvent(this, listenerMenuOpen);
        ServerEventUtil.registerEvent(this, listenerPowerCommandCast);
        ServerEventUtil.registerEvent(this, listenerPowerPreCast);
        ServerEventUtil.registerEvent(this, listenerPowerShootCast);
        ServerEventUtil.registerEvent(this, listenerPowerSpecialCast);
        ServerEventUtil.registerEvent(this, listenerPlayerHealthMaxChange);
        if (ServerUtil.isCompatible(VersionNMS.V1_9_R2)) {
            final Listener listenerBlockExplode = new ListenerBlockExplode(this);
            final Listener listenerPlayerSwapHandItems = new ListenerPlayerSwapHandItems(this);
            ServerEventUtil.registerEvent(this, listenerBlockExplode);
            ServerEventUtil.registerEvent(this, listenerPlayerSwapHandItems);
        }
        if (PluginUtil.isPluginInstalled("SkillAPI")) {
            final Listener listenerPlayerLevelUp = new ListenerPlayerLevelUp(this);
            ServerEventUtil.registerEvent(this, listenerPlayerLevelUp);
        }
        if (PluginUtil.isPluginInstalled("MythicMobs")) {
            final Listener listenerMythicMobSpawn = new ListenerMythicMobSpawn(this);
            final Listener listenerMythicMobDeath = new ListenerMythicMobDeath(this);
            ServerEventUtil.registerEvent(this, listenerMythicMobSpawn);
            ServerEventUtil.registerEvent(this, listenerMythicMobDeath);
        }
        if (PluginUtil.isPluginInstalled("LifeEssence")) {
            final Listener listenerPlayerHealthRegenChange = new ListenerPlayerHealthRegenChange(this);
            ServerEventUtil.registerEvent(this, listenerPlayerHealthRegenChange);
        }
        if (PluginUtil.isPluginInstalled("CombatStamina")) {
            final Listener listenerPlayerStaminaMaxChange = new ListenerPlayerStaminaMaxChange(this);
            final Listener listenerPlayerStaminaRegenChange = new ListenerPlayerStaminaRegenChange(this);
            ServerEventUtil.registerEvent(this, listenerPlayerStaminaMaxChange);
            ServerEventUtil.registerEvent(this, listenerPlayerStaminaRegenChange);
        }
    }

    public void onDisable() {
        AntiBugUtil.antiBugCustomStats();
    }
}
