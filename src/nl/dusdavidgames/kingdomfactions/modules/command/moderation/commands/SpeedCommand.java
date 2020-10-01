package nl.dusdavidgames.kingdomfactions.modules.command.moderation.commands;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SpeedCommand extends KingdomFactionsCommand
{

	public SpeedCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		Player player = PlayerModule.getInstance().getPlayer(getSender()).getPlayer();
		   try
	        {
			   if(getArgs()[0].equalsIgnoreCase("reset")) {
				   if (player.isFlying())
		            {
		            //  player.setFlySpeed((float)(speedlevel / 10.0D));
		              player.sendMessage(Messages.getInstance().getPrefix() + "Je hebt je Fly speed gereset.");
		              broadcast(PlayerModule.getInstance().getPlayer(player).getFormattedName() +ChatColor.YELLOW + " Want zijn Fly speed gereset.");
		            }
		            else
		            {
		              //player.setWalkSpeed((float)(speedlevel / 10.0D));
		              player.sendMessage(Messages.getInstance().getPrefix() + "Je hebt je Walk speed gereset.");
		              broadcast(PlayerModule.getInstance().getPlayer(player).getFormattedName() +ChatColor.YELLOW + " Heeft zijn Walk Speed gereset.");
		            } 
				  
			   }
	          double speedlevel = Double.parseDouble(getArgs()[0]);
	         
	            if (player.isFlying())
	            {
	              player.setFlySpeed((float)(speedlevel / 10.0D));
	              player.sendMessage(Messages.getInstance().getPrefix() + "Fly speed aangepast naar " + speedlevel + ".");
	              broadcast(PlayerModule.getInstance().getPlayer(player).getFormattedName() +ChatColor.YELLOW + " zette zijn fly speed naar " + speedlevel);
	            }
	            else
	            {
	              player.setWalkSpeed((float)(speedlevel / 10.0D));
	              player.sendMessage(Messages.getInstance().getPrefix() + "Walk speed aangepast naar " + speedlevel + ".");
	              broadcast(PlayerModule.getInstance().getPlayer(player).getFormattedName() +ChatColor.YELLOW + " zette zijn walk speed naar " + speedlevel);
	            }
	        }
	        catch (NumberFormatException e)
	        {
	        	player.sendMessage(Messages.getInstance().getPrefix() + "Vul aub een correct nummer in.");
	        } catch(IllegalArgumentException e) {
	        	player.sendMessage(Messages.getInstance().getPrefix() + "Vul aub een toegestaan aantal in.");
	        }
	      }

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
		
	}

