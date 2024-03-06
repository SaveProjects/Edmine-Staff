package fr.edmine.staff.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ToggleItem
{
	
	private static boolean etat;
	private ItemStack itemStack;
	private ItemMeta itemMeta;
	
	public ToggleItem(ItemStack itemStack)
	{
		this.itemStack = itemStack;
		this.itemMeta = itemStack.getItemMeta();
	}
	
	public void addInteractInventory(InventoryClickEvent event)
	{
		if (event.getClickedInventory() != null)
		{
			if (etat == true) etat = false;
			else if (etat == false) etat = true;
		}
	}

	public void toggle(Material old_material, Material material)
	{
		List<String> lores = new ArrayList<>();
		
		if (etat == false)
		{
			etat = true;
			this.itemStack.setType(material);
			this.itemMeta.addEnchant(Enchantment.DURABILITY, 0, false);
			for (String line : this.itemStack.getItemMeta().getLore())
			{
				lores.add(line.replaceAll("§8Mode: §cDésactiver", "§8Mode: §aActiver"));
			}
		}
		else if (etat == true)
		{
			etat = false;
			this.itemStack.setType(old_material);
			this.itemMeta.removeEnchant(Enchantment.DURABILITY);
			for (String line : this.itemStack.getItemMeta().getLore())
			{
				lores.add(line.replaceAll("§8Mode: §aActiver", "§8Mode: §cDésactiver"));
			}
		}
		this.itemMeta.setLore(lores);
		
		this.itemStack.setItemMeta(this.itemMeta);
	}
	
	public void setEtat(boolean itemEtat)
	{
		etat = itemEtat;
		System.out.println("Etat: " + etat);
	}
}
