package nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class NightVisionCommand extends KingdomFactionsCommand {



	public NightVisionCommand(String name, String permission, String info, String usage, boolean sub,
			boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(getSender());
		if (p.getSettingsProfile().hasNightVision()) {
			Bukkit.getPlayer(p.getName()).removePotionEffect(PotionEffectType.NIGHT_VISION);
			p.getSettingsProfile().setNightvision(false);
			broadcast(p.getFormattedName() + ChatColor.YELLOW + " heeft NightVision uitgezet!");
		} else {
			p.getSettingsProfile().setNightvision(true);
			Bukkit.getPlayer(p.getName())
					.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 3600, 3, true, false));
			broadcast(p.getFormattedName() + ChatColor.YELLOW +  " heeft NightVision aangezet!");
		}

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

}