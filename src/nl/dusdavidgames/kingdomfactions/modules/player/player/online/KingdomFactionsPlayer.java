package nl.dusdavidgames.kingdomfactions.modules.player.player.online;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.kitteh.vanish.staticaccess.VanishNoPacket;

import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.Packet;
import nl.dusdavidgames.kingdomfactions.modules.chat.events.PrivateMessageEvent;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.MySQLModule;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.PlayerDatabase;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.Spell;
import nl.dusdavidgames.kingdomfactions.modules.empirewand.SpellModule;
import nl.dusdavidgames.kingdomfactions.modules.exception.kingdom.KingdomException;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.PlayerException;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.exception.shop.ShopException;
import nl.dusdavidgames.kingdomfactions.modules.exception.value.ValueException;
import nl.dusdavidgames.kingdomfactions.modules.faction.Faction;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionMember;
import nl.dusdavidgames.kingdomfactions.modules.faction.FactionRank;
import nl.dusdavidgames.kingdomfactions.modules.faction.invite.Invite;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.KingdomType;
import nl.dusdavidgames.kingdomfactions.modules.nexus.INexus;
import nl.dusdavidgames.kingdomfactions.modules.nexus.NexusModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.BuildModule;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.buildings.BuildAction;
import nl.dusdavidgames.kingdomfactions.modules.nexus.protection.ProtectionModule;
import nl.dusdavidgames.kingdomfactions.modules.permission.PermissionModule;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.deathban.DeathBan;
import nl.dusdavidgames.kingdomfactions.modules.player.deathban.DeathBanModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.player.player.offline.OfflineKingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.ChatProfile;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.CombatTracker;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.MembershipProfile;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.SettingsProfile;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.profiles.StatisticsProfile;
import nl.dusdavidgames.kingdomfactions.modules.scoreboard.ScoreBoard;
import nl.dusdavidgames.kingdomfactions.modules.scoreboard.ScoreboardModule;
import nl.dusdavidgames.kingdomfactions.modules.time.TimeHelper;
import nl.dusdavidgames.kingdomfactions.modules.utils.Cooldown;
import nl.dusdavidgames.kingdomfactions.modules.utils.MetaData;
import nl.dusdavidgames.kingdomfactions.modules.utils.Randomizer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Utils;
import nl.dusdavidgames.kingdomfactions.modules.utils.action.IAction;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.MessagePrefix;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.Title;
import nl.dusdavidgames.kingdomfactions.modules.utils.enums.TitleDuration;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;
import nl.dusdavidgames.kingdomfactions.modules.utils.yesnoconfirm.YesNoConfirmation;
import nl.dusdavidgames.kingdomfactions.modules.viewdistance.ViewDistanceModule;

public class KingdomFactionsPlayer implements IPlayerBase {

	private @Getter UUID uuid;
	private @Getter Player player;
	private @Getter String name;
	private @Getter String ipAdres;
	private @Getter @Setter String territoryId;
	private @Getter @Setter KingdomType kingdomTerritory;
	private @Getter CombatTracker combatTracker;
	private @Getter @Setter Spell spell;
	private @Setter Spell activeSpell;
	private @Getter @Setter int activeSpellStrikes;
	private ScoreBoard scoreboard;
	private @Getter SettingsProfile settingsProfile;
	private @Getter @Setter StatisticsProfile statisticsProfile;
	private @Getter ChatProfile chatProfile;
	private @Getter MembershipProfile membershipProfile;
	private @Getter @Setter Location lastLocation;
	public @Getter MetaData metaData = new MetaData();
	public @Getter YesNoConfirmation yesNoConfirmation;
	
	
	public void setYesNoConfirmation(YesNoConfirmation confirm) {
		this.yesNoConfirmation = confirm;
	}
	public boolean hasYesNoConfirmation() {
		return this.yesNoConfirmation != null;
	}
	
	private @Getter ArrayList<Cooldown> cooldowns = new ArrayList<Cooldown>();
	
	
	public void addCooldown(Cooldown cooldown) {
		this.cooldowns.add(cooldown);
	}
	public Cooldown getCooldown(String key) {
		Iterator<Cooldown> c = this.cooldowns.iterator();
		while(c.hasNext()) {
			Cooldown d = c.next();
			if(d.getKey().equalsIgnoreCase(key)) {
				return d;
			}
		}
		return null;
	}
	
