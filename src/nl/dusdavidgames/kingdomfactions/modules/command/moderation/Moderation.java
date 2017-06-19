package nl.dusdavidgames.kingdomfactions.modules.command.moderation;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.BroadcastCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.DeathBanCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.EnderseeCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.FlyCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.GodmodeCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.InfoCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.InvseeCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.LagCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.ListCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.NightVisionCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.SayCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.SpeedCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.TeleportationCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.TphereCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.gamemode.GameModeCommand;

public class Moderation {

	private static @Getter @Setter Moderation instance;
	public Moderation() {
		setInstance(this);
		new GameModeCommand("gamemode", "kingdomfactions.command.gamemode", "Verander jouw gamemode", "[gamemode]", false, false).registerCommand();;
		new BroadcastCommand("broadcast", "kingdomfactions.command.broadcast","Broadcast een bericht over de server!" , "[bericht]", false, true).registerCommand();
	    new DeathBanCommand("deathban", "kingdomfactions.command.deathban", "Main commando voor DeathBans!", "[sub]", true, true).registerCommand();
	    new EnderseeCommand("endersee", "kingdomfactions.command.endersee", "Bekijk een gebruiker's enderchest", "[gebruiker]", false, false).registerCommand();
	    new FlyCommand("fly", "kingdomfactions.command.fly", "Zet Fly mode aan/uit", "", false, false).registerCommand();
	    new GodmodeCommand("godmode", "kingdomfactions.command.godmode", "Zet GodMode aan/uit", "[mode]", false, false).registerCommand();
	    new InvseeCommand("invsee", "kingdomfactions.command.invsee", "Bekijk de inventory van een speler!", "", false, false).registerCommand();
	    new ListCommand("list", "kingdomfactions.command.list", "Krijg een lijst met Speler informatie over Kingdom Factions", "", false, true).registerCommand();
	    new NightVisionCommand("nightvision", "kingdomfactions.command.nightvision", "Zet NightVision aan/uit", "", false, false).registerCommand();
	    new SayCommand("say", "kingdomfactions.command.say", "Zeg iets over de server!", "[bericht]", false, true).registerCommand();
	    new SpeedCommand("speed", "kingdomfactions.command.speed", "Verander je snelheid", "[snelheid]", false, false).registerCommand();
	    new TeleportationCommand("tp", "kingdomfactions.command.teleport", "Teleporteer naar een gebruiker", "[gebruiker]", false, false).registerCommand();
	    new TphereCommand("tphere", "kingdomfactions.command.tphere", "Teleporteer een gebruiker naar jouw locatie", "[gebruiker]", false, false).registerCommand();
	    new LagCommand("lag", "kingdomfactions.command.lag", "Displays lag related information", "", false, true).registerCommand();
        new InfoCommand("informatie", "kingdomfactions.command.info", "Verkrijg informatie over een speler!", "[speler]", false, true).registerCommand();
	}
}
