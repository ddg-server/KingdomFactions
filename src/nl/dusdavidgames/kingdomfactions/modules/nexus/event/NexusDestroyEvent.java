package nl.dusdavidgames.kingdomfactions.modules.nexus.event;

import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NexusDestroyEvent extends Event implements Cancellable {

   private static final HandlerList handlerlist = new HandlerList();
   private Location loc;
   private KingdomFactionsPlayer p;
   private INexus n;
   
   private boolean cancel;
   
   public NexusDestroyEvent(INexus n,  KingdomFactionsPlayer p) {
	 this.loc = n.getNexusLocation();
	 this.p = p;
	 this.n = n;
   }

   public INexus getNexus() {
	   return n;
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
