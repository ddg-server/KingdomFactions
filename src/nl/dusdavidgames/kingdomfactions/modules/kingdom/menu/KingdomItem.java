package nl.dusdavidgames.kingdomfactions.modules.kingdom.menu;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.utils.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class KingdomItem {

	private @Getter KingdomType type;
	private @Getter ItemStack item;
	private @Getter int slot;

	public KingdomItem(KingdomType type, int slot) {
	    this.item = getGlassColor(type);
		this.type = type;
		this.slot = slot;

	}

	private ItemStack getGlassColor(KingdomType type) {
		switch (type) {
		case ADAMANTIUM:
			return Item.getInstance().getItem(Material.STAINED_GLASS_PANE,
					ChatColor.DARK_RED + "A" + ChatColor.DARK_GRAY + "damantium", 1, 15);

		case DOK:
			return Item.getInstance().getItem(Material.STAINED_GLASS_PANE, ChatColor.DARK_PURPLE + "Dok", 1, 10);

		case EREDON:
			return Item.getInstance().getItem(Material.STAINED_GLASS_PANE, ChatColor.AQUA + "Eredon", 1, 3);

		case GEEN:

		case HYVAR:
			return Item.getInstance().getItem(Material.STAINED_GLASS_PANE, ChatColor.GOLD + "Hyvar", 1, 1);

		case MALZAN:
			return Item.getInstance().getItem(Material.STAINED_GLASS_PANE, ChatColor.YELLOW + "Malzan", 1, 4);

		case TILIFIA:
			return Item.getInstance().getItem(Material.STAINED_GLASS_PANE, ChatColor.DARK_GREEN + "Tilifia", 1, 13);

		default:
			break;

		}
		return Item.getInstance().getItem(Material.STAINED_GLASS_PANE, ChatColor.DARK_GREEN + "Tilifia", 1, 0);
	}

}
