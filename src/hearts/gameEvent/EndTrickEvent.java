package hearts.gameEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Stores the data for the end trick event.
 */
public class EndTrickEvent implements IEvent
{
	private HashMap<String, Integer> fPlayerScore = new HashMap<String, Integer>();
	/**
	 * Set the score of the specified player.
	 * @param name Name of the player.
	 * @param score Score of the player.
	 */
	public void setPlayerScore(String name, int score)
	{
		fPlayerScore.put(name, new Integer(score));
	}
	/**
	 * Get the score of the specified player.
	 * @param name Name of the player.
	 * @return Score integer.
	 */
	public Integer getPlayerScore(String name)
	{
		return fPlayerScore.get(name);
	}
	/**
	 * Get the iterator over the entries of
	 * player with their score.
	 * @return Iterator.
	 */
	public Iterator<Entry<String, Integer>> getScoreIterator()
	{
		return fPlayerScore.entrySet().iterator();
	}
}
