package nl.dusdavidgames.kingdomfactions.modules.coins.command;


import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.exception.value.ValueException;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class PayCommand extends KingdomFactionsCommand{



	public PayCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() throws UnkownPlayerException {
		KingdomFactionsPlayer t = PlayerModule.getInstance().getPlayer(getArgs()[0]);
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(getSender().getName());
		if(t!=null) {
			if(Integer.valueOf(getArgs()[1]) > 0) {
				if(p.getStatisticsProfile().canAffordCoins(Integer.valueOf(getArgs()[1]))) {
			try {
				p.getStatisticsProfile().removeCoins(Integer.valueOf(getArgs()[1]), false);
			} catch (NumberFormatException e) {
				p.sendMessage(Messages.getInstance().getPrefix() + "Gelieve een correct geval invullen!");
			} catch (ValueException e) {
				p.sendMessage(Messages.getInstance().getPrefix() + "Je hebt niet genoeg coins voor deze actie!");
			}
			t.getStatisticsProfile().addCoins(Integer.valueOf(getArgs()[1]));
			p.sendMessage(Messages.getInstance().getPrefix() + "Je hebt " + Integer.valueOf(getArgs()[1]) + " coins aan " + t.getName() + " gegeven!");
			t.sendMessage(Messages.getInstance().getPrefix() + "Je hebt " + Integer.valueOf(getArgs()[1]) + " coins van " + p.getName() + " gekregen!");
			} else {
				p.sendMessage(Messages.getInstance().getPrefix() + "Je hebt niet genoeg coins voor deze actie!");
			}
		} else {
			p.sendMessage(Messages.getInstance().getPrefix() + "Je moet een positief getal invullen!");
		}
		}else {
			p.sendMessage(Messages.getInstance().getPrefix() + "Deze speler is niet online!");
		}

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	
	

}
