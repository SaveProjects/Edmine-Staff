package fr.edmine.staff.actions;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.edmine.staff.Staff;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class FreezePlayer implements Listener
{
	private Staff instance;
	
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
			this.sendTitle(player, "§cVous êtes freeze !", "§7Un modérateur vous a freeze", 0, 0, 0);
		}
	}
	
	public void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut)
	{
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        ChatComponentText titleJSON = new ChatComponentText(title);
        ChatComponentText subtitleJSON = new ChatComponentText(subtitle);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitleJSON);
        connection.sendPacket(titlePacket);
        connection.sendPacket(subtitlePacket);
    }
}
