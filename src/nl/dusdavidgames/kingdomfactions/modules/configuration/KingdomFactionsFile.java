package nl.dusdavidgames.kingdomfactions.modules.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.data.DataManager;
import nl.dusdavidgames.kingdomfactions.modules.data.types.ArrayData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.BooleanData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.IntegerData;
import nl.dusdavidgames.kingdomfactions.modules.data.types.StringData;
import nl.dusdavidgames.kingdomfactions.modules.exception.DataException;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.DataType;

public class KingdomFactionsFile {

	private @Getter String name;
	private File file;
	private FileConfiguration config;

	public KingdomFactionsFile(String name) {
		ConfigModule.getInstance().getFiles().add(this);
		this.name = name;
		file = new File(KingdomFactionsPlugin.getInstance().getDataFolder(), name);
		this.config = YamlConfiguration.loadConfiguration(file);
		if (this.file.exists()) {
			try {
				this.file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createConfigPath(String path, ArrayList<String> value) {
		if (!config.contains(path)) {
			config.set(path, value);

		}
		prepareData(path, config, DataType.LIST);
	}

	public void createConfigPath(String path, boolean value) {

		if (!config.contains(path)) {
			config.set(path, value);

		}
		prepareData(path, config, DataType.BOOLEAN);
	}

	public void createConfigPath(String path, int value) {

		if (!config.contains(path)) {
			config.set(path, value);

		}
		prepareData(path, config, DataType.INT);
	}

	public void createConfigPath(String path, String value) {

		if (!config.contains(path)) {
			config.set(path, value);

		}
		prepareData(path, config, DataType.STRING);
	}

	public void prepareData(String path, FileConfiguration config, DataType type) {
		DataManager manager = KingdomFactionsPlugin.getInstance().getDataManager();
		switch (type) {
		case BOOLEAN:
			try {
				manager.addData(new BooleanData(path, config.getBoolean(path)));
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case INT:
			try {
				manager.addData(new IntegerData(path, config.getInt(path)));
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case STRING:
			try {
				manager.addData(new StringData(path, config.getString(path)));
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case LIST:
			try { 
				manager.addData(new ArrayData(path, (ArrayList<String>) config.getStringList(path)));
			} catch (DataException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		default:
			break;

		}
	}

}
