package nl.dusdavidgames.kingdomfactions.modules.empirewand.spells;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.Spell;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.event.SpellExecuteEvent;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Set;

public class Confuse extends Spell {

	public Confuse(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	private @Getter Location executeLocation;

	@Override
	public void execute(KingdomFactionsPlayer player) {

		setLocation(player.getPlayer().getTargetBlock((Set<Material>) null, 80).getLocation());

		SpellExecuteEvent event = new SpellExecuteEvent(executeLocation, this, player);
		if (event.isCancelled())
			return;
		playEffect(executeLocation);
		for (Entity e : Utils.getInstance().getNearbyEntities(executeLocation, 3)) {
			if (e instanceof LivingEntity) {
				LivingEntity en = (LivingEntity) e;

				if (e instanceof Player) {
					Player p = (Player) e;
					if (PlayerModule.getInstance().getPlayer(p).isVanished()) continue;
				    en.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 5 * 20, 1));
				    en.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 5 * 20, 1));
				    en.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 5 * 20, 1));
				}
			}
		}

	}

	public void setLocation(Location loc) {
		this.executeLocation = Utils.getInstance().getNewLocation(loc);
	}

	public void playEffect(Location l) {
		for(Entity e : Utils.getInstance().getNearbyEntities(l, 100)) {
			if(!(e instanceof Player))  continue;
		Utils.getInstance().playFirework(((Player)e), l, Color.PURPLE, Color.BLACK, Type.BURST);
		}
		

	}
}
