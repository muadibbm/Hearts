package hearts.gameEvent;

/**
 * Stores the data for the new trick event.
 */
public class NewTrickEvent implements IEvent
{
	private String fPlayer;
	
	/**
	 * Construct a new trick event.
	 * @param player Name of the current player.
	 */
	public NewTrickEvent(String player)
	{
		fPlayer = player;
	}
	/**
	 * Get the first player to start the trick.
	 * @return Player name
	 */
	public String getCurrentPlayer()
	{
		return fPlayer;
	}

}
