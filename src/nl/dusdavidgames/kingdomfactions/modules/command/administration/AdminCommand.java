package nl.dusdavidgames.kingdomfactions.modules.command.administration;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.SubCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoConfirmation;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoListener;

public class AdminCommand extends KingdomFactionsCommand {

	public AdminCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		return;
	}

	@Override
	public void init() {
		this.registerSub(new SubCommand("build", "kingdomfactions.command.admin.build",
				"Zet Bouw mode aan/uit. Let op! Je kan dan overal bouwen.", false) {
                  
			@Override
			public void execute(String[] args) {
				this.setAllowConsole(false);
				KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(getSender());
				if (!player.getSettingsProfile().hasAdminMode()) {
					player.getSettingsProfile().setAdminMode(true);
					player.sendMessage(Messages.getInstance().getPrefix() + "Je hebt Bouw Mode aangezet!");
				} else {
					player.getSettingsProfile().setAdminMode(false);
					player.sendMessage(Messages.getInstance().getPrefix() + "Je hebt Bouw Mode uitgezet!");
				}
			}
		
		});
	
		this.registerSub(new SubCommand("purge", "kingdomfactions.command.admin.purge", "Purge een speler") {

			@Override
			public void execute(String[] args) throws UnkownPlayerException {
				IPlayerBase base = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);
				if(getUser().isConsole()) {
					broadcast(getUser().getFormattedName() + ChatColor.YELLOW + " heeft de spelerdata van " + base.getFormattedName() +ChatColor.YELLOW+" verwijderd!");
					base.purge();
					return;
				}
				KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(getSender());
				new YesNoConfirmation(player, ChatColor.RED + "Weet je zeker dat je deze speler wil verwijderen?",
						Utils.getInstance().getLore(ChatColor.GREEN + "Deze actie kan niet terug gedraait worden!"),
						Utils.getInstance().getLore(ChatColor.RED + "Annuleer de actie"), new YesNoListener() {
							
							@Override
							public void onDeny(KingdomFactionsPlayer player) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onClose(KingdomFactionsPlayer player) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onAgree(KingdomFactionsPlayer player) {
								broadcast(getUser().getFormattedName() + ChatColor.YELLOW + " heeft de spelerdata van " + base.getFormattedName() +ChatColor.YELLOW+" verwijderd!");
								base.purge();
							}
						});
          }
		});
	

	}
}