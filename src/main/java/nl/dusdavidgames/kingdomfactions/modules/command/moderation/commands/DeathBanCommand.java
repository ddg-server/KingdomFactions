package nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.SubCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class DeathBanCommand extends KingdomFactionsCommand{

	public DeathBanCommand(String name, String permission, String info, String usage, boolean sub,
			boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		this.registerSub(new SubCommand("check", "kingdomfactions.command.deathban.check", "Controleer of een gebruiker een DeathBan heeft.") {
			
			@Override
			public void execute(String[] args) {
				IPlayerBase other = null;
				try {
					other = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);
				} catch (UnkownPlayerException e) {
				   getSender().sendMessage(Messages.getInstance().getPrefix() + "Kon deze speler niet vinden.");
				}
				if(other.hasDeathBan()) {
					getSender().sendMessage(Messages.getInstance().getPrefix() + "--------------------");
					getSender().sendMessage(ChatColor.WHITE + "Naam: " + other.getName());
					getSender().sendMessage(ChatColor.WHITE + "Kingdom: " + other.getKingdom().getType().getPrefix());
					getSender().sendMessage(ChatColor.WHITE + "Tijd te gaan: " + other.getDeathBan().getTime());
					getSender().sendMessage(Messages.getInstance().getPrefix() + "--------------------");
				} else {
					getSender().sendMessage(Messages.getInstance().getPrefix() + other.getName() + " heeft geen DeathBan!");
				}
				
			}
		});
		this.registerSub(new SubCommand("unban", "kingdomfactions.command.deathban.unban", "Verwijder een speler's deathban") {
			
			@Override
			public void execute(String[] args) {
				IPlayerBase other = null;
				try {
					other = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);
				} catch (UnkownPlayerException e) {
					   getSender().sendMessage(Messages.getInstance().getPrefix() + "Kon deze speler niet vinden.");
				}
				if(other.hasDeathBan()) {
					getSender().sendMessage(Messages.getInstance().getPrefix() + "Je hebt de DeathBan van " + other.getName() + " verwijderd!");
					other.getDeathBan().unban();
				} else {
					getSender().sendMessage(Messages.getInstance().getPrefix() + other.getName() + " heeft geen DeathBan!");
				}
			}
		});
		
	}

	@Override
	public void execute() {
		return;
	}

	
	
	

}
