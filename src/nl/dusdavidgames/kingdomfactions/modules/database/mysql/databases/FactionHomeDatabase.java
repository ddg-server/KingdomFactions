package nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.home.Home;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FactionHomeDatabase {

	private static @Getter @Setter FactionHomeDatabase instance;

	public FactionHomeDatabase() {
		setInstance(this);
		setupTable();
		}

		public void setupTable() {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS faction_home (faction_id VARCHAR(40), nexus_id VARCHAR(40), home_x INT(10), home_y INT(10), home_z INT(10), home_world VARCHAR(40), pitch FLOAT, yaw FLOAT)");
			pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			Logger.WARNING.log("Something went wrong on creating the table @ PlayerDatabase:setupPlayerDataTable()");
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
	}

	public void setHome(Home h) {

			Connection connection = null;
			PreparedStatement pStatement = null;
			Location loc = h.getLocation();
			String nexus_id = h.getNexus().getNexusId();
			String faction_id = h.getFaction().getFactionId();
            int x= loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();
            String world = loc.getWorld().getName();
            
			try {
				connection = MySQLModule.getInstance().datasource.getConnection();
				pStatement = connection.prepareStatement("insert into faction_home ("
						+ "faction_id, "
						+ "nexus_id, "
						+ "home_x, "
						+ "home_y, "
						+ "home_z, "
						+ "home_world,"
						+ " pitch, "
						+ "yaw) values ("
						+ "'" + faction_id + "'"
						+ ",'" + nexus_id +"','"+x+"','"+y+"','"+z+"','"+world+"','"+loc.getPitch()+"','" +loc.getYaw() + "')");

				pStatement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
				Logger.WARNING.log("Something went wrong on adding player to the database @ Database:create");

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
	}

	public void loadFactionHome(Faction faction) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			String statement = "SELECT * FROM faction_home WHERE faction_id= '" + faction.getFactionId() + "'";
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement(statement);
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				Nexus nexus = NexusModule.getInstance().getNexus(rs.getString("nexus_id"));
				Location location = new Location(Bukkit.getWorld(rs.getString("home_world")), rs.getInt("home_x"), rs.getInt("home_y"), rs.getInt("home_z"));
				location.add(0.5, 0, 0.5);
				location.setYaw(rs.getFloat("yaw"));
				location.setPitch(rs.getFloat("pitch"));
				Home home = new Home(nexus, faction, location);
				faction.setHome(home);
				
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

	}
	

}
