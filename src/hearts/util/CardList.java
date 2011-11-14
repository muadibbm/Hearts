package hearts.util;

import hearts.util.Card.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A cloneable, bounded, sorted list of cards. The cards are sorted by increasing rank
 * and by suit. Does not support duplicates.
 */
public class CardList implements Cloneable, Iterable<Card>
{
	private List<Card> fCardList;
	private final int fMaxCards;
	
	/**
	 * Create a new bounded card list.
	 * @param pMaxCards The maximum number of cards allowed in this list.
	 */
	public CardList(int pMaxCards)
	{
		fMaxCards = pMaxCards;
		fCardList = new ArrayList<Card>(fMaxCards);
	}
	
	/**
	 * @return The number of cards actually in the list.
	 */
	public int size()
	{
		return fCardList.size();
	}
	
	/**
	 * @return Returns true if the card list is empty.
	 */
	public boolean isEmpty()
	{
		return fCardList.isEmpty();
	}
	
	/**
	 * getter.
	 * @param i index
	 * @return card at index i
	 */
	public Card get(int i)
	{
		return fCardList.get(i);
	}
	
	/**
	 * Add a card to the list.
	 * @param card The card to add.
	 * @throws CardListException if a card is added to a full list or already exists in the list.
	 * @precondition Explicit argument card is not null
	 */
	public void add(Card card) throws CardListException
	{
		if(fCardList.contains(card))
		{
			throw new CardListException("Card already in the list.");
		}
		if(fCardList.size() >= fMaxCards)
		{
			throw new CardListException("Card list is already full.");
		}
		fCardList.add(card);
		this.sort();
	}
	
	/**
	 * Similar to add, but with an indicated index.
	 * @param card
	 * @param i index
	 * @throws CardListException
	 */
	public void add(int i, Card card) throws CardListException
	{
		if(fCardList.contains(card))
		{
			throw new CardListException("Card already in the list.");
		}
		if(fCardList.size() >= fMaxCards)
		{
			throw new CardListException("Card list is already full.");
		}
		fCardList.add(i, card);
		this.sort();
	}
	
	/**
	 * Adds all the cards in cardList to this list.
	 * @param cardList The list of cards to add.
	 * @throws CardListException if a card is added to a full list or already exists in the list.
	 * @precondition Explicit argument cardlist is not null
	 */
	public void addAll(CardList cardList)  throws CardListException
	{
		for(Card card : cardList)
		{
			if(fCardList.contains(card))
			{
				throw new CardListException("Card already in the list.");
			}
			if(fCardList.size() >= fMaxCards)
			{
				throw new CardListException("Card list is already full.");
			}
			fCardList.add(card);
		}
		this.sort();
	}
	
	/**
	 * Removes the first card from the list that is equal to card. 
	 * Does nothing if the card is not in the list.
	 * @param card The card to remove (or one equal to it).
	 * @throws CardListException if the card is not in the list.
	 * @precondition Explicit argument card is not null
	 */
	public void remove(Card card) throws CardListException
	{
		if(!fCardList.contains(card))
		{
			throw new CardListException("Card not in the list.");
		}
		for(int i = 0; i<fCardList.size(); i++)
		{
			if(fCardList.get(i).equals(card))
			{
				fCardList.remove(i);
				break;
			}
		}
		//fCardList.remove(card); //Does this remove the *first* card?
		this.sort();
	}
	
	/**
	 * Removes card at index i and returns it.
	 * @param index The index to select the card.
	 * @return The card at index i.
	 */
	public Card remove(int index)
	{
		return fCardList.remove(index);
	}

	@Override
	public Iterator<Card> iterator()
	{
		/* Only this? Where are the next, hasNext & remove methods? */
		return fCardList.iterator();
	}
	
	/**
	 * Shuffle the cards in the list.
	 */
	public void shuffle()
	{
		Collections.shuffle(fCardList);
	}
	
