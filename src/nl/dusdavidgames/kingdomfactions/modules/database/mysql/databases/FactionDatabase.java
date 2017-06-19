package nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import nl.dusdavidgames.kingdomfactions.modules.exception.faction.UnkownFactionException;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

public class FactionDatabase {

	private static @Getter @Setter FactionDatabase instance;

	public FactionDatabase() {
		setInstance(this);
		setupTable();
	}

	public void setupTable() {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS faction (faction_name VARCHAR(40), faction_id VARCHAR(40), style VARCHAR(40))");
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

	public void createFaction(String faction_id, String faction_name, KingdomType style) {
			Connection connection = null;
			PreparedStatement pStatement = null;

			try {
				connection = MySQLModule.getInstance().datasource.getConnection();
				pStatement = connection.prepareStatement("INSERT INTO faction (faction_name, faction_id, style) values ('"+faction_name+"','"+faction_id+"','"+style+"')");
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
	
	public Faction loadFaction(String faction_id) throws UnkownFactionException {
		Connection connection = null;
		PreparedStatement pStatement = null;
        Faction faction = null;
		try {
			String statement = "SELECT * FROM faction WHERE faction_id= '"+faction_id+"'";
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement(statement);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()) {
				String faction_name = rs.getString("faction_name");
				KingdomType style = KingdomType.getKingdom(rs.getString("style"));
				faction = new Faction(faction_id, faction_name, style);
			
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
		if(faction == null) {
			throw new UnkownFactionException("Could not load faction with factionId " + faction_id);
		}
		return faction;
		
	}
	
	public ArrayList<String> getFactions() {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ArrayList<String> temp = new ArrayList<String>();

		try {
			String statement = "SELECT * FROM faction";
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement(statement);
			ResultSet rs = pStatement.executeQuery();
			while(rs.next()) {
		        
				temp.add(rs.getString("faction_id"));
				
			}
			return temp;
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
	


	}

