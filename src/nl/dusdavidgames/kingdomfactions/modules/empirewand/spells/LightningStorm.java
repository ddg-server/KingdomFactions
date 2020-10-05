package nl.dusdavidgames.kingdomfactions.modules.empirewand.spells;

import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.Spell;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.particles.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LightningStorm extends Spell{

	public LightningStorm(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(KingdomFactionsPlayer player) {
		player.sendMessage(Messages.getInstance().getPrefix() + "Er is een magische storm op komst..");
		player.setActiveSpell(this);	
	}

	
	
	public static void startStrikeRunnable() {
		Bukkit.getScheduler().runTaskTimer(KingdomFactionsPlugin.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for(KingdomFactionsPlayer player : PlayerModule.getInstance().getPlayers()) {
					if(player.hasActiveSpell()) { 
						if(player.getActiveSpell() instanceof LightningStorm) {
						  if(player.getActiveSpellStrikes() >= 6) {
							  player.setActiveSpell(null);
							  player.sendMessage(Messages.getInstance().getPrefix() + "De magische storm is afgelopen..");
							  return;
						  }
							Utils.getInstance().strikeRandomLightning(player.getLocation(), 20, true);
							int i  = player.getActiveSpellStrikes();
							i++;
							player.setActiveSpellStrikes(i);
						}
					}
				}
				
			}
		}, 0, 20 * 5L);
	}
	
	public static void startCloudRunnable() {
	Bukkit.getScheduler().runTaskTimer(KingdomFactionsPlugin.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				for(KingdomFactionsPlayer player : PlayerModule.getInstance().getPlayers()) {
					if(player.hasActiveSpell()) { 
						if(player.getActiveSpell() instanceof LightningStorm) {
						 for(Location loc : Utils.getInstance().drawCircle(player.getLocation(), 10, 1, false, false, 10)) {
							 Utils.getInstance().playParticle(loc, ParticleEffect.CLOUD);
						 }
						}
					}
				}
				
			}
		}, 0, 20L);
	}
	
	
}
