// 
// Decompiled by Procyon v0.5.36
// 

package com.praya.myitems.listener.main;

import api.praya.myitems.builder.passive.PassiveEffectEnum;
import api.praya.myitems.builder.socket.SocketGems;
import api.praya.myitems.builder.socket.SocketGemsTree;
import com.praya.agarthalib.utility.EquipmentUtil;
import com.praya.agarthalib.utility.PlayerUtil;
import com.praya.agarthalib.utility.SenderUtil;
import com.praya.agarthalib.utility.ServerUtil;
import com.praya.myitems.MyItems;
import com.praya.myitems.builder.handler.HandlerEvent;
import com.praya.myitems.config.plugin.MainConfig;
import com.praya.myitems.manager.game.*;
import com.praya.myitems.manager.plugin.LanguageManager;
import com.praya.myitems.menu.MenuSocket;
import com.praya.myitems.utility.main.TriggerSupportUtil;
import core.praya.agarthalib.bridge.unity.Bridge;
import core.praya.agarthalib.builder.menu.MenuGUI;
import core.praya.agarthalib.builder.menu.MenuSlot;
import core.praya.agarthalib.enums.branch.SoundEnum;
import core.praya.agarthalib.enums.main.Slot;
import core.praya.agarthalib.enums.main.SlotType;
import core.praya.agarthalib.enums.main.VersionNMS;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashMap;

