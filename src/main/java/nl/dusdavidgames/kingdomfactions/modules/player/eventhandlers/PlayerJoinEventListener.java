package nl.dusdavidgames.kingdomfactions.modules.player.eventhandlers;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.ChannelNotFoundException;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.ChannelRankException;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.PlayerException;
import nl.dusdavidgames.kingdomfactions.modules.exception.value.ValueException;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionMember;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.ProtectionModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Item;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

public class PlayerJoinEventListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		try {
			e.setJoinMessage(null);
			KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
			p.restoreOldPvP();
			p.sendTitle(ChatColor.RED + "The Kingdom Factions", ChatColor.RED + "We laden jouw gegevens..", 20, 40, 20);
			p.setTerritoryId(ProtectionModule.getInstance().getTerritoryId(p));
			p.setKingdomTerritory(ProtectionModule.getInstance().getKingdomTerritory(p));
			if (p.hasFaction()) {
				FactionMember mem = p.getFaction().getFactionMember(p.getUuid());
				mem.setRank(p.getFactionRank());
				mem.updateName();
			}
			p.loadScoreboard();
			ProtectionModule.getInstance().updateTerritory(p);
			try {
				this.prepareChat(p);
			} catch (ChannelNotFoundException | ChannelRankException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (p.getKingdom().getType().equals(KingdomType.GEEN)) {
				p.getInventory().clear();
				p.getInventory().addItem(
						Item.getInstance().getItem(Material.COMPASS, ChatColor.RED + "Selecteer jouw kingdom", 1));
				p.updateInventory();
				p.teleport(p.getKingdom().getSpawn());
			}
			if (p.hasSwitch()) {
				try {
					p.executeSwitch();
				} catch (PlayerException | ValueException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} catch (Exception ex) {
			Logger.ERROR.log("Exception ----------------------------------");
			ex.printStackTrace();
			Logger.ERROR.log("Exception ----------------------------------");
			e.getPlayer().kickPlayer(ChatColor.RED + "" + ChatColor.BOLD + "The Kingdom Factions \n"
					+ "We konden jouw spelerdata niet laden! Ons excuses. \nProbeer later nog eens te joinen!");

		}

	}

	public void prepareChat(KingdomFactionsPlayer player) throws ChannelNotFoundException, ChannelRankException {
		
		player.getChatProfile().wipeChannels();
		/**ChatChannel radius = ChatModule.getInstance().getChannelByName("RADIUS");

		KingdomChannel kingdom = (KingdomChannel) ChatModule.getInstance()
				.getChannelByName(player.getKingdom().getName());

		radius.allow(player);
		radius.join(player, false);
		kingdom.allow(player);
		kingdom.join(player);
		if (player.isStaff()) {
			player.getChatProfile().setRank(new DDGStaffChannelRank(new KingdomChannelRank(player.getKingdomRank())),
					kingdom);
			player.getChatProfile().setRank(new DDGStaffChannelRank(new KingdomChannelRank(player.getKingdomRank())),
					radius);
			if (player.hasFaction()) {
				player.getFaction().getChannel().allow(player);
				player.getFaction().getChannel().join(player);
				player.getFaction().getChannel().setRankFor(player, new FactionChannelRank(player.getFactionRank()));
			}
		} else {
			player.getChatProfile().setRank(new KingdomChannelRank(player.getKingdomRank()), kingdom);
			player.getChatProfile().setRank(new SpeakerChannelRank(), radius);
			if (player.hasFaction()) {
				player.getFaction().getChannel().allow(player);
				player.getFaction().getChannel().join(player);
				player.getFaction().getChannel().setRankFor(player, new FactionChannelRank(player.getFactionRank()));
			}
		}

		player.getChatProfile().setCurrent(kingdom);

*/
	}

}
