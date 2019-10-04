package nl.dusdavidgames.kingdomfactions.modules.chat.commands;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.ChatProfile;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class MsgCommandExecutor extends KingdomFactionsCommand {

	public MsgCommandExecutor(String name, String permission, String info, String usage, boolean sub,
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
	KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(getArgs()[0]);
	if(player == null) {
		getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Onbekende speler!");
		return;
	}
	if(player.getKingdom() != getPlayer().getKingdom() && !getPlayer().isStaff() ) {
		getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je kan alleen privé berichten sturen naar spelers in jouw kingdom!");
		return;
	}
	String message = "";
	for (int i = 1; i < getArgs().length; i++) {
		message += getArgs()[i] + " ";
	}
	ChatProfile profile = player.getChatProfile();
	if(!getPlayer().getChatProfile().mayUseMsg(message)) return;
	profile.setReplyPlayer(getPlayer());
	
	
	
	
	player.sendPrivateMessage(getPlayer(), message);
		
	}


}
