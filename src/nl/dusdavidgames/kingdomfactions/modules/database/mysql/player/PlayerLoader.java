package nl.dusdavidgames.kingdomfactions.modules.database.mysql.player;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionModule;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.player.player.offline.OfflineKingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.StatisticsProfile;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class PlayerLoader {

	public KingdomFactionsPlayer loadPlayer(UUID uuid) throws UnkownPlayerException {

		Connection connection = null;
		PreparedStatement pStatement = null;
		KingdomFactionsPlayer kdfp = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			String statement = "SELECT * FROM playerdata WHERE player_id='{id}'";
			pStatement = connection.prepareStatement(statement.replace("{id}", uuid + ""));
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				FactionRank fRank = FactionRank.getRank(rs.getString("factionrank"));
				KingdomRank kRank = KingdomRank.getRank(rs.getString("kingdomrank"));
				Faction f = FactionModule.getInstance().getFaction(rs.getString("faction_id"));
				KingdomType k = KingdomType.getKingdom(rs.getString("player_kingdom"));

				kdfp = new KingdomFactionsPlayer(fRank, kRank, KingdomModule.getInstance().getKingdom(k), f, uuid);
				kdfp.setStatisticsProfile(new StatisticsProfile(kdfp, rs.getInt("kills"), rs.getInt("deaths"), rs.getInt("coins"),rs.getInt("influence"), rs.getLong("player_playtime"), rs.getLong("player_firstjoin")));
                kdfp.setHasSwitch(rs.getBoolean("isSwitch"));
                kdfp.handleExtraCoins();
                
			}
			rs.close();
		} catch (Exception e) {
			Logger.ERROR.log("Failed to load " + uuid );
			Logger.ERROR.log("Stack trace ---------------------------------");
			e.printStackTrace();
			Logger.ERROR.log("Stack trace ---------------------------------");
			Bukkit.getPlayer(uuid).kickPlayer(ChatColor.RED + ""+ ChatColor.BOLD + "The Kingdom Factions \n" + "We konden jouw speler data niet laden! Ons excuses. \nProbeer later nog eens te joinen!");
			
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				Logger.WARNING.log("Couldn't close DB Connection");
			}
			try {
				if (pStatement != null) {
					pStatement.close();
				}
			} catch (SQLException ex) {
				Logger.WARNING.log("Couldn't close DB PreparedStatement");
			}
		}
		if (kdfp == null) {
			throw new UnkownPlayerException("Could not load Player.");
		} else {
			return kdfp;
		}
	}

	public OfflineKingdomFactionsPlayer loadOfflinePlayer(UUID uuid) throws UnkownPlayerException {
		OfflineKingdomFactionsPlayer kdfp = null;
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			String statement = "SELECT * FROM playerdata WHERE player_id='{id}'";
			pStatement = connection.prepareStatement(statement.replace("{id}", uuid + ""));
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				String address = rs.getString("player_ip");
				String name = rs.getString("player_name");
				FactionRank fRank = FactionRank.getRank(rs.getString("factionrank"));
				KingdomRank kRank = KingdomRank.getRank(rs.getString("kingdomrank"));
				Faction f = FactionModule.getInstance().getFaction(rs.getString("faction_id"));
				KingdomType k = KingdomType.getKingdom(rs.getString("player_kingdom"));
				int coins = rs.getInt("coins");
				int influence = rs.getInt("influence");
				int playtime = rs.getInt("player_playtime");
				kdfp = new OfflineKingdomFactionsPlayer(name, uuid, address, KingdomModule.getInstance().getKingdom(k),
						f, fRank, kRank, coins, rs.getInt("deaths"), rs.getInt("kills"), rs.getLong("player_firstjoin"),
						playtime, influence, Utils.getInstance().DbStringToLocation(rs.getString("location")));
				rs.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				Logger.WARNING.log("Couldn't close DB Connection");
			}
			try {
				if (pStatement != null) {
					pStatement.close();
				}
			} catch (SQLException ex) {
				Logger.WARNING.log("Couldn't close DB PreparedStatement");
			}
		}
		if (kdfp == null) {
			throw new UnkownPlayerException("Failed to load OfflinePlayer!");
		}
		return kdfp;
	}

	public OfflineKingdomFactionsPlayer loadOfflinePlayer(String name) throws UnkownPlayerException {
		OfflineKingdomFactionsPlayer kdfp = null;
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			String statement = "SELECT * FROM playerdata WHERE player_name='{name}'";
			pStatement = connection.prepareStatement(statement.replace("{name}", name));
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				String address = rs.getString("player_ip");
				UUID uuid = UUID.fromString(rs.getString("player_id"));
				FactionRank fRank = FactionRank.getRank(rs.getString("factionrank"));
				KingdomRank kRank = KingdomRank.getRank(rs.getString("kingdomrank"));
				Faction f = FactionModule.getInstance().getFaction(rs.getString("faction_id"));
				KingdomType k = KingdomType.getKingdom(rs.getString("player_kingdom"));
				int coins = rs.getInt("coins");
				int influence = rs.getInt("influence");
				int playtime = rs.getInt("player_playtime");
				kdfp = new OfflineKingdomFactionsPlayer(name, uuid, address, KingdomModule.getInstance().getKingdom(k),
						f, fRank, kRank, coins, rs.getInt("deaths"), rs.getInt("kills"), rs.getLong("player_firstjoin"),
						playtime, influence, Utils.getInstance().DbStringToLocation(rs.getString("location")));
				rs.close();
				return kdfp;//to prevent "Operation not allowed after ResultSet closed" error
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				Logger.WARNING.log("Couldn't close DB Connection");
			}
			try {
				if (pStatement != null) {
					pStatement.close();
				}
			} catch (SQLException ex) {
				Logger.WARNING.log("Couldn't close DB PreparedStatement");
			}
		}
		if (kdfp == null) {
			throw new UnkownPlayerException("Failed to load OfflinePlayer!");
		}
		return kdfp;
	}

	public ArrayList<IPlayerBase> loadPlayers(Faction f) {
		ArrayList<IPlayerBase> players = new ArrayList<IPlayerBase>();
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			String statement = "SELECT * FROM playerdata WHERE faction_id='{faction_id}'";
			pStatement = connection.prepareStatement(statement.replace("{faction_id}", f.getFactionId()));
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				String address = rs.getString("player_ip");
				UUID uuid = UUID.fromString(rs.getString("player_id"));
				FactionRank fRank = FactionRank.getRank(rs.getString("factionrank"));
				KingdomRank kRank = KingdomRank.getRank(rs.getString("kingdomrank"));
				KingdomType k = KingdomType.getKingdom(rs.getString("player_kingdom"));
				int coins = rs.getInt("coins");
				int influence = rs.getInt("influence");
				int playtime = rs.getInt("player_playtime");
				players.add(new OfflineKingdomFactionsPlayer(rs.getString("player_name"), uuid, address,
						KingdomModule.getInstance().getKingdom(k), f, fRank, kRank, coins, rs.getInt("deaths"),
						rs.getInt("kills"), rs.getLong("player_firstjoin"), playtime, influence, Utils.getInstance().DbStringToLocation(rs.getString("location"))));
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				Logger.WARNING.log("Couldn't close DB Connection");
			}
			try {
				if (pStatement != null) {
					pStatement.close();
				}
			} catch (SQLException ex) {
				Logger.WARNING.log("Couldn't close DB PreparedStatement");
			}
		}
		return players;
	}
}