	public boolean hasActiveSpell() {
		return this.activeSpell != null;
	}
	public Spell getActiveSpell() {
		return this.activeSpell;
	}
	
	public void executeCommand(String command) {
		Bukkit.dispatchCommand(this.getPlayer(), command);
	}
	public void executeCommands(String... command) {
		for(String s : command) {
			this.executeCommand(s);
		}
	}
	
	public boolean hasCooldown(String key) {
		return getCooldown(key) != null;
	}
	public void removeCooldown(String key) {
		this.cooldowns.remove(getCooldown(key));
	}
	private @Getter @Setter IAction action;
	
	
	public void setExp(float xp) {
		this.player.setExp(xp);
	}
	public void setExp(int level) {
		this.player.setLevel(level);
	}
	public float getExp() {
		return this.player.getExp();
	}
	public int getExpToLevel() {
		return this.getExpToLevel();
	}
	
	public boolean hasAction() {
		return getAction() != null;
	}

	
	
	public void sendPacket(Packet<?> packet) {
		this.getEntityPlayer().playerConnection.sendPacket(packet);
	}
	public void sendTab(String header, String footer) {
		Utils.getInstance().sendTabTitle(this, header, footer);
	}
	
	public KingdomFactionsPlayer(FactionRank factionRank, KingdomRank kingdomRank, Kingdom kingdom, Faction faction,
			String name) {
		this.player = Bukkit.getPlayer(name);
		this.name = name;
		this.uuid = player.getUniqueId();
		this.ipAdres = player.getAddress().getHostString();
		this.membershipProfile = new MembershipProfile(this, faction, kingdom, kingdomRank, factionRank);
		this.settingsProfile = new SettingsProfile(this);
		this.chatProfile = new ChatProfile(this);
		this.combatTracker = new CombatTracker(this);
	}

	public KingdomFactionsPlayer(FactionRank factionRank, KingdomRank kingdomRank, Kingdom kingdom, Faction faction,
			UUID uuid) {
		this.player = Bukkit.getPlayer(uuid);
		this.name = player.getName();
		this.uuid = player.getUniqueId();
		this.ipAdres = player.getAddress().getHostString();
		this.membershipProfile = new MembershipProfile(this, faction, kingdom, kingdomRank, factionRank);
		this.settingsProfile = new SettingsProfile(this);
		this.chatProfile = new ChatProfile(this);
		this.combatTracker = new CombatTracker(this);

	}
	


	public void unRegister() {
		PlayerModule.getInstance().getPlayers().remove(this);
		PlayerModule.getInstance().getQueue().remove(this);
	}

	public String getPrefix() {
		if (this.getMembershipProfile().getKingdomRank() == KingdomRank.KONING) {
			return this.getMembershipProfile().getKingdomRank().getPrefix();
		}
		return ChatColor.GRAY + "";
	}

	public void sendMessage(String message) {
		player.sendMessage(message);
	}

	public void sendMessage(MessagePrefix prefix, String message) {
		Utils.getInstance().sendMessage(prefix, this, message);
	}

	public String getFormattedName() {
		return getMembershipProfile().getKingdom().getType().getColor() + name;
	}

	public void setGameMode(GameMode g) {
		player.setGameMode(g);
	}

	public void setScoreBoard(ScoreBoard b) {
		this.scoreboard = b;
		player.setScoreboard(b.getBoard());
		
	}

	public void heal() {
		player.setHealth(20);
	}

	public void clearInventory() {
		this.getInventory().clear();
		this.getInventory().setBoots(null);
		this.getInventory().setHelmet(null);
		this.getInventory().setChestplate(null);
		this.getInventory().setLeggings(null);
	}

	public void feed() {
		player.setFoodLevel(20);
	}

	public void updateInventory() {
		player.updateInventory();
	}

