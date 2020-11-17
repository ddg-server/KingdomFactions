package nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom;

import lombok.Data;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.chat.ChatModule;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.ChatMode;
import nl.dusdavidgames.kingdomfactions.modules.chat.framework.channels.KingdomChannel;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.KingdomDatabase;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.ChannelNotFoundException;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.CapitalNexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.IInhabitable;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.InhabitableType;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import org.bukkit.Location;

import java.util.ArrayList;

public @Data class Kingdom implements IInhabitable{

	private Location spawn;
	private CapitalNexus nexus;
	private KingdomType type;
	private Location miningSpawn;
	private ChatMode chatMode = ChatMode.EVERYONE;
	private int members;
	
	private String name;

	public Kingdom(KingdomType kingdom, Location miningSpawn, Location spawn, Location nexus) {
		this.spawn = spawn;
		this.type = kingdom;
		this.miningSpawn = miningSpawn;
		this.nexus = new CapitalNexus(nexus, this.type);

		this.members = KingdomDatabase.getInstance().getMembers(this);
		this.name = this.type.toString().toLowerCase();

	}

	public KingdomChannel getChannel() {
		try {
			return (KingdomChannel) ChatModule.getInstance().getChannelByName(type.toString());
		} catch (ChannelNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void broadcast(String message) {
		for (KingdomFactionsPlayer p : PlayerModule.getInstance().getPlayers()) {
			if (p.getKingdom() == this || p.isStaff()) {
				p.sendMessage(this.type.getPrefix() + ChatColor.GOLD + "" + ChatColor.BOLD + ">> " + ChatColor.WHITE
						+ message);
				Logger.BROADCAST.log(message);
			}
		}
	}

	public ArrayList<KingdomFactionsPlayer> getOnlineMembers() {
		ArrayList<KingdomFactionsPlayer> temp = new ArrayList<KingdomFactionsPlayer>();
		for (KingdomFactionsPlayer player : PlayerModule.getInstance().getPlayers()) {
			if (player.getKingdom() == this) {
				temp.add(player);
			}
		}
		return temp;
	}
	
	@Override
	public InhabitableType getInhabitableType() {
		return InhabitableType.KINGDOM;
	}
	
	
	public void initNexus() {
		NexusModule.getInstance().addCapitalNexus(getNexus());
	}
	
	public void setMiningSpawn(Location loc) {
		this.miningSpawn = loc;
		KingdomDatabase.getInstance().saveMining(this, loc);
	}
	
	public int getDistanceToMineSpawn(Location loc) {
		Location loc1 = Utils.getInstance().getNewLocation(miningSpawn);
		Location loc2 = Utils.getInstance().getNewLocation(loc);
		loc1.setY(0);
		loc2.setY(0);
		return (int) loc1.distance(loc2);
	}
	
}
