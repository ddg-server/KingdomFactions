package nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.framework.AsyncStatement;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.player.PlayerLoader;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.player.player.offline.OfflineKingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

public class PlayerDatabase extends PlayerLoader {

	private static @Getter @Setter PlayerDatabase instance;

	public PlayerDatabase() {
		setInstance(this);
		setupTable();
		}

		public void setupTable() {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS playerdata"
					+ " (player_id varchar(40),"
					+ " player_name varchar(40),"
					+ " player_playtime bigint,"
					+ " player_firstjoin bigint,"
					+ " player_kingdom varchar(40),"
					+ " coins int(10), "
					+ " player_ip varchar(40),"
					+ " factionrank varchar(40),"
					+ " kingdomrank varchar(40),"
					+ " faction_id varchar(40),"
					+ " influence int(10),"
					+ " kills int(10),"
					+ " deaths int(10),"
					+ " location varchar(1000),"
					+ " isSwitch varchar(1),"
					+ " extracoins int(100))");
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

	public void createPlayer(String id, String name, String ip) {
			Connection connection = null;
			PreparedStatement pStatement = null;

			try {
				connection = MySQLModule.getInstance().datasource.getConnection();
				pStatement = connection.prepareStatement(
						"INSERT INTO playerdata ("
						+ "player_id,"
						+ " player_name,"
						+ " player_playtime,"
						+ " player_firstjoin,"
						+ " player_kingdom,"
						+ " coins, "
						+ " player_ip,"
						+ " factionrank,"
						+ " kingdomrank,"
						+ " faction_id,"
						+ " influence,"
						+ " deaths,"
						+ " kills,"
						+ " location,"
						+ " isSwitch,"
						+ " extracoins)"
						+ " values "
						+ "('"+ id + "',"
						+ "'" + name + "',"
                        + "'" + 0 + "',"
                        + "'"+System.currentTimeMillis()+"',"
                        + "'geen',"
                        + "'" + 0 + "',"
                        + " '"+ip+"',"
                        + "'speler',"
                        + "'speler',"
                        + "'geen',"
                        + "'" + 0 + "',"
                        + "'" + 0 + "',"
                        + "'" + 0 + "',"
                        + "'0-0-0-0-0-WORLD',"
                        + "'0',"
                        + "'0')");
				pStatement.execute();
				Logger.WARNING.log("Creating new Player..");
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


/**

	public void savePlayer(KingdomFactionsPlayer p) {
	   String fRank = FactionRank.getRank(p.getMembershipProfile().getFactionRank());
	   String kRank = KingdomRank.getRank(p.getMembershipProfile().getKingdomRank());
		int coins = p.getStatisticsProfile().getCoins();
		String faction = getId(p.getFaction());
		String kingdom = KingdomType.getKingdom(p.getKingdom().getType());
		long playtime = p.getStatisticsProfile().getSecondsConnected();
		int influence = p.getStatisticsProfile().getInfluence();
		Connection connection = null;
		PreparedStatement pStatement = null;
       
		try {
			String statement = "UPDATE playerdata SET player_name= "
				        	+ "'"+p.getName()+"', "
							+ "faction_id= '"+faction+"', "
							+ "player_kingdom='"+kingdom+"', "
						    + "factionrank='"+fRank+"', "
						    + "kingdomrank= '"+kRank+"', "
						    + "player_playtime= '"+playtime+"', "
						    + "coins= '"+coins+"', "
						    + "player_ip='"+p.getIpAdres()+"', "
						    + "influence= '"+influence+"', "
						    + "kills= '"+p.getStatisticsProfile().getKills()+"', " 
						    + "deaths= '"+p.getStatisticsProfile().getDeaths()+"',"
						    + "location= '"+Utils.getInstance().locationToDbString(p.getLocation())+"' " 
						    + "WHERE player_id='"+p.getUuid()+"'";
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
	*/
	

	public void savePlayer(KingdomFactionsPlayer p) {
	   String fRank = FactionRank.getRank(p.getMembershipProfile().getFactionRank());
	   String kRank = KingdomRank.getRank(p.getMembershipProfile().getKingdomRank());
		int coins = p.getStatisticsProfile().getCoins();
		String faction = getId(p.getFaction());
		String kingdom = KingdomType.getKingdom(p.getKingdom().getType());
		long playtime = p.getStatisticsProfile().getSecondsConnected();
		int influence = p.getStatisticsProfile().getInfluence();
			String statement = "UPDATE playerdata SET player_name= "
				        	+ "'"+p.getName()+"', "
							+ "faction_id= '"+faction+"', "
							+ "player_kingdom='"+kingdom+"', "
						    + "factionrank='"+fRank+"', "
						    + "kingdomrank= '"+kRank+"', "
						    + "player_playtime= '"+playtime+"', "
						    + "coins= '"+coins+"', "
						    + "player_ip='"+p.getIpAdres()+"', "
						    + "influence= '"+influence+"', "
						    + "kills= '"+p.getStatisticsProfile().getKills()+"', " 
						    + "deaths= '"+p.getStatisticsProfile().getDeaths()+"',"
						    + "location= '"+Utils.getInstance().locationToDbString(p.getLocation())+"' " 
						    + "WHERE player_id='"+p.getUuid()+"'";
			new AsyncStatement(statement);

	}
	public Integer getRegisteredPlayers() {
		int i = 0;
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement("SELECT * FROM playerdata");
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				i++;
			}
			rs.close();
			return i;
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
		return i;
	}
	
	public void remove(IPlayerBase p) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement("DELETE FROM playerdata WHERE player_id ='" + p.getUuid() + "'");

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

	
	public UUID getUuid(String name) {
		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			String statement = "SELECT * FROM playerdata WHERE player_name='"+name+"'";
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement(statement);
            ResultSet rs = pStatement.executeQuery();
			while(rs.next()) {
				return UUID.fromString(rs.getString("player_id"));
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
	

	
	public void savePlayer(OfflineKingdomFactionsPlayer p) {
		   String fRank = FactionRank.getRank(p.getFactionRank());
		   String kRank = KingdomRank.getRank(p.getKingdomRank());
			int coins = p.getCoins();
			String faction = getId(p.getFaction());
			String kingdom = KingdomType.getKingdom(p.getKingdom().getType());
			long playtime = p.getSecondsConnected();
			int influence = p.getInfluence();
			Connection connection = null;
			PreparedStatement pStatement = null;

			try {
				String statement = "UPDATE playerdata SET player_name= "
					        	+ "'"+p.getName()+"', "
								+ "faction_id= '"+faction+"', "
								+ "player_kingdom='"+kingdom+"', "
							    + "factionrank='"+fRank+"', "
							    + "kingdomrank= '"+kRank+"', "
							    + "player_playtime= '"+playtime+"', "
							    + "coins= '"+coins+"', "
							    + "player_ip='"+p.getAddress()+"', "
							    + "influence= '"+influence+"', "
							    + "kills= '"+p.getKills()+"', " 
							    + "deaths= '"+p.getDeaths()+"' " 
							    + "WHERE player_id='"+p.getUuid()+"'";
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
	
	
	
	private String getId(Faction f) {
		if(f == null) {
			return "GEEN";
		} else {
			return f.getFactionId();
		}
	}
	
	
	public void handleExtraCoins(KingdomFactionsPlayer player) {
		int coins = getExtraCoins(player.getUuid());
		if(coins == 0) {
			return;
		} else {
			player.addCoins(coins);
			player.save();
			MySQLModule.getInstance().insertQuery("UPDATE playerdata SET extracoins='0' WHERE player_id='"+player.getUuid()+"'");
		    player.sendMessage(Messages.getInstance().getPrefix() + "Je hebt " + coins + " ontvangen door jouw aankoop van coins!");
		}
	}
	
	
	private int getExtraCoins(UUID player_id) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		int coins = 0;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement("SELECT * FROM playerdata WHERE player_id='" + player_id + "'");
			ResultSet rs = pStatement.executeQuery();
			while (rs.next()) {
				coins = rs.getInt("extracoins");
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
		return coins;
	}
	}
