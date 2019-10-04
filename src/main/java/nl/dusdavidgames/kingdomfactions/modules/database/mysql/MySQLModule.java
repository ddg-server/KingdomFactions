package nl.dusdavidgames.kingdomfactions.modules.database.mysql;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import com.zaxxer.hikari.HikariDataSource;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.configuration.ConfigModule;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.BuildingDatabase;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.FactionDatabase;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.FactionHomeDatabase;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.KingdomDatabase;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.NexusDatabase;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.PlayerDatabase;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.ShopDatabase;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.ShopLogDatabase;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

public class MySQLModule {

	private final String DB_URL;
	private final String USERNAME;
	private final String PASSWORD;
	private String DB_NAME = ConfigModule.getInstance().getFile(ConfigModule.CONFIG).getConfig()
			.getString("Database.MySQL.name");
	public @Getter HikariDataSource datasource;

	private static @Getter @Setter MySQLModule instance;

	public MySQLModule() {
		setInstance(this);
		YamlConfiguration bukkitYml = YamlConfiguration
				.loadConfiguration(new File(Bukkit.getWorldContainer().getAbsoluteFile(), "bukkit.yml"));
		DB_URL = bukkitYml.getString("database.url").replace("{NAME}", DB_NAME);
		USERNAME = bukkitYml.getString("database.username");
		PASSWORD = bukkitYml.getString("database.password");
		datasource = new HikariDataSource();
		datasource.setMaximumPoolSize(25);
		datasource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		datasource.addDataSourceProperty("url", DB_URL);
		datasource.addDataSourceProperty("port", 3306);
		datasource.addDataSourceProperty("databaseName", DB_NAME);
		datasource.addDataSourceProperty("user", USERNAME);
		datasource.addDataSourceProperty("password", PASSWORD);
		insertQuery("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
		new PlayerDatabase();
		new FactionDatabase();
		new NexusDatabase();
		new KingdomDatabase();
		new BuildingDatabase();
		new FactionHomeDatabase();
		
		new ShopDatabase();
		new ShopLogDatabase();
	}

	public void closeConnection() {
		if (datasource != null) {
			datasource.close();
		} else {
			Logger.WARNING.log("Datascoure was not closed!");
		}
	}

	public void insertQuery(String query) {

		Connection connection = null;
		PreparedStatement pStatement = null;

		try {
			connection = datasource.getConnection();
			pStatement = connection.prepareStatement(query);
			pStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			Logger.WARNING.log("Something went wrong on inserting Query @ Database:insertQuery");
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
