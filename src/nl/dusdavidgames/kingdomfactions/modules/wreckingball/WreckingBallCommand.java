package nl.dusdavidgames.kingdomfactions.modules.wreckingball;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import org.bukkit.inventory.PlayerInventory;

public class WreckingBallCommand extends KingdomFactionsCommand{

	public WreckingBallCommand(String name, String permission, String info, String usage, boolean sub,
			boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() throws KingdomFactionsException {
	
		PlayerInventory i = getPlayer().getInventory();
		
		if(i.contains(WreckingBallModule.getInstance().getWreckingBall())) {
			getPlayer().sendMessage(Messages.getInstance().getPrefix() + ChatColor.RED + "Je hebt al een WreckingBall!");
			return;
		}
		getPlayer().addItem(WreckingBallModule.getInstance().getWreckingBall());
		getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt een WreckingBall gekregen! Dit item is alleen voor staffleden, het is niet toegestaan om dit item te delen met spelers.");
		return;
	}
}
