package nl.dusdavidgames.kingdomfactions.modules.faction.home;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import org.bukkit.Location;

public class Home {

	
	private @Getter Nexus nexus;
	private @Getter Faction faction;
	private @Getter Location location;
	
	
	public Home(Nexus n, Faction f, Location loc) {
		this.nexus = n;
		this.faction = f;
		this.location = loc;
	}
	
	
	public void remove() {
         MySQLModule.getInstance().insertQuery("DELETE FROM faction_home WHERE faction_id ='" + this.faction.getFactionId() + "'");
	}

}
