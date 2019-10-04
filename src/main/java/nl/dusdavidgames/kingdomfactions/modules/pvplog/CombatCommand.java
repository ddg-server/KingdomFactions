package nl.dusdavidgames.kingdomfactions.modules.pvplog;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.SubCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.CombatTracker;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class CombatCommand extends KingdomFactionsCommand {

	public CombatCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		this.registerSub(
				new SubCommand("check", "kingdomfactions.command.combat.check", "Controleer of jij in gevecht bent!") {

					@Override
					public void execute(String[] args) throws KingdomFactionsException {

						if (getArgs().length == 1) {
							if (getPlayer().hasPermission("kingdomfactions.command.combat.check.others")) {
								CombatTracker other = PlayerModule.getInstance().getPlayer(getArgs()[1])
										.getCombatTracker();
								if (other.isInCombat()) {
									getPlayer().sendMessage(
											Messages.getInstance().getPrefix() + other.getPlayer().getName()
													+ " is in gevecht! CombatTag tijd: " + other.getCombatSeconds());
								} else {
									getPlayer().sendMessage(Messages.getInstance().getPrefix()
											+ other.getPlayer().getName() + " is niet ingevecht");
								}
							} else {
								getPlayer().sendMessage(Messages.getInstance().getNoPerm());
							}
						} else {
							CombatTracker player = getPlayer().getCombatTracker();

							if (player.isInCombat()) {
								getPlayer().sendMessage(Messages.getInstance().getPrefix()
										+ "Je bent in gevecht! CombatTag seconden: " + player.getCombatSeconds());
							} else {
								getPlayer().sendMessage(
										Messages.getInstance().getPrefix() + "Je bent momenteel niet in gevecht.");
							}
						}

					}
				});
		
		this.registerSub(new SubCommand("untag", "kingdomfactions.command.combat.untag", "Ga uit een gevecht.") {
			
			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				
				CombatTracker c = getPlayer().getCombatTracker();
				
			if(c.isInCombat()) {
				c.setCombatSeconds(0);
				c.setInCombat(false);
				getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je bent het gevecht verlaten.");
				broadcast(getPlayer().getFormattedName() + ChatColor.YELLOW + " heeft een gevecht verlaten!");
			} else {
				getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je bent niet ingevecht.");
			}
				
			}
		});
	}

	@Override
	public void execute() throws KingdomFactionsException {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * 
	 * if(args.length == 0) { CombatTracker c = getPlayer().getCombatTracker();
	 * if(c.isInCombat()) {
	 * getPlayer().sendMessage(Messages.getInstance().getPrefix() +
	 * "Je bent nog " + c.getCombatSeconds() +
	 * " seconden in gevecht. Let op! Dit kan langer duren, als je weer geslagen word!"
	 * ); } else { getPlayer().sendMessage(Messages.getInstance().getPrefix() +
	 * "Je bent momenteel niet in gevecht."); } } else {
	 */
}
