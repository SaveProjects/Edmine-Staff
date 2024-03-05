package fr.edmine.staff.actions;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.edmine.staff.Staff;
import fr.edmine.staff.nms.NMSPlayer;

public class FreezePlayer implements Listener
{
	private Staff instance;
	private NMSPlayer nmsPlayer;
	
	public FreezePlayer(Staff main)
	{
		this.instance = main;
	}
	
	@EventHandler
	public void onFreeze(PlayerMoveEvent event)
	{
		Player player = event.getPlayer();
		
		if (this.instance.getActions().getFreezeList().contains(player))
		{
			event.setTo(event.getFrom());
			
			this.nmsPlayer = new NMSPlayer(player);
			this.nmsPlayer.sendTitle("§cVous êtes freeze !", "§7Un modérateur vous a freeze", 0, 0, 0);
		}
	}
}
