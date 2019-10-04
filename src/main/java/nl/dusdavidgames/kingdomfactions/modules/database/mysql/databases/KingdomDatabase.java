package nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import nl.dusdavidgames.kingdomfactions.modules.exception.kingdom.UnkownKingdomException;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.CapitalNexus;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

public class KingdomDatabase {

	private static @Getter @Setter KingdomDatabase instance;

	public KingdomDatabase() {
		setInstance(this);
		setupTable();
	}

	public void setupTable() {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS kingdom (kingdom VARCHAR(40), "
					+ "world varchar(40), " + "x_spawn int(10), " + "y_spawn int(10), " + "z_spawn int(10), "
					+ "pitch FLOAT, " + "yaw FLOAT, " + "x_nexus int(10), " + "y_nexus int(10), " + "z_nexus int(10),"+ "x_mining int(10),"+ "y_mining int(10),"+ "z_mining int(10))");
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

	public void prepareKingdom(String name) {
		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement("INSERT kingdom (kingdom," + " world," + " x_spawn," + " y_spawn,"
					+ " z_spawn," + " pitch," + " yaw," + " x_nexus," + " y_nexus," + " z_nexus, x_mining, y_mining, z_mining) values ('" + name
					+ "','" + Utils.getInstance().getOverWorld().getName() + "','0','0','0','0','0','0','0','0','0','0','0')");

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

	public void setSpawn(String kingdom, Location spawn) {
		String world = spawn.getWorld().getName();
		int x = spawn.getBlockX();
		int y = spawn.getBlockY();
		int z = spawn.getBlockZ();
		float pitch = spawn.getPitch();
		float yaw = spawn.getYaw();
		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			String statement = "UPDATE kingdom SET world= '" + world + "', x_spawn='" + x + "', y_spawn='" + y
					+ "', z_spawn='" + z + "', yaw='" + yaw + "', pitch='" + pitch + "' WHERE kingdom='" + kingdom
					+ "'";
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement(statement);

			pStatement.execute();
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

	public void saveNexus(CapitalNexus nexus) {
		int x = nexus.getNexusLocation().getBlockX();
		int y = nexus.getNexusLocation().getBlockY();
		int z = nexus.getNexusLocation().getBlockZ();
		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			String statement = "UPDATE kingdom SET x_nexus='" + x + "', y_nexus='" + y + "', z_nexus='" + z
					+ "' WHERE kingdom='" + nexus.getKingdom() + "'";
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement(statement);

			pStatement.execute();
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

	
	public void saveMining(Kingdom k, Location loc) {
		int x, y, z;
		x = loc.getBlockX();
		y = loc.getBlockY();
		z = loc.getBlockZ();
		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			String statement = "UPDATE kingdom SET x_mining='" + x + "', y_mining='" + y + "', z_mining='" + z
					+ "' WHERE kingdom='" + k.getType().toString() + "'";
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement(statement);

			pStatement.execute();
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

	public Kingdom loadKingdom(KingdomType t) throws UnkownKingdomException {
		Connection connection = null;
		PreparedStatement pStatement = null;
		Kingdom k = null;
		try {
			String statement = "SELECT * FROM  kingdom WHERE kingdom= '" + t + "'";
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement(statement);
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				Location spawn = new Location(Bukkit.getWorld(rs.getString("world")), rs.getInt("x_spawn"),
						rs.getInt("y_spawn"), rs.getInt("z_spawn"));
				Location nexus = new Location(Bukkit.getWorld(rs.getString("world")), rs.getInt("x_nexus"),
						rs.getInt("y_nexus"), rs.getInt("z_nexus"));
				Location miningspawn = new Location(Utils.getInstance().getMiningWorld(), rs.getInt("x_mining"),
						rs.getInt("y_mining"), rs.getInt("z_mining"));
				if(miningspawn.getBlockY() <= 20) {
				   miningspawn = Utils.getInstance().getMiningWorld().getSpawnLocation();
				}

				k = new Kingdom(t, miningspawn, spawn, nexus);
			
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
		if (k == null) {
			throw new UnkownKingdomException("Could not load Kingdom " + t + "!");
		} else {
			return k;
		}

	}

	public int getMembers(Kingdom k) {
		ArrayList<String> name = new ArrayList<String>();
		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			String statement = "SELECT * FROM playerdata WHERE player_kingdom='" + k.getType().toString() + "'";
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement(statement);
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				name.add(rs.getString("player_name"));
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
		return name.size();
	}

}
