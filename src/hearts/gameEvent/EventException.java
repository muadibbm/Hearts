package hearts.gameEvent;

/**
 * Indicates an attempt to draw a card from an empty deck.
 */
@SuppressWarnings("serial")
public class EventException extends RuntimeException 
{
	/**
	 * Generates a standard error message.
	 * @param message Error string.
	 */
	public EventException(String message) 
	{
		super(message);
	}
}
