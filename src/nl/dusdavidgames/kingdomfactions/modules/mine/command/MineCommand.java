package nl.dusdavidgames.kingdomfactions.modules.mine.command;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class MineCommand extends KingdomFactionsCommand{

	public MineCommand(String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super("setmine", permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		Kingdom k = KingdomModule.getInstance().getKingdom(getArgs()[0]);
		if(k == null) {
			getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Ongeldig Kingdom!");
		}
		k.setMiningSpawn(getPlayer().getLocation());
		getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt de Mining Spawn van " + k.getType().getPrefix() + ChatColor.WHITE +" gezet!");
	}

}
