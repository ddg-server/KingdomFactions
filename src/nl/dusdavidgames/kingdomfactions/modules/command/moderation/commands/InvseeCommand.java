package nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import org.bukkit.ChatColor;

public class InvseeCommand extends KingdomFactionsCommand{



	public InvseeCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws UnkownPlayerException {
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(getSender());
		KingdomFactionsPlayer t= PlayerModule.getInstance().getPlayer(getArgs()[0]);
		
		   if(t != null) {
			   if(t == p) {
					p.sendMessage(Messages.getInstance().getPrefix() + "Klik op 'e' om je inventory te bekijken! =) ");
					return;
				}
			    p.getPlayer().closeInventory();
			   	p.getPlayer().openInventory(t.getPlayer().getInventory());
			   
		   broadcast(p.getFormattedName()  + ChatColor.YELLOW + " bekijkt de inventory van "  + t.getFormattedName());
		   } else {
			   p.sendMessage(Messages.getInstance().getPrefix() + "Deze speler is niet online!");
		   }

		}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}


}
