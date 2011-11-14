package hearts.gameEvent;

import hearts.util.CardList;

/**
 * Stores the data for the pass event.
 */
public class PassEvent implements IEvent
{
	private String fFrom;
	private String fTo;
	private CardList fCardsPassed;
	/**
	 * Sets the players involved in the passing.
	 * @param from Name of the player who passes.
	 * @param to Name of the player who receives.
	 */
	public void setPlayers(String from, String to)
	{
		fFrom = from;
		fTo = to;
	}
	/**
	 * Sets the cards passed.
	 * @param cards Cards passed.
	 * @pre Clone the cardlist before the pass
	 */
	public void setCardsPassed(CardList cards)
	{
		fCardsPassed = cards;
	}
	/**
	 * Get the name of the player.
	 * @return name of the player.
	 */
	public String getFrom()
	{
		return fFrom;
	}
	
	/**
	 * Get the name of the player.
	 * @return name of the player.
	 */
	public String getTo()
	{
		return fTo;
	}
	/**
	 * Get the card passed.
	 * @return card passed.
	 */
	public CardList getCardsPassed()
	{
		return fCardsPassed;
	}
}

