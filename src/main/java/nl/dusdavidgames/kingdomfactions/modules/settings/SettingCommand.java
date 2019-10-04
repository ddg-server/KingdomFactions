package nl.dusdavidgames.kingdomfactions.modules.settings;

import net.md_5.bungee.api.ChatColor;
import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.SubCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class SettingCommand extends KingdomFactionsCommand {

    public SettingCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
        super(name, permission, info, usage, sub, allowConsole);
    }

    @Override
    public void init() {
        this.registerSub(new SubCommand("set", "kingdomfactions.command.setting.set", "Verzet een instelling!") {

            @Override
            public void execute(String[] args) throws KingdomFactionsException {
                Setting s = null;
                try {
                    s = Setting.valueOf(getArgs()[1].toUpperCase());
                } catch (IllegalArgumentException e) {
                    getUser().sendMessage(Messages.getInstance().getPrefix() + "Onbekende optie!");
                    return;
                }
                boolean b = Boolean.parseBoolean(getArgs()[2]);
                s.setEnabled(b);
                getUser().sendMessage(Messages.getInstance().getPrefix() + "Optie " + s + " verzet naar " + b);
            }
        });
        this.registerSub(new SubCommand("list", "kingdomfactions.command.setting.list",
                "Verkrijg een lijst met alle instellingen!") {

            @Override
            public void execute(String[] args) throws KingdomFactionsException {
                getUser().sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "---------------------------");
                for (Setting s : Setting.values()) {
                    getUser().sendMessage(ChatColor.BLUE + s.toString() + ": " + s.isEnabled());
                }
                getUser().sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "---------------------------");

            }
        });

    }

    @Override
    public void execute() throws KingdomFactionsException {
    }

}
