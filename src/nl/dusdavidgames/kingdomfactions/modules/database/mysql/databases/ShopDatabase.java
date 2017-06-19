package nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Material;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildingType;
import nl.dusdavidgames.kingdomfactions.modules.shops.shop.ShopItem;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

public class ShopDatabase {

	private static @Getter @Setter ShopDatabase instance;

	public ShopDatabase() {
		setInstance(this);
		setupTables();
	}

	public void setupTables() {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS shops (build_type VARCHAR(40), build_level VARCHAR(40), item_material VARCHAR(40), item_value INT(40), item_amount INT(40), buy_price INT(40), sell_price INT(40), item_enchantments VARCHAR(40), item_enchantment_levels VARCHAR(40), item_displayname VARCHAR(40), item_lore VARCHAR(40), item_use_displayname VARCHAR(40), `limit` INT(10), extra_data VARCHAR(50));");
			pStatement.execute();
			pStatement.close();

			pStatement = connection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS shop_limits (faction_id VARCHAR(40), item_id VARCHAR(40), amount INT(40));");
			pStatement.execute();
			pStatement.close();
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

	public ArrayList<ShopItem> loadShopItems(BuildingType buildingType, BuildLevel buildLevel) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		ArrayList<ShopItem> shopitems = new ArrayList<>();

		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			String statement = "SELECT * FROM shops WHERE build_type='" + buildingType + "' AND build_level='"
					+ buildLevel + "';";
			pStatement = connection.prepareStatement(statement);
			ResultSet result = pStatement.executeQuery();
			while (result.next()) {
				shopitems.add(new ShopItem(Material.valueOf(result.getString("item_material")),
						result.getShort("item_value"), result.getInt("item_amount"), result.getInt("buy_price"),
						result.getInt("sell_price"), result.getString("item_displayname"),
						new ArrayList<String>(Arrays.asList(result.getString("item_lore").split("!"))),
						result.getBoolean("item_use_displayname"), result.getString("item_enchantments"),
						result.getString("item_enchantment_levels"), result.getInt("limit"), result.getString("extra_data")));
			}
			result.close();
			return shopitems;

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

		return shopitems;
	}

	public void save(String type, String level, Material material, short s, int i, int buyPrice, int sellprice,
			String string, String displayname, String enchantment, String enchantmentLevels, boolean useDisplayname, String extraData, int limit) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			pStatement = connection.prepareStatement(
					"INSERT INTO shops (build_type, build_level, item_material, item_value, item_amount, buy_price, sell_price, item_enchantments, item_enchantment_levels, item_displayname, item_lore, item_use_displayname, `limit`, extra_data)"
							+ " values ('" + type + "','" + level + "','" + material + "','" + s + "','" + i + "','"
							+ buyPrice + "','" + sellprice + "','" + enchantment + "','" + enchantmentLevels + "','"
							+ displayname + "','" + string + "','" + useDisplayname + "', '" + limit + "', '" + extraData + "');");
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

	public HashMap<String, Integer> loadShopItemLimits(String factionID) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		HashMap<String, Integer> shopLimits = new HashMap<String, Integer>();

		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			String statement = "SELECT * FROM shop_limits WHERE faction_id='" + factionID + "';";
			pStatement = connection.prepareStatement(statement);
			ResultSet result = pStatement.executeQuery();
			while (result.next()) {
				shopLimits.put(result.getString("item_id"), result.getInt("amount"));
			}
			result.close();
			return shopLimits;

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

		return shopLimits;
	}

	public void save(String factionID, HashMap<String, Integer> shopLimits) {
		Connection connection = null;
		PreparedStatement pStatement = null;
		try {
			connection = MySQLModule.getInstance().datasource.getConnection();
			if (shopLimits.isEmpty())
				return;

			for (String key : shopLimits.keySet()) {

				pStatement = connection
						.prepareStatement("INSERT INTO shop_limits (faction_id, item_id, amount) VALUES ('" + factionID
								+ "', '" + key + "', '" + shopLimits.get(key)
								+ "') ON DUPLICATE KEY UPDATE shop_limits amount='" + shopLimits.get(key) + "';");
				pStatement.execute();
				pStatement.close();
			}
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