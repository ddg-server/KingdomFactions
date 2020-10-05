package nl.dusdavidgames.kingdomfactions.modules.influence;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.SubCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.exception.value.ValueException;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import org.bukkit.command.ConsoleCommandSender;

public class InfluenceCommand extends KingdomFactionsCommand {

	public InfluenceCommand(String name, String permission, String info, String usage, boolean sub,
			boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		this.registerSub(
				new SubCommand("info", "kingdomfactions.command.influence.check", "Bekijk jouw eigen influence!") {

					@Override
					public void execute(String[] args) {
						if (getSender() instanceof ConsoleCommandSender) {
							getSender().sendMessage(Messages.getInstance().getPrefix()
									+ "Dit commando kan niet worden uitgevoerd door de console!");
							return;

						}
						KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(getSender());
						p.sendMessage(Messages.getInstance().getPrefix() + "Je hebt momenteel " + ChatColor.RED
								+ p.getStatisticsProfile().getInfluence() + ChatColor.WHITE + " influence.");

					}
				});
		this.registerSub(new SubCommand("add", "kingdomfactions.command.influence.add",
				"Voeg Influence toe aan een gebruiker!") {

			@Override
			public void execute(String[] args) throws UnkownPlayerException {
				IPlayerBase player = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);


				try {
					player.addInfluence(Integer.valueOf(getArgs()[2]));
					getSender()
							.sendMessage(Messages.getInstance().getPrefix() + "Je hebt " + Integer.valueOf(getArgs()[2])
									+ " influence toegevoegd aan " + player.getName() + "'s account");

				} catch (NumberFormatException e) {
					getSender().sendMessage(Messages.getInstance().getPrefix() + "Gebruik een geldig getal!");
				} catch(Exception e) {
					e.printStackTrace();
				}

			}
		});
		this.registerSub(new SubCommand("remove", "kingdomfactions.command.influence.remove",
				"Verwijder influence van een gebruiker's account") {

			@Override
			public void execute(String[] args) throws UnkownPlayerException {
		
				IPlayerBase player = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);
				try {
					player.removeInfluence(Integer.valueOf(getArgs()[2]), true);
					getSender()
							.sendMessage(Messages.getInstance().getPrefix() + "Je hebt " + Integer.valueOf(getArgs()[2])
									+ " influence toegevoegd aan " + player.getName() + "'s account");

				} catch (NumberFormatException e) {
					getSender().sendMessage(Messages.getInstance().getPrefix() + "Gebruik een geldig getal!");
				} catch (ValueException e) {
                 
				} catch(Exception e) {
					e.printStackTrace();
				}

			}

		});
		this.registerSub(
				new SubCommand("ignore", "kingdomfactions.command.influence.ignore", "Negeer Influence kosten") {

					@Override
					public void execute(String[] args) {

						if (getSender() instanceof ConsoleCommandSender) {
							getSender().sendMessage(Messages.getInstance().getPrefix()
									+ "Dit commando kan niet worden uitgevoerd door de console!");
							return;

						}
						KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(getSender());
						if (p.getSettingsProfile().hasIgnoreInfluence()) {
							p.getSettingsProfile().setIgnoreInfluence(false);
							p.sendMessage(Messages.getInstance().getPrefix()
									+ "Je bent gestopt met het negeren van influence kosten.");
							broadcast(p.getFormattedName() + ChatColor.YELLOW
									+ " is gestopt met het negeren van influence kosten");
							
						} else {
							p.getSettingsProfile().setIgnoreInfluence(true);
							p.sendMessage(Messages.getInstance().getPrefix() + "Je negeert nu influence kosten.");
							broadcast(p.getFormattedName() + ChatColor.YELLOW + " negeert nu Influence kosten");
						}

					}
				});
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
