package nl.dusdavidgames.kingdomfactions.modules.player.player;

import nl.dusdavidgames.kingdomfactions.modules.exception.faction.FactionException;
import nl.dusdavidgames.kingdomfactions.modules.exception.value.ValueException;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionMember;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.player.deathban.DeathBan;

import java.util.UUID;

public interface IPlayerBase {

	public boolean isOnline();

	public boolean hasFaction();
	
	
	public String getName();

	public UUID getUuid();

	public KingdomRank getKingdomRank();

	public FactionRank getFactionRank();

	public Faction getFaction();

	public Kingdom getKingdom();

	public void setKingdom(Kingdom kingdom);

	public void setFaction(Faction faction);

	public void setKingdomRank(KingdomRank kingdomrank);

	public void setFactionRank(FactionRank factionrank);

	public void save();
	
	public DeathBan getDeathBan();
	
	public boolean hasDeathBan();
	
	public void purge();
	
	public String getFormattedName();
	
	public void addCoins(int coins);
	
	public void addInfluence(int influence);
	
	public void removeInfluence(int influence, boolean mayBeNegative) throws ValueException;
	
	public void removeCoins(int coins, boolean mayBeNegative) throws ValueException;
	
	public int getInfluence();
	
	public int getCoins();
	
	public FactionMember toFactionMember();
	
	public IPlayerBase convert(IPlayerBase old);
	
	public int[] getPlaytime();
	
	
	public default void leaveFaction() throws FactionException {
		if(this.hasFaction()) {
			Faction f = this.getFaction();
			f.removeMember(this);
			this.setFaction(null);
		   
		} else {
			throw new FactionException("Can't leave a faction, when someone is not member of one!");
		}
	}
}
