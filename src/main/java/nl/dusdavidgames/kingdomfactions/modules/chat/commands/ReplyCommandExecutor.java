package nl.dusdavidgames.kingdomfactions.modules.chat.commands;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class ReplyCommandExecutor extends KingdomFactionsCommand {

	public ReplyCommandExecutor(String name, String permission, String info, String usage, boolean sub,
			boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	
	public static final String REPLY_KEY = "kingdomfactions.reply";
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() throws KingdomFactionsException {
		String message = "";
		for (int i = 0; i < getArgs().length; i++) {
			message += getArgs()[i] + " ";
		}
		
		if(!getPlayer().getChatProfile().mayUseMsg(message)) return;
        KingdomFactionsPlayer player = getPlayer().getChatProfile().getReplyPlayer();
        if(player == null) {
        	getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je kan op niemand reageren op dit moment!");
        	return;
        }
		player.sendPrivateMessage(getPlayer(), message);
	}

}
