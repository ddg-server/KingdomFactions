package nl.dusdavidgames.kingdomfactions.modules.shops.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import nl.dusdavidgames.kingdomfactions.modules.command.KingdomFactionsCommand;
import nl.dusdavidgames.kingdomfactions.modules.command.SubCommand;
import nl.dusdavidgames.kingdomfactions.modules.exception.KingdomFactionsException;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildLevel;
import nl.dusdavidgames.kingdomfactions.modules.nexus.build.type.BuildingType;
import nl.dusdavidgames.kingdomfactions.modules.player.PlayerModule;
import nl.dusdavidgames.kingdomfactions.modules.shops.ShopsModule;
import nl.dusdavidgames.kingdomfactions.modules.shops.shop.AddItemMenu;
import nl.dusdavidgames.kingdomfactions.modules.utils.Messages;

public class ShopCommand extends KingdomFactionsCommand {

	public ShopCommand(String name, String permission, String info, String usage, boolean sub, boolean allowConsole) {
		super(name, permission, info, usage, sub, allowConsole);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		this.registerSub(
				new SubCommand("additem", "kingdomfactions.command.shop.additem", "Voeg een item toe aan de shop!") {

					@Override
					public void execute(String[] args) {
						if (!(getSender() instanceof Player)) {
							getSender().sendMessage(Messages.getInstance().getPrefix()
									+ "Dit command is niet voor de console beschikbaar!");
							return;
						}

						if (args.length < 4) {
							getSender().sendMessage(Messages.getInstance().getPrefix()
									+ "/shop additem <type> <level> <buyprice> <sellprice> <displayname> [limit] [extraData]");
							getSender().sendMessage(Messages.getInstance().getPrefix()
									+ "Example: /shop additem nexus 1 1000 -1 false 1 Spider");
							return;
						}

						Player player = null;

						if (getSender() instanceof Player)
							player = (Player) getSender();

						String type = getArgs()[1].toUpperCase();
						String level = "LEVEL_" + getArgs()[2];
						int buyPrice = Integer.parseInt(getArgs()[3]);
						int sellprice = Integer.parseInt(getArgs()[4]);
						boolean useDisplayname = Boolean.parseBoolean(getArgs()[5]);

						boolean goodLevel = false;
						boolean goodType = false;

						for (BuildingType t : BuildingType.values()) {
							if (t.name().equalsIgnoreCase(type)) {
								goodType = true;
							}
						}

						if (!goodType) {
							player.sendMessage(Messages.getInstance().getPrefix() + "Type niet gevonden! Types: "
									+ BuildingType.values().toString());
							return;
						}

						for (BuildLevel levels : BuildLevel.values()) {
							if (levels.name().equalsIgnoreCase(level)) {
								goodLevel = true;
							}
						}

						if (!goodLevel) {
							player.sendMessage(Messages.getInstance().getPrefix() + "Level niet gevonden! Levels: "
									+ BuildLevel.values().toString());
							return;
						}

						int limit = -1;

						if (args.length > 6) {
							limit = Integer.parseInt(getArgs()[6]);
						}

						String extraData = " ";

						if (args.length > 7) {
							extraData = getArgs()[7];
						}

						new AddItemMenu(PlayerModule.getInstance().getPlayer(player), type, level, buyPrice, sellprice,
								useDisplayname, extraData, limit);
					}
				});

		this.registerSub(new SubCommand("open", "kingdomfactions.command.shop.open", "Open een shop menu!") {

			@Override
			public void execute(String[] args) {
				if (!(getSender() instanceof Player)) {
					getSender().sendMessage(
							Messages.getInstance().getPrefix() + "Dit command is niet voor de console beschikbaar!");
					return;
				}

				String buildingName = getArgs()[1];

				BuildingType bType = null;

				try {
					bType = BuildingType.valueOf(buildingName.toUpperCase());
				} catch (Exception e) {
				}

				if (bType == null) {
					getSender().sendMessage(
							Messages.getInstance().getPrefix() + "Gebruik /kingdom openshop <Type> <Level>");
					getSender().sendMessage("Types: NEXUS, BLACKSMITH, BAKERY, MAGETOWER en FORGE.");
					return;
				}

				String level = "LEVEL_" + getArgs()[2];
				BuildLevel sLevel = null;
				try {
					sLevel = BuildLevel.valueOf(level);
				} catch (Exception e) {
				}

				if (sLevel == null) {
					getSender().sendMessage(
							Messages.getInstance().getPrefix() + "Gebruik /kingdom openshop <Type> <Level>");
					getSender().sendMessage("Levels: 1, 2, 3, 4, 5, 6, 7 en 8.");
					return;
				}

				Player player = null;

				if (getSender() instanceof Player)
					player = (Player) getSender();

				player.openInventory(ShopsModule.getInstance().getShop(bType, sLevel).getShopInventory());
				broadcast(PlayerModule.getInstance().getPlayer(getSender()).getFormattedName() + ChatColor.YELLOW
						+ " heeft een shop geopend type " + bType + " level " + sLevel + ".");
			}
		});

		this.registerSub(new SubCommand("reload", "kingdomfactions.command.shop.reload", "reload de shops!") {

			@Override
			public void execute(String[] args) {
				getSender().sendMessage(Messages.getInstance().getPrefix() + "Shops herladen...");
				ShopsModule.getInstance().reload(getSender());
			}
		});
	}

	@Override
	public void execute() throws KingdomFactionsException {
		// TODO Auto-generated method stub

	}
}