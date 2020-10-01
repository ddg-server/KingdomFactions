package nl.dusdavidgames.kingdomfactions.modules.faction;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FactionMember {

	
	public FactionMember(Faction f, UUID uuid, String name, FactionRank rank) {
		this.uuid = uuid;
		this.name = name;
		this.rank = rank;
		this.faction = f;
	}
	public boolean isOnline() {
		return Bukkit.getPlayer(uuid) != null;
	}
	
	public Player toBukkitPlayer() {
		return Bukkit.getPlayer(uuid);
	}
	
	
	private @Getter UUID uuid;
	private @Getter String name;
	private @Getter Faction faction;
	private  @Getter FactionRank rank;
	
	public IPlayerBase toPlayer() {
	        try {
				return PlayerModule.getInstance().getPlayerBase(uuid);
			} catch (UnkownPlayerException e) {
		
			}
			return null;
	}
	
	public void setRank(FactionRank rank) {
		this.rank = rank;

	}
	
	public void updateName() {
	IPlayerBase base = toPlayer();
	if(!base.getName().equalsIgnoreCase(this.name)) {
		this.name = base.getName();
	}
	}
}