public class ListenerInventoryClick extends HandlerEvent implements Listener {
    public ListenerInventoryClick(final MyItems plugin) {
        super(plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void socket(final InventoryClickEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final MenuManager menuManager = gameManager.getMenuManager();
        final SocketManager socketManager = gameManager.getSocketManager();
        final LanguageManager lang = this.plugin.getPluginManager().getLanguageManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (!event.isCancelled()) {
            final Inventory inventory = event.getInventory();
            final InventoryType type = inventory.getType();
            if (type.equals(InventoryType.CRAFTING)) {
                final InventoryHolder holder = inventory.getHolder();
                if (!(holder instanceof MenuGUI)) {
                    final Player player = PlayerUtil.parse(event.getWhoClicked());
                    final ItemStack cursorItem = event.getCursor();
                    final ItemStack currentItem = event.getCurrentItem();
                    if (EquipmentUtil.isSolid(cursorItem) && EquipmentUtil.isSolid(currentItem)) {
                        final GameMode gameMode = player.getGameMode();
                        final InventoryAction inventoryAction = event.getAction();
                        final boolean checkSocket = gameMode.equals(GameMode.CREATIVE) ? inventoryAction.equals(InventoryAction.PLACE_ALL) : inventoryAction.equals(InventoryAction.SWAP_WITH_CURSOR);
                        if (checkSocket) {
                            final ItemStack itemRodUnlock = mainConfig.getSocketItemRodUnlock();
                            final ItemStack itemRodRemove = mainConfig.getSocketItemRodRemove();
                            final boolean containsSocketEmpty = socketManager.containsSocketEmpty(currentItem);
                            final boolean containsSocketLocked = socketManager.containsSocketLocked(currentItem);
                            final boolean containsSocketGems = socketManager.containsSocketGems(currentItem);
                            final boolean isSocketGems = socketManager.isSocketItem(cursorItem);
                            final boolean isSocketRodUnlock = cursorItem.isSimilar(itemRodUnlock);
                            final boolean isSocketRodRemove = cursorItem.isSimilar(itemRodRemove);
                            if (isSocketGems) {
                                if (!containsSocketEmpty) {
                                    final String message = lang.getText(player, "Socket_Slot_Empty_Failure");
                                    SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                                    SenderUtil.sendMessage(player, message);
                                    return;
                                }
                                final SocketGems socket = socketManager.getSocketBuild(cursorItem);
                                final SocketGemsTree socketTree = socket.getSocketTree();
                                final SlotType typeItem = socketTree.getTypeItem();
                                final SlotType typeDefault = SlotType.getSlotType(currentItem);
                                if (!typeItem.equals(typeDefault) && !typeItem.equals(SlotType.UNIVERSAL)) {
                                    final String message2 = lang.getText(player, "Socket_Type_Not_Match");
                                    SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                                    SenderUtil.sendMessage(player, message2);
                                    return;
                                }
                            } else if (isSocketRodUnlock) {
                                if (!containsSocketLocked) {
                                    final String message = lang.getText(player, "Socket_Slot_Locked_Failure");
                                    SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                                    SenderUtil.sendMessage(player, message);
                                    return;
                                }
                            } else {
                                if (!isSocketRodRemove) {
                                    return;
                                }
                                if (!containsSocketGems) {
                                    final String message = lang.getText(player, "Socket_Slot_Gems_Failure");
                                    SenderUtil.playSound(player, SoundEnum.ENTITY_BLAZE_DEATH);
                                    SenderUtil.sendMessage(player, message);
                                    return;
                                }
                            }
                            if (!ServerUtil.isCompatible(VersionNMS.V1_10_R1)) {
                                event.setCancelled(true);
                            }
                            menuManager.openMenuSocket(player);
                            final InventoryView view = player.getOpenInventory();
                            final Inventory topInventory = view.getTopInventory();
                            final InventoryHolder topHolder = topInventory.getHolder();
                            if (topHolder instanceof MenuGUI) {
                                final MenuGUI menuGUI = (MenuGUI) topHolder;
                                final MenuSocket executor = (MenuSocket) menuGUI.getExecutor();
                                final MenuGUI.SlotCell cellItemInput = MenuGUI.SlotCell.B3;
                                final MenuGUI.SlotCell cellSocketInput = MenuGUI.SlotCell.C3;
                                final MenuSlot menuSlotItemInput = menuGUI.getMenuSlot(cellItemInput.getIndex());
                                final MenuSlot menuSlotSocketInput = menuGUI.getMenuSlot(cellSocketInput.getIndex());
                                menuSlotItemInput.setItem(currentItem);
                                menuSlotSocketInput.setItem(cursorItem);
                                menuGUI.setMenuSlot(menuSlotItemInput);
                                menuGUI.setMenuSlot(menuSlotSocketInput);
                                executor.updateSocketMenu(menuGUI, player);
                                player.setItemOnCursor(null);
                                if (gameMode.equals(GameMode.CREATIVE)) {
                                    new BukkitRunnable() {
                                        public void run() {
                                            event.setCurrentItem(null);
                                        }
                                    }.runTaskLater(this.plugin, 0L);
                                } else {
                                    event.setCurrentItem(null);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void triggerSupport(final InventoryClickEvent event) {
        if (!event.isCancelled()) {
            final Inventory inventory = event.getInventory();
            final InventoryHolder holder = inventory.getHolder();
            if (!(holder instanceof MenuGUI)) {
                final Player player = PlayerUtil.parse(event.getWhoClicked());
                final ItemStack oldHelmet = Bridge.getBridgeEquipment().getEquipment(player, Slot.HELMET);
                final ItemStack oldChestplate = Bridge.getBridgeEquipment().getEquipment(player, Slot.CHESTPLATE);
                final ItemStack oldLeggings = Bridge.getBridgeEquipment().getEquipment(player, Slot.LEGGINGS);
                final ItemStack oldBoots = Bridge.getBridgeEquipment().getEquipment(player, Slot.BOOTS);
                new BukkitRunnable() {
                    public void run() {
                        final ItemStack newHelmet = Bridge.getBridgeEquipment().getEquipment(player, Slot.HELMET);
                        final ItemStack newChestplate = Bridge.getBridgeEquipment().getEquipment(player, Slot.CHESTPLATE);
                        final ItemStack newLeggings = Bridge.getBridgeEquipment().getEquipment(player, Slot.LEGGINGS);
                        final ItemStack newBoots = Bridge.getBridgeEquipment().getEquipment(player, Slot.BOOTS);
                        if (oldHelmet != newHelmet || oldChestplate != newChestplate || oldLeggings != newLeggings || oldBoots != newBoots) {
                            TriggerSupportUtil.updateSupport(player);
                        }
                    }
                }.runTaskLater(this.plugin, 0L);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void eventChangeEquipment(final InventoryClickEvent event) {
        final GameManager gameManager = this.plugin.getGameManager();
        final PassiveEffectManager passiveEffectManager = gameManager.getPassiveEffectManager();
        final ItemSetManager itemSetManager = gameManager.getItemSetManager();
        final MainConfig mainConfig = MainConfig.getInstance();
        if (!event.isCancelled()) {
            final Player player = PlayerUtil.parse(event.getWhoClicked());
            final InventoryType.SlotType slotType = event.getSlotType();
            final ClickType click = event.getClick();
            final ItemStack itemCurrent = event.getCurrentItem();
            final ItemStack itemCursor = event.getCursor();
            final boolean enableGradeCalculation = mainConfig.isPassiveEnableGradeCalculation();
            final Collection<PassiveEffectEnum> currentPassiveEffects = passiveEffectManager.getPassiveEffects(itemCurrent);
            final Collection<PassiveEffectEnum> cursorPassiveEffects = passiveEffectManager.getPassiveEffects(itemCursor);
            final HashMap<Slot, ItemStack> mapItemSlot = new HashMap<Slot, ItemStack>();
            Slot[] values;
            for (int length = (values = Slot.values()).length, i = 0; i < length; ++i) {
                final Slot slot = values[i];
                final ItemStack itemSlot = Bridge.getBridgeEquipment().getEquipment(player, slot);
                mapItemSlot.put(slot, itemSlot);
            }
            new BukkitRunnable() {
                public void run() {
                    final InventoryView inventoryView = player.getOpenInventory();
                    final InventoryType inventoryType = inventoryView.getType();
                    final Inventory inventory = inventoryType.equals(InventoryType.CREATIVE) ? null : inventoryView.getTopInventory();
                    Slot[] values;
                    for (int length = (values = Slot.values()).length, i = 0; i < length; ++i) {
                        final Slot slot = values[i];
                        final ItemStack itemBefore = mapItemSlot.get(slot);
                        final ItemStack itemAfter = Bridge.getBridgeEquipment().getEquipment(player, slot);
                        final boolean isSolidBefore = itemBefore != null;
                        final boolean isSolidAfter = itemAfter != null;
                        if (isSolidBefore && isSolidAfter) {
                            if (itemBefore.equals(itemAfter)) {
                                continue;
                            }
                        } else if (isSolidBefore == isSolidAfter) {
                            continue;
                        }
                        if (itemSetManager.isItemSet(itemBefore) || itemSetManager.isItemSet(itemAfter)) {
                            itemSetManager.updateItemSet(player, true, inventory);
                            break;
                        }
                    }
                }
            }.runTaskLater(this.plugin, 0L);
            if (click.equals(ClickType.NUMBER_KEY) || click.equals(ClickType.LEFT) || click.equals(ClickType.SHIFT_LEFT) || click.equals(ClickType.RIGHT) || click.equals(ClickType.SHIFT_RIGHT)) {
                final ItemStack itemMainHand = Bridge.getBridgeEquipment().getEquipment(player, Slot.MAINHAND);
                final ItemStack itemOffHand = Bridge.getBridgeEquipment().getEquipment(player, Slot.OFFHAND);
                final Collection<PassiveEffectEnum> mainPassiveEffects = passiveEffectManager.getPassiveEffects(itemMainHand);
                final Collection<PassiveEffectEnum> offPassiveEffects = passiveEffectManager.getPassiveEffects(itemOffHand);
                new BukkitRunnable() {
                    public void run() {
                        passiveEffectManager.reloadPassiveEffect(player, mainPassiveEffects, enableGradeCalculation);
                        passiveEffectManager.reloadPassiveEffect(player, offPassiveEffects, enableGradeCalculation);
                        passiveEffectManager.reloadPassiveEffect(player, cursorPassiveEffects, enableGradeCalculation);
                        passiveEffectManager.reloadPassiveEffect(player, currentPassiveEffects, enableGradeCalculation);
                        if (click.equals(ClickType.NUMBER_KEY)) {
                            TriggerSupportUtil.updateSupport(player);
                        }
                    }
                }.runTaskLater(this.plugin, 0L);
            } else if (slotType.equals(InventoryType.SlotType.ARMOR) || slotType.equals(InventoryType.SlotType.QUICKBAR)) {
                new BukkitRunnable() {
                    public void run() {
                        passiveEffectManager.reloadPassiveEffect(player, cursorPassiveEffects, enableGradeCalculation);
                        passiveEffectManager.reloadPassiveEffect(player, currentPassiveEffects, enableGradeCalculation);
                        TriggerSupportUtil.updateSupport(player);
                    }
                }.runTaskLater(this.plugin, 0L);
            }
        }
    }
}
