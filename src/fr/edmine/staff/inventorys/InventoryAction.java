package fr.edmine.staff.inventorys;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.edmine.staff.Staff;
import fr.edmine.staff.channel.Message;
import fr.edmine.staff.channel.Message.Channel;
import fr.edmine.staff.item.ItemBuilder;
import fr.edmine.staff.managers.Sessions;

public class InventoryAction implements Listener
{
	private Message message;
	private Sessions session;
	private ItemBuilder item;
	private Staff instance;
	
	public InventoryAction(Staff main)
	{
		this.instance = main;
		this.session = main.getSessions();
	}

	public void openInventory(Player player)
	{
		Inventory inventory = Bukkit.createInventory(null, 54, "Actions");
		
		Sessions playerSession = this.session.getSession(player);
		
		String ifFreeze = playerSession.getFreeze() ? "§aActiver" : "§cDésactiver";
		this.item = new ItemBuilder(Material.PACKED_ICE);
		this.item.setDisplayName("§bFreeze")
		.setLore(new String[] {"", "§8Mode: " + ifFreeze, ""})
		.build();
		if (playerSession.getFreeze()) this.item.addEnchant(Enchantment.DURABILITY).build();
		inventory.setItem(20, this.item.getItemStack());
		
		player.openInventory(inventory);
	}
	
	@EventHandler
	public void onInteractInventory(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		this.message = new Message(player);
		ItemStack itemClicked = event.getCurrentItem();
		
		Sessions playerSession = this.session.getSession(player);
		
		if (event.getView().getTitle().equals("Actions"))
		{
			if (itemClicked == null || event.getClickedInventory() == null) return;
			event.setCancelled(true);
			
			switch (event.getSlot())
			{
					//freeze
				case 20:
					ItemMeta itemMeta = itemClicked.getItemMeta();
					if (playerSession.getFreeze() == false)
					{
						playerSession.setFreeze(true);
						itemMeta.setLore(Arrays.asList(new String[] {"", "§8Mode: §aActiver", ""}));
						itemMeta.addEnchant(Enchantment.DURABILITY, 0, false);
						this.instance.getActions().getFreezeList().add(playerSession.getTarget());
						this.message.send(Channel.PLAYER, "§7Vous avez été freeze par §3" + player.getName());
					}
					else if (playerSession.getFreeze() == true)
					{
						playerSession.setFreeze(false);
						itemMeta.setLore(Arrays.asList(new String[] {"", "§8Mode: §cDésactiver", ""}));
						itemMeta.removeEnchant(Enchantment.DURABILITY);
						this.instance.getActions().getFreezeList().remove(playerSession.getTarget());
						this.message.send(Channel.PLAYER, "§7Vous avez été défreeze par §3" + player.getName());
					}
					itemClicked.setItemMeta(itemMeta);
					event.getView().setItem(20, itemClicked);
					break;
			}
		}
	}
	
}
