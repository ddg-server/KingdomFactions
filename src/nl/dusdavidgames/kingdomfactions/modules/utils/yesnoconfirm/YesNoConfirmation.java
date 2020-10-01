package nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Item;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class YesNoConfirmation {

	
	private @Getter KingdomFactionsPlayer player;
	private @Getter String title;
	private @Getter ArrayList<String> yesLore;
	private @Getter ArrayList<String> noLore;
	private YesNoListener listener;
	
	
	public YesNoConfirmation(KingdomFactionsPlayer player, String title, ArrayList<String> yesLore, ArrayList<String> noLore, YesNoListener listener) {
		this.player = player;
		this.title = title;
		this.yesLore = yesLore;
		this.noLore = noLore;
		this.listener = listener;
		this.player.setYesNoConfirmation(this);
		this.player.openInventory(this.getMenu());
	}
	
	
	
	public YesNoConfirmation(KingdomFactionsPlayer player, String title, String yesLore, String noLore, YesNoListener listener) {
	    this(player, title, Utils.getInstance().getLore(yesLore), Utils.getInstance().getLore(noLore), listener);
	    
	}
	
	
	
	
	
	public void callYes() {
		remove();
		listener.onAgree(player);
	
	}
	public void callNo() {
		remove();
		listener.onDeny(player);
	}
	
	public void callClose() {
		remove();
		listener.onClose(player);
	}
	
	public void remove() {
		this.player.setYesNoConfirmation(null);
        	this.player.getPlayer().closeInventory();
	}
	
	public Inventory getMenu() {
		Inventory in = Bukkit.createInventory(null, 27, ChatColor.RED + "Weet je het zeker?");
		for (int i = 0; i < 27; i++) {
			in.setItem(i, Item.getInstance().getItem(Material.STAINED_GLASS_PANE, " ", 1, 0));
		}
		in.setItem(4, Item.getInstance().getItem(Material.NETHER_STAR, title, 1));
        	in.setItem(11, Item.getInstance().getItem(Material.EMERALD_BLOCK, ChatColor.GREEN + "Ja.", 1, yesLore));
       	 	in.setItem(15, Item.getInstance().getItem(Material.REDSTONE_BLOCK, ChatColor.RED + "Nee.", 1, noLore));
        	return in;
	}
}
