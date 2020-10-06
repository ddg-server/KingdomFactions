package nl.dusdavidgames.kingdomfactions.modules.nexus.eventhandlers;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.monster.IGuard;
import nl.dusdavidgames.kingdomfactions.modules.monster.MonsterModule;
import nl.dusdavidgames.kingdomfactions.modules.utils.Item;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GuardManager implements Listener{

	
	
	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		if(MonsterModule.getInstance().getGuard(e.getEntity()) != null) {
			IGuard guard = MonsterModule.getInstance().getGuard(e.getEntity());
			e.getDrops().clear();
			e.setDroppedExp(0);
			e.getDrops().add(getGuardDrops());
			guard.remove();
		}
	}
	
	
	
	
	private ItemStack getGuardDrops() {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		HashMap<Enchantment, Integer> enchant = new HashMap<Enchantment, Integer>();
		enchant.put(Enchantment.DAMAGE_ALL, 3);
		list.add(Item.getInstance().getItem(Material.DIAMOND_SWORD, ChatColor.RED + "Wachter's Zwaard", 1, enchant , true));
		enchant.clear();
		enchant.put(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
		list.add(Item.getInstance().getItem(Material.DIAMOND_CHESTPLATE,  ChatColor.AQUA + "Wachter's Kuras", 1, enchant, true));
		enchant.clear();
		enchant.put(Enchantment.DEPTH_STRIDER, 3);
		list.add(Item.getInstance().getItem(Material.DIAMOND_BOOTS, ChatColor.GREEN + "Wachter's Schoenen", 1, enchant, true));
		list.add(Item.getInstance().getItem(Material.ARROW, "Pijlen", 32));
		list.add(new ItemStack(Material.POTATO, 1));
		enchant.put(Enchantment.DAMAGE_ALL, 4);
		enchant.put(Enchantment.FIRE_ASPECT, 2);
		list.add(Item.getInstance().getItem(Material.DIAMOND_SWORD, ChatColor.RED + "Wachter's zwaard", 1, enchant, true));
		enchant.clear();
		enchant.put(Enchantment.ARROW_FIRE, 1);
		enchant.put(Enchantment.ARROW_DAMAGE, 4);
		enchant.put(Enchantment.ARROW_INFINITE, 1);
		list.add(Item.getInstance().getItem(Material.BOW, ChatColor.DARK_RED + "Wachter's Boog", 1, enchant, true));
		
		list.add(Item.getInstance().getItem(Material.ARROW, "Pijlen", 32));
		list.add(Item.getInstance().getItem(Material.ARROW, "Pijlen", 32));
		list.add(Item.getInstance().getItem(Material.ARROW, "Pijlen", 32));
		list.add(Item.getInstance().getItem(Material.ARROW, "Pijlen", 32));
		list.add(new ItemStack(Material.DIAMOND_SWORD, 1));
		list.add(new ItemStack(Material.BOW, 1));
		list.add(new ItemStack(Material.DIAMOND_CHESTPLATE, 1));
		list.add(new ItemStack(Material.DIAMOND_BOOTS, 1));
		list.add(new ItemStack(Material.DIAMOND_HELMET, 1));
		list.add(new ItemStack(Material.DIAMOND_LEGGINGS, 1));
		list.add(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
		list.add(new ItemStack(Material.LEATHER_BOOTS, 1));
		list.add(new ItemStack(Material.LEATHER_HELMET, 1));
		list.add(new ItemStack(Material.LEATHER_LEGGINGS, 1));
		list.add(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
		list.add(new ItemStack(Material.LEATHER_BOOTS, 1));
		list.add(new ItemStack(Material.LEATHER_HELMET, 1));
		list.add(new ItemStack(Material.LEATHER_LEGGINGS, 1));
		
		return list.get(new Random().nextInt(list.size()));
	}
}
