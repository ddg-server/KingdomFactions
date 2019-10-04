package nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings.Building;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildingType;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

@Slf4j
public class BuildingDatabase {

    private static @Getter
    @Setter
    BuildingDatabase instance;

    public BuildingDatabase() {
        setInstance(this);
        setupTable();
    }

    public void setupTable() {
        try (Connection connection = MySQLModule.getInstance().datasource.getConnection();
             PreparedStatement ps = connection.prepareStatement("CREATE TABLE IF NOT EXISTS building ( " +
                     "nexus_id VARCHAR(40), " +
                     "building VARCHAR(40), " +
                     "level INT(10), " +
                     "x_coordinate INT(40), " +
                     "y_coordinate INT(40), " +
                     "z_coordinate INT(40), " +
                     "world VARCHAR(40), " +
                     "x_paste INT(10), " +
                     "y_paste INT(10), " +
                     "z_paste INT(10), " +
                     "world_paste VARCHAR(40)" +
                     ")")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Error with connecting to database", e);
        }
    }

    public void createBuilding(String nexus_id, BuildingType building, int level, Location loc,
                               Location pasteLocation) {
        int x_coord = loc.getBlockX();
        int y_coord = loc.getBlockY();
        int z_coord = loc.getBlockZ();
        int x_paste = pasteLocation.getBlockX();
        int y_paste = pasteLocation.getBlockY();
        int z_paste = pasteLocation.getBlockZ();
        String coord = loc.getWorld().getName();
        String paste = pasteLocation.getWorld().getName();
        if (!check(nexus_id, building)) {
            Connection connection = null;
            PreparedStatement pStatement = null;

            try {
                connection = MySQLModule.getInstance().datasource.getConnection();
                pStatement = connection.prepareStatement(
                        "INSERT INTO building (nexus_id, building, level, x_coordinate, y_coordinate, z_coordinate, world, x_paste, y_paste, z_paste, world_paste) values "
                                + "('"
                                + nexus_id + "','" + building.toString() + "','" + level + "','" + x_coord + "','"
                                + y_coord + "','" + z_coord + "','" + coord + "','" + x_paste + "','" + y_paste + "','"
                                + z_paste + "','" + paste + "')");
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
    }

    public boolean check(String id, BuildingType b) {
        try (Connection connection = MySQLModule.getInstance().datasource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM building WHERE nexus_id = ? AND building = ?;")) {
            ps.setString(0, id);
            ps.setString(1, b.toString());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            log.warn("Something went wrong on checking player to the database @ Database:check", e);
            return false;
        }
    }

    public Building loadBuilding(Nexus n, BuildingType b) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = MySQLModule.getInstance().datasource.getConnection();
            String statement = "SELECT * FROM building WHERE nexus_id='" + n.getNexusId() + "' AND building= '" + b.toString()
                    + "'";
            pStatement = connection.prepareStatement(statement);
            ResultSet rs = pStatement.executeQuery();
            while (rs.next()) {
                int x = rs.getInt("x_coordinate");
                int y = rs.getInt("y_coordinate");
                int z = rs.getInt("z_coordinate");

                int paste_x = rs.getInt("x_paste");
                int paste_y = rs.getInt("y_paste");
                int paste_z = rs.getInt("z_paste");
                String paste = rs.getString("world_paste");
                String world = rs.getString("world");
                Location loc = new Location(Bukkit.getWorld(world), x, y, z);
                Location pasteLoc = new Location(Bukkit.getWorld(paste), paste_x, paste_y, paste_z);
                Logger.DEBUG.log(Utils.getInstance().getLocationString(loc) + " <--- Location");
                Logger.DEBUG.log(Utils.getInstance().getLocationString(pasteLoc) + "<--- Paste loc");
                Building building = new Building(loc, pasteLoc, b,
                        n,
                        BuildLevel.getLevel(rs.getInt("level")));
                rs.close();
                n.getBuildings().add(building);
                return building;
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

    public void save(Building b) {

        Connection connection = null;
        PreparedStatement pStatement = null;

        int x = b.getLocation().getBlockX();
        int y = b.getLocation().getBlockY();
        int z = b.getLocation().getBlockZ();
        try {
            String statement = "UPDATE building" + " SET x_coordinate='" + x + "', y_coordinate='" + y + "', z_coordinate='" + z + "', level= '" + b.getLevel().getLevel() + "' WHERE nexus_id='"
                    + b.getNexus().getNexusId() + "' AND building= '" + b.getType().toString() + "'";
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


}
