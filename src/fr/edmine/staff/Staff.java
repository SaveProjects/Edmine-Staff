package fr.edmine.staff;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.edmine.staff.actions.Actions;
import fr.edmine.staff.actions.FreezePlayer;
import fr.edmine.staff.channel.Message;
import fr.edmine.staff.channel.Message.Channel;
import fr.edmine.staff.commands.CommandCheck;
import fr.edmine.staff.inventorys.InventoryAction;
import fr.edmine.staff.inventorys.PlayerInventory;
import fr.edmine.staff.managers.Sessions;

public class Staff extends JavaPlugin
{
	private Sessions sessions;
	private Message message = new Message(null);
	private Actions actions;
	
	
	@Override
	public void onEnable()
	{
		this.sessions = new Sessions();
		this.actions = new Actions();
		
		this.onCommands();
		this.onListeners();
		
		this.message.send(Channel.CONSOLE, "§aAllumer");
	}
	
	@Override
	public void onDisable()
	{
		this.message.send(Channel.CONSOLE, "§cEteint");
	}
	
	private void onCommands()
	{
		this.getCommand("check").setExecutor(new CommandCheck(this));
	}
	
	private void onListeners()
	{
		PluginManager pluginManager = Bukkit.getPluginManager();
		
		pluginManager.registerEvents(new PlayerInventory(this), this);
		pluginManager.registerEvents(new InventoryAction(this), this);
		
		pluginManager.registerEvents(new FreezePlayer(this), this);
	}
	
	public Sessions getSessions()
	{
		return this.sessions;
	}
	
	public Actions getActions()
	{
		return this.actions;
	}
}
