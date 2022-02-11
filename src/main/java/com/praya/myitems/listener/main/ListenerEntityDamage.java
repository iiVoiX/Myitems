// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import api.praya.myitems.builder.lorestats.LoreStatsArmor;
import api.praya.myitems.builder.socket.SocketGemsProperties;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.GameManager;
import com.praya.myitems.manager.game.LoreStatsManager;
import com.praya.myitems.manager.game.RequirementManager;
import com.praya.myitems.manager.game.SocketManager;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.manager.plugin.PluginManager;
import core.praya.agarthalib.bridge.main.MainBridge;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.utility.*;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ListenerEntityDamage extends HandlerEvent implements Listener {
    public ListenerEntityDamage(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void checkBound(final EntityDamageEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PluginManager pluginManager = this.plugin.getPluginManager();
        final RequirementManager requirementManager = gameManager.getRequirementManager();
        final LanguageManager lang = pluginManager.getLanguageManager();
        if (!event.isCancelled()) {
            final Entity entity = event.getEntity();
            if (entity instanceof Player) {
                final Player player = (Player) entity;
                Slot[] values;
                for (int length = (values = Slot.values()).length, i = 0; i < length; ++i) {
                    final Slot slot = values[i];
                    if (slot.getID() > 1) {
                        final ItemStack item = MainBridge.getBridgeEquipment().getEquipment(player, slot);
                        if (EquipmentUtil.loreCheck(item)) {
                            if (!requirementManager.isAllowed(player, item)) {
                                final String message = TextUtil.placeholder(lang.getText("Item_Lack_Requirement"), "Item", EquipmentUtil.getDisplayName(item));
                                SenderUtil.sendMessage(player, message);
                                SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                            } else {
                                final Integer lineUnbound = requirementManager.getLineRequirementSoulUnbound(item);
                                if (lineUnbound != null) {
                                    final String loreBound = requirementManager.getTextSoulBound(player);
                                    final Integer lineOld = requirementManager.getLineRequirementSoulBound(item);
                                    final HashMap<String, String> map = new HashMap<String, String>();
                                    if (lineOld != null) {
                                        EquipmentUtil.removeLore(item, lineOld);
                                    }
                                    String message2 = lang.getText("Item_Bound");
                                    map.put("item", EquipmentUtil.getDisplayName(item));
                                    map.put("player", player.getName());
                                    message2 = TextUtil.placeholder(map, message2);
                                    requirementManager.setMetadataSoulbound(player, item);
                                    EquipmentUtil.setLore(item, lineUnbound, loreBound);
                                    SenderUtil.sendMessage(player, message2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void damageEvent(final EntityDamageEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        final LoreStatsManager statsManager = gameManager.getStatsManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (!event.isCancelled()) {
            final boolean customDamage = mainConfig.isModifierEnableCustomModifier();
            if (customDamage) {
                final Entity getEntity = event.getEntity();
                if (EntityUtil.isLivingEntity(getEntity) && MainBridge.getBridgeLivingEntity().isLivingEntity(getEntity)) {
                    final LivingEntity victims = EntityUtil.parseLivingEntity(getEntity);
                    boolean defenseAffect = false;
                    switch (event.getCause()) {
                        case ENTITY_EXPLOSION: {
                            defenseAffect = mainConfig.isModifierDefenseAffectEntityExplosion();
                            break;
                        }
                        case BLOCK_EXPLOSION: {
                            defenseAffect = mainConfig.isModifierDefenseAffectBlockExplosion();
                            break;
                        }
                        case CUSTOM: {
                            defenseAffect = mainConfig.isModifierDefenseAffectCustom();
                            break;
                        }
                        case CONTACT: {
                            defenseAffect = mainConfig.isModifierDefenseAffectContact();
                            break;
                        }
                        case FALL: {
                            defenseAffect = mainConfig.isModifierDefenseAffectFall();
                            break;
                        }
                        case FALLING_BLOCK: {
                            defenseAffect = mainConfig.isModifierDefenseAffectFallingBlock();
                            break;
                        }
                        case THORNS: {
                            defenseAffect = mainConfig.isModifierDefenseAffectThorn();
                            break;
                        }
                        default: {
                            defenseAffect = false;
                            break;
                        }
                    }
                    if (defenseAffect) {
                        final LoreStatsArmor loreStatsVictims = statsManager.getLoreStatsArmor(victims);
                        final SocketGemsProperties socketVictims = socketManager.getSocketProperties(victims);
                        final double scaleDefenseOverall = mainConfig.getModifierScaleDefenseOverall();
                        final double scaleDefensePhysic = mainConfig.getModifierScaleDefensePhysic();
                        double damage = event.getDamage();
                        double defense = loreStatsVictims.getDefense() + socketVictims.getAdditionalDefense() + damage * socketVictims.getPercentDefense() / 100.0;
                        defense = defense * scaleDefenseOverall * scaleDefensePhysic;
                        damage -= defense;
                        damage = MathUtil.limitDouble(damage, 1.0, damage);
                        event.setDamage(damage);
                    }
                }
            }
        }
    }
}
