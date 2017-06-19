package nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings;

import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;

import lombok.Data;
import nl.dusdavidgames.kingdomfactions.modules.exception.nexus.NexusException;
import nl.dusdavidgames.kingdomfactions.modules.exception.value.ValueException;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.nexus.Nexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildingType;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.action.IAction;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.MessagePrefix;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import nl.dusdavidgames.kingdomfactions.modules.utils.particles.ParticleEffect;

public @Data class BuildAction implements IAction {

	private KingdomFactionsPlayer player;
	private Nexus nexus;
	private Location location;
	private String schematic;
	private Building building;
	private BuildingType type;
	private BuildLevel level;
	private Location pasteLocation;
	
	
	private int price;
	
	private boolean free;

	/**
	 * Buildings
	 * @param nexus
	 * @param location
	 * @param building
	 * @param style
	 * @param level
	 * @param player
	 */
	public BuildAction(Nexus nexus, Location location, BuildingType building, KingdomType style, BuildLevel level,
			KingdomFactionsPlayer player) {
		this.nexus = nexus;
		this.location = location;
		this.player = player;
		this.type = building;
		this.level = level;
		
		this.building = null;
		this.schematic = Utils.getInstance().getSchematic(KingdomModule.getInstance().getKingdom(style), level,
				building);
		
		this.price = building.getPrice(style, level);
	}

	/**
	 * Building creations
	 * @param nexus
	 * @param type
	 * @param style
	 * @param player
	 * Requires the location to be set later!
	 */
	public BuildAction(Nexus nexus, BuildingType type, KingdomType style, KingdomFactionsPlayer player) {
		this(nexus, null, type, style, BuildLevel.LEVEL_1, player);
		
	}

	/**
	 * Only used for Building Upgrades
	 * @param b
	 * @param player
	 */
	public BuildAction(Building b, KingdomFactionsPlayer player) {
		//b.setLevel(b.getLevel().next());
		this.level = b.getLevel();	
		this.schematic = Utils.getInstance().getSchematic(KingdomModule.getInstance().getKingdom(b.getStyle()),
				b.getLevel(), b.getType());
		this.building = b;

		this.player = player;
		this.price = building.getType().getPrice(b.getStyle(), level);
		this.pasteLocation = b.getPasteLocation();
	}

	/**
	 * Only used for creating a NEXUS
	 * @param player
	 * @param style
	 */
	public BuildAction(KingdomFactionsPlayer player, KingdomType style) {
		this.type = BuildingType.NEXUS;
		this.schematic = Utils.getInstance().getSchematic(KingdomModule.getInstance().getKingdom(style), BuildLevel.LEVEL_1, BuildingType.NEXUS);
	    this.level = BuildLevel.LEVEL_1;
	    this.price = this.type.getPrice(style, level);
	    this.player = player;
	 }
	
	/**
	 * Only used for NEXUS upgrades
	 * @param player
	 * @param nexus
	 */
	
	public BuildAction(KingdomFactionsPlayer player, Nexus nexus) {
		this.schematic = Utils.getInstance().getSchematic(KingdomModule.getInstance().getKingdom(nexus.getKingdom()), BuildLevel.getLevel(nexus.getLevel()), BuildingType.NEXUS);
		this.type = BuildingType.NEXUS;
	    this.level = BuildLevel.getLevel(nexus.getLevel());
	    this.player = player;
	    this.nexus = nexus;
	    this.location = nexus.getPasteLocation();
	    this.price = this.type.getPrice(this.nexus.getKingdom(), level);
	    this.building = null;
	}
	
	public void execute() {
		Location pasteLocation = this.pasteLocation;

		Logger.INFO.log("Pasting schematic " + this.schematic + " to location " + Utils.getInstance().getLocationString(this.pasteLocation));
		for (Location loc : Utils.getInstance().drawCircle(pasteLocation, 10, 1, true, false, 0)) {
			Utils.getInstance().playFirework(player.getPlayer(), loc, Color.PURPLE, Color.BLACK, Type.BURST);
			 Utils.getInstance().playParticle(loc, ParticleEffect.HAPPY_VILLAGER);
			 Utils.getInstance().playParticle(loc, ParticleEffect.LARGE_SMOKE);
		}
		
	
		
		
		Utils.getInstance().pasteSchematic(player, schematic, pasteLocation);
		Utils.getInstance().pasteSchematicSilence(player, schematic, pasteLocation, building);
	}
	
	
	public void cancel() {
		this.player.setAction(null);
	}
	
	public Building executeAction() throws NexusException, ValueException {
		if(!this.isFree()) {
			this.player.removeCoins(this.price, false);
		}
		if(this.type == BuildingType.NEXUS) {
			throw new NexusException("Creating Nexus in a Building BuildAction!");
		}
		
		if(building == null) {
			this.execute();
			Building b = new Building(type, this.pasteLocation, this.nexus, BuildLevel.LEVEL_1);
			b.updateLocation();
			this.nexus.getBuildings().add(b);
			b.save();
			return b;
			
		} else {
			this.pasteLocation = this.building.getPasteLocation();
			building.setLevel(building.getLevel().next());
			this.execute();
			building.save();
			return building;
		}
	
	}

	public Nexus executeNexusAction() throws NexusException, ValueException {
		if(!this.isFree()) {
			this.player.removeCoins(this.price, false);
		}
		if(this.type != BuildingType.NEXUS) {
			throw new NexusException("Creating Nexus in a Building BuildAction!");
		}
		
		if(this.nexus == null) {
			String nexusid = Utils.getInstance().generateRandomString(Utils.NEXUS);
            Nexus n = new Nexus(600, nexusid, this.pasteLocation, player.getFaction().getFactionId(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), 1, location.getWorld(), false);
            NexusModule.getInstance().getNexuses().add(n);
            
            this.execute();
            return n;
		} else {
			this.pasteLocation = this.nexus.getPasteLocation();
			this.nexus.setLevel(nexus.getLevel() + 1);
			
			this.schematic = Utils.getInstance().getSchematic(KingdomModule.getInstance().getKingdom(nexus.getKingdom()), BuildLevel.getLevel(nexus.getLevel()), BuildingType.NEXUS);
			try {
			this.execute();
			} catch(NullPointerException e) {
				this.player.sendMessage(MessagePrefix.KDFERROR, "Er ging iets fout! Meld dit DIRECT bij een Stafflid!");
			}
			return this.nexus;
		}
	
	}
	public Nexus executeNexusAction(Location nexusLocation, KingdomFactionsPlayer player) throws NexusException, ValueException {
		if(!this.isFree()) {
			this.player.removeCoins(this.price, false);
		}
		if(this.type != BuildingType.NEXUS) {
			throw new NexusException("Creating Building in a Nexus BuildAction!");
		}

        this.pasteLocation = nexusLocation;

		this.location = nexusLocation;
		
		if(this.player == null) {
			this.player = player;
		}
		
		if(this.nexus == null) {
			String nexusid = Utils.getInstance().generateRandomString(Utils.NEXUS);
            Nexus n = new Nexus(600, nexusid, nexusLocation, player.getFaction().getFactionId(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), 1, location.getWorld(), false);
            NexusModule.getInstance().getNexuses().add(n);
            this.nexus = n;
            this.execute();
            return n;
		} else {
			this.pasteLocation = this.nexus.getPasteLocation();
			this.nexus.setLevel(nexus.getLevel() + 1);
			this.execute();
			this.nexus.save();
			return this.nexus;
		}
		
	}
}
