package nl.dusdavidgames.kingdomfactions.modules.faction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.UUID;

import lombok.Data;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.chat.ChatModule;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatChannel;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.FactionHomeDatabase;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.PlayerDatabase;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.ShopDatabase;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.ChannelNotFoundException;
import nl.dusdavidgames.kingdomfactions.modules.exception.faction.FactionException;
import nl.dusdavidgames.kingdomfactions.modules.faction.home.Home;
import nl.dusdavidgames.kingdomfactions.modules.faction.invite.Invite;
import nl.dusdavidgames.kingdomfactions.modules.faction.invite.InviteModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.CapitalNexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.IInhabitable;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.InhabitableType;

public @Data class Faction implements IInhabitable {

	private String factionId;
	private ArrayList<FactionMember> members = new ArrayList<FactionMember>();
	private String name;
	private Home home;
	private @Getter ArrayList<Invite> invites = new ArrayList<Invite>();
	private KingdomType style;
	private @Getter HashMap<String, Integer> shopLimits = new HashMap<>();

	public Faction(String factionId, String name, KingdomType style) {
		this.name = name;
		this.factionId = factionId;
		this.style = style;
		this.initMembers();
		FactionHomeDatabase.getInstance().loadFactionHome(this);
		this.shopLimits.putAll(ShopDatabase.getInstance().loadShopItemLimits(factionId));
	}

	public String getFactionId() {
		return factionId;
	}

	public void setHome(Home home) {
		if (hasHome()) {
			getHome().remove();
		}
		this.home = home;

		FactionHomeDatabase.getInstance().setHome(home);
	}

	public void setName(String name) {
		this.name = name;
		this.save();
	}

	public boolean hasPlayer(IPlayerBase player) {
		return player.getFaction() == this;
	}

	public String getName() {
		return name;
	}

	public void save() {
		MySQLModule.getInstance().insertQuery(
				"UPDATE faction SET faction_name='" + this.name + "' WHERE faction_id='" + this.factionId + "'");
		ShopDatabase.getInstance().save(this.factionId, this.shopLimits);
	}

	public void unRegister() {
		FactionModule.getInstance().getFactions().remove(this);
	}

	public ArrayList<Nexus> getNexuses() {
		return NexusModule.getInstance().getNexuses(this);
	}

	public boolean hasNexus() {
		for (INexus ne : NexusModule.getInstance().getNexuses()) {
			if (ne instanceof CapitalNexus)
				continue;
			Nexus n = (Nexus) ne;
			if (n.getOwnerId().equals(this.getFactionId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method will remove the faction
	 */
	public void remove() {

		this.delete();
		FactionModule.getInstance().getFactions().remove(this);
		//Iterator<FactionMember> iter = this.members.iterator();
		//while (iter.hasNext()) {
		//	removeFaction(iter.next().toPlayer());
		//}
		
		for(int i = 0; i < this.members.size(); i++){
			removeFaction(this.getMembers().get(i).toPlayer());
		}
		this.getChannel().deleteIgnorePersitent(null);
		
		Iterator<Nexus> iter = this.getNexuses().iterator();
		while(iter.hasNext()) {
			iter.next().setOwner(null);
		}
	}

	private void removeFaction(IPlayerBase player) {
		player.setFactionRank(FactionRank.SPELER);
		
		player.setFaction(null);
		
		if (player.isOnline()) {
			KingdomFactionsPlayer pl = (KingdomFactionsPlayer) player;
			pl.updateTerritory();
			pl.getChatProfile().wipeChannels();
		}
	}

	public void broadcast(String prefix, String message) {
		for (KingdomFactionsPlayer p : PlayerModule.getInstance().getPlayers()) {
			if (p.getFaction() == this) {
				p.sendMessage(prefix + message);
			}
		}
	}

	public boolean hasHome() {
		return getHome() != null;
	}

	public ChatChannel getChannel() {
		try {
			return ChatModule.getInstance().getChannelById(this.factionId);
		} catch (ChannelNotFoundException e) {

			e.printStackTrace(); // If this happens. I really screwed up
		}
		return null;
	}

	public boolean isInvited(KingdomFactionsPlayer p) {
		return this.invites.contains(InviteModule.getInstance().getInvite(this, p));
	}

	public void addMember(IPlayerBase member) {
		if(hasMember(member)) return;
		
		this.members.add(member.toFactionMember());
	}

	public void removeMember(IPlayerBase member) {
		this.members.remove(getFactionMember(member.getUuid()));
	}

	public boolean hasMember(IPlayerBase member) {
		return getFactionMember(member.getUuid()) != null;
	}

	private void initMembers() {
		for (IPlayerBase player : PlayerDatabase.getInstance().loadPlayers(this)) {
			this.members.add(player.toFactionMember());
		}
	}

	public ArrayList<FactionMember> getOfficers() {
		ArrayList<FactionMember> temp = new ArrayList<FactionMember>();
		for (FactionMember player : members) {
			if (player.getRank() == FactionRank.OFFICER) {
				temp.add(player);
			}
		}
		return temp;
	}

	public ArrayList<FactionMember> getLeaders() throws FactionException {
		ArrayList<FactionMember> temp = new ArrayList<FactionMember>();
		for (FactionMember player : members) {
			if (player.getRank() == FactionRank.LEADER) {
				temp.add(player);
			}
		}
		if(temp.isEmpty()) {
			throw new FactionException("Faction without leader");
		}
		return temp;
	}
	public ArrayList<FactionMember> getPlayers() {
		ArrayList<FactionMember> temp = new ArrayList<FactionMember>();
		for (FactionMember player : members) {
			if (player.getRank() == FactionRank.SPELER) {
				temp.add(player);
			}
		}
		return temp;
	}
	

	

	public ArrayList<FactionMember> getOnlineMembers() {
		ArrayList<FactionMember> temp = new ArrayList<FactionMember>();
		for (FactionMember p : members) {

			if (p.isOnline()) {
				temp.add(p);
			}
		}
		return temp;
	}

	public void delete() {
		MySQLModule.getInstance().insertQuery("DELETE FROM faction WHERE faction_id='" + this.factionId + "'");
		MySQLModule.getInstance().insertQuery("DELETE FROM shop_limits WHERE faction_id='" + this.factionId + "'");
	}

	@Override
	public InhabitableType getInhabitableType() {
		return InhabitableType.FACTION;
	}

	public FactionMember getFactionMember(UUID uuid) {
		for (FactionMember f : members) {
			if (f.getUuid().equals(uuid)) {
				return f;
			}
		}
		return null;
	}
	/**
	public INexus getClosestNexus(KingdomFactionsPlayer player) {
		HashMap<INexus, Double> distance = new HashMap<INexus, Double>();
		for (INexus n : NexusModule.getInstance().getNexuses()) {
			Location nexusloc = Utils.getInstance().getNewLocation(n.getNexusLocation());
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
	*/
	
	
	public Nexus getBestNexus() {
		HashMap<Nexus, Integer> levels = new HashMap<Nexus, Integer>();
		for(Nexus n : this.getNexuses()) {
			levels.put(n, n.getLevel());
		}
		int maxValueInMap = (Collections.max(levels.values()));
		for(Entry<Nexus, Integer> entry : levels.entrySet()) {
			if(entry.getValue() == maxValueInMap) {
				return entry.getKey();
			}
		}
		return null;
   }
	
	
	public String getPrefix() {
		return ChatColor.GRAY + "[" + this.style.getColor() + this.name + ChatColor.GRAY + "] ";
	}
}
