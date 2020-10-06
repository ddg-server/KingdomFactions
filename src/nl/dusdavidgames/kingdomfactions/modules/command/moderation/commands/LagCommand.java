package nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.KingdomFactionsPlugin;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class LagCommand extends KingdomFactionsCommand {

	public LagCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void execute() {
		Runtime runtime = Runtime.getRuntime();
		long i = runtime.maxMemory();
		long j = runtime.totalMemory();
		long k = runtime.freeMemory();
		long max = i / 1024L / 1024L;
		long total = j / 1024L / 1024L;
		long free = k / 1024L / 1024L;
		
		getSender().sendMessage(ChatColor.BLUE + "Worlds:");
		for (World w : Bukkit.getWorlds()) {
			getSender().sendMessage(ChatColor.BLUE + "World Name: " +ChatColor.WHITE + w.getName());
			getSender().sendMessage(ChatColor.BLUE + "Loaded Chunks: " +ChatColor.WHITE + w.getLoadedChunks().length);
			getSender().sendMessage(ChatColor.BLUE + "Loaded Entities: " +ChatColor.WHITE + w.getEntities().size());
		}
		getSender().sendMessage(ChatColor.BLUE + "--------------------------");
		getSender().sendMessage(ChatColor.BLUE + "Players: " + ChatColor.WHITE +  PlayerModule.getInstance().getPlayers().size());
		getSender().sendMessage(ChatColor.BLUE + "Maximum Memory: " +ChatColor.WHITE + max + " MB");
		getSender().sendMessage(ChatColor.BLUE + "Allocated Memory: " +ChatColor.WHITE + total + " MB");
		getSender().sendMessage(ChatColor.BLUE + "Free Memory: " +ChatColor.WHITE + free + " MB");
		getSender().sendMessage(ChatColor.BLUE + "TPS: " + colorTps(KingdomFactionsPlugin.getInstance().getTPS()));

	
	
	}
	/**
	private double getTPS(){
		double tps = KingdomFactionsPlugin.getInstance().getServer().getTPS()[0];
		String preTPS1 = tps + "";
		String preTPS2 = preTPS1.substring(5);
		String preTPS3 = preTPS1.replaceAll(preTPS2, "");
		
		double finalTPS = Double.parseDouble(preTPS3);
		
		return finalTPS;
	}
  */
	private String colorTps(int tps) {
		if(tps > 20) {
			return ChatColor.DARK_RED + "" + tps + " - Something went wrong.";
		}
		if (tps > 18) {
			return ChatColor.GREEN + "" + tps;
		}
		if (tps > 16) {
			return ChatColor.GOLD + "" + tps;
		}
		if (tps > 10) {
			return ChatColor.RED + "" + tps;
		}
		return ChatColor.DARK_RED + "" + tps;

	}

}
