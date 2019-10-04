package nl.dusdavidgames.kingdomfactions.modules.nexus.eventhandlers;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionMember;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.CapitalNexus;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.guardian.CapitalGuard;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.event.NexusAttackEvent;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.particles.ParticleEffect;

public class NexusAttackEventHandler implements Listener {

	@EventHandler
	public void onAttack(NexusAttackEvent e) {
		if (e.isCancelled())
			return;
		switch (e.getNexus().getType()) {
		case CAPITAL:
            e.getPlayer().addCoins(5);
			CapitalNexus c = (CapitalNexus) e.getNexus();
			if (c.getHealth() == 800) {
				for (CapitalGuard g : c.spawnGuards()) {
					g.setTarget(e.getPlayer());
				}
			}
			e.getPlayer().sendActionbar(ChatColor.GREEN + "Je valt een HoofdStad aan! Levens: " + e.getHealth());
			Kingdom k = KingdomModule.getInstance().getKingdom(c.getKingdom());
			for (KingdomFactionsPlayer p : k.getOnlineMembers()) {
				p.sendActionbar(ChatColor.GREEN + "Let op! De Hoofdstad wordt aangevallen! Levens: " + e.getHealth());
			}

			if (((e.getNexus().getHealth() - 1) % 200) == 0 || c.getHealth() == 800) {// edit
																						// Wouter
																						// bliksem
																						// naar
																						// bij
																						// elke
																						// 200
																						// i.p.v.
																						// alleen
																						// bij
																						// 300
				Utils.getInstance().strikeLighting(e.getNexus().getNexusLocation(), false);
			}

			Location spawnParticle = Utils.getInstance().getNewLocation(e.getLocation());
			spawnParticle.setY(spawnParticle.getY() + 1);
			Utils.getInstance().playParticle(spawnParticle, ParticleEffect.WITCH_MAGIC);
			break;
		case FACTION:
			Nexus n = (Nexus) e.getNexus();
			Faction f = n.getOwner();
			e.getPlayer().addCoins(2);
			KingdomFactionsPlayer g = e.getPlayer();
			
			
			if (((e.getNexus().getHealth() - 1) % 200) == 0 || n.getHealth() == 600) {// edit
				// Wouter
				// bliksem
				// naar
				// bij
				// elke
				// 200
				// i.p.v.
				// alleen
				// bij
				// 300
Utils.getInstance().strikeLighting(e.getNexus().getNexusLocation(), false);
}

			
			
			
			
			
			
			
			g.sendActionbar(ChatColor.GREEN + "Jij valt een Faction-Nexus aan! Levens: " + n.getHealth());
			if (f == null) {
				return;
			}
			for (FactionMember player : f.getOnlineMembers()) {
				KingdomFactionsPlayer pl = (KingdomFactionsPlayer) player.toPlayer();
				pl.sendActionbar(ChatColor.GREEN + "Jouw Faction-Nexus wordt aangevallen! Levens: " + n.getHealth());
			}
			break;

		}

	}
}
