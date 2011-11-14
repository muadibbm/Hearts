 package hearts.gameEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Stores the data for the end game event.
 */
public class EndGameEvent implements IEvent
{
	private String fGameWinner;
	private int fScore;
	private HashMap<String, Integer> fPlayerScores = new HashMap<String, Integer>();
	/**
	 * Construct an end game event.
	 * @param name Name of the game winner.
	 * @param score Score of the winner.
	 */
	public EndGameEvent(String name, int score)
	{
		fGameWinner = name;
		fScore = score;
	}

	/**
	 * Get the score of the game winner.
	 * @return Score int.
	 */
	public int getWinnerScore()
	{
		return fScore;
	}
	/**
	 * Get the name of the game winner.
	 * @return Name.
	 */
	public String getWinnerName()
	{
		return fGameWinner;
	}
	
	/**
	 * Set the score of the specified player.
	 * @param name Name of the player.
	 * @param score Score of the player.
	 */
	public void setPlayerScore(String name, int score)
	{
		fPlayerScores.put(name, new Integer(score));
	}
	/**
	 * Get the score of the specified player.
	 * @param name Name of the player.
	 * @return Score integer.
	 */
	public Integer getPlayerScore(String name)
	{
		return fPlayerScores.get(name);
	}
	
	
}
