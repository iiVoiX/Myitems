// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.custom;

import api.praya.myitems.builder.event.PowerCommandCastEvent;
import api.praya.myitems.builder.event.PowerPreCastEvent;
import api.praya.myitems.builder.event.PowerShootCastEvent;
import api.praya.myitems.builder.event.PowerSpecialCastEvent;
import api.praya.myitems.builder.lorestats.LoreStatsEnum;
import api.praya.myitems.builder.lorestats.LoreStatsOption;
import api.praya.myitems.builder.player.PlayerPowerCooldown;
import api.praya.myitems.builder.power.PowerClickEnum;
import api.praya.myitems.builder.power.PowerEnum;
import api.praya.myitems.builder.power.PowerSpecialEnum;
import com.praya.agarthalib.utility.MathUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.ServerEventUtil;
import com.praya.agarthalib.utility.TimeUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.*;
import com.praya.myitems.manager.player.PlayerManager;
import com.praya.myitems.manager.player.PlayerPowerManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import com.praya.myitems.utility.main.ProjectileUtil;
import core.praya.agarthalib.builder.message.MessageBuild;
import core.praya.agarthalib.enums.branch.ProjectileEnum;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ListenerPowerPreCast extends HandlerEvent implements Listener {
    public ListenerPowerPreCast(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void eventPowerPreCast(final PowerPreCastEvent event) {
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final PlayerManager playerManager = this.plugin.getPlayerManager();
        final GameManager gameManager = this.plugin.getGameManager();
        final PowerManager powerManager = gameManager.getPowerManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final RequirementManager requirementManager = gameManager.getRequirementManager();
        final PowerCommandManager powerCommandManager = powerManager.getPowerCommandManager();
        final PlayerPowerManager playerPowerManager = playerManager.getPlayerPowerManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (!event.isCancelled()) {
            final Player player = event.getPlayer();
            final PowerEnum power = event.getPower();
            final PowerClickEnum click = event.getClick();
            final ItemStack item = event.getItem();
            final String lore = event.getLore();
            final int durability = (int) statsManager.getLoreValue(item, LoreStatsEnum.DURABILITY, LoreStatsOption.CURRENT);
            final PlayerPowerCooldown powerCooldown = playerPowerManager.getPlayerPowerCooldown(player);
            final String[] cooldownList = lore.split(MainConfig.KEY_COOLDOWN);
            if (requirementManager.isAllowed(player, item) && cooldownList.length > 1) {
                final String keyCooldown = cooldownList[1].replace(mainConfig.getPowerColorCooldown(), "");
                if (!MathUtil.isNumber(keyCooldown)) {
                    return;
                }
                final double cooldown = Math.max(0.0, Double.valueOf(keyCooldown));
                if (!statsManager.checkDurability(item, durability)) {
                    statsManager.sendBrokenCode(player, Slot.MAINHAND, false);
                } else {
                    final boolean enableMessageCooldown = mainConfig.isPowerEnableMessageCooldown();
                    if (power.equals(PowerEnum.COMMAND)) {
                        final String[] commandList = lore.split(MainConfig.KEY_COMMAND);
                        if (commandList.length > 1) {
                            final String loreCommand = commandList[1].replaceFirst(mainConfig.getPowerColorType(), "");
                            final String keyCommand = powerCommandManager.getCommandKeyByLore(loreCommand);
                            if (keyCommand != null) {
                                if (powerCooldown.isPowerCommandCooldown(keyCommand)) {
                                    if (enableMessageCooldown) {
                                        final long timeLeft = powerCooldown.getPowerCommandTimeLeft(keyCommand);
                                        final MessageBuild message = lang.getMessage("Power_Command_Cooldown");
                                        final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
                                        mapPlaceholder.put("power", keyCommand);
                                        mapPlaceholder.put("time", TimeUtil.getTextTime(timeLeft));
                                        message.sendMessage(player, mapPlaceholder);
                                        SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                                    }
                                } else {
                                    final PowerCommandCastEvent powerCommandCastEvent = new PowerCommandCastEvent(player, power, click, item, lore, keyCommand, cooldown);
                                    ServerEventUtil.callEvent(powerCommandCastEvent);
                                }
                            }
                        }
                    } else if (power.equals(PowerEnum.SHOOT)) {
                        final String[] projectileList = lore.split(MainConfig.KEY_SHOOT);
                        if (projectileList.length > 1) {
                            final String loreProjectle = projectileList[1].replace(mainConfig.getPowerColorType(), "");
                            final ProjectileEnum projectile = ProjectileUtil.getProjectileByLore(loreProjectle);
                            if (projectile != null) {
                                if (powerCooldown.isPowerShootCooldown(projectile)) {
                                    if (enableMessageCooldown) {
                                        final long timeLeft = powerCooldown.getPowerShootTimeLeft(projectile);
                                        final MessageBuild message = lang.getMessage("Power_Shoot_Cooldown");
                                        final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
                                        mapPlaceholder.put("power", ProjectileUtil.getText(projectile));
                                        mapPlaceholder.put("time", TimeUtil.getTextTime(timeLeft));
                                        message.sendMessage(player, mapPlaceholder);
                                        SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                                    }
                                } else {
                                    final PowerShootCastEvent powerShootCastEvent = new PowerShootCastEvent(player, power, click, item, lore, projectile, cooldown);
                                    ServerEventUtil.callEvent(powerShootCastEvent);
                                }
                            }
                        }
                    } else if (power.equals(PowerEnum.SPECIAL)) {
                        final String[] specialList = lore.split(MainConfig.KEY_SPECIAL);
                        if (specialList.length > 1) {
                            final String loreSpecial = specialList[1].replace(mainConfig.getPowerColorType(), "");
                            final PowerSpecialEnum special = PowerSpecialEnum.getSpecialByLore(loreSpecial);
                            if (special != null) {
                                if (powerCooldown.isPowerSpecialCooldown(special)) {
                                    if (enableMessageCooldown) {
                                        final long timeLeft = powerCooldown.getPowerSpecialTimeLeft(special);
                                        final MessageBuild message = lang.getMessage("Power_Special_Cooldown");
                                        final HashMap<String, String> mapPlaceholder = new HashMap<String, String>();
                                        mapPlaceholder.put("power", special.getText());
                                        mapPlaceholder.put("time", TimeUtil.getTextTime(timeLeft));
                                        message.sendMessage(player, mapPlaceholder);
                                        SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                                    }
                                } else {
                                    final PowerSpecialCastEvent powerSpecialCastEvent = new PowerSpecialCastEvent(player, power, click, item, lore, special, cooldown);
                                    ServerEventUtil.callEvent(powerSpecialCastEvent);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
