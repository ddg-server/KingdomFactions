package nl.dusdavidgames.kingdomfactions.modules.nexus.event;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NexusAttackEvent extends Event implements Cancellable{

   private static final HandlerList handlerlist = new HandlerList();
   private Location loc;
   private KingdomFactionsPlayer p;
   private INexus n;
   
   private boolean cancel;
   
   public NexusAttackEvent(INexus n, KingdomFactionsPlayer p) {
	 this.loc = n.getNexusLocation();
	 this.p = p;
	 this.n = n;
   }
   public INexus getNexus() {
	   return n;
   }
   public void setHealth(int health) {
	   this.n.setHealth(health);
   }
   public int getHealth() {
	   return this.n.getHealth();
   }
   public KingdomFactionsPlayer getPlayer() {
	   return p;
   }
   public Location getLocation() {
	   return loc;
   }
public HandlerList getHandlers() {
	return handlerlist;
}
public static HandlerList getHandlerList() {
	return handlerlist;
}
	
public String getNexusHealth() {
	int health = this.n.getHealth();
	if(health >= 500) {
		return ChatColor.DARK_GREEN +""+ health;
	}
	if(health >= 400) {
		return ChatColor.GREEN +""+ health;
	}
	if(health >= 300) {
		return ChatColor.YELLOW +""+ health;
	}
	if(health >= 200) {
		return ChatColor.GOLD +""+ health;
	}
	if(health >= 100) {
		return ChatColor.RED +""+ health;
	}
	if(health >= 50) {
		return ChatColor.DARK_RED +""+ health;
	}
	return ChatColor.DARK_RED +""+ health;
}
@Override
public boolean isCancelled() {
	// TODO Auto-generated method stub
	return cancel;
}
@Override
public void setCancelled(boolean cancel) {
	this.cancel = cancel;
	
}
}
