package fr.edmine.staff.channel;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Message
{
	private String prefix = "§8[§bStaff§8] §r";
	private Player player;
	
	public Message(Player player)
	{
		this.player = player;
	}
	
	public void send(Channel channelType, String message)
	{
		switch (channelType)
		{
			case CONSOLE:
				Bukkit.getServer().getConsoleSender().sendMessage(this.prefix + message);
				break;
				
			case BROADCAST:
				for (Player player : Bukkit.getOnlinePlayers())
				{
					player.sendMessage(this.prefix + message);
				}
				break;
				
			case PLAYER:
				player.sendMessage(this.prefix + message);
				break;
				
			case STAFF:
				if (this.player.hasPermission("staff.channel"))
				{
					this.player.sendMessage(this.prefix + message);
				}
				break;
		}
	}
	
	public enum Channel
	{
		CONSOLE,
		BROADCAST,
		PLAYER,
		STAFF
	}
}
