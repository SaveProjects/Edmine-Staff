package fr.edmine.staff.inventorys;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.edmine.staff.Staff;
import fr.edmine.staff.channel.Message;
import fr.edmine.staff.channel.Message.Channel;
import fr.edmine.staff.item.ItemBuilder;
import fr.edmine.staff.managers.Sessions;

public class PlayerInventory implements Listener
{

	private Sessions session;
	private ItemBuilder item;
	private Message message;
	private Staff instance;

	public PlayerInventory(Staff main)
	{
		this.session = main.getSessions();
		this.instance = main;
	}

	public void openInventory(Player player)
	{
		Sessions playerSession = this.session.getSession(player);

		Inventory inventory = Bukkit.createInventory(null, 54, playerSession.getTarget().getName());

		this.item = new ItemBuilder(Material.REDSTONE_TORCH_ON);
		this.item.setDisplayName("§aAction")
		.setLore(new String[] {
				"", 
				"§7Permet d'éffectuer des actions sur le joueur", 
				""
		}).build();
		inventory.setItem(23, this.item.getItemStack());
		
		this.item = new ItemBuilder(Material.WRITTEN_BOOK);
		this.item.setDisplayName("§eSanction").build();
		inventory.setItem(25, this.item.getItemStack());

		this.item = new ItemBuilder(Material.PAPER);
		this.item.setDisplayName("§fAvertir")
		.setLore(new String[] {"", 
				"§7§nDescription:", 
				"§7Avertit le joueur",
				""
		}).build();
		inventory.setItem(41, this.item.getItemStack());
		
		this.item = new ItemBuilder(Material.BOOK_AND_QUILL);
		this.item.setDisplayName("§4Catégories")
		.setLore(new String[] {"", 
				"§7§nDescription:", 
				"§7Cette catégorie contient 3 catégories", 
				"§eMute", "§6Ban", "§4Ban-ip",
				"§7Sélectionner s'en une adapter à la situation",
				""
		}).build();
		inventory.setItem(43, this.item.getItemStack());
		
		player.openInventory(inventory);
	}

	@EventHandler
	public void onInteractInventory(InventoryClickEvent event)
	{
		Player player = (Player) event.getWhoClicked();
		this.message = new Message(player);
		ItemStack itemClicked = event.getCurrentItem();

		this.message.send(Channel.PLAYER, "§7Slot: §b" + event.getSlot());
		
		Sessions playerSession = this.session.getSession(player);
		if (event.getView().getTitle().equals(playerSession.getTarget().getName()))
		{
			if (itemClicked == null || event.getClickedInventory() == null) return;
			event.setCancelled(true);

			switch (event.getSlot())
			{
					//Action
				case 23:
					new InventoryAction(this.instance).openInventory(player);
					break;
				
					//Sanction
				case 25:
					break;
					//Warn
				case 41:
					playerSession.setInstance(this.instance);
					if (playerSession.getWarn() == 3)
					{
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mute " + playerSession.getTarget().getName());
						this.message.send(Channel.BROADCAST, "§3" + playerSession.getTarget().getName() + " §7a été mute après §33 §7avertissements");
						this.message = new Message(playerSession.getTarget());
						this.message.send(Channel.PLAYER, "§7Vous avez été mute après §33 §7avertissements");
						return;
					}
					playerSession.addWarn();
					this.message.send(Channel.PLAYER, "§7Vous avez avertit §3" + playerSession.getTarget().getName());
					this.message = new Message(playerSession.getTarget());
					this.message.send(Channel.PLAYER, "§3" + player.getName() + " §7Vous a avertit");
					break;
					
					//Catégories
				case 43:
					break;
			}
		}
	}
}
