package nl.dusdavidgames.kingdomfactions.modules.nexus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.data.DataManager;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.NexusDatabase;
import nl.dusdavidgames.kingdomfactions.modules.exception.DataException;
import nl.dusdavidgames.kingdomfactions.modules.exception.nexus.NexusTypeException;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.CapitalNexus;
import nl.dusdavidgames.kingdomfactions.modules.monster.MonsterModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.BuildModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings.Building;
import nl.dusdavidgames.kingdomfactions.modules.nexus.command.NexusCommand;
import nl.dusdavidgames.kingdomfactions.modules.nexus.eventhandlers.NexusAttackEventHandler;
import nl.dusdavidgames.kingdomfactions.modules.nexus.eventhandlers.NexusBlockBreakEventListener;
import nl.dusdavidgames.kingdomfactions.modules.nexus.eventhandlers.NexusDestoryEventHandler;
import nl.dusdavidgames.kingdomfactions.modules.nexus.eventhandlers.NexusProtectionListener;
import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.ProtectionModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.runnables.CoinsRunnable;
import nl.dusdavidgames.kingdomfactions.modules.nexus.runnables.Regeneration;
import nl.dusdavidgames.kingdomfactions.modules.nexus.transfer.TransferManager;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;

public class NexusModule {

	private static @Getter @Setter NexusModule instance;

	public @Getter ArrayList<INexus> nexuses = new ArrayList<INexus>();
//	public @Getter HashMap<KingdomFactionsPlayer, Faction> updateOwner = new HashMap<KingdomFactionsPlayer, Faction>();
//	public @Getter HashMap<KingdomFactionsPlayer, Integer> editHealth = new HashMap<KingdomFactionsPlayer, Integer>();
//	public @Getter ArrayList<KingdomFactionsPlayer> nexusInfo = new ArrayList<KingdomFactionsPlayer>();

	public NexusModule() {
		setInstance(this);

		new MonsterModule();
		new ProtectionModule();
		loadNexuses();
		
		for(Kingdom k : KingdomModule.getInstance().getKingdoms()) {
			k.initNexus();
		}
 		Bukkit.getScheduler().scheduleSyncRepeatingTask(KingdomFactionsPlugin.getInstance(), new Regeneration(), 0,
				100);
		new NexusCommand("nexus", "kingdomfactions.command.nexus", "Main Nexus Commando", "", true, false).registerCommand();
		KingdomFactionsPlugin.getInstance().registerListener(new NexusAttackEventHandler());
		KingdomFactionsPlugin.getInstance().registerListener(new NexusDestoryEventHandler());
		KingdomFactionsPlugin.getInstance().registerListener(new NexusBlockBreakEventListener());
		KingdomFactionsPlugin.getInstance().registerListener(new NexusProtectionListener());
        new CoinsRunnable();
		this.dataManager = KingdomFactionsPlugin.getInstance().getDataManager();
		this.transferManager = new TransferManager();
		new BuildModule();
	}

	public DataManager dataManager;
    public TransferManager transferManager;    
	public void loadNexuses() {
		for (String s : NexusDatabase.getInstance().getNexuses()) {
			Nexus n = NexusDatabase.getInstance().loadNexus(s);
			nexuses.add(n);

		}
	
	}
	
	public void addCapitalNexus(CapitalNexus capitalNexus){
		nexuses.add(capitalNexus);
	}

	public INexus getINexus(String id) {
		if(id.equalsIgnoreCase("~GEEN~")) {
			return null;
		}
		for (INexus n : nexuses) {
			if (n.getNexusId().equalsIgnoreCase(id)) {
				return n;
			}
		}
		INexus n = null;
		if (NexusDatabase.getInstance().check(id)) {
			n = NexusDatabase.getInstance().loadNexus(id);
			nexuses.add(n);
		}

		return n;
	}

	public Nexus getNexus(INexus nexus) throws NexusTypeException {
		if (nexus instanceof Nexus) {
			return (Nexus) nexus;
		}
		throw new NexusTypeException("Invalid Nexus Type!");
	}

