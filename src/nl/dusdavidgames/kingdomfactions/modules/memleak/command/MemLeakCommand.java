package nl.dusdavidgames.kingdomfactions.modules.memleak.command;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.SubCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.memleak.MemLeakMessage;

public class MemLeakCommand extends KingdomFactionsCommand {

	public MemLeakCommand(String name, String permission, String info, String usage, boolean sub,
			boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
	}

	@Override
	public void init() {
		this.registerSub(new SubCommand("display", "kingdomfactions.command.data.get", "get data!") {
			@Override
			public void execute(String[] args) {
				MemLeakMessage.getInstance().sendMessage(getSender());
			}
		});
	}

	@Override
	public void execute() throws KingdomFactionsException {
		// TODO Auto-generated method stub
	}
}