package hearts.game;

/**
 * Indicates a misuse of a model class.
 */
@SuppressWarnings("serial")
public class GameException extends RuntimeException 
{
	/**
	 * Generates a standard error message.
	 * @param message The message for the exception.
	 */
	public GameException(String message) 
	{
		super(message);
	}
}
