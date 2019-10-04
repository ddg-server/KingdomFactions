package nl.dusdavidgames.kingdomfactions.modules.wreckingball;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;

public class WreckingBallModule {

	
	
	public static @Getter @Setter WreckingBallModule instance;
	
	public WreckingBallModule() {
		setInstance(this);
		KingdomFactionsPlugin.getInstance().registerListener(new WreckingBallListeners());
		new WreckingBallCommand("wreckingball", "kingdomfactions.command.wreckingball", "Get a wreckingball", "<>", false, false).registerCommand();
	}
	
	
 	final static String  WRECKINGBALL_NAME = ChatColor.RED + "Wrecking Ball";
	
	public ItemStack getWreckingBall() {
		ItemStack i = new ItemStack(Material.FIREBALL);
         	ItemMeta m = i.getItemMeta();
         	m.setDisplayName(WRECKINGBALL_NAME);
         	m.setLore(Utils.getInstance().getLore(ChatColor.RED + "Misbruik wordt bestraft! \n Absoluut NIET delen met spelers."));
         	m.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
         	m.addItemFlags(ItemFlag.HIDE_ENCHANTS);
         	i.setItemMeta(m);
         	return i;
	}
}
