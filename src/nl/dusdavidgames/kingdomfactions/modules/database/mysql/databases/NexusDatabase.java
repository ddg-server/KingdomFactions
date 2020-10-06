package nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class NexusDatabase {

	private static @Getter @Setter NexusDatabase instance;

	public NexusDatabase() {
		setInstance(this);
		setupTable();
	}

	public void setupTable() {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS nexus"
					+ " (nexus_id varchar(40), owner_id varchar(40), nexus_level int(10),"
					+ " coordinate_x int(10), coordinate_y int(10), coordinate_z int(10), "
					+ "paste_coordinate_x int(10), paste_coordinate_y int(10), paste_coordinate_z int(10), world varchar(40), protect VARCHAR(1))");
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

	public void createNexus(String nexus_id, String owner_id, int x, int y, int z, String world, int pasteX, int pasteY, int pasteZ) {
		if (!check(nexus_id)) {
			Connection connection = null;
			PreparedStatement pStatement = null;

			try {
				connection = MySQLModule.getInstance().datasource.getConnection();
				pStatement = connection.prepareStatement(
						"insert into nexus (nexus_id, owner_id, nexus_level, coordinate_x, coordinate_y, coordinate_z, paste_coordinate_x, paste_coordinate_y, paste_coordinate_z, world, protect) values ('"
								+ nexus_id + "','" + owner_id + "','" + 1 + "','" + x + "','" + y + "','" + z + "','" + pasteX + "','" + pasteY + "','" + pasteZ + "','"
								+ world + "', '0')");
				pStatement.execute();
			} catch (SQLException e) {
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

	public boolean check(String id) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement("SELECT * FROM nexus WHERE nexus_id='" + id + "'");
			ResultSet rs = pStatement.executeQuery();
			boolean player;
			if (rs.next()) {
				player = true;
			} else {
				player = false;
			}
			rs.close();
			return player;
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
		return false;

	}

	public Nexus loadNexus(String nexus_id) {

		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			String statement = "SELECT * FROM nexus WHERE nexus_id='" + nexus_id + "'";
			pStatement = connection.prepareStatement(statement);
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				int x = rs.getInt("coordinate_x");
				int y = rs.getInt("coordinate_y");
				int z = rs.getInt("coordinate_z");
				Location loc = new Location(Bukkit.getWorld(rs.getString("world")), rs.getInt("paste_coordinate_x"), rs.getInt("paste_coordinate_y"), rs.getInt("paste_coordinate_z"));
				
				String world = rs.getString("world");
				String f = rs.getString("owner_id");
				Nexus nexus = new Nexus(600, nexus_id, loc, f, x, y, z, rs.getInt("nexus_level"), Bukkit.getWorld(world), rs.getBoolean("protect"));

				rs.close();
				return nexus;
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
		return null;
	}

	public void updateOwner(String newowner_id, String nexus_id) {
		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			String statement = "UPDATE nexus SET owner_id= '" + newowner_id + "' WHERE nexus_id='" + nexus_id + "'";
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

	public ArrayList<String> getNexuses() {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ArrayList<String> nexus = new ArrayList<String>();
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement("SELECT * FROM nexus");
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				nexus.add(rs.getString("nexus_id"));
			}
			rs.close();
			return nexus;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				Logger.INFO.log("Couldn't close DB Connection");
			}
			try {
				if (pStatement != null) {
					pStatement.close();
				}
			} catch (SQLException ex) {
				Logger.INFO.log("Couldn't close DB PreparedStatement");
			}
		}
		return nexus;
	}

	public void save(Nexus n) {
		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			String statement = "UPDATE nexus SET owner_id= '" + n.getOwner().getFactionId() + "',"
					+ " nexus_level='" + n.getLevel() + "',"
					+ " coordinate_x='" + n.getNexusLocation().getBlockX() + "',"
					+ " coordinate_y='"+ n.getNexusLocation().getBlockY() + "',"
					+ " coordinate_z='" + n.getNexusLocation().getBlockZ() + "',"
					+ " paste_coordinate_x='" + n.getPasteLocation().getBlockX() + "',"
				    + " paste_coordinate_y='"+ n.getPasteLocation().getBlockY() + "',"
				   	+ " paste_coordinate_z='"+ n.getPasteLocation().getBlockZ() + "',"
				   	+ " protect='" + getProtect(n.isProtected()) + "'"
					+ " WHERE nexus_id='" + n.getNexusId() + "'";
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement(statement);

			pStatement.execute();
		} catch (Exception e) {
			if(n.hasOwner()) {
				n.getOwner().broadcast(Messages.getInstance().getPrefix(), "Er ging iets fout! Meld bij een stafflid: SQLException");
			}
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
	
	public Location getPasteLocation(String id) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		Location loc = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement("SELECT * FROM nexus WHERE nexus_id='" + id + "'");
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				loc = new Location(Bukkit.getWorld(rs.getString("world")), rs.getInt("paste_coordinate_x"), rs.getInt("paste_coordinate_y"), rs.getInt("paste_coordinate_z"));
			}
			rs.close();
			return loc;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException ex) {
				Logger.INFO.log("Couldn't close DB Connection");
			}
			try {
				if (pStatement != null) {
					pStatement.close();
				}
			} catch (SQLException ex) {
				Logger.INFO.log("Couldn't close DB PreparedStatement");
			}
		}
		return loc;
	}
	
	
	private String getProtect(boolean protect) {
		if(protect) {
			return "1";
		} else {
			return "0";
		}
	}
}