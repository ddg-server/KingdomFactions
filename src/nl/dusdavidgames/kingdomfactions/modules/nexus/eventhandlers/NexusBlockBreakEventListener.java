package nl.dusdavidgames.kingdomfactions.modules.nexus.eventhandlers;

import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.CapitalNexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.event.NexusAttackEvent;
import nl.dusdavidgames.kingdomfactions.modules.nexus.event.NexusDestroyEvent;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.war.WarModule;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class NexusBlockBreakEventListener implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getBlock() == null)
			return;
		
		if (!NexusModule.getInstance().isNexus(e.getBlock()))
			return;
		e.setCancelled(true);

		
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
		
		
		if(!WarModule.getInstance().isWarActive()) {
			p.sendMessage(Messages.getInstance().getPrefix() + "Je kan geen stad aanvallen buiten de oorlog!");
			return;
		}
		
		INexus nexus = NexusModule.getInstance().getNexus(e.getBlock());
		switch (nexus.getType()) {
		case CAPITAL:
			CapitalNexus c = (CapitalNexus) nexus;
			if (c.getKingdom() == p.getKingdom().getType()) {
				p.sendMessage(Messages.getInstance().getPrefix() + "Je kan je eigen hoofdstad niet aanvallen.");
			} else {
				if(c.isDestroyed()) {
					p.sendMessage(Messages.getInstance().getPrefix() + "Deze Nexus is al vernietigd!");
					return;
				}
				this.registerAttack(p, nexus);
			}
			break;
		case FACTION:
			Nexus n = (Nexus) nexus;
			if (n.hasOwner()) {
				if (p.hasFaction()) {
					if (p.getFaction() == n.getOwner()) {
						p.sendMessage(Messages.getInstance().getPrefix() + "Je mag je eigen Nexus niet aanvallen!");
					} else {
						if(n.isProtected()) {
							p.sendMessage(Messages.getInstance().getPrefix() + "Deze Nexus wordt voor deze oorlog beschermd met een magische spreuk!");
						   return;
						}
						this.registerAttack(p, nexus);
					}
				}
			} else {
				if (p.hasFaction()) {
					this.registerAttack(p, nexus);
				} else {

					p.sendMessage(Messages.getInstance().getPrefix()
							+ "Deze nexus heeft geen eigenaar! Je moet een Faction hebben om neutrale Nexuses aan te vallen!");
				}
			}
			break;

		}

	}

	private void registerAttack(KingdomFactionsPlayer player, INexus nexus) {
		if(nexus.getHealth() <= 1) {
			NexusDestroyEvent e = new NexusDestroyEvent(nexus,  player);
			Bukkit.getPluginManager().callEvent(e);
			if(e.isCancelled()) {
				return;
			}
		    switch(nexus.getType()) {
			case CAPITAL:
				CapitalNexus ne = (CapitalNexus) nexus;
				ne.setDestroyed(true);
				break;
			case FACTION:
				Nexus n = (Nexus) nexus;
				n.setOwner(player.getFaction());
				player.updateTerritory();
				
				break;
		    }
		}else{
			NexusAttackEvent e = new NexusAttackEvent(nexus, player);
			Bukkit.getPluginManager().callEvent(e);
			if (!e.isCancelled()) {
				nexus.setHealth((nexus.getHealth() - 1));

		}
	}
}
}
