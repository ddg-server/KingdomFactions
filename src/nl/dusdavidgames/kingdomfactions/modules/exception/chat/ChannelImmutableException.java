package nl.dusdavidgames.kingdomfactions.modules.exception.chat;

public class ChannelImmutableException extends ChannelException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ChannelImmutableException(String channelName) {
		super("Kanaal "+channelName+" is onveranderlijk.");
	}

}
