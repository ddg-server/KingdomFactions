package nl.dusdavidgames.kingdomfactions.modules.nexus.eventhandlers;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.CapitalNexus;
import nl.dusdavidgames.kingdomfactions.modules.monster.IGuard;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.event.NexusDestroyEvent;
import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.detection.TerritoryUpdateEvent;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import nl.dusdavidgames.kingdomfactions.modules.utils.particles.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class NexusDestoryEventHandler implements Listener{

	
	@EventHandler
	public void onDestroy(NexusDestroyEvent e) {
		if(e.isCancelled()) return;
		switch(e.getNexus().getType()) {
		case CAPITAL:
			CapitalNexus c = (CapitalNexus) e.getNexus();
			Logger.INFO.log("Capital nexus of " + c.getKingdom() + " is destroyed!");
			
			if(!c.getGuards().isEmpty()){
				ArrayList<IGuard> toRemove = new ArrayList<>();
				
				for(IGuard g : c.getGuards()) {
					toRemove.add(g);
				}
				
				for(IGuard guard : toRemove){
					Utils.getInstance().playParticle(guard.getLocation(), ParticleEffect.WITCH_MAGIC);
					guard.kill();
				}
				toRemove.clear();
			}
			
		Bukkit.broadcastMessage(Messages.getInstance().getPrefix() + "De Nexus van "+c.getKingdom().getPrefix() + ChatColor.WHITE +"is vernietigd door " + e.getPlayer().getKingdom().getType().getPrefix() + ChatColor.GRAY + e.getPlayer().getName());
			Utils.getInstance().strikeLighting(c.getLocation(), false);
			for(Location l : Utils.getInstance().drawCircle(c.getLocation(), 5, 2, true, false, 1)) {
				Utils.getInstance().playParticle(l, ParticleEffect.MAGIC_CRIT);
			}
			break;
		case FACTION:
			Nexus n = (Nexus) e.getNexus();
			Utils.getInstance().strikeLighting(n.getNexusLocation(), false);
			for(Location l : Utils.getInstance().drawCircle(n.getNexusLocation(), 5, 2, true, false, 1)) {
				Utils.getInstance().playParticle(l, ParticleEffect.MAGIC_CRIT);
			}
			e.getPlayer().updateTerritory();
			
			KingdomFactionsPlayer p = e.getPlayer();
			if(p.hasFaction()) {
			  if(n.hasOwner()) {
				  p.getFaction().broadcast(Messages.getInstance().getPrefix(), "De Nexus van " + n.getOwner().getName() + " is overgenomen door " + p.getName());
			  } else {
				  p.getFaction().broadcast(Messages.getInstance().getPrefix(), "Een neutrale Nexus is overgenomen door " + p.getName());
					   
			  }
			}
			   Bukkit.getPluginManager().callEvent(new TerritoryUpdateEvent(p));
			break;
		
		}
	}
}