	public Nexus getNexus(String id) {
		try {
			return getNexus(getINexus(id));
		} catch (NexusTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public CapitalNexus getCapitalNexus(INexus nexus) throws NexusTypeException {
		if (nexus instanceof CapitalNexus) {
			return (CapitalNexus) nexus;
		}
		throw new NexusTypeException("Invalid Nexus Type!");
	}

	public CapitalNexus getCapitalNexus(String id) {
		try {
			return getCapitalNexus(getINexus(id));
		} catch (NexusTypeException e) {
			e.printStackTrace();
			return null;
		}
	}

	public INexus getNexus(Location loc) {
		for (INexus n : nexuses) {			
			if (n.getNexusLocation().getBlockX() == loc.getBlockX()
					&& n.getNexusLocation().getBlockY() == loc.getBlockY()
					&& n.getNexusLocation().getBlockZ() == loc.getBlockZ()) {

				return n;
			}
		}
		return null;
	}

	public ArrayList<Nexus> getNexuses(Faction f) {
		ArrayList<Nexus> temp = new ArrayList<Nexus>();
		for (INexus ne : nexuses) {
			if (ne instanceof Nexus) {
				Nexus n = (Nexus) ne;
				if (n.getOwnerId().equals(f.getFactionId())) {
					temp.add(n);
				}
			}
		}
		return temp;
	}
	
	public INexus getNexus(Block b) {
		return getNexus(b.getLocation());
	}

	public boolean isNexus(Location loc) {
		if (loc.getBlock().getType().equals(Material.COAL_BLOCK)) {
			if (getNexus(loc) != null) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	public boolean isNexus(Block block) {

		return isNexus(block.getLocation());
	}
	
	public boolean containsNexus(List<Block> blocks) {
		Block bl = null;
		for(Block b : blocks) {
			if(isNexus(b)) {
				bl = b;
			}
		}
		return bl != null;
	}
	public boolean containsNexusLocation(List<Location> locations) {
		Location loc = null;
		for(Location l : locations) {
			if(isNexus(l)) {
				loc = l;
			}
		}
		return loc != null;
		
	}
	public boolean canCreateNexus(Location loc, KingdomFactionsPlayer p) {
		try {
			int kingdomTotal = dataManager.getInteger("kingdom.total.protected_region");
			int nexusProtection = dataManager.getInteger("nexus.protected_region");

		
			int maxOut = kingdomTotal - nexusProtection;

			KingdomType territory = ProtectionModule.getInstance().getKingdomTerritory(loc);
			if (territory == KingdomType.GEEN || territory == KingdomType.ERROR) {

				return false;
			}
			if (KingdomModule.getInstance().getKingdom(territory).getNexus().getNexusLocation().distance(loc) >= maxOut) {

				return false;
			}
		
			if (territory != p.getKingdom().getType()) {

				return false;
			}
			
			boolean isNexusNearby = false;
			
			INexus n = p.getClosestNexus();
			int minDis = n.getProtectedRadius() +  nexusProtection;
			if(n.getDistance(loc) < minDis) {
				isNexusNearby = true;
			}
			
			if(isNexusNearby){
				return false;
			}else{ 
				return true;
			}
		} catch (DataException e) {

		}
		return false;
	}

	
	public boolean canCreateBuilding(Location location, KingdomFactionsPlayer player) {
		INexus n = player.getClosestNexus();
		if(n instanceof Nexus) {
			int dis = n.getProtectedRadius();
			//Location loc = Utils.getInstance().getNewLocation(n.getNexusLocation());
		    int maxDis = (dis - 30);
		    if(n.getDistance(location) >= maxDis) {
	
		    	return false;
		    }
		    if(n.getDistance(location) < 30) {
		    	return false;
		    }
		    boolean buildingNearby = false;
		    
		    for(Building b : ((Nexus) n).getBuildings()) {
		    	if(!(b.getDistance(location) > 20)) {
		   
		    		buildingNearby = true;
		    	} 
		    	
		    }
		    return !buildingNearby;
		} else {
			return false;
		}
	}
	public INexus getClosestNexus(KingdomFactionsPlayer player) {
		HashMap<INexus, Double> distance = new HashMap<INexus, Double>();
		for (INexus n : NexusModule.getInstance().getNexuses()) {
			Location nexusloc = Utils.getInstance().getNewLocation(getLoc(n));
			Location playerloc = Utils.getInstance().getNewLocation(player.getLocation());
			nexusloc.setY(0);
			playerloc.setY(0);
			distance.put(n, nexusloc.distance(playerloc));
		}
		double minValueInMap = (Collections.min(distance.values()));
		if (distance.isEmpty()) {
			return null;
		}
		for (Entry<INexus, Double> entry : distance.entrySet()) {
			if (entry.getValue() == minValueInMap) {
				entry.getKey(); // nexus
				entry.getValue();// value
				return entry.getKey();

			}
		}
		return null;
	}
	
	
	private Location getLoc(INexus n ) {
		if(n instanceof Nexus) {
			return ((Nexus) n).getPasteLocation();
		} else {
			return n.getNexusLocation();
		}
	}
}
