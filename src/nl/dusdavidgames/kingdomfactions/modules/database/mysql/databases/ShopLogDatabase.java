package nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import nl.dusdavidgames.kingdomfactions.modules.shops.shoplogger.ShopLog;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ShopLogDatabase {

	private static @Getter @Setter ShopLogDatabase instance;

	public ShopLogDatabase() {
		setInstance(this);
		setupTable();
	}
	
	/**
	 * DATASOURCE MUST BE REPLACED WITH SHOPLOGGER <---
	 */

	public void setupTable() {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS shoplogs (playername VARCHAR(40), offline_uuid VARCHAR(40), item VARCHAR(40), value INT(40), amount INT(40), action VARCHAR(40), coins INT(40), time VARCHAR(40))");
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

	public void save(ShopLog shopLog) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		String statement = "INSERT INTO shoplogs (playername, offline_uuid, item, value, amount, action, coins, time) values ('"
				+ shopLog.getPlayerName() + "','" + shopLog.getOfflineUUID() + "','"
				+ shopLog.getShopItem().getItem().getType() + "','" + shopLog.getShopItem().getItem().getDurability()
				+ "','" + shopLog.getShopItem().getItem().getAmount() + "','" + shopLog.getShopAction() + "','"
				+ shopLog.getCoins() + "','" + shopLog.getDate() + "');";


		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement(statement);
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
}