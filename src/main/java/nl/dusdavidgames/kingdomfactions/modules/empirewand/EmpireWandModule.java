package nl.dusdavidgames.kingdomfactions.modules.empirewand;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.command.EmpireWandCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.DataException;
import nl.dusdavidgames.kingdomfactions.modules.utils.Randomizer;

public class EmpireWandModule {
	private static @Getter @Setter EmpireWandModule instance;

	public EmpireWandModule() {
		setInstance(this);
        new EmpireWandCommand("empirewand", "kingdomfactions.command.empirewand", "Creëer een EmpireWand", "", false, false).registerCommand();
		new SpellModule();
	}

	public ItemStack getEmpireWand() {

	
		ItemStack empirewand = new ItemStack(Material.BLAZE_ROD, 1);
		ItemMeta meta = empirewand.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Empire Wand");
		ArrayList<String> lore = new ArrayList<String>();
		try {
			lore.add(ChatColor.translateAlternateColorCodes('&', new Randomizer<String>(KingdomFactionsPlugin.getInstance()
							.getDataManager().getArrayData("Empirewand.lores").getValue()).result()));
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		meta.setLore(lore);
		meta.addEnchant(Enchantment.LURE, 1, true);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		empirewand.setItemMeta(meta);
		
		return empirewand;
	}


}
