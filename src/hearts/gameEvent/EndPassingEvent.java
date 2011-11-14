package hearts.gameEvent;

import hearts.util.CardList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Stores the data for the end of cards passing.
 */
public class EndPassingEvent implements IEvent
{
	private HashMap<String, CardList> fPlayerHand = new HashMap<String, CardList>();
	/**
	 * Sets the cards of the hand of the specified player
	 * after the pass.
	 * @param name Name of the player.
	 * @param hand Hand of the player.
	 */
	public void setHand(String name, CardList hand)
	{
		fPlayerHand.put(name, hand);
	}
	/**
	 * Get the card played.
	 * @param name Name of the player.
	 * @return card Current hand of the player.
	 */
	public CardList getHand(String name)
	{
		return fPlayerHand.get(name);
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
}
