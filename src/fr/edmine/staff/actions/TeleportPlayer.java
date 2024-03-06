package fr.edmine.staff.actions;

import org.bukkit.entity.Player;

public class TeleportPlayer
{
	public void onTeleport(Player player, Player target)
	{
		player.teleport(target);
	}
}
