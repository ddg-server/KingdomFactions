package nl.dusdavidgames.kingdomfactions.modules.command;

import java.util.ArrayList;

import lombok.Getter;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;

public abstract class SubCommand {

	
	public SubCommand(ArrayList<String> commands, String permission, String info) {
	  this.commands = commands;
	  this.mainCommand = commands.get(0);
	  this.permission = permission;
	  this.info = info;
	}
	
	public SubCommand(String[] command, String permission, String info) {
		for(String s : command) {
			this.commands.add(s);
		}
		this.mainCommand = command[0];
		this.permission = permission;
		this.info = info;
	}
	public SubCommand(String command, String permission, String info) {
		this(new String[] { command }, permission, info);

	}
	
	public SubCommand(String command, String permission, String info, boolean allowConsole) {
		this(command, permission, info);
		this.allowConsole = allowConsole;
	}
	
	
	
	
	
	private boolean allowConsole = true;
    private @Getter String info;
	private @Getter String permission;
	
	private @Getter String mainCommand;
	
	public ArrayList<String> commands = new ArrayList<String>();
	public abstract void execute(String[] args) throws KingdomFactionsException;
	
	public boolean allowConsole() {
		return allowConsole;
	}
	
	public void setAllowConsole(boolean allow) {
		this.allowConsole = allow;
	}
}
