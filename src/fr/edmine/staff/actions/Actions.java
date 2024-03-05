package fr.edmine.staff.actions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Actions
{
	private List<Player> freezeList;
	
	public Actions()
	{
		this.freezeList = new ArrayList<>();
	}
	
	public List<Player> getFreezeList()
	{
		return this.freezeList;
	}
}
