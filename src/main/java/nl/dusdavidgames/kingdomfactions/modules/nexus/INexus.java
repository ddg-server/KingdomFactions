package nl.dusdavidgames.kingdomfactions.modules.nexus;

import java.util.ArrayList;

import org.bukkit.Location;

import nl.dusdavidgames.kingdomfactions.modules.monster.IGuard;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.IInhabitable;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.NexusType;

public interface INexus {

	String getNexusId();

	Location getNexusLocation();

	int getHealth();

	void setHealth(int health);

	int getProtectedRadius();

	double getDistance(KingdomFactionsPlayer p);

	double getDistance(Location loc);

	NexusType getType();

	public ArrayList<IGuard> getGuards();
	
	IInhabitable getOwner();
	
    String toString();
}
