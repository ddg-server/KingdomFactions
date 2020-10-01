package nl.dusdavidgames.kingdomfactions.modules.coins.command;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.SubCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.exception.value.ValueException;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.player.player.offline.OfflineKingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import org.bukkit.ChatColor;

public class CoinsCommand extends KingdomFactionsCommand {

	public CoinsCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		this.registerSub(new SubCommand("add", "kingdomfactions.command.coins.add",
				"Voeg coins toe aan een gebruiker's account!") {

			@Override
			public void execute(String[] args) {
				IPlayerBase player = null;
				try {
					player = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);
				} catch (UnkownPlayerException e) {
					getSender().sendMessage(Messages.getInstance().getPrefix() + "Kon deze speler niet vinden!");
					return;
				}
				try {
					if(player instanceof KingdomFactionsPlayer){
						int coins = Integer.parseInt(getArgs()[2]);
					player.addCoins(coins);
						getSender().sendMessage(Messages.getInstance().getPrefix() + "Je hebt " + ChatColor.RED + coins + ChatColor.WHITE + " coins toegevoegd aan " + player.getName() + " zijn/haar account!" );
					}else{
						int coins = Integer.parseInt(getArgs()[2]);
						((OfflineKingdomFactionsPlayer) player).edit().addCoins(coins);
						getSender().sendMessage(Messages.getInstance().getPrefix() + "Je hebt " + ChatColor.RED + coins + ChatColor.WHITE + " coins toegevoegd aan " + player.getName() + " zijn/haar account! (Offline account)" );
					}
				} catch (NumberFormatException e) {
					getSender().sendMessage(Messages.getInstance().getPrefix() + "Gelieve een geldig bedrag invullen!");
				}

			}
		});
		this.registerSub(new SubCommand("remove", "kingdomfactions.command.coins.remove",
				"Verwijder coins van een gebruiker's account!") {

			@Override
			public void execute(String[] args) {
				IPlayerBase player = null;
				try {
					player = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);
				} catch (UnkownPlayerException e) {
					getSender().sendMessage(Messages.getInstance().getPrefix() + "Kon deze speler niet vinden!");
					return;
				}
					try {
						if(player instanceof KingdomFactionsPlayer){
						int coins = Integer.parseInt(getArgs()[2]);
						player.removeCoins(coins, true);
						getSender().sendMessage(Messages.getInstance().getPrefix() + "Je hebt " + ChatColor.RED + coins + ChatColor.WHITE + " coins weggehaald van " + player.getName() + " zijn/haar account!" );
					}else{
						int coins = Integer.parseInt(getArgs()[2]);
						player.removeCoins(coins, true);
						getSender().sendMessage(Messages.getInstance().getPrefix() + "Je hebt " + ChatColor.RED + coins + ChatColor.WHITE + " coins weggehaald van " + player.getName() + " zijn/haar account! (Offline account)" );
			//		    broadcast(message);
					}
				} catch (NumberFormatException e) {
					getSender().sendMessage(Messages.getInstance().getPrefix() + "Gelieve een geldig bedrag invullen!");
				} catch (ValueException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	@Override
	public void execute() {
		return;
	}

}
