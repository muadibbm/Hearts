package hearts.model;

import hearts.util.Card;
import hearts.util.CardList;

/**
 * Allows querying the state of the game without modifying its state.
 */
public interface IImmutableGameState
{
	/**
	 * @return true if a Hearts card has been played so far.
	 */
	boolean heartsPlayed();
	
	/**
	 * @return The number of tricks completed (4 cards played).
	 */
	int tricksCompleted();
	
	/**
	 * Returns all the cards accumulated by a player by winning tricks.
	 * @param pPlayer The name of the player to query.
	 * @return All the cards won by the player.
	 */
	CardList getCardsAccumulated(String pPlayer);
	
	/**
	 * Returns the current score of a player in this game. 
	 * @param pPlayer The name of the player to query.
	 * @return The trick score accumulated in this game.
	 */
	int getScore(String pPlayer);
	
	/**
	 * @return The suit led in this trick.
	 * @throws ModelException If there is no play (so no suit was led).
	 */
	Card.Suit getSuitLed() throws ModelException;
	
	/**
	 * @return The trick currently being played.
	 */
	Trick getCurrentTrick();
}
