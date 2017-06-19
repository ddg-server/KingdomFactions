package nl.dusdavidgames.kingdomfactions.modules.command.help;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import nl.dusdavidgames.kingdomfactions.modules.command.CommandModule;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;



public class HelpCommand extends KingdomFactionsCommand {

	

	public HelpCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		if(getArgs().length == 0) {
			sendHelpPage(getSender(), 1);
		} else {
	   try{
		   sendHelpPage(getSender(), Integer.parseInt(getArgs()[0]));
	   } catch(NumberFormatException e) {
		   getSender().sendMessage(Messages.getInstance().getPrefix() + "Gelieve een getal invullen.");
	   }
		}
	}

	public void sendHelpPage(CommandSender sender, int page){
        if(page > 5) {
        	sender.sendMessage(Messages.getInstance().getPrefix() + "Onbekende pagina!");
        	return;
        }
		int pageNum = (5 *  page) -5;
		int maxHelp = 5*page;
		sender.sendMessage(ChatColor.BLUE +""+ChatColor.STRIKETHROUGH+ "------------------------------------");
		for(int i = pageNum; i < maxHelp; i++){
		if(i < CommandModule.getInstance().getCommand().size()){
			KingdomFactionsCommand c = CommandModule.getInstance().getCommand().get(i);
			if(!sender.hasPermission(c.getPermission())) continue;
			if(c.getUsage() == "") {
				sender.sendMessage(ChatColor.GOLD + "/" + c.getName() + " " + ChatColor.LIGHT_PURPLE + c.getInfo());
			} else {
				sender.sendMessage(ChatColor.GOLD + "/" + c.getName() + " " + ChatColor.GREEN + c.getUsage() + " " + ChatColor.LIGHT_PURPLE + c.getInfo());
			}
		}else {
			sender.sendMessage(ChatColor.BLUE +""+ChatColor.STRIKETHROUGH+ "------------------------------------");
             return;
		}
		}

		int maxPages = CommandModule.getInstance().getCommand().size() / 5;
		int newPage = page + 1;
		
		if(newPage <= maxPages){
			sender.sendMessage(ChatColor.BLUE + "Kijk verder op pagina "+ newPage);
		}
		sender.sendMessage(ChatColor.BLUE +""+ChatColor.STRIKETHROUGH+ "------------------------------------");

		}

	@Override
	public void init() {
		return;
	}
}
