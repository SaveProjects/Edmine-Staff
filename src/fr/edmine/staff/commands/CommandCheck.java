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
				
				//Désactivation provisoir
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
				this.message.send(Channel.CONSOLE, "§7Lecture du fichier §6'dat' §7de: §3" + target.getName());
				return true;
			}
		}
		return false;
	}
}
