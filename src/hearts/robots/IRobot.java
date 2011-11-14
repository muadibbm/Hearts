package hearts.robots;

import hearts.model.IImmutableGameState;
import hearts.util.Card;
import hearts.util.CardList;

/**
 * Behavior for the decision-making of robots players.
 */
public interface IRobot
{
	/**
	 * Returns a list of 3 cards to pass to another player.
	 * @param hand The current hand of the player.
	 * @return A list of 3 cards currently in hand that are to be
	 * passed to another player.
	 */
	CardList getCardsToPass(CardList hand);
	
	/**
	 * Chooses a card to play next. The card must be playable (follow suit or
	 * anything if the hand is void in the suit led.)
	 * @param pGame The game status object.
	 * @param pPlayer The name of the current player.
	 * @param pPassedTo The name of the player cards were passed to. Null if none.
	 * @param hand The list of cards currently in the player's hand.
	 * @return A single card selected for play.
	 */
	Card getCardToPlay(IImmutableGameState pGame, String pPlayer, String pPassedTo, CardList hand);
}
