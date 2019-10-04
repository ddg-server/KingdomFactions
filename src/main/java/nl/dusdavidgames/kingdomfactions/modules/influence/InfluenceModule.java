package nl.dusdavidgames.kingdomfactions.modules.influence;

import org.bukkit.Bukkit;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.influence.event.InfluenceEditEvent;
import nl.dusdavidgames.kingdomfactions.modules.influence.runnable.InfluenceRunnable;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.StatisticsProfile;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.ValueEditAction;

public class InfluenceModule {

	
	private static @Getter @Setter InfluenceModule instance; 
	public InfluenceModule() {
		setInstance(this);
		Bukkit.getScheduler().runTaskTimer(KingdomFactionsPlugin.getInstance(), new InfluenceRunnable(), 0L, 20 * 60);
        new InfluenceCommand("influence", "kingdomfactions.command.influence", "Influence gerelateerde commando's", "influence", true, true).registerCommand();
	
	}

	
    
    
	public boolean canAfford(StatisticsProfile profile, int influence) {
		if (profile.getInfluence() >= influence) {
			return true;
		}
		return false;
	}

	public void addInfluence(StatisticsProfile profile,int influence) {
         profile.setInfluence(profile.getInfluence() + influence);
		Bukkit.getPluginManager().callEvent(new InfluenceEditEvent(profile, ValueEditAction.ADD));
	}

	public void removeInfluence(StatisticsProfile profile, int influence, boolean mayBeNegative) {
		if (mayBeNegative) {
	         profile.setInfluence(profile.getInfluence() - influence);
			Bukkit.getPluginManager().callEvent(new InfluenceEditEvent(profile, ValueEditAction.REMOVE));
		} else {
			if (canAfford(profile, influence)) {
		         profile.setInfluence(profile.getInfluence() - influence);
				Bukkit.getPluginManager().callEvent(new InfluenceEditEvent(profile, ValueEditAction.REMOVE));
			}
		}
	}
}
