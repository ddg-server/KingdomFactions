package nl.dusdavidgames.kingdomfactions.modules.memleak;

import lombok.Getter;
import lombok.Setter;
import nl.dusdavidgames.kingdomfactions.modules.memleak.command.MemLeakCommand;

public class MemLeakModule {

	private static @Getter @Setter MemLeakModule instance;

	public MemLeakModule() {
		setInstance(this);
		
		new MemLeakMessage();

		new MemLeakCommand("data", "kingdomfactions.command.data", "Main command for data management", "", true, true)
				.registerCommand();
	}
}