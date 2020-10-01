package nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.KingdomDatabase;
import nl.dusdavidgames.kingdomfactions.modules.exception.DataException;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.nexus.guardian.CapitalGuard;
import nl.dusdavidgames.kingdomfactions.modules.monster.GuardType;
import nl.dusdavidgames.kingdomfactions.modules.monster.IGuard;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.IInhabitable;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.NexusType;
import nl.dusdavidgames.kingdomfactions.modules.utils.particles.ParticleEffect;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CapitalNexus implements INexus {

	public CapitalNexus(Location location, KingdomType kingdom) {
		this.location = location;
		this.health = 800;
		this.kingdom = kingdom;
		this.nexusId = kingdom.toString();
	}

	private @Getter Location location;
	private @Setter @Getter int health;
	private @Setter @Getter KingdomType kingdom;
	private @Setter @Getter String nexusId;
	private boolean destroy;
	
	private @Getter ArrayList<IGuard> guards = new ArrayList<IGuard>();

	@Override
	public Location getNexusLocation() {
		return location;
	}

	@Override
	public int getProtectedRadius() {
		// TODO Auto-generated method stub
		try {
			return KingdomFactionsPlugin.getInstance().getDataManager().getInteger("kingdom.capital.protected_region");
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 400;
	}

	public double getDistance(KingdomFactionsPlayer p) {
		Location player = Utils.getInstance().getNewLocation(p.getLocation());
		Location nexus = Utils.getInstance().getNewLocation(this.getNexusLocation());
		player.setY(0);
		nexus.setY(0);
		return nexus.distance(player);
	}

	public double getDistance(Location location) {
		Location loc = Utils.getInstance().getNewLocation(location);
		Location nexus = Utils.getInstance().getNewLocation(this.getNexusLocation());
		loc.setY(0);
		nexus.setY(0);
		return nexus.distance(loc);
	}

	@Override
	public NexusType getType() {
		// TODO Auto-generated method stub
		return NexusType.CAPITAL;
	}

	public boolean isDestroyed() {
		return destroy;
	}

	public void setDestroyed(boolean destroy) {
		this.destroy = destroy;
	}
	
	
	
	public ArrayList<CapitalGuard> spawnGuards() {
		ArrayList<CapitalGuard> guards = new ArrayList<CapitalGuard>();
		for(int i = 0; i <= 4; i++) {
			CapitalGuard guard = spawnGuard();
			guards.add(guard);
			this.guards.add(guard);
		}
		return guards;
	}
	
	private CapitalGuard spawnGuard() {
		List<Location> list = Utils.getInstance().drawCircle(this.location, 4, 1, true, false, 0);
		Location spawn = list.get(new Random().nextInt(list.size()));
		Utils.getInstance().playParticle(spawn, ParticleEffect.WITCH_MAGIC);
		CapitalGuard g = new CapitalGuard(this, getRandomType(), spawn);
		g.spawn();
		return g;
	}
	
	
	private GuardType getRandomType() {
		Random r = new Random();
		int i = r.nextInt(4);
		if(i == 3) {
			return GuardType.SKELETON;
		}
		return GuardType.ZOMBIE;
	}
	
	public void save() {
		KingdomDatabase.getInstance().saveNexus(this);
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public IInhabitable getOwner() {
		return KingdomModule.getInstance().getKingdom(this.getKingdom());
	}
	
	
	@Override
	public String toString() {
		return Utils.getInstance().getLocationString(this.location) + "/" + this.nexusId;
	}
}
