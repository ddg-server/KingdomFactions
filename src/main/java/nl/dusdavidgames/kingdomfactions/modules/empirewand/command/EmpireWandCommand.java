package nl.dusdavidgames.kingdomfactions.modules.empirewand.command;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.EmpireWandModule;
import nl.dusdavidgames.kingdomfactions.modules.exception.shop.ShopException;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class EmpireWandCommand extends KingdomFactionsCommand {

	public EmpireWandCommand(String name, String permission, String info, String usage, boolean sub,
			boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		
		try {
			getPlayer().addItem(EmpireWandModule.getInstance().getEmpireWand());
		} catch (ShopException e) {
			getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt geen ruimte in je inventory!");
			return;
		}
		getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt een Empire Wand gekregen! Dit is een erg kachtig wapen! Misbruik het niet!");
		broadcast(getPlayer().getFormattedName() + ChatColor.YELLOW + " heeft zichzelf een " + ChatColor.RED + "Empire Wand" + ChatColor.YELLOW + " gegeven!");
	}

}
