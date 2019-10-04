package nl.dusdavidgames.kingdomfactions.modules.command;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.command.administration.AdminCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.help.HelpCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.Moderation;
import nl.dusdavidgames.kingdomfactions.modules.command.spawn.SpawnCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.time.TimeCommand;

public class CommandModule {

	private static @Getter @Setter CommandModule instance;
	private @Getter ArrayList<KingdomFactionsCommand> command = new ArrayList<KingdomFactionsCommand>();

	public CommandModule() {
		setInstance(this);
		new Moderation();
		new HelpCommand("help", "kingdomfactions.command.help", "Vraag een lijst of van alle commando's", "", false,
				false).registerCommand();
		new TimeCommand("time", "kingdomfactions.command.time", "Vraag de speeltijd op van een speler!", "", false,
				false).registerCommand();
		new AdminCommand("admin", "kingdomfactions.command.admin", "Admin commando's", "", true, true).registerCommand();
	    new SpawnCommand("spawn", "kingdomfactions.command.spawn", "Teleporteer naar de spawn van jouw Kingdom", "", false, false).registerCommand();
	}

}
