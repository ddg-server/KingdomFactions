package nl.dusdavidgames.kingdomfactions.modules.nexus.event;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class NexusCreateEvent extends Event{

   private static final HandlerList handlerlist = new HandlerList();
   private Location loc;
   private Faction owner;
   private KingdomFactionsPlayer p;
   private Nexus n;
   public NexusCreateEvent(Nexus n, Location loc, Faction owner, KingdomFactionsPlayer p) {
	 this.loc = loc;
	 this.owner = owner;
	 this.p = p;
	 this.n = n;
   }
   public Nexus getNexus() {
	   return n;
   }
   public KingdomFactionsPlayer getPlayer() {
	   return p;
   }
   public Faction getOwner() {
	   return owner;
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
	

}
