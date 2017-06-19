package nl.dusdavidgames.kingdomfactions.modules.kingdom.menu;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.exception.kingdom.KingdomException;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.GlassColor;
import nl.dusdavidgames.kingdomfactions.modules.utils.Item;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.Randomizer;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.Title;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.TitleDuration;

public class KingdomMenu implements Listener {

	private static @Getter @Setter KingdomMenu instance;

	public KingdomMenu() {
		setInstance(this);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getAction() == Action.PHYSICAL)
			return;
		if (e.getPlayer().getItemInHand() == null)
			return;
		if (!e.getPlayer().getItemInHand().getType().equals(Material.COMPASS))
			return;
		if (!e.getPlayer().getItemInHand().hasItemMeta())
			return;
		if (!e.getPlayer().getItemInHand().getItemMeta().getDisplayName()
				.equalsIgnoreCase(ChatColor.RED + "Selecteer jouw kingdom"))
			return;
		setKindomMenu(PlayerModule.getInstance().getPlayer(e.getPlayer()));
		e.setCancelled(true);
	}

	public void setKindomMenu(KingdomFactionsPlayer p) {
		Inventory in = Bukkit.createInventory(null, 45, ChatColor.BLUE + "Kies jouw Kingdom!");
		for (int i = 0; i < 45; i++) {
			in.setItem(i, Item.getInstance().getItem(Material.STAINED_GLASS_PANE, " ", 1, GlassColor.WHITE));
		}
		Item.getInstance().setPane(KingdomType.EREDON, 2, in);
		Item.getInstance().setPane(KingdomType.DOK, 4, in);
		Item.getInstance().setPane(KingdomType.TILIFIA, 6, in);
		Item.getInstance().setPane(KingdomType.ADAMANTIUM, 20, in);
		Item.getInstance().setPane(KingdomType.MALZAN, 22, in);
		Item.getInstance().setPane(KingdomType.HYVAR, 24, in);
		Item.getInstance().setPane(KingdomType.GEEN, 40, in);
		p.openInventory(in);
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (!ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Kies jouw Kingdom!")) {
			return;
		}
		Player player = (Player) e.getWhoClicked();
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(player);
		e.setCancelled(true);

		if ((e.getCurrentItem() == null)) {
			player.closeInventory();
			return;
		}
		switch (e.getRawSlot()) {
		case 2:
			this.selectKingdom(p, KingdomType.EREDON);
			break;
		case 4:
			this.selectKingdom(p, KingdomType.DOK);
			break;
		case 6:
			this.selectKingdom(p, KingdomType.TILIFIA);
			break;
		case 20:
			this.selectKingdom(p, KingdomType.ADAMANTIUM);
			break;
		case 22:
		this.selectKingdom(p, KingdomType.MALZAN);
			break;
		case 24:
		this.selectKingdom(p, KingdomType.HYVAR);
			break;
		case 40:
			Kingdom k = getRandom();
			try {
				p.getMembershipProfile().setKingdom(k);
			} catch (KingdomException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			p.getPlayer().closeInventory();
			p.sendTitle(Title.TITLE, TitleDuration.SHORT, KingdomString(p.getKingdom()));
			p.teleport(p.getKingdom().getSpawn());
			p.getInventory().clear();
			p.getPlayer().getInventory().setHelmet(null);
			p.getPlayer().getInventory().setChestplate(null);
			p.getPlayer().getInventory().setLeggings(null);
			p.getPlayer().getInventory().setBoots(null);
		}
	}

	private String KingdomString(Kingdom k) {
		switch (k.getType()) {
		case ADAMANTIUM:
			return ChatColor.DARK_RED + "A" + ChatColor.DARK_GRAY + "damantium";
		case DOK:
			return ChatColor.DARK_PURPLE + "Dok";
		case EREDON:
			return ChatColor.AQUA + "Eredon";
		case GEEN:
			break;
		case HYVAR:
			return ChatColor.GOLD + "Hyvar";
		case MALZAN:
			return ChatColor.YELLOW + "Malzan";
		case TILIFIA:
			return ChatColor.DARK_GREEN + "Tilifia";
		default:
			break;

		}
		return "Er ging iets fout";
	}

	private Kingdom getRandom() {
		ArrayList<Kingdom> kingdoms = new ArrayList<Kingdom>();
		for (Kingdom k : KingdomModule.getInstance().getKingdoms()) {
			if (k.getType() == KingdomType.GEEN || k.getType() == KingdomType.ERROR)
				continue;
			kingdoms.add(k);
		}
		Randomizer<Kingdom> random = new Randomizer<Kingdom>(kingdoms);
		if (!random.hasResult()) {
			try {
				throw new KingdomException("Unexpected issue occured!");
			} catch (KingdomException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return random.result();

	}


	
	private void selectKingdom(KingdomFactionsPlayer p, KingdomType t) {
		this.selectKingdom(p, KingdomModule.getInstance().getKingdom(t));
	}
	private void selectKingdom(KingdomFactionsPlayer p, Kingdom k) {
		try {
			p.getMembershipProfile().setKingdom(k);
		} catch (KingdomException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		p.getPlayer().closeInventory();
		p.sendTitle(Title.TITLE, TitleDuration.SHORT, KingdomString(p.getKingdom()));
		p.sendMessage(Messages.getInstance().getPrefix() + "Je hebt het kingdom " + KingdomString(p.getKingdom())
				+ ChatColor.WHITE + " gekozen!");
		p.teleport(p.getKingdom().getSpawn());
		p.getInventory().clear();
		p.getPlayer().getInventory().setHelmet(null);
		p.getPlayer().getInventory().setChestplate(null);
		p.getPlayer().getInventory().setLeggings(null);
		p.getPlayer().getInventory().setBoots(null);
	}
}
