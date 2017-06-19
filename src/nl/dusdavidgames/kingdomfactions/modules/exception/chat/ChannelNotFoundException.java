package nl.dusdavidgames.kingdomfactions.modules.exception.chat;

public class ChannelNotFoundException extends ChannelException {

	private static final long serialVersionUID = 1L;

	public ChannelNotFoundException() {
		super("Chatkanaal niet gevonden!");
	}

	public ChannelNotFoundException(String channelName) {
		super("Kon het chat kanaal " + channelName + " niet vinden" );
	}

}