	/**
	 * Sort the cards in the list in ascending order.
	 */
	public void sort()
	{
		
		// System.out.println("sorting list: " + fCardList.toString());
		if(this.size() <= 1)
		{
			return;
		}
		int i;
		int j;
		ArrayList<Card> sortedHearts = new ArrayList<Card>(fCardList.size());
		ArrayList<Card> sortedClubs = new ArrayList<Card>(fCardList.size());
		ArrayList<Card> sortedSpades = new ArrayList<Card>(fCardList.size());
		ArrayList<Card> sortedDiamonds = new ArrayList<Card>(fCardList.size());
		ArrayList<Card> sorted;
		//ArrayList<Card> sorted = new ArrayList<Card>(fCardList.size());

		boolean added;
		Card card;
		for(j = 0; j < fCardList.size(); j++)
		{
			card = fCardList.get(j);
			if(card.getSuit() == Card.Suit.HEARTS)
			{
				sorted = sortedHearts;
			}
			else if(card.getSuit() == Card.Suit.CLUBS)
			{
				sorted = sortedClubs;
			}
			else if(card.getSuit() == Card.Suit.SPADES)
			{
				sorted = sortedSpades;
			}
			else if(card.getSuit() == Card.Suit.DIAMONDS)
			{
				sorted = sortedDiamonds;
			}
			else
			{
				return;
			}
			
			//System.out.println("j = " + j);
			added = false;
			if(sorted.isEmpty())
			{
				sorted.add(card);
				added = true;
			}
			else
			{
				for(i = 0; i< sorted.size(); i++)
				{
					//System.out.println("i = " + i);

					if (card.compareTo(sorted.get(i)) < 0)
					{
						sorted.add(i, card);
						added = true;
						break;
					}
				}
				if(!added)
				{
					sorted.add(card);	
				}
			}
		}
		fCardList.clear();
		for(i = 0; i < 4; i++)
		{
			if(i == 0)
			{
				sorted = sortedHearts;
			}
			else if(i == 1)
			{
				sorted = sortedClubs;
			}
			else if(i == 2)
			{
				sorted = sortedSpades;
			}
			else if(i ==3)
			{
				sorted = sortedDiamonds;
			}
			else
			{
				sorted = null;
			}
			
			for(j = 0; j < sorted.size(); j++)
			{
				fCardList.add(sorted.get(j));
			}
		}
		
		//System.out.println("end : " + fCardList.toString());
    }
		
	/**
	 * Removes all the cards in the list.
	 */
	public void clear()
	{
		fCardList.clear();
	}
	
	/**
	 * Tests if two CardList variables are equals.
	 * @param c Object (CardList) to compare with this.
	 * @return true iff both pointers refer to the same object, or both objects contain the same cards
	 * in the same order.
	 */
	@Override
	public boolean equals(Object c)
	{
		if(c == null || c.getClass() != this.getClass())
		{
			return false;
		}
		if(c == this)
		{
			return true;
		}
		if(this.size() != ((CardList) c).size())
		{
			return false;
		}
		int index = 0;
		Iterator<Card> itc = ((CardList) c).iterator();
	    while (itc.hasNext())
	    {
	    	Card element = itc.next();
	    	boolean sameCard;
	    	sameCard = element.equals(fCardList.get(index));
	    	if(!sameCard)
	    	{
	    		return false;
	    	}
	    	index++;
	    }
	    return true;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1;
		final int hash31 = 31;
		final int hash13 = 13;
		for (Card c : fCardList)
		{
			hash *= hash31*(c.getSuit().ordinal()*hash13 + c.getRank().ordinal());
		}
		return hash;
	}

	/**
	 * Returns a one line compact representation of all the cards in the list.
	 * @return A string with all the cards in the hand.
	 */
	@Override
	public String toString()
	{
		final int charsPerCard = 4;
		StringBuffer sb = new StringBuffer(charsPerCard*Constants.NUMBER_OF_CARDS+charsPerCard);
		for (Card c : fCardList)
		{
			sb.append(c.toCompactString() + " ");
		}
		return sb.toString();
	}

	@Override
	public CardList clone()
	{
	   try
	    {
	    	CardList cloned = (CardList) super.clone();
	    	cloned.fCardList = new ArrayList<Card>(fCardList.size());
	        for(Card card: fCardList)
	        {
	        	cloned.fCardList.add(new Card(card.getRank(), card.getSuit()));
	        }
	        return cloned;
	    }
	    catch (CloneNotSupportedException e)
	    {
	        System.out.println("Cloning not allowed.");
	        return null;
	    }
	}
	
	/**
	 * Returns true if the list contains card.
	 * @param card The card to check.
	 * @return True if card is in the list.
	 * @precondition Explicit argument card is not null
	 */
	public boolean contains(Card card)
	{
		return fCardList.contains(card);
	}
	
	/**
	 * Returns all the cards in the hand that match a particular suit.
	 * @param pSuit The suit to match.
	 * @return A copy of the hand with only the cards matching the suit.
	 * @precondition Explicit argument pSuit is not null
	 */
	public CardList getCardsOf(Suit pSuit)
	{
		CardList c = new CardList(fMaxCards);
		for(Card card : fCardList)
		{
			if(card.getSuit().equals(pSuit))
			{
				c.add(card);
			}
		}
		return c;
	}
	
	/**
	 * Returns all the cards in the hand that do not match a particular suit.
	 * @param pSuit The suit to avoid.
	 * @return A copy of the hand with only the cards not matching the suit.
	 * @precondition Explicit argument pSuit is not null
	 */
	public CardList getCardsNotOf(Suit pSuit)
	{
		CardList c = new CardList(fMaxCards);
		for(Card card : fCardList)
		{
			if(!card.getSuit().equals(pSuit))
			{
				c.add(card);
			}
		}
		return c;
	}
}
