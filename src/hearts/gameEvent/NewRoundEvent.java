package hearts.gameEvent;

import hearts.game.GameEngine;
import hearts.util.CardList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Stores the data for the new round event.
 */
public class NewRoundEvent implements IEvent
{
	private HashMap<String, CardList> fPlayerHand = new HashMap<String, CardList>();
	private String fDealer;
	private GameEngine.PassingDirection fDirection;
	
	/**
	 * set the direction.
	 */
	public void setPassingDirection(GameEngine.PassingDirection direction)
	{
		fDirection = direction;
	}
	
	/**
	 * get the direction.
	 */
	public GameEngine.PassingDirection getPassingDirection()
	{
		return fDirection;
	}
	
	/**
	 * Set the dealer.
	 * @param name Name of the dealer.
	 */
	public void setDealer(String name)
	{
		fDealer = name;
	}
	
	/**
	 * Sets the cards of the hand of the specified player.
	 * @param name Name of the player.
	 * @param hand Hand of the player.
	 * @pre Clone the card list argument.
	 */
	public void setHand(String name, CardList hand)
	{
		fPlayerHand.put(name, hand);
	}

	/**
	 * Get the hand of the specified player.
	 * @param name Hand of the player.
	 * @return Hand.
	 * @throws EventException on no mapping for the name parameter
	 */
	public CardList getPlayerHand(String name) throws EventException
	{
		CardList c = fPlayerHand.get(name);
		if(c == null)
		{
			throw new EventException("No player corresponding to name, cannot obtain hand.");
		}
		return c;
	}
	/**
	 * Get the iterator over the hands of the specified players.
	 * TEMP - Only for the logger.
	 * @return Iterator of the name/hands entries.
	 */
	public Iterator<Entry<String, CardList>> getHandIterator()
	{
		return fPlayerHand.entrySet().iterator();
	}
	
	/**
	 * Get the current dealer name.
	 * @return Name.
	 */
	public String getDealer()
	{
		return fDealer;
	}
}
