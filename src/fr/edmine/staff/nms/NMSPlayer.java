package fr.edmine.staff.nms;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class NMSPlayer
{
	private Player player;
	
	public NMSPlayer(Player player)
	{
		this.player = player;
	}
	
	public void sendTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut)
	{
        CraftPlayer craftplayer = (CraftPlayer) this.player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        ChatComponentText titleJSON = new ChatComponentText(title);
        ChatComponentText subtitleJSON = new ChatComponentText(subtitle);
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitleJSON);
        connection.sendPacket(titlePacket);
        connection.sendPacket(subtitlePacket);
    }
}
