package nl.dusdavidgames.kingdomfactions.modules.empirewand;

import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;

public abstract class Spell {

	private String name;

	public Spell(String name) {
		this.name = name;
	}

	public abstract void execute(KingdomFactionsPlayer player);


	public String getSpellName() {
		return name;
	}
}
