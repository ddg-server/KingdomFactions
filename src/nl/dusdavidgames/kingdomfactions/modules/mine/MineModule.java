package nl.dusdavidgames.kingdomfactions.modules.mine;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.mine.command.MineCommand;
import nl.dusdavidgames.kingdomfactions.modules.mine.listeners.PlayerTeleportEventListener;
import nl.dusdavidgames.kingdomfactions.modules.mine.listeners.PortalProtection;
import nl.dusdavidgames.kingdomfactions.modules.mine.listeners.PortalTravelEventListener;
import nl.dusdavidgames.kingdomfactions.modules.mine.listeners.WorldProtection;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

public class MineModule {

	
	
	private static @Getter @Setter MineModule instance; 
	public MineModule() {
		setInstance(this);
		new MineCommand("kingdomfactions.command.mine", "zet de mijn spawn", "[kingdom]", false, false).registerCommand();;
		KingdomFactionsPlugin.getInstance().registerListener(new PortalTravelEventListener());
		KingdomFactionsPlugin.getInstance().registerListener(new PortalProtection());
		KingdomFactionsPlugin.getInstance().registerListener(new WorldProtection());
		KingdomFactionsPlugin.getInstance().registerListener(new PlayerTeleportEventListener());
	}
	
	
	
	
	public boolean isMinePortal(Location location) {
		return(location.getBlock().getType() == Material.PORTAL);
	}
	
	public boolean containsMinePortal(List<Location> locations) {
	    Location loc = null;
	    
	    for(Location l : locations) {
	    	if(isMinePortal(l)) {
	    		loc = l;
	    	}
	    }
	    return loc != null;
	}
	public void teleportToMineWorld(KingdomFactionsPlayer player) {
		
	}
}
