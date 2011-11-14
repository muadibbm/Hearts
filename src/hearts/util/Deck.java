package hearts.util;

/**
 * Models a deck of 52 cards (no joker).
 */
public class Deck 
{
	private final CardList fDeck;
	
	/**
	 * Creates a new deck of 52 cards, not shuffled.
	 */
	public Deck()
	{
		fDeck = new CardList(Constants.NUMBER_OF_CARDS);
		refillDeck();
	}
	
	/**
	 * Utility function : Clear and refill deck with all cards.
	 */
	private void refillDeck()
	{
		fDeck.clear();
		for (Card.Suit suit : Card.Suit.values())
		{
            for (Card.Rank rank : Card.Rank.values())
            {
            	fDeck.add(new Card(rank, suit));
            }
		}
	}
	
	/**
	 * Refill and shuffle the deck of cards by randomizing
	 * their order.
	 */
	public void shuffle()
	{
		refillDeck();
		fDeck.shuffle();
	}
	
	/**
	 * Draws a card from the deck and removes the card from the deck.
	 * @return The card drawn.
	 * @throws EmptyDeckException if called on an empty deck.
	 * @post final.size() == initial.size() - 1 or exception
	 */
	public Card draw() throws EmptyDeckException
	{
		if (fDeck.isEmpty())
		{
			throw new EmptyDeckException();
		}
		return fDeck.remove(0);
	}
	
	/**
	 * Returns the size of the deck.
	 * @return The number of cards in the deck.
	 */
	public int size()
	{
		return fDeck.size();
	}
	
	/**
	 * @return Returns true if the deck is empty.
	 */
	public boolean isEmpty()
	{
		return fDeck.isEmpty();
	}
}
