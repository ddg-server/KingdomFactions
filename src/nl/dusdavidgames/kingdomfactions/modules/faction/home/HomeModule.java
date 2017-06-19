package nl.dusdavidgames.kingdomfactions.modules.faction.home;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.faction.home.listeners.HomeTeleportMoveEvent;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.CapitalNexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class HomeModule {


	private static @Getter @Setter HomeModule instance;
	public HomeModule() {
		setInstance(this);
		KingdomFactionsPlugin.getInstance().registerListener(new HomeTeleportMoveEvent());
	//	scheduleHomeTeleports();
	}
	

	
	public Home createHome(KingdomFactionsPlayer p) {
		if(p.getCurrentNexus() == null) {
			return null;
		}
		if(p.getCurrentNexus() instanceof CapitalNexus) {
			return null;
		}
		
		Nexus n = (Nexus) p.getCurrentNexus();
		Home h = new Home(n, p.getFaction(), p.getLocation());
		p.getFaction().setHome(h);
		return h;
		
	}

	/**
	private void scheduleHomeTeleports() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(KingdomFactionsPlugin.getInstance(),new Runnable() {
			
			@Override
			public void run() {
				for(KingdomFactionsPlayer player : PlayerModule.getInstance().getPlayers()) {
					if(player.getAction() instanceof HomeTeleportAction) {
						HomeTeleportAction action = (HomeTeleportAction) player.getAction();
						action.lowerDelay();
					}
				}
				
			}
		}, 0L, 20L);
	}
	*/
}
