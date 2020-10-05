package nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class YesNoConfirmationModule implements Listener{

	
	public YesNoConfirmationModule() {
		setInstance(this);
	}
	
   private static @Getter @Setter YesNoConfirmationModule instance;
   
   
   @EventHandler
   public void onClick(InventoryClickEvent e) {
	  
	   if(e.getCurrentItem() == null) {
		   return;
	   }
  
   KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getWhoClicked());
	   if(e.getInventory().getName().equalsIgnoreCase(ChatColor.RED + "Weet je het zeker?")) {
		   e.setCancelled(true);
		   if(!p.hasYesNoConfirmation()) 
			   p.getPlayer().closeInventory();

	   }
	   if(p.hasYesNoConfirmation()) {
		   e.setCancelled(true);
          switch(e.getCurrentItem().getType()) {
          case EMERALD_BLOCK:
         p.getYesNoConfirmation().callYes();
          break;
          case REDSTONE_BLOCK:
         p.getYesNoConfirmation().callNo();
          break;
          default:
        	  return;
          }
          
          p.getPlayer().closeInventory();
	   }
   }
   
   @EventHandler
   public void onClose(InventoryCloseEvent e) {
	   KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(e.getPlayer());
	   if(player == null) return;
	   if(player.hasYesNoConfirmation()) {
		   player.getYesNoConfirmation().callClose();
	   }
   }
}
