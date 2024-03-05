package fr.edmine.staff.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.edmine.staff.Staff;
import fr.edmine.staff.channel.Message;
import fr.edmine.staff.channel.Message.Channel;
import fr.edmine.staff.inventorys.PlayerInventory;
import fr.edmine.staff.managers.Sessions;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class CommandCheck implements CommandExecutor
{

	private Staff instance;
	private Sessions session;
	private Message message;

	public CommandCheck(Staff main)
	{
		this.instance = main;
		this.session = main.getSessions();
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		this.message = new Message(null);

		if (!(sender instanceof Player))
		{
			this.message.send(Channel.CONSOLE, "§7Seul un joueur peut éffectuer cette commande");
			return false;
		}

		Player player = (Player) sender;
		this.message = new Message(player);

		if (command.getName().equalsIgnoreCase("check"))
		{
			if (!player.hasPermission("staff.check"))
			{
				this.message.send(Channel.PLAYER, "§7Tu n'as pas la permission d'éffectuer cette commande");
				return false;
			}

			if (args.length == 0)
			{
				this.message.send(Channel.PLAYER, "§7Commande invalide: §8/check §aplayer");
				return false;
			}

			if (args.length == 1)
			{

				Player target = Bukkit.getPlayer(args[0]);
				if (target == null)
				{
					this.message.send(Channel.PLAYER, "§c" + args[0] + " §7n'est pas en ligne");
					return false;
				}
				
				//Désactiver pour le moment
				/*
				if (target.hasPermission("staff.nocheck"))
				{
					this.message.send(Channel.PLAYER, "§7Le joueur §3" + target.getName() + " §7est un membre du staff, donc il ne peut pas être vérifier");
					return false;
				}
				 */
				
				this.session.create(player);
				this.session.getSession(player).setTarget(target);

				new PlayerInventory(this.instance).openInventory(player);
				this.message.send(Channel.PLAYER, "§7Ouverture du menu");

				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				
				
				this.message.send(Channel.CONSOLE, "§7Lecture du fichier §6'dat' §7de: §3" + target.getName());
				File playerDataFolder = new File(target.getWorld().getName() + "/playerdata/");
				File playerDat = new File(playerDataFolder, player.getUniqueId().toString() + ".dat");
				if (!playerDataFolder.exists() || !playerDat.exists())
				{
					this.message.send(Channel.PLAYER, "§cUn problème est survenu lors de la récupération des informations du joueur");
					return false;
				}
				
				try
				{
					FileInputStream inputStream = new FileInputStream(playerDat);
					NBTTagCompound nbt = NBTCompressedStreamTools.a(inputStream);
					/*
					for (String line : nbt.c())
					{
						this.message.send(Channel.CONSOLE, "§7" + line);
					}
					*/
					this.message.send(Channel.PLAYER, "§7§m---------------------");
					
					Path filePath = playerDat.toPath();
					BasicFileAttributes attributes = null;
					try
			        {
			            attributes = Files.readAttributes(filePath, BasicFileAttributes.class);
			        }
			        catch (IOException exception)
			        {
			        	
			        }
					Date creationDate = new Date(attributes.creationTime().to(TimeUnit.MILLISECONDS));
					this.message.send(Channel.PLAYER, "§bArriver le: §7" + creationDate.getDay() + "/" + (creationDate.getMonth() +1) + "/" + (creationDate.getYear() + 1900));
					
					this.message.send(Channel.PLAYER, "§bMonde: §7" + target.getWorld().getName());
					
					DecimalFormat decimalFormat = new DecimalFormat("#####.##");
					String x = decimalFormat.format(target.getLocation().getX());
					String y = decimalFormat.format(target.getLocation().getY());
					String z = decimalFormat.format(target.getLocation().getZ());
					this.message.send(Channel.PLAYER, "§bPosition: " + "§7x:" + x + " y:" + y + " z:" + z);
					
					this.message.send(Channel.PLAYER, "§7§m---------------------");
				}
				catch (Exception exeption)
				{
					exeption.printStackTrace();
				}
				
				return true;
			}
		}
		return false;
	}
}
