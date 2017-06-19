package nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.coins.CoinsModule;
import nl.dusdavidgames.kingdomfactions.modules.exception.value.ValueException;
import nl.dusdavidgames.kingdomfactions.modules.influence.InfluenceModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class StatisticsProfile extends Profile {
	
	
	public StatisticsProfile(KingdomFactionsPlayer player, int kills, int deaths, int coins, int influence, long secondsConnected, long firstjoin) {
		super(player);
		this.kills = kills;
		this.deaths = deaths;
		this.secondsConnected = secondsConnected;
		this.influence = influence;
		this.coins = coins;
		this.firstjoin = firstjoin;
	}
	
	
	public StatisticsProfile(KingdomFactionsPlayer player) {
		this(player, 0, 0, 0, 0, 0, System.currentTimeMillis());
	}
	private int kills;
	private int deaths;
	private @Getter long firstjoin;
	private @Getter @Setter int coins;
	private @Getter @Setter int influence;
	private @Getter @Setter long lastUpdate = System.currentTimeMillis();
	private @Getter @Setter long secondsConnected = 0;
	
	
	public int getKills() {
		return kills;
		
	}
	public int getDeaths() {
		return deaths;
	}
	
	public void addDeath(int death) {
		this.deaths = this.deaths + death;
	}
	public void addKill(int kill) {
		this.kills = this.kills + kill;
	}
    public void setKills(int kill) {
    	this.kills = kill;
    }
    public void setDeaths(int death) {
    	this.deaths = death;
    }
    
    
    
    public void addInfluence(int influence) {
    	InfluenceModule.getInstance().addInfluence(this, influence);
    }
    public void removeInfluence(int influence, boolean mayBeNegative) {
    	InfluenceModule.getInstance().removeInfluence(this, influence, mayBeNegative);
    }
    public boolean canAffordInfluence(int influence) {
    	return InfluenceModule.getInstance().canAfford(this, influence);
    }
    public boolean canAffordCoins(int coins) {
    	return CoinsModule.getInstance().canAfford(this, coins);
    }
    public void addCoins(int coins) {
    	CoinsModule.getInstance().addCoins(this, coins);
    }
    public void removeCoins(int coins, boolean mayBeNegative) throws ValueException {
    	CoinsModule.getInstance().removeCoins(this, coins, mayBeNegative);
    }

}
