package hearts.model;

import hearts.util.Card;
import hearts.util.CardList;
import hearts.util.Card.Suit;
import hearts.util.CardListException;
import hearts.util.Constants;

/**
 * Represents the state of a player.
 */
public class Player 
{
	private String fName;
	private CardList fHand;
	private CardList fHandBuffer;

	private int fScore;
	
	/**
	 * Create a new player.
	 * @param pName The name of the player.
	 */
	public Player(String pName)
	{
		this.fName = pName;
		this.fHand = new CardList(Constants.NUMBER_OF_TRICKS);
		this.fScore = 0;
		this.fHandBuffer = new CardList(Constants.NUMBER_OF_CARDS_PASSED);
	}
	
	/**
	 * @return The name of the player.
	 */
	public String getName()
	{
		return this.fName;
	}
	
	/** 
	 * @return The name of the player.
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return this.fName;
	}
	
	/**
	 * Clears the player's hand.
	 */
	public void clearHand()
	{
		this.fHand.clear();
	}
	
	/**
	 * Clears the player's buffer.
	 */
	public void clearHandBuffer()
	{
		this.fHandBuffer.clear();
	}
	
	/**
	 * Add cards to the player's hand.
	 * @param pCards The cards to add.
	 */
	public void addCards(CardList pCards)
	{
		this.fHand.addAll(pCards);
	}
	
	/**
	 * Add cards to the player's handBuffer.
	 * @param pCards The cards to add.
	 */
	public void addCardsBuffer(CardList pCards)
	{
		this.fHandBuffer.addAll(pCards);
	}
	
	/**
	 * @return A string representing the player's hand.
	 */
	public String getHandString()
	{
		return this.fHand.toString();
	}
	
	/**
	 * Remove the cards in pCards from the player's hand.
	 * @param pCards The list of cards to remove.
	 * @throws ModelException if any of the cards to remove is not the
	 * player's hand.
	 */
	public void removeCards(CardList pCards) throws ModelException
	{
		for (Card card : pCards)
		{
			if (!this.fHand.contains(card))
			{
				throw new ModelException("This card is not in the player's hand");
			}
			this.fHand.remove(card);
		}
	}
	
	/**
	 * Adds a card to the player's hand.
	 * @param pCard The card to add.
	 * @throws ModelException If the player's hand is full.
	 */
	public void addCard(Card pCard) 
	{
		try
		{
			fHand.add(pCard);
		} 
		catch (CardListException e)
		{
			throw new ModelException("Player's hand is full"); 
		}
	}
	
	/**
	 * Removed the card from the player's hand.
	 * @param pCard The card to remove.
	 * @throws ModelException if pCard is not in the hand.
	 */

	public void removeCard(Card pCard)
	{
		try
		{
			this.fHand.remove(pCard);
		} 
		catch (CardListException e)
		{
			throw new ModelException("Card is not in the hand");
		}
	}
	
	/**
	 * @return A copy of the hand.
	 */
	public CardList getHand()
	{
		return this.fHand.clone();
	}
	
	/**
	 * @return A copy of the handBuffer.
	 */
	public CardList getHandBuffer()
	{
		return this.fHandBuffer.clone();
	}
	
	/**
	 * @return The player's score
	 */
	public int getScore()
	{
		return this.fScore;
	}
	
	/**
	 * Sets the player's score to 0.
	 */
	public void resetScore()
	{
		this.fScore = 0;
	}
	
	/**
	 * Returns all the cards in the hand that match a particular suit.
	 * @param pSuit The suit to match.
	 * @return A copy of the hand with only the cards matching the suit.
	 */
	public CardList getCardsFor(Suit pSuit)
	{
		return this.fHand.getCardsOf(pSuit);
	}
	
	/**
	 * Adds pPoints to the player's score.
	 * @param pPoints The number of points to add.
	 */
	public void addToScore(int pPoints)
	{
		this.fScore = this.fScore + pPoints;
	}
}
