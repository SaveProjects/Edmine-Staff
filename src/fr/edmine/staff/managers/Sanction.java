package fr.edmine.staff.managers;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.edmine.staff.Staff;

public class Sanction
{
	private Staff instance;
	
	private File dataFile;
	private FileConfiguration configuration;
	
	private Player target;
	
	public Sanction(Staff main, Player target)
	{
		this.instance = main;
		this.target = target;
	}
	
	public File getDataTarget()
	{
		return this.dataFile = new File(this.instance.getDataFolder(), "playersData/" + this.target.getUniqueId().toString() + ".yml");
	}
	
	public FileConfiguration getConfigurationData()
	{
		return this.configuration = YamlConfiguration.loadConfiguration(this.dataFile);
	}

	public void saveFileData()
	{
		try
		{
			this.configuration.save(this.dataFile);
		}
		catch (IOException exeption)
		{
			exeption.printStackTrace();
		}
	}
	
	public void addWarn()
	{
		this.dataFile = new File(this.instance.getDataFolder(), "playersData/" + this.target.getUniqueId().toString() + ".yml");
		this.configuration = YamlConfiguration.loadConfiguration(this.dataFile);
		this.configuration.set("warns", this.configuration.getInt("warns") + 1);
		try
		{
			this.configuration.save(this.dataFile);
		}
		catch (IOException exeption)
		{
			exeption.printStackTrace();
		}
	}
	
	public int getWarn()
	{
		this.dataFile = new File(this.instance.getDataFolder(), "playersData/" + this.target.getUniqueId().toString() + ".yml");
		this.configuration = YamlConfiguration.loadConfiguration(this.dataFile);
		return this.configuration.getInt("warns");
	}
}
