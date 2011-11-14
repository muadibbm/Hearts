package hearts.util;

/**
 * Indicates an attempt to draw a card from an empty deck.
 */
@SuppressWarnings("serial")
public class CardListException extends RuntimeException 
{
	/**
	 * Generates a standard error message.
	 * @param pMessage The message to store.
	 */
	public CardListException(String pMessage) 
	{
		super(pMessage);
	}
}