	public void kill() {
		player.setHealth(0);
	}

	
	public boolean canSee(KingdomFactionsPlayer other) {
		return this.player.canSee(other.player);
	}
	public void addPotionEffect(PotionEffect e) {
		this.player.addPotionEffect(e);
	}
	public List<Block> drawLineOfSight(Set<Material> material, int length) {
		return this.player.getLineOfSight(material, length);
	}
	
	public void damage(double damage) {
		if (player.getHealth() > damage) {
			player.damage(damage);
		} else {
			this.kill();
		}

	}
	
	public void sendPrivateMessage(KingdomFactionsPlayer sender, String message) {
		PrivateMessageEvent event = new PrivateMessageEvent(sender, this, message);
		Bukkit.getPluginManager().callEvent(event);
		if(!event.isCancelled()) {
			sender.sendMessage(event.getFormat());
			this.sendMessage(event.getFormat());
			sender.getChatProfile().setReplyPlayer(this);
			this.chatProfile.setReplyPlayer(sender);
		}
	}

	public void openInventory(Inventory i) {
		player.openInventory(i);
	}

	public void save() {
		PlayerDatabase.getInstance().savePlayer(this);
	}

	public Location getLocation() {
		return player.getLocation();
	}

	public void teleport(Location destination) {
		this.lastLocation = player.getLocation();
		player.teleport(destination);
	}

	public void kick(String reason) {
		player.kickPlayer(reason);
	}

	public PlayerInventory getInventory() {
		return player.getInventory();
	}

	public Inventory getEnderChest() {
		return player.getEnderChest();
	}

	public boolean hasPermission(String permission) {
		return player.hasPermission(permission);
	}

	public void sendActionbar(String message) {
		Utils.getInstance().sendActionBar(this, message);
	}

	public void sendTitle(String titleMessage, String subTitleMessage, int inFadeTicks, int showTicks,
			int outFadeTicks) {
		Utils.getInstance().sendSubTitle(this, subTitleMessage, inFadeTicks, showTicks, outFadeTicks);
		Utils.getInstance().sendTitle(this, titleMessage, inFadeTicks, showTicks, outFadeTicks);
	}

	public void sendTitle(Title t, TitleDuration d, String message) {
		Utils.getInstance().sendTitle(this, t, d, message);
	}

	public void sendTitle(TitleDuration d, String message, String subMessage) {
		Utils.getInstance().sendTitle(this, Title.SUBTITLE, d, subMessage);
		Utils.getInstance().sendTitle(this, Title.TITLE, d, message);
	}

	public boolean canBuild(Location place) {
		return ProtectionModule.getInstance().canBuild(this, place);
	}

	public ChatColor getChatColor() {
		if (hasPermission("kingdomfactions.chat.white")) {
			return ChatColor.WHITE;
		}
		return ChatColor.GRAY;
	}

	public ScoreBoard getScoreboard() {
		return scoreboard;
	}

	public void loadScoreboard() {
		ScoreboardModule.getInstance().setupScoreboard(this);
	}

	public boolean isStaff() {
		return PermissionModule.getInstance().isStaff(this);
	}

	public INexus getClosestNexus() {
		return NexusModule.getInstance().getClosestNexus(this);
	}

	public INexus getCurrentNexus() {
		return NexusModule.getInstance().getINexus(this.territoryId);
	}

	public boolean hasBuildAction() {
		return BuildModule.getInstance().getAction(this) != null;
	}

	public BuildAction getBuildAction() {
		if (hasBuildAction()) {
			return BuildModule.getInstance().getAction(this);
		} else {
			return null;
		}
	}

	public void rotateSpells() {
		SpellModule.getInstance().rotateSpells(this);
	}

	/**
	 * VanishNoPacket's method `IsVanished` Created to allow player.isVanished()
	 * boolean.
	 */
	public boolean isVanished() {
		try {
			return VanishNoPacket.isVanished(getName());
		} catch (Exception e) {
			return false;
		}

	}

	public boolean hasFaction() {
		return this.membershipProfile.hasFaction();
	}

