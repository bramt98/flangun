package net.arcanon.flanguns.command;

import de.tr7zw.nbtapi.NBTItem;
import net.arcanon.flanguns.FlanGuns;
import net.arcanon.flanguns.attachment.Attachment;
import net.arcanon.flanguns.attachment.list.*;
import net.arcanon.flanguns.item.CustomItem;
import net.arcanon.flanguns.item.ItemType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FlanGunsCommand implements TabExecutor {

    private final FlanGuns plugin;

    public FlanGunsCommand(FlanGuns plugin) {
        this.plugin = plugin;
    }

    private final String incorrectUsageMessage = "§cIncorrect usage. Use /flanguns help for help!";
    private final String invalidPlayer = "§cThat player does not exist!";
    private final String offlinePlayer = "§cThat player is currently not online!";
    private final String invalidItem = "§cThat item does not exist!";
    private final String givenItem = "§7You have given §f%item% §7to §f%player%§7.";
    private final String notAGun = "§cThis command can only be used on guns!";
    private final String invalidAttachment = "§cThat attachment doesn't exist!";
    private final String noAttachmentsAllowed = "§cThat gun does not allow any attachments!";
    private final String attachmentNotAllowed = "§cThat attachment is not allowed on this gun!";
    private final String alreadyHasAttachment = "§cThis gun already has that attachment!";
    private final String addedAttachment = "§aSuccessfully added the '%attachment%' attachment to your gun!";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("flanguns")) {

            // Speler check (niet console die send)
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cOnly players can run that command!");
                return true;
            }

            // Permission check -> hier kun je het aanpassen
            Player player = (Player) sender;
            if (!player.hasPermission("flanguns.admin")) {
                player.sendMessage("§cYou don't have permission!");
                return true;
            }

            if (args.length <= 4) {

                // Help command
                if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                    player.sendMessage("");
                    player.sendMessage("§8----- §7§lFlanGuns Help §8-----");
                    player.sendMessage("§7/flanguns help");
                    player.sendMessage("§7/flanguns addattachment <attachment>");
                    player.sendMessage("§7/flanguns give <player> <item>");
                    player.sendMessage("");
                    return true;
                }

                // Attachment command
                else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("addattachment")) {
                        ItemStack itemInHand = player.getInventory().getItemInMainHand();
                        if (itemInHand == null || itemInHand.getType().equals(Material.AIR)) {
                            player.sendMessage(notAGun);
                            return true;
                        }

                        ItemMeta handMeta = itemInHand.getItemMeta();
                        List<String> lore = handMeta.getLore();
                        NBTItem nbtItem = new NBTItem(itemInHand);

                        // Item in hand is not a gun
                        if (!nbtItem.hasKey("FlanGuns_maxAmmo")) {
                            player.sendMessage(notAGun);
                            return true;
                        }

                        Attachment attachment = null;
                        if (args[1].equalsIgnoreCase("ACOG_Sight")) {
                            attachment = new ACOGSight();
                        } else if (args[1].equalsIgnoreCase("Foregrip")) {
                            attachment = new Foregrip();
                        } else if (args[1].equalsIgnoreCase("4x_Scope")) {
                            attachment = new FourXSight();
                        } else if (args[1].equalsIgnoreCase("Red_Dot_Sight")) {
                            attachment = new RedDotSight();
                        } else if (args[1].equalsIgnoreCase("Silencer")) {
                            attachment = new Silencer();
                        }

                        // Invalid args input
                        if (attachment == null) {
                            player.sendMessage(invalidAttachment);
                            return true;
                        }

                        List<Attachment> allowedAttachment = new ArrayList<>();
                        String[] attachments = nbtItem.getString("FlanGuns_allowedAttachments").split(";");
                        for (String attach : attachments) {
                            switch (attach) {
                                case "ACOG_Sight":
                                    allowedAttachment.add(new ACOGSight());
                                    break;
                                case "Foregrip":
                                    allowedAttachment.add(new Foregrip());
                                    break;
                                case "4x_Scope":
                                    allowedAttachment.add(new FourXSight());
                                    break;
                                case "Red_Dot_Sight":
                                    allowedAttachment.add(new RedDotSight());
                                    break;
                                case "Silencer":
                                    allowedAttachment.add(new Silencer());
                                    break;
                            }
                        }

                        // Gun doesn't allow any attachments
                        if (allowedAttachment.isEmpty()) {
                            player.sendMessage(noAttachmentsAllowed);
                            return true;
                        }

                        boolean allowed = false;
                        for (Attachment attachment1 : allowedAttachment) {
                            if (attachment1.getName().equals(attachment.getName())) {
                                allowed = true;
                                break;
                            }
                        }

                        // Time to add attachment
                        if (allowed) {
                            ItemStack itemToUpdate;

                            // Set scope
                            if (attachment instanceof ACOGSight || attachment instanceof FourXSight || attachment instanceof RedDotSight) {
                                System.out.println("hasKey: " + nbtItem.hasKey("FlanGuns_attachmentScope"));
                                System.out.println("equals: " + nbtItem.getString("FlanGuns_attachmentScope").equals(attachment.getName()));
                                if (nbtItem.hasKey("FlanGuns_attachmentScope") && nbtItem.getString("FlanGuns_attachmentScope").equals(attachment.getName())) {
                                    player.sendMessage(alreadyHasAttachment);
                                    return true;
                                }

                                nbtItem.setString("FlanGuns_attachmentScope", attachment.getName());
                                ItemStack newStack = nbtItem.getItem();
                                ItemMeta itemMeta = newStack.getItemMeta();
                                String nameToUse = attachment.getName().replace("_", " ");
                                List<String> loreHere = itemMeta.getLore();
                                loreHere.set(0, "§9Scope: §7" + nameToUse);
                                itemMeta.setLore(loreHere);
                                newStack.setItemMeta(itemMeta);
                                itemToUpdate = newStack;
                            }

                            // Set Foregrip
                            else if (attachment instanceof Foregrip) {
                                if (nbtItem.getBoolean("FlanGuns_hasForegrip")) {
                                    player.sendMessage(alreadyHasAttachment);
                                    return true;
                                }

                                nbtItem.setBoolean("FlanGuns_hasForegrip", true);

                                ItemStack newStack = nbtItem.getItem();
                                ItemMeta itemMeta = newStack.getItemMeta();
                                List<String> loreHere = itemMeta.getLore();

                                if (nbtItem.getBoolean("FlanGuns_hasSilencer")) {
                                    loreHere.set(1, "§9Attachments: §7Foregrip, Silencer");
                                } else {
                                    loreHere.set(1, "§9Attachments: §7Foregrip");
                                }

                                itemMeta.setLore(loreHere);
                                newStack.setItemMeta(itemMeta);
                                itemToUpdate = newStack;
                            }

                            // Set Silencer
                            else {
                                if (nbtItem.getBoolean("FlanGuns_hasSilencer")) {
                                    player.sendMessage(alreadyHasAttachment);
                                    return true;
                                }

                                nbtItem.setBoolean("FlanGuns_hasSilencer", true);

                                ItemStack newStack = nbtItem.getItem();
                                ItemMeta itemMeta = newStack.getItemMeta();
                                List<String> loreHere = itemMeta.getLore();

                                if (nbtItem.getBoolean("FlanGuns_hasForegrip")) {
                                    lore.set(1, "§9Attachments: §7Foregrip, Silencer");
                                } else {
                                    lore.set(1, "§9Attachments: §7Silencer");
                                }

                                itemMeta.setLore(loreHere);
                                newStack.setItemMeta(itemMeta);
                                itemToUpdate = newStack;
                            }

                            player.getInventory().setItemInMainHand(itemToUpdate);
                            String nameToUse = attachment.getName().replace("_", " ");
                            String finalSuccessMessage = addedAttachment.replace("%attachment%", nameToUse);
                            player.sendMessage(finalSuccessMessage);
                        }

                        // Particular attachment not allowed
                        else {
                            player.sendMessage(attachmentNotAllowed);
                            return true;
                        }
                    } else {
                        player.sendMessage(incorrectUsageMessage);
                    }
                    return true;
                }

                // Give command
                else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("give")) {
                        if (Bukkit.getPlayer(args[1]) != null) {
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target.isOnline()) {
                                if (!plugin.getItemManager().customItemExists(args[2])) {
                                    player.sendMessage(invalidItem);
                                    return true;
                                }
                                CustomItem customItem = plugin.getItemManager().getCustomItemByName(args[2]);
                                ItemStack itemStack = customItem.getItemStack();
                                NBTItem nbtItem = new NBTItem(itemStack);
                                UUID uuid = UUID.randomUUID();
                                if (nbtItem.getString("FlanGuns_type").equals(ItemType.GUN.name())) {
                                    nbtItem.setString("FlanGuns_gunUUID", uuid.toString());
                                    plugin.getAmmoManager().register(uuid, (short) 0);
                                }

                                target.getInventory().addItem(nbtItem.getItem());
                                player.sendMessage(givenItem.replace("%player%", target.getName()).replace("%item%", customItem.getName()));
                                return true;
                            } else {
                                sender.sendMessage(offlinePlayer);
                                return true;
                            }
                        } else {
                            sender.sendMessage(invalidPlayer);
                            return true;
                        }
                    } else {
                        player.sendMessage(incorrectUsageMessage);
                    }
                    return true;
                } else {
                    player.sendMessage(incorrectUsageMessage);
                    return true;
                }
            } else {
                player.sendMessage(incorrectUsageMessage);
                return true;
            }
        }
        return false;
    }

    // Tab complete stuff
    private final List<String> COMMANDS = Arrays.asList("help", "give", "addattachment");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], COMMANDS, new ArrayList<>());
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("give")) {
                List<String> playerNames = new ArrayList<>();
                Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
                Bukkit.getServer().getOnlinePlayers().toArray(players);
                for (Player player : players) {
                    playerNames.add(player.getName());
                }
                return StringUtil.copyPartialMatches(args[1], playerNames, new ArrayList<>());
            } else if (args[0].equalsIgnoreCase("addattachment")) {
                List<String> commandNames = new ArrayList<>();

                for (Attachment attachment : plugin.getItemManager().getAttachmentList()) {
                    commandNames.add(attachment.getName());
                }

                return StringUtil.copyPartialMatches(args[1], commandNames, new ArrayList<>());
            } else {
                for (String argument : args) {
                    return new ArrayList<>();
                }
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("give")) {
                List<String> commandNames = new ArrayList<>();

                for (CustomItem customItem : plugin.getItemManager().getItemList()) {
                    commandNames.add(customItem.getName());
                }

                return StringUtil.copyPartialMatches(args[2], commandNames, new ArrayList<>());
            } else {
                for (String argument : args) {
                    return new ArrayList<>();
                }
            }
        } else {
            return new ArrayList<>();
        }
        return null;
    }
}