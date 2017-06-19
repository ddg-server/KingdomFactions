package nl.dusdavidgames.kingdomfactions.modules.war;

import org.bukkit.Bukkit;

import lombok.Data;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerList;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.war.event.WarStartEvent;
import nl.dusdavidgames.kingdomfactions.modules.war.event.WarStopEvent;

public @Data class War {

	private WarState warState = WarState.NOWAR;
	private PlayerList totalSoldiers = new PlayerList();
	private PlayerList malzanSoldiers = new PlayerList();
	private PlayerList eredonSoldiers = new PlayerList();
	private PlayerList adamantiumSoldiers = new PlayerList();
	private PlayerList hyvarSoldiers = new PlayerList();
	private PlayerList tilfiaSoldiers = new PlayerList();
	private PlayerList dokSoldiers = new PlayerList();
	
	private long time;
	private long timeInMilliSeconds;

	public War(long time) {
		this.time = time;
		
		long currentTime = System.currentTimeMillis();
		long timeInMilliseconds = ((time * 60) * 1000);
		this.timeInMilliSeconds = (currentTime + timeInMilliseconds);
	}
	
	public String getRemainingTime(){
		String time = "";
		long timediff = timeInMilliSeconds - System.currentTimeMillis();
		long timeInSeconds = timediff / 1000;
		long minutes = ((timeInSeconds / 60) % 60);
		long hours = ((timeInSeconds / 60 / 60) % 24);
		
		if(hours < 10)
			time += "0";
		
		time += hours;
		
		time += ":";
		
		if(minutes < 10)
			time += "0";
		
		time += minutes;
		
		return time;
	}

	public void start() {
		for (KingdomFactionsPlayer p : PlayerModule.getInstance().getPlayers()) {
			switch (p.getKingdom().getType()) {
			case ADAMANTIUM:
				getAdamantiumSoldiers().add(p);
				break;
			case DOK:
				getDokSoldiers().add(p);
				break;
			case EREDON:
				getEredonSoldiers().add(p);
				break;
			case GEEN:
				break;
			case HYVAR:
				getHyvarSoldiers().add(p);
				break;
			case MALZAN:
				getMalzanSoldiers().add(p);
				break;
			case TILIFIA:
				getTilfiaSoldiers().add(p);
				break;
			default:
				break;

			}
			getTotalSoldiers().add(p);
			p.sendTitle(ChatColor.DARK_RED + "Oorlog", ChatColor.RED + "Er is een oorlog begonnen!", 20, 20, 20);
	
		}
     	setWarState(WarState.WAR);
		Bukkit.getPluginManager().callEvent(new WarStartEvent());
	}

	public void end() {
		getAdamantiumSoldiers().clear();
		getDokSoldiers().clear();
		getEredonSoldiers().clear();
		getHyvarSoldiers().clear();
		getTilfiaSoldiers().clear();
		getMalzanSoldiers().clear();
		
		for(KingdomFactionsPlayer p : PlayerModule.getInstance().getPlayers()) {
			p.sendTitle(ChatColor.RED + "Oorlog", ChatColor.RED + "De oorlog is afgelopen!", 20, 40, 20);
		}
		WarModule.getInstance().setWar(null);
		for(INexus n : NexusModule.getInstance().getNexuses()) {
			if(!(n instanceof Nexus)) continue;
			if(((Nexus) n).isProtected()) {
			((Nexus) n).setProtected(false);
			}
		}
		Bukkit.getPluginManager().callEvent(new WarStopEvent());
	}
	
	
	public PlayerList getSoldiers(Kingdom k) {
		switch(k.getType()) {
		case ADAMANTIUM:
			return getAdamantiumSoldiers();
		
		case DOK:
			return getDokSoldiers();
	
		case EREDON:
			return getEredonSoldiers();
	
		case HYVAR:
			return getHyvarSoldiers();
		
		case MALZAN:
			return getMalzanSoldiers();
		
		case TILIFIA:
			return getTilfiaSoldiers();

		default:
			return getTotalSoldiers();
		
		}
	}

}
