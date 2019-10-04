package nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands;

import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffectType;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.GodMode;

public class GodmodeCommand extends  KingdomFactionsCommand {



	public GodmodeCommand(String name, String permission, String info, String usage, boolean sub,
			boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(getSender());
		if (getArgs().length == 0) {
			switch (p.getSettingsProfile().getGodMode()) {
			case FAKEDAMAGE:
				p.getSettingsProfile().setGodMode(GodMode.NOGOD);
				p.sendMessage(Messages.getInstance().getPrefix() + "Je hebt Godmode uit gezet!");
				broadcast(p.getFormattedName()  + ChatColor.YELLOW + " heeft godmode uitgezet!");
				p.getPlayer().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
				break;
			case NODAMAGE:
				p.getSettingsProfile().setGodMode(GodMode.NOGOD);
				p.sendMessage(Messages.getInstance().getPrefix() + "Je hebt Godmode uit gezet!");
				broadcast(p.getFormattedName()  + ChatColor.YELLOW + " heeft godmode uitgezet!");
				p.getPlayer().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
				break;
			case NOGOD:
				p.getSettingsProfile().setGodMode(GodMode.NODAMAGE);
				p.sendMessage(Messages.getInstance().getPrefix() + "Je hebt Godmode aan gezet!");
				p.sendMessage(Messages.getInstance().getPrefix() + "GodMode Type: NODAMAGE");
				broadcast(p.getFormattedName()  + ChatColor.YELLOW + " heeft godmode aangezet! GodMode Type: NODAMAGE");
				break;
			default:
				p.getSettingsProfile().setGodMode(GodMode.NODAMAGE);
				p.sendMessage(Messages.getInstance().getPrefix() + "Je hebt Godmode aan gezet!");
				p.sendMessage(Messages.getInstance().getPrefix() + "GodMode Type: NODAMAGE");
				broadcast(p.getFormattedName()  + ChatColor.YELLOW + " heeft godmode aangezet! GodMode Type: NODAMAGE");
				break;

			}
		} else {
			if (getArgs()[0].equalsIgnoreCase("fakedamage")) {
				p.getSettingsProfile().setGodMode(GodMode.FAKEDAMAGE);
				p.sendMessage(Messages.getInstance().getPrefix() + "Je hebt Godmode aan gezet!");
				p.sendMessage(Messages.getInstance().getPrefix() + "GodMode Type: FAKEDAMAGE");
				broadcast(p.getFormattedName()  + ChatColor.YELLOW +  " heeft godmode aangezet! GodMode Type: FAKEDAMAGE");
			} else if (getArgs()[0].equalsIgnoreCase("nodamage")) {
				p.getSettingsProfile().setGodMode(GodMode.NODAMAGE);
				p.sendMessage(Messages.getInstance().getPrefix() + "Je hebt Godmode aan gezet!");
				p.sendMessage(Messages.getInstance().getPrefix() + "GodMode Type: NODAMAGE");
				broadcast(p.getFormattedName()  + ChatColor.YELLOW +  " heeft godmode aangezet! GodMode Type: NODAMAGE");
			} else if (getArgs()[0].equalsIgnoreCase("disable")) {
				p.getSettingsProfile().setGodMode(GodMode.NOGOD);
				p.sendMessage(Messages.getInstance().getPrefix() + "Je hebt Godmode uit gezet!");
				broadcast(p.getFormattedName()  + ChatColor.YELLOW + " heeft godmode uitgezet!");
				p.getPlayer().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
			}
		}

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
}
