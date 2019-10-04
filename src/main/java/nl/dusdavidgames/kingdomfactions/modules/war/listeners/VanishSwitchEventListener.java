package nl.dusdavidgames.kingdomfactions.modules.war.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.vanish.event.VanishStatusChangeEvent;

import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.war.War;
import nl.dusdavidgames.kingdomfactions.modules.war.WarModule;

public class VanishSwitchEventListener implements Listener{
	
	
	@EventHandler
	public void onVanishSwitch(VanishStatusChangeEvent e) {
		if(!WarModule.getInstance().isWarActive()) return;
	if(e.isVanishing()) {	
		War war = WarModule.getInstance().getWar();
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
		switch (p.getKingdom().getType()) {
		case ADAMANTIUM:
			war.getAdamantiumSoldiers().remove(p);
			break;
		case DOK:
			war.getDokSoldiers().remove(p);
			break;
		case EREDON:
			war.getEredonSoldiers().remove(p);
			break;
		case GEEN:
			break;
		case HYVAR:
			war.getHyvarSoldiers().remove(p);
			break;
		case MALZAN:
			war.getMalzanSoldiers().remove(p);
			break;
		case TILIFIA:
			war.getTilfiaSoldiers().remove(p);
			break;
		default:
			break;

		}
		war.getTotalSoldiers().remove(p);
	} else {
		War war = WarModule.getInstance().getWar();
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
		switch (p.getKingdom().getType()) {
		case ADAMANTIUM:
			war.getAdamantiumSoldiers().add(p);
			break;
		case DOK:
			war.getDokSoldiers().add(p);
			break;
		case EREDON:
			war.getEredonSoldiers().add(p);
			break;
		case GEEN:
			break;
		case HYVAR:
			war.getHyvarSoldiers().add(p);
			break;
		case MALZAN:
			war.getMalzanSoldiers().add(p);
			break;
		case TILIFIA:
			war.getTilfiaSoldiers().add(p);
			break;
		default:
			break;

		}
		war.getTotalSoldiers().add(p);
	}
	}

}
