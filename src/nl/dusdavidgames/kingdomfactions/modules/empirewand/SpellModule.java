package nl.dusdavidgames.kingdomfactions.modules.empirewand;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.eventlisteners.EmpireWandInteractEventListener;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.spells.Confuse;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.spells.Leap;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.spells.LightningStorm;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.spells.Spark;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public class SpellModule {

	
	
	private static @Getter @Setter SpellModule instance;
	
	public SpellModule() {
		setInstance(this);
		LightningStorm.startCloudRunnable();
		LightningStorm.startStrikeRunnable();
		KingdomFactionsPlugin.getInstance().registerListener(new EmpireWandInteractEventListener());
		this.registerSpell(new Spark("Spark"));
		this.registerSpell(new Leap("Leap"));
		//this.registerSpell(new Comet("Comet")); Disabled due unkown exception
		this.registerSpell(new Confuse("Confuse"));
	//	this.registerSpell(new LightningStorm("LightingStorm"));
	}
	
	private @Getter ArrayList<Spell> spells = new ArrayList<Spell>();
	
	public void registerSpell(Spell spell) {
		spells.add(spell);
	}
	
	public Spell getSpell(String name) {
		for(Spell spell : spells) {
			if(spell.getSpellName().equalsIgnoreCase(name)) {
				return spell;
			}
		}
		return null;
	}
	
	
	public void rotateSpells(KingdomFactionsPlayer p) {
		if(spells.isEmpty()){
			p.sendMessage(ChatColor.GOLD + "[" + ChatColor.GRAY + "] Selecteerde spreuk: " + ChatColor.RED + "GEEN SPELLS BESCHIKBAAR" + ChatColor.GOLD + "!");
			return;
		}
		
		if(p.getSpell() == null){
			p.setSpell(spells.get(0));
		}else{
			int newSpell = getNewSpellInt(p.getSpell());
			if(newSpell > (spells.size() - 1)){
				p.setSpell(spells.get(0));
			}else{
				p.setSpell(spells.get(newSpell));
			}
		}
		
		p.sendMessage(ChatColor.GOLD + "[" + ChatColor.GRAY + "X" + ChatColor.GOLD + "] Selecteerde spreuk: " + ChatColor.GRAY + p.getSpell().getSpellName() + ChatColor.GOLD + "!");
	}
	
	private int getNewSpellInt(Spell spell){
		int currentSpell = 0;
		for(Spell spells : this.spells){
			if(spells.equals(spell)){
				currentSpell++;
				return currentSpell;
			}
			currentSpell++;
		}
		return 0;
	}
	
}
