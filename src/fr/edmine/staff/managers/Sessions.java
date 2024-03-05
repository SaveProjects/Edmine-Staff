package fr.edmine.staff.managers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.edmine.staff.Staff;

public class Sessions
{
	private Staff instance;
	
	private HashMap<Player, Sessions> manager = new HashMap<>();
	private File dataTarget;
	private FileConfiguration configuration;
	
	private Player target;
	private boolean freeze = false;
	
	public void create(Player player)
	{
		if (!this.manager.containsKey(player)) this.manager.put(player, new Sessions());
	}
	
	public void setTarget(Player player)
	{
		this.target = player;
	}
	
	public Player getTarget()
	{
		return this.target;
	}
	
	public void addWarn()
	{
		this.dataTarget = new File(this.instance.getDataFolder(), "playersData/" + target.getUniqueId().toString() + ".yml");
		this.configuration = YamlConfiguration.loadConfiguration(this.dataTarget);
		this.configuration.set("warns", this.configuration.getInt("warns") + 1);
		try
		{
			this.configuration.save(this.dataTarget);
		}
		catch (IOException exeption)
		{
			exeption.printStackTrace();
		}
	}
	
	public int getWarn()
	{
		this.dataTarget = new File(this.instance.getDataFolder(), "playersData/" + target.getUniqueId().toString() + ".yml");
		this.configuration = YamlConfiguration.loadConfiguration(this.dataTarget);
		return this.configuration.getInt("warns");
	}
	
	public void setFreeze(boolean freeze)
	{
		this.freeze = freeze;
	}
	
	public boolean getFreeze()
	{
		return this.freeze ? true : false;
	}
	
	public void setInstance(Staff main)
	{
		this.instance = main;
	}
	
	public Sessions getSession(Player player)
	{
		return this.manager.get(player);
	}
}
