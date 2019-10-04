package nl.dusdavidgames.kingdomfactions.modules.player.player.offline;

import nl.dusdavidgames.kingdomfactions.modules.exception.value.ValueException;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionModule;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

public class OfflinePlayerEditManager {

	public OfflinePlayerEditManager(OfflineKingdomFactionsPlayer player) {
		this.player = player;
	}
	
	private OfflineKingdomFactionsPlayer player;
	
	
	public void setFaction(Faction faction) {
		if(faction == null) {
			this.player.faction = null;
			return;
		}
		for(Faction f : FactionModule.getInstance().getFactions()) {
			if(f.hasMember(this.player)) {
				f.removeMember(this.player);
			}
		}
		faction.addMember(this.player);
		this.player.faction = faction;
		this.player.save();
	}
	public void setKingdom(Kingdom kingdom) {
		this.player.kingdom = kingdom;
	}
	public void setCoins(int coins) {
		this.player.coins = coins;
	}
	public void addCoins(int coins) {
		Logger.INFO.log("Added " + coins + " coins to offline player " + this.player.getName() + "/" + this.player.getUuid());
		this.player.coins = this.player.coins + coins;
	}
	public void removeCoins(int coins, boolean mayBeNegative) throws ValueException {
		Logger.INFO.log("Removing " + coins + " coins from offline player " + this.player.getName() + "/" + this.player.getUuid());
		if(!mayBeNegative) {
			if(!canAfford(coins)) {
				throw new ValueException("Player could not afford this amount of coins.");
			}
		}
		this.player.coins = this.player.coins - coins;
	}
	public void addInfluence(int influence) {
		this.player.influence = this.player.influence + influence;
	}
	public void removeInfluence(int influence, boolean mayBeNegative) throws ValueException {
		if(!mayBeNegative) {
			if(canAffordInfluence(influence)) {
				throw new ValueException("Player could not afford this amount of influence.");
			}
		}
		this.player.influence = this.player.influence - influence;

	}
	public void setInfluence(int influence) {
		this.player.influence = influence;

	}
	public void setFactionRank(FactionRank rank) {
		this.player.factionRank = rank;
		this.player.getFaction().getFactionMember(this.player.getUuid()).setRank(rank);

	}
	public void setKingdomRank(KingdomRank rank) {
		this.player.kingdomRank = rank;

	}
	public void setKills(int kills) {
		this.player.kills = kills;

	}
	public void setDeaths(int deaths) {
		this.player.deaths = deaths;

	}
	public void setPlaytime(long playtime) {
		this.player.secondsConnected = playtime;
	}
	public void save() {
   this.player.save();
	}
	
	public boolean canAfford(int coins) {
		if(this.player.getCoins() >= coins) {
			return true;
		}
		return false;
	}
	public boolean canAffordInfluence(int influence) {
		if(this.player.getInfluence() >= influence) {
			return true;
		}
		return false;
	}
	
}