	public void setKingdom(Kingdom k) {
		try {
			this.membershipProfile.setKingdom(k);
		} catch (KingdomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setFaction(Faction f) {
			this.getMembershipProfile().setFaction(f);
	}

	public Kingdom getKingdom() {
		return this.getMembershipProfile().getKingdom();
	}

	public Faction getFaction() {
		return this.getMembershipProfile().getFaction();
	}

	/**
	 * Player's values will be set to 0. But he will not be registered as a new
	 * player.
	 */
	public void reset() {
	
		try {
			this.membershipProfile.setKingdom(KingdomModule.getInstance().getKingdom(KingdomType.GEEN));
		} catch (KingdomException e) {
			e.printStackTrace();
		}
		setStatisticsProfile(new StatisticsProfile(this, 0, 0, 0, 0, 0, System.currentTimeMillis()));
		PlayerDatabase.getInstance().savePlayer(this);
		Logger.INFO.log("Reset player " + this.name);
	}

	/**
	 * Permanently remove a player from the database. On login, a new player
	 * will be created.
	 */
	public void purge() {

		PlayerDatabase.getInstance().remove(this);
		this.kick(ChatColor.RED + "Jouw spelerdata is verwijderd!");

	}

	/**
	 * @since Minecraft 1.9 Code used to remove 1.9 pvp cooldown. Might not work
	 *        properly.
	 */
	public void restoreOldPvP() {
		player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16.0D);
	}
	public boolean canAfford(int coins) {
		return this.statisticsProfile.canAffordCoins(coins);
	}

	public void removeCoins(int coins, boolean mayBeNegative) throws ValueException {
		this.statisticsProfile.removeCoins(coins, mayBeNegative);
	}

	public void addCoins(int coins) {
		this.statisticsProfile.addCoins(coins);
	}

	public void addInfluence(int influence) {
		this.statisticsProfile.addInfluence(influence);
	}

	public void removeInfluence(int influence, boolean mayBeNegative) {
		this.statisticsProfile.removeInfluence(influence, mayBeNegative);
	}

	public int getInfluence() {
		return this.statisticsProfile.getInfluence();
	}

	public int getCoins() {
		return this.statisticsProfile.getCoins();
	}
	@Deprecated
	public void handleViewDistance() {
		ViewDistanceModule.getInstance().setViewDistance(this);
	}
	@Override
	public boolean isOnline() {
		return true;
	}

	@Override
	public KingdomRank getKingdomRank() {
		// TODO Auto-generated method stub
		return getMembershipProfile().getKingdomRank();
	}

	@Override
	public FactionRank getFactionRank() {
		// TODO Auto-generated method stub
		return getMembershipProfile().getFactionRank();
	}

	@Override
	public void setKingdomRank(KingdomRank kingdomrank) {
		this.getMembershipProfile().setKingdomRank(kingdomrank);
	}

	@Override
	public void setFactionRank(FactionRank factionrank) {
		this.getMembershipProfile().setFactionRank(factionrank);

	}
	


	@Override
	public DeathBan getDeathBan() {
		// TODO Auto-generated method stub
		return DeathBanModule.getInstance().getBan(this.name);
	}

	@Override
	public boolean hasDeathBan() {
		return DeathBanModule.getInstance().getBan(this.name) != null;
	}

	public void sendHoverMessage(String text, String hoverText) {
		TextComponent c = new TextComponent();
		c.setText(text);
		c.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverText).create()));
		this.getPlayer().spigot().sendMessage(c);
	}

	public void sendCommandMessage(String text, String hoverText, String command) {
		TextComponent c = new TextComponent();
		c.setText(text);
		c.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(hoverText).create()));
		c.setClickEvent(new ClickEvent(Action.RUN_COMMAND, command));
		this.player.spigot().sendMessage(c);
	}

	public OfflineKingdomFactionsPlayer convert() {
		return PlayerModule.getInstance().convert(this);
	}

	public void saveLogOut() {
		this.save();
		this.unRegister();
		
		
		if (this.getMembershipProfile().hasPendingInvites()) {
			for (Invite i : this.getMembershipProfile().getPendingInvites()) {
				i.unregister();
			}
		}
		this.confirmLogOut();
	}

	public void removeItem(Material material, int amount) throws ShopException {
		Player p = this.player;
		Inventory inv = p.getInventory();
		int j = inv.getSize();
		for (int i = 0; i < j; i++) {
			if (inv.getItem(i) != null) {
				if (inv.getItem(i).getType() == material) {
					if (inv.getItem(i).getAmount() == amount) {
						inv.setItem(i, new ItemStack(Material.AIR));
					return;
					} else if (inv.getItem(i).getAmount() >= amount) {
						int newAmount = inv.getItem(i).getAmount() - amount;
						if (newAmount >= 0) {
							p.getInventory().getItem(i).setAmount(newAmount);
				        return;
						}
					}
				}
			} 
			}
		throw new ShopException("Player "+p.getName()+" does not have enough items!");
		}

	
	public void addItem(ItemStack is) throws ShopException {
		Player p = this.player;
		Inventory inv = p.getInventory();
		for (int i = 0; i < inv.getSize(); i++) {
			if (inv.getItem(i) == null) {

				p.getInventory().addItem(is);
				return;
			}
		}
		throw new ShopException("Player "+p.getName()+" lacks inventory space!");
	}
	
	public Entity[] getNearbyEntities(int radius) {
		return Utils.getInstance().getNearbyEntities(this.getLocation(), radius);
	}

	public void chat(String message) {
		this.player.chat(message);
	}
	
	
	public String toString() {
		return this.name;
	}
	
	public CraftPlayer getCraftPlayer() {
		return (CraftPlayer) player;
	}
	public EntityPlayer getEntityPlayer() {
		return getCraftPlayer().getHandle();
	}
	private void confirmLogOut() {
	    PlayerModule.getInstance().getPlayers().removeIf(player -> Bukkit.getPlayer(player.getUuid()) == null);
	}

	@Override
	public IPlayerBase convert(IPlayerBase old) {
		// TODO Auto-generated method stub
		try {
			return PlayerModule.getInstance().getPlayerBase(old.getUuid());
		} catch (UnkownPlayerException e) {
			return old;
		}
	}

	@Override
	public FactionMember toFactionMember() {
		return new FactionMember(this.getFaction(), this.uuid, this.name, this.getFactionRank());
	}
	
	public void updateTerritory() {
		ProtectionModule.getInstance().updateTerritory(this);
	}
	@Override
	public int[] getPlaytime() {
		// TODO Auto-generated method stub
		return TimeHelper.getInstance().translateTime(this.getStatisticsProfile().getSecondsConnected());
	}
	
	private boolean kdswitch;
	
	public boolean hasSwitch() {
		return kdswitch;
	}
	public void setHasSwitch(boolean kdswitch) {
		this.kdswitch = kdswitch;
	}
	
	public void executeSwitch() throws PlayerException, ValueException {
		if(this.hasSwitch()) {
			this.teleport(this.getKingdom().getSpawn());
			if(this.hasFaction()) {
				Faction f = this.getFaction();
				if(this.getMembershipProfile().isFactionLeader()) {
					if(!f.getOfficers().isEmpty()) {
						FactionMember member = new Randomizer<FactionMember>(f.getOfficers()).result();
						member.setRank(FactionRank.LEADER);
						member.toPlayer().setFactionRank(FactionRank.LEADER);
						
					} else if(!f.getMembers().isEmpty()) {
						FactionMember member = new Randomizer<FactionMember>(f.getMembers()).result();
						member.setRank(FactionRank.LEADER);
						member.toPlayer().setFactionRank(FactionRank.LEADER);
					} else {
						f.remove();
					}
			
				}
			}
			Logger.INFO.log("Executing Switch for " + this.getName());
				this.removeInfluence(this.getInfluence(), false);
				int coins = this.getCoins() / 10;
				this.removeCoins(this.getCoins(), false);
				this.addCoins(coins);
				this.setFaction(null);
				this.clearInventory();
				this.setFactionRank(FactionRank.SPELER);
				MySQLModule.getInstance().insertQuery("UPDATE KingdomFactions.playerdata SET isSwitch='0' WHERE player_id='"+this.uuid+"'");
			
		} else {
			throw new PlayerException("Attempting to execute Kingdom switch which doesn't exists.");
		}
	}
	public void updateScoreboard() {
	   ScoreboardModule.getInstance().updateScoreboard(this);
	}
	public void handleExtraCoins() {
		PlayerDatabase.getInstance().handleExtraCoins(this);
	}

}