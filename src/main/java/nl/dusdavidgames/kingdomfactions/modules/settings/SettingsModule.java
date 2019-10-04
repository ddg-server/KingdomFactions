package nl.dusdavidgames.kingdomfactions.modules.settings;

public class SettingsModule {

	
	public SettingsModule() {
		new SettingCommand("setting", "kingdomfactions.command.setting", "Verzet instellingen", "[args]", true, true).registerCommand();
	}
}
