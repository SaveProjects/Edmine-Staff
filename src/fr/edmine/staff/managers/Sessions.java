package fr.edmine.staff.managers;

import java.util.HashMap;

import org.bukkit.entity.Player;

import fr.edmine.staff.Staff;

public class Sessions
{
	private Staff instance;
	
	private HashMap<Player, Sessions> manager = new HashMap<>();
	
	private Player target;
	
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
	
	public Sanction getSanction()
	{
		return new Sanction(this.instance, this.target);
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
