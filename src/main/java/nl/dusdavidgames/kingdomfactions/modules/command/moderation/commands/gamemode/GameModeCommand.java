package nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands.gamemode;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class GameModeCommand extends KingdomFactionsCommand {

	public GameModeCommand(String name, String permission, String info, String usage, boolean sub,
			boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws UnkownPlayerException {
		if (getArgs().length == 0) {
			getSender().sendMessage(ChatColor.RED + "Verkeerde argumenten!");
			return;
		}
		if (getArgs().length == 1) {
			KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(getSender().getName());
			GameMode c = KingdomFactionsGameMode
					.getBukkitGameMode(KingdomFactionsGameMode.getGamemodeOfString(getArgs()[0]));
			p.setGameMode(c);
			p.sendMessage(Messages.getInstance().getPrefix() + "Jij hebt jouw gamemode gezet naar " + c.toString().toLowerCase() + "!");
			broadcast(p.getFormattedName() + ChatColor.YELLOW + " heeft zijn gamemode naar " + c.toString().toLowerCase() + " gezet!");
		} else {
			if (getSender().hasPermission("kingdomfactions.command.gamemode.others")) {
				KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(getSender().getName());
				KingdomFactionsPlayer t = PlayerModule.getInstance().getPlayer(getArgs()[0]);
				if (t != null) {
					GameMode c = KingdomFactionsGameMode
							.getBukkitGameMode(KingdomFactionsGameMode.getGamemodeOfString(getArgs()[1]));
					t.setGameMode(c);
					p.sendMessage(Messages.getInstance().getPrefix() + "Jij hebt "+t.getName()+"'s gamemode gezet naar " + c + "!");
					broadcast(p.getFormattedName() + ChatColor.YELLOW + " heeft " + t.getFormattedName()
							+ ChatColor.YELLOW + "'s gamemode naar " + c.toString().toLowerCase() + " gezet!");
				} else {
					getSender().sendMessage(Messages.getInstance().getPrefix() + "Deze speler is offline!");
				}
			} else {
				getSender().sendMessage(ChatColor.RED + "Je hebt onvoldoende rechten!");
			}
		}

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

}
