package nl.dusdavidgames.kingdomfactions.modules.kingdom.command;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.SubCommand;
import nl.dusdavidgames.kingdomfactions.modules.database.mysql.databases.KingdomDatabase;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomModule;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.KingdomRank;
import nl.dusdavidgames.kingdomfactions.modules.kingdom.kingdom.Kingdom;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.IPlayerBase;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class KingdomCommand extends KingdomFactionsCommand {

	public KingdomCommand(String name, String permission, String info, String usage, boolean sub,
			boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		this.registerSub(new SubCommand("setkingdom", "kingdomfactions.command.kingdom.setkingdom",
				"Zet iemand in een kingdom!") {

			@Override
			public void execute(String[] args) {
				IPlayerBase player = null;
				try {
					player = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);
				} catch (UnkownPlayerException e) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Kon deze speler niet vinden.");
				}
				Kingdom kingdom = KingdomModule.getInstance().getKingdom(getArgs()[2]);
				
				if(kingdom == null){
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Ongeldig Kingdom! Kies uit: HYVAR, EREDON, TILIFIA, MALZAN, ADAMANTIUM en DOK");
					return;
				}
				
				player.setKingdom(kingdom);
				getPlayer().sendMessage(
						Messages.getInstance().getPrefix() + "Je hebt " + player.getName() + " naar het Kingdom "
								+ player.getKingdom().getType().getPrefix() + ChatColor.WHITE + "gezet!");
			}
		});
		this.registerSub(new SubCommand("setrank", "kingdomfactions.command.kingdom.setrank",
				"Zet iemand's rank in een Kingdom") {

			@Override
			public void execute(String[] args) {
				IPlayerBase player = null;
				try {
					player = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);
				} catch (UnkownPlayerException e) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Kon deze speler niet vinden.");
				}
				player.setKingdomRank(KingdomRank.getRank(getArgs()[2]));
				getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt " + player.getName()
						+ " gepromoveerd tot " + KingdomRank.getRank(getArgs()[2]));
			}
		});
        this.registerSub(new SubCommand("setspawn", "kingdomfactions.command.kingdom.setspawn", "Zet de spawn van een kingdom!") {
			
			@Override
			public void execute(String[] args) {
				Kingdom k = KingdomModule.getInstance().getKingdom(getArgs()[1]);
				if(k != null) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt de Spawn van " + k.getType().getPrefix() + ChatColor.WHITE + "gezet!");
					k.setSpawn(getPlayer().getLocation());
					KingdomDatabase.getInstance().setSpawn(k.getType().toString(), getPlayer().getLocation());
				} else {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Ongeldig Kingdom! Kies uit: HYVAR, EREDON, TILIFIA, MALZAN, ADAMANTIUM en DOK");
				}
				
			}
		});
        this.registerSub(new SubCommand("spawn", "kingdomfactions.command.kingdom.spawn", "Teleporteer naar de spawn van een Kingdom") {
			
			@Override
			public void execute(String[] args) throws KingdomFactionsException {
			try {
		   KingdomFactionsPlayer player = PlayerModule.getInstance().getPlayer(getArgs()[2]);
		   if(player == null) {
			   getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Deze speler is offline!");
		   } else {
			   Kingdom k = KingdomModule.getInstance().getKingdom(getArgs()[1]);
				if(k != null) {
					if(k.getSpawn() == null) {
						getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Dit Kingdom heeft geen spawn gezet!");
						return;
					}
				   player.teleport(k.getSpawn());
				   getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt " + player.getName() + " naar de spawn van " + k.getType().getPrefix() + "geteleporteerd!");
				} else {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Ongeldig Kingdom!");
				}
		   }
			} catch(ArrayIndexOutOfBoundsException e) {
				  Kingdom k = KingdomModule.getInstance().getKingdom(getArgs()[1]);
					if(k != null) {
						if(k.getSpawn() == null) {
							getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Dit Kingdom heeft geen spawn gezet!");
							return;
						}
					   getPlayer().teleport(k.getSpawn());
					   getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Teleporteren naar de spawn van " + k.getType().getPrefix());
					} else {
						getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Ongeldig Kingdom!");
					}
			}
			}
		});
        this.registerSub(new SubCommand("check", "kingdomfactions.command.kingdom.check", "Verkrijg informatie over een speler") {

			@Override
			public void execute(String[] args) throws UnkownPlayerException {
				this.setAllowConsole(true);
					IPlayerBase player = PlayerModule.getInstance().getPlayerBase(getArgs()[1]);
                    getSender().sendMessage(Messages.getInstance().getPrefix() + "------------------");
                    getSender().sendMessage(ChatColor.WHITE + "Kingdom: " + player.getKingdom().getType().getPrefix());
                    if(player.getKingdomRank() != KingdomRank.SPELER) {
                    getSender().sendMessage(ChatColor.WHITE + "Kingdom Rank: " + player.getKingdomRank().getPrefix());
                    }
                    getSender().sendMessage(Messages.getInstance().getPrefix() + "------------------");
				
			}
		});
        this.registerSub(new SubCommand("setcapital", "kingdomfactions.command.kingdom.setcapital", "Zet de hoofdstad van een Kingdom!") {
			
			@Override
			public void execute(String[] args) throws KingdomFactionsException {
				if(!getPlayer().hasAction()) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je bent een Hoofdstad-zet actie begonnen!");
					KingdomModule.getInstance().createAction(KingdomModule.getInstance().getKingdom(getArgs()[1]), getPlayer());
				} else {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Je hebt al een actie lopen!!");
				}
				
			}
		});
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
