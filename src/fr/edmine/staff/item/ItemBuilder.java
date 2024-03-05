package fr.edmine.staff.item;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder
{
	private ItemStack itemStack;
	private ItemMeta itemMeta;
	
	private String displayName;
	private String[] lores;
	private int tag;
	
	public ItemBuilder(Material material)
	{
		this.itemStack = new ItemStack(material);
		this.itemMeta = this.itemStack.getItemMeta();
	}
	
	public ItemBuilder setDisplayName(String displayName)
	{
		this.displayName = displayName;
		this.itemMeta.setDisplayName(displayName);
		return this;
	}
	
	public String setDisplayName()
	{
		return this.displayName;
	}
	
	public ItemBuilder setLore(String[] lores)
	{
		this.lores = lores;
		this.itemMeta.setLore(Arrays.asList(lores));
		return this;
	}
	
	public String[] getLores()
	{
		return this.lores;
	}
	
	public ItemBuilder setTag(int tag)
	{
		this.tag = tag;
		this.itemStack.setDurability((short) tag);
		return this;
	}
	
	public int getTag()
	{
		return this.tag;
	}
	
	public ItemBuilder addEnchant(Enchantment enchant)
	{
		this.itemMeta.addEnchant(enchant, 0, false);
		return this;
	}
	
	public ItemBuilder removeEnchant(Enchantment enchant)
	{
		this.itemMeta.removeEnchant(enchant);
		return this;
	}
	
	public ItemMeta getItemMeta()
	{
		return this.itemMeta;
	}
	
	public ItemStack getItemStack()
	{
		return this.itemStack;
	}
	
	public void build()
	{
		this.itemStack.setItemMeta(this.itemMeta);
	}
}
