package hearts.util;

/**
 * An immutable description of a playing card.
 */
public final class Card implements Comparable<Card>
{
	private static final int POINTS_QUEEN_OF_SPADES = 13;
	private static final int POINTS_HEARTS = 1;
	private static final int POINTS_OTHERS = 0;
	
	/**
	 * Enum type representing the rank of the card.
	 */
	public enum Rank 
	{ TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE}
	
	/**
	 * Enum type representing the suit of the card.
	 */
	public enum Suit 
	{ CLUBS, DIAMONDS, SPADES, HEARTS }
	
	private final Rank fRank;
	private final Suit fSuit;

	/**
	 * Create a new card object. 
	 * @param rank The rank of the card.
	 * @param suit The suit of the card.
	 */
	Card(Rank rank, Suit suit)
	{
		fRank = rank;
		fSuit = suit;
	}
	
	/**
	 * @return The number of points that this card is worth: 
	 * 13 points for the queen of spades, 1 point for each 
	 * card of hearts, and 0 point for all other cards.
	 */
	public int getPoints()
	{
		if(fSuit == Suit.SPADES && fRank==Rank.QUEEN)
		{
			return POINTS_QUEEN_OF_SPADES;
		}
		else if(fSuit == Suit.HEARTS)
		{
			return POINTS_HEARTS;
		}
		else
		{
			return POINTS_OTHERS;
		}
	}
	
	/**
	 * Obtain the rank of the card.
	 * @return An object representing the rank of the card.
	 */
	public Rank getRank()
	{
		return fRank;
	}
	
	/**
	 * @return A 2-character string representation of the hand.
	 */
	public String toCompactString()
	{
		String lReturn = "";
		if(fRank.ordinal()<=Rank.NINE.ordinal())
		{
			lReturn = new Integer(fRank.ordinal() + 2).toString();
		}
		else
		{
			lReturn = fRank.toString().substring(0, 1);
		}
		return lReturn + fSuit.toString().substring(0, 1);
	}
	
	/**
	 * Obtain the suit of the card.
	 * @return An object representing the suit of the card.
	 */
	public Suit getSuit()
	{
		return fSuit;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * @return See above.
	 */
	public String toString()
	{
		return fRank + " of " + fSuit;
	}
	
	/**
	 * Compares two cards according to game rules (rank only, ace high), heart trump.
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * @param card The card to compare to
	 * @return Returns a negative integer, zero, or a positive integer 
	 * as this object is less than, equal to, or greater than pCard
	 */
	public int compareTo(Card card)
	{
		if (getSuit() != card.getSuit())
		{
			return getSuit().ordinal() - card.getSuit().ordinal();
		}
		
		return getRank().ordinal() - card.getRank().ordinal();
	}
	
	/**
	 * Two cards are equal if they have the same suit and rank.
	 * Note that this is different from the behavior of compareTo, 
	 * which returns 0 (equal) independently of the suits.
	 * @param gCards The card to test.
	 * @return true if the two cards are equal
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object gCards) 
	{
		if(gCards == null)
		{
			return false;
		}
		if(gCards == this)
		{
			return true;
		}
		if(gCards.getClass() != getClass())
		{
			return false;
		}
		return (((Card)gCards).getRank() == getRank()) && (((Card)gCards).getSuit() == getSuit());
	}

	/** 
	 * The hashcode for a card is the suit*13 + that of the rank (perfect hash).
	 * @return the hashcode
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() 
	{
		return getSuit().ordinal() * Rank.values().length + getRank().ordinal();
	}
}
