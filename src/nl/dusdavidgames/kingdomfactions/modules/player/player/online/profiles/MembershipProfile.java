package nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles;

import java.util.ArrayList;

import org.bukkit.Bukkit;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.exception.kingdom.KingdomException;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionModule;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionRank;
import nl.dusdavidgames.kingdomfactions.modules.faction.event.FactionSwitchEvent;
import nl.dusdavidgames.kingdomfactions.modules.faction.invite.Invite;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.event.KingdomSwitchEvent;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.event.RankChangeEvent;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.menu.KingdomMenu;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class MembershipProfile extends Profile {

	private @Getter Faction faction;
	private @Getter Kingdom kingdom;
	private @Getter KingdomRank kingdomRank;
	private @Getter FactionRank factionRank;


	public MembershipProfile(KingdomFactionsPlayer player, Faction faction, Kingdom kingdom, KingdomRank kRank,
			FactionRank fRank) {
		super(player);
		this.faction = faction;
		this.kingdomRank = kRank;
		this.factionRank = fRank;
		this.kingdom = kingdom;
	}

	public void setKingdom(Kingdom k) throws KingdomException {
			this.kingdom = k;
			Bukkit.getPluginManager().callEvent(new KingdomSwitchEvent(this.player));

	}

	public void setFaction(Faction f) {
		
		Bukkit.getPluginManager().callEvent(new FactionSwitchEvent(this.player, f));
			this.player.getChatProfile().wipeChannels();
			this.faction = f;
			if(f != null){
				f.addMember(this.player);
				this.player.getChatProfile().addChannel(f.getChannel());
			}
			
	
	  
	}

	public boolean isFactionMod() {
		if (this.hasFaction()) {
			if (this.getFactionRank() == FactionRank.LEADER || this.getFactionRank() == FactionRank.OFFICER || this.player.isStaff()) {
				return true;
			}
		}
		return false;
	}
	
	
 public boolean isFactionOfficer() {
		if (this.hasFaction()) {
			if (this.getFactionRank() == FactionRank.OFFICER) {
				return true;
			}
		}
		return false;
	}

	public boolean isFactionLeader() {
		if (this.hasFaction()) {
			if (this.getFactionRank() == FactionRank.LEADER) {
				return true;
			}
		}
		return false;
	}

	public void openKingdomSelectMenu() {
		KingdomMenu.getInstance().setKindomMenu(this.player);
	}

	public boolean hasFaction() {
		return faction != null;
	}

	public boolean hasPendingInvites() {
		for (Faction f : FactionModule.getInstance().getFactions()) {
			for (Invite i : f.getInvites()) {
				if (i.getPlayer().getName().equalsIgnoreCase(this.player.getName())) {
					return true;
				}
			}

		}
		return false;
	}
   public ArrayList<Invite> getPendingInvites() {
	   ArrayList<Invite> temp = new ArrayList<Invite>();
		for (Faction f : FactionModule.getInstance().getFactions()) {
			for (Invite i : f.getInvites()) {
				if (i.getPlayer().getName().equalsIgnoreCase(this.player.getName())) {
					temp.add(i);
				}
			}

		}
		return temp;
   }
   
  public Invite getPendingInviteForFaction(Faction f) {
	  for(Invite i : getPendingInvites()) {
		  if(FactionModule.getInstance().compareFactions(f, i.getTargetFaction())) {
			  return i;
		  }
	  }
	  return null;
  }
  
  public boolean hasPendingInviteForFaction(Faction f) {
	  return getPendingInviteForFaction(f) != null;
  }
  
  public boolean isFactionAdmin() {
	  if(isFactionLeader() || player.isStaff()) {
		  return true;
	  }
	  return false;
  }

	public boolean isKoning() {
		return this.getKingdomRank() == KingdomRank.KONING;
	}
	public boolean isWachter() {
		return this.getKingdomRank() == KingdomRank.WACHTER;
	}
	
	public String toString() {
		return this.player.getName();
	}
	
	public void setKingdomRank(KingdomRank rank) {
		this.kingdomRank = rank;
		Bukkit.getPluginManager().callEvent(new RankChangeEvent(rank, this.player));
		
	}
	
	public void setFactionRank(FactionRank rank) {
		this.factionRank = rank;
		Bukkit.getPluginManager().callEvent(new RankChangeEvent(rank, this.player));
	}
}
