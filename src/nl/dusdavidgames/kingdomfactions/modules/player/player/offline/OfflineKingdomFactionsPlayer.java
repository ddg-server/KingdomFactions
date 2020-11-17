package nl.dusdavidgames.kingdomfactions.modules.player.player.offline;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.PlayerDatabase;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.exception.value.ValueException;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionMember;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.deathban.DeathBan;
import nl.dusdavidgames.kingdomfactions.modules.player.deathban.DeathBanModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.time.TimeHelper;
import org.bukkit.Location;

import java.util.UUID;


public class OfflineKingdomFactionsPlayer implements IPlayerBase {

	
	public OfflineKingdomFactionsPlayer(String name, UUID uuid, String address, Kingdom kingdom, Faction faction,
			FactionRank fRank, KingdomRank kRank, int coins, int deaths, int kills, long firstjoin, long playtime,
			int influence, Location location) {
		this.location = location;
		this.name = name;
		this.influence = influence;
		this.uuid = uuid;
		this.address = address;
		this.kills = kills;
		this.deaths = deaths;
		this.kingdom = kingdom;
		this.faction = faction;
		this.factionRank = fRank;
		this.kingdomRank = kRank;
		this.firstjoin = firstjoin;
		this.secondsConnected = playtime;
		this.coins = coins;
		this.editmanager = new OfflinePlayerEditManager(this);
	}
    protected @Getter Location location;
	protected @Getter int influence;
	protected @Getter String name;
	protected @Getter UUID uuid;
	protected @Getter String address;
	protected @Getter Kingdom kingdom;
	protected @Getter Faction faction;
	protected @Getter FactionRank factionRank;
	protected @Getter KingdomRank kingdomRank;
	protected @Getter int coins;
	protected @Getter int deaths;
	protected @Getter int kills;
	protected @Getter long firstjoin;
	protected @Getter long secondsConnected;
	protected OfflinePlayerEditManager editmanager;

	public OfflinePlayerEditManager edit() {
		return editmanager;
	}

	public void save() {
		PlayerDatabase.getInstance().savePlayer(this);
	}

	@Override
	public boolean isOnline() {
		return false;
	}

	@Override
	public void setKingdom(Kingdom kingdom) {
		this.edit().setKingdom(kingdom);
		this.edit().save();
	}

	@Override
	public void setFaction(Faction faction) {
		this.edit().setFaction(faction);
		this.edit().save();
	}

	@Override
	public void setKingdomRank(KingdomRank kingdomrank) {
		this.edit().setKingdomRank(kingdomrank);
		this.edit().save();
	}

	@Override
	public void setFactionRank(FactionRank factionrank) {
		
		this.edit().setFactionRank(factionrank);

		
		this.edit().save();
	}
	
	@Override
	public DeathBan getDeathBan() {
		// TODO Auto-generated method stub
		return DeathBanModule.getInstance().getBan(this.name);
	}

	@Override
	public boolean hasDeathBan() {
		return DeathBanModule.getInstance().getBan(this.name) != null;
	}

	@Override
	public void purge() {
    MySQLModule.getInstance().insertQuery("DELETE FROM playerdata WHERE player_id='" + getUuid() + "'");
		
	}
	public String getFormattedName() {
		return getKingdom().getType().getColor() + name;
	}
	
	public void addCoins(int coins) {
		this.edit().addCoins(coins);
		this.edit().save();
	}
	public void removeCoins(int coins, boolean mayBeNegative) throws ValueException {
		this.edit().removeCoins(coins, mayBeNegative);
		this.edit().save();
	}
	public void addInfluence(int influence) {
		this.edit().addInfluence(influence);
		this.edit().save();
	}
	public void removeInfluence(int influence, boolean mayBeNegative) throws ValueException {
		this.edit().removeInfluence(influence, mayBeNegative);
	}

	@Override
	public boolean hasFaction() {
		return getFaction() != null;
	}

	@Override
	public IPlayerBase convert(IPlayerBase old) {
		// TODO Auto-generated method stub
		try {
			return PlayerModule.getInstance().getPlayerBase(old.getUuid());
		} catch (UnkownPlayerException e) {
			return old;
		}
	}
	
	@Override
	public FactionMember toFactionMember() {
		// TODO Auto-generated method stub
		return new FactionMember(this.getFaction(),this.uuid, this.name, this.factionRank);
	}
	
	@Override
	public int[] getPlaytime() {
		// TODO Auto-generated method stub
		return TimeHelper.getInstance().translateTime(getSecondsConnected());
	}

}
