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
				Bukkit.getServer().getConsoleSender().sendMessage(prefix + message);
				break;
			case BROADCAST:
				Bukkit.broadcastMessage(prefix + message);
				break;
			case PLAYER:
				player.sendMessage(prefix + message);
				break;
		}
	}
	
	public enum Channel
	{
		CONSOLE,
		BROADCAST,
		PLAYER
	}
}
