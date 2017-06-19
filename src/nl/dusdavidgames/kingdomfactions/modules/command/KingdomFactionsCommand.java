package nl.dusdavidgames.kingdomfactions.modules.command;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.exception.chat.ChannelNotFoundException;
import nl.dusdavidgames.kingdomfactions.modules.exception.command.KingdomFactionsCommandException;
import nl.dusdavidgames.kingdomfactions.modules.exception.kingdom.UnkownKingdomException;
import nl.dusdavidgames.kingdomfactions.modules.exception.player.UnkownPlayerException;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.player.player.console.CommandUser;
import nl.dusdavidgames.kingdomfactions.modules.player.player.online.KingdomFactionsPlayer;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import nl.dusdavidgames.kingdomfactions.modules.utils.logger.Logger;

public abstract class KingdomFactionsCommand implements CommandExecutor {

	private @Getter ArrayList<SubCommand> subCommands = new ArrayList<SubCommand>();
	private @Getter String name;
	private @Getter String permission;
	private @Getter int lenght;
	private @Getter String[] args;
	private @Getter CommandSender sender;
	private @Getter String info;
	private @Getter String usage;
	private boolean sub;
	private boolean allowConsole;

	public boolean allowSub() {
		return sub;
	}

	public boolean allowConsole() {
		return allowConsole;
	}

	public KingdomFactionsCommand(String name, String permission, String info, String usage, boolean sub,
			boolean allowConsole) {
		this.name = name;
		this.permission = permission;
		this.info = info;
		this.usage = usage;
		CommandModule.getInstance().getCommand().add(this);
		this.sub = sub;
		this.allowConsole = allowConsole;
		if (sub) {
			initHelp();
			init();
		}
	}

	public abstract void init();

