package hearts.gameEvent;
import hearts.util.Card;
import hearts.util.Card.Suit;

/**
 * Stores the data for the play event.
 */
public class PlayEvent implements IEvent
{
	private boolean fIsHuman = false;
	private String fName;
	private Card fCard;
	private Suit fSuit;
	/**
	 * Construct a play event.
	 * @param name Name of the player.
	 * @param card Card played.
	 */
	public PlayEvent(String name, Card card, Suit suit)
	{
		fName = name;
		fCard = card;
		fSuit = suit;
	}
	
	/**
	 * Get the name of the player.
	 * @return name of the player.
	 */
	public String getName()
	{
		return fName;
	}
	/**
	 * Get the card played.
	 * @return card played.
	 */
	public Card getCard()
	{
		return fCard;
	}
	/**
	 * Set play event as human.
	 */
	public void setHuman(boolean isHuman)
	{
		fIsHuman = isHuman;
	}
	/**
	 * Set play event as human.
	 */
	public boolean isHuman()
	{
		return fIsHuman;
	}
	/**
	 * Get the suit of the trick.
	 * @return Suit led.
	 */
	public Suit getSuitLed()
	{
		return fSuit;
	}
}
