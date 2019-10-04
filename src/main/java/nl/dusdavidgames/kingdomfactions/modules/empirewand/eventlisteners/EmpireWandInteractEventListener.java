package nl.dusdavidgames.kingdomfactions.modules.empirewand.eventlisteners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.SpellModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Cooldown;
import nl.dusdavidgames.kingdomfactions.modules.utils.CooldownType;

public class EmpireWandInteractEventListener implements Listener{
	
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if(e.getAction() == Action.PHYSICAL) return;
	    KingdomFactionsPlayer p = PlayerModule.getInstance().getPlayer(e.getPlayer());
	    if(p.getPlayer().getInventory() == null) return;
	    if(p.getPlayer().getItemInHand() == null) return;
	    if(p.getPlayer().getItemInHand().getType() != Material.BLAZE_ROD) return;
	    if(!p.getPlayer().getItemInHand().hasItemMeta()) return;
	    if(!p.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.RED + "Empire Wand")) return;
		e.setCancelled(true);
		if(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) { 
			if(p.hasCooldown(CooldownType.WAND)) {
				p.sendMessage(ChatColor.GOLD + "[" + ChatColor.GRAY + "X" + ChatColor.GOLD + "] "
						+ "Je moet nog " + ChatColor.GRAY + p.getCooldown(CooldownType.WAND).getCooldown() + " seconde(n)" + ChatColor.GOLD + " wachten!");
				return;
			}
 			if(p.getSpell() == null) {
				p.setSpell(SpellModule.getInstance().getSpell("Spark"));
			}
	       p.getSpell().execute(p);
	       p.addCooldown(new Cooldown(CooldownType.WAND, p, 60));
		} else if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			p.rotateSpells();
		}
	}

}
