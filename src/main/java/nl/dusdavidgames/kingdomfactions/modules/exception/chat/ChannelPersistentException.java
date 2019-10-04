package nl.dusdavidgames.kingdomfactions.modules.exception.chat;

public class ChannelPersistentException extends ChannelException {

	private static final long serialVersionUID = 1L;

	public ChannelPersistentException() {
		super("Dit chatkanaal is blijvend.");
	}

	public ChannelPersistentException(String name) {
		super("Chatkanaal " + name + " is blijvend.");
	}

}
