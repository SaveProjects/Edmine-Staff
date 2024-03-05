package fr.edmine.staff.inventorys;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

	@SuppressWarnings("deprecation")
	public void openInventory(Player player)
	{
		Sessions playerSession = this.session.getSession(player);
		Inventory inventory = Bukkit.createInventory(null, 54, playerSession.getTarget().getName());
		
		this.message = new Message(player);
		
		File playerData = new File(playerSession.getTarget().getWorld().getName() + "/playerdata/" + playerSession.getTarget().getUniqueId().toString() + ".dat");
		if (!playerData.exists())
		{
			this.message.send(Channel.PLAYER, "§cUn problème est survenu lors de la récupération des informations du joueur");
		}
		Path filePath = playerData.toPath();
		BasicFileAttributes attributes = null;
		try
        {
            attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
        }
        catch (IOException exception)
        {
        	
        }
		Date creationDate = new Date(attributes.creationTime().to(TimeUnit.MILLISECONDS));
		
		DecimalFormat decimalFormat = new DecimalFormat("#####.##");
		String x = decimalFormat.format(playerSession.getTarget().getLocation().getX());
		String y = decimalFormat.format(playerSession.getTarget().getLocation().getY());
		String z = decimalFormat.format(playerSession.getTarget().getLocation().getZ());
		
		this.item = new ItemBuilder(Material.BEDROCK);
		this.item.setDisplayName("§6Informations")
		.setLore(new String[] {
				"", 
				"§7§nInformation du joueur",
				"",
				"§b1er connexion: §7" + creationDate.getDay() + "/" + (creationDate.getMonth() +1) + "/" + (creationDate.getYear() + 1900), 
				"§bMonde: §7" + playerSession.getTarget().getWorld().getName(), 
				"§bPosition: " + "§7x:" + x + " y:" + y + " z:" + z, 
				""
		}).build();
		inventory.setItem(21, this.item.getItemStack());
		
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
				"", 
				"§7Avertit le joueur",
				""
		}).build();
		inventory.setItem(41, this.item.getItemStack());
		
		this.item = new ItemBuilder(Material.BOOK_AND_QUILL);
		this.item.setDisplayName("§4Catégories")
		.setLore(new String[] {"", 
				"§7§nDescription:", 
				"", 
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
		this.message.send(Channel.PLAYER, "§7Slot: §b" + event.getSlot());
		
		Sessions playerSession = this.session.getSession(player);
		if (event.getView().getTitle().equals(playerSession.getTarget().getName()))
		{
			ItemStack itemClicked = event.getCurrentItem();
			
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