	public abstract void execute() throws KingdomFactionsException;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		try {
			if (!sender.hasPermission(this.permission)) {
				sender.sendMessage(Messages.getInstance().getNoPerm());
				return false;
			}
			if (!allowConsole) {
				if (sender instanceof ConsoleCommandSender) {
					getUser().sendMessage(
							Messages.getInstance().getPrefix() + "Dit commando is alleen beschikbaar voor spelers!");
					return false;
				}
			}
			this.args = args;
			this.sender = sender;
			if (sub) {
				SubCommand subC = getSub(getArgs()[0]);
				if (subC == null) {
					getSub("help").execute(new String[] { "help", "1" });
				} else {
					if (!sender.hasPermission(subC.getPermission())) {
						sender.sendMessage(Messages.getInstance().getNoPerm());
						return false;
					}
					if (!subC.allowConsole()) {
						if (sender instanceof ConsoleCommandSender) {
							getUser().sendMessage(Messages.getInstance().getPrefix()
									+ "Dit commando is alleen beschikbaar voor spelers!");
							return false;
						}
					}
					subC.execute(args);
				}
			} else {
				try {
				this.execute();
				} catch(ArrayIndexOutOfBoundsException e) {
					sender.sendMessage(ChatColor.GOLD + "/" + this.name + " " + ChatColor.GREEN + this.getInfo() + "");
				} catch (UnkownPlayerException e) {
					getUser().sendMessage(Messages.getInstance().getPrefix() + "Onbekende speler!");
				} catch(ChannelNotFoundException e) {
					getPlayer().sendMessage(Messages.getInstance().getPrefix() + "Onbekend chatkanaal");
				} catch (Exception e) {
					getUser().sendMessage(Messages.getInstance().getPrefix() + "Er ging iets fout!");
					Logger.WARNING.log("Discovered Command Exception!");
					Logger.ERROR.log("---------------------------");
					Logger.ERROR.log("Exception: " + e.toString());
					for (StackTraceElement s : e.getStackTrace()) {
						Logger.ERROR.log(s.getClassName() + " [" + s.getLineNumber() + "/" + s.getMethodName()
								+ "] [" + s.getFileName() + "]");
					}
					Logger.ERROR.log("---------------------------");
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			if(args.length == 0) {
				try {
					if(sub) {
					getSub("help").execute(new String[] { "help", "1" });
					}
				} catch (KingdomFactionsException e1) {
				}
			}
			if (sub) {
				SubCommand subC = null;
				try {
		        subC = getSub(args[0]);
				} catch(ArrayIndexOutOfBoundsException ex) {
					
				}
				if(subC == null) return false;
				sender.sendMessage(ChatColor.GOLD + "/" + this.name + " " + ChatColor.GREEN + subC.getMainCommand() + " "
						+ ChatColor.LIGHT_PURPLE + subC.getInfo());
			} else {
				if (this.getUsage() == "") {
					sender.sendMessage(
							ChatColor.GOLD + "/" + this.getName() + " " + ChatColor.LIGHT_PURPLE + this.getInfo());
				} else {
					sender.sendMessage(ChatColor.GOLD + "/" + this.getName() + " " + ChatColor.GREEN + this.getUsage()
							+ " " + ChatColor.LIGHT_PURPLE + this.getInfo());
				}
			}
		} catch (UnkownKingdomException e) {
			getUser().sendMessage(Messages.getInstance().getPrefix() + "Onbekende speler!");
		} catch (Exception e) {
			getUser().sendMessage(Messages.getInstance().getPrefix() + "Er ging iets fout!");
			Logger.WARNING.log("Discovered Command Exception!");
			Logger.ERROR.log("---------------------------");
			Logger.ERROR.log("Exception: " + e.toString());
			for (StackTraceElement s : e.getStackTrace()) {
				Logger.ERROR.log(s.getClassName() + " [" + s.getLineNumber() + "/" + s.getMethodName()
						+ "] [" + s.getFileName() + "]");
			}
			Logger.ERROR.log("---------------------------");
		}
		return false;
	}

	public void registerCommand() {
		KingdomFactionsPlugin.getInstance().getServer().getPluginCommand(name).setExecutor(this);
	}

	public void registerSub(SubCommand sub) {
		this.subCommands.add(sub);
	}

	private SubCommand getSub(String command) {
		for (SubCommand sub : subCommands) {
			for(String c : sub.commands) {
				if(c.equalsIgnoreCase(command)) {
					return sub;
				}
			}
		}
		return null;
	}

	public KingdomFactionsPlayer getPlayer() {
		if (!allowConsole) {
			return PlayerModule.getInstance().getPlayer(sender);
		}
		try {
			throw new KingdomFactionsCommandException("getPlayer() is illegal for this command!");
		} catch (KingdomFactionsCommandException e) {
			e.printStackTrace();
		}
		return null;

	}

	public CommandUser getUser() {
		return new CommandUser(sender);
	}

	public void broadcast(String message) {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[STAFF-COMMAND] " +  message);
		for (KingdomFactionsPlayer player : PlayerModule.getInstance().getPlayers()) {
			if (player.hasPermission("kingdomfactions.commandcheck")) {
				player.sendMessage(ChatColor.YELLOW + message);
			}
		}
	}

	private void initHelp() {
		this.registerSub(new SubCommand("help", this.permission, "Help commando") {

			@Override
			public void execute(String[] args) {
				try {
					try {
						sendHelpPage(sender, Integer.parseInt(getArgs()[1]));
					} catch (ArrayIndexOutOfBoundsException  e) {
						sendHelpPage(sender, 1);
					}
				} catch (NumberFormatException e) {
					sender.sendMessage(Messages.getInstance().getPrefix() + "Onbekende pagina!");
				}
			}
		});
	}

	public void sendHelpPage(CommandSender sender, int page) {
		if (page > 5) {
			sender.sendMessage(Messages.getInstance().getPrefix() + "Onbekende pagina!");
			return;
		}
		int pageNum = (5 * page) - 5;
		int maxHelp = 5 * page;
		sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "------------------------------------");
		for (int i = pageNum; i < maxHelp; i++) {
			if (i < this.subCommands.size()) {
				SubCommand c = this.subCommands.get(i);
				if (!sender.hasPermission(c.getPermission()))
					continue;
				if (c.getMainCommand().equalsIgnoreCase("help"))
					continue;
				sender.sendMessage(ChatColor.GOLD + "/" + this.name + " " + ChatColor.GREEN + c.getMainCommand() + " "
						+ ChatColor.LIGHT_PURPLE + c.getInfo());

			} else {
				sender.sendMessage(
						ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "------------------------------------");
				return;
			}
		}

		sender.sendMessage(ChatColor.BLUE + "Gebruik /" + this.name + " help " + (page + 1) + " om verder te kijken!");
		sender.sendMessage(ChatColor.BLUE + "" + ChatColor.STRIKETHROUGH + "------------------------------------");

	}
	
	
	
}
