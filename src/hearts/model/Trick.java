package hearts.model;

import hearts.util.Card;
import hearts.util.CardList;
import hearts.util.Card.Suit;
import hearts.util.Constants;

//using arraylist for creating a list of plays
import java.util.ArrayList;
import java.util.List;

/**
 * A sequence of up to four plays (one by each player), led by a "lead".
 * Mutable only from classes within the package.
 */
public class Trick 
{
	private ArrayList<Play> fPlays;
	
	/**
	 * Creates a new empty trick.
	 */
	public Trick()
	{	
		fPlays = new ArrayList<Play>();
	}
	
	/**
	 * Adds a plays to this trick. Must be package private so the class
	 * is mutable only from the package classes.
	 * @param pPlayer The player who played.
	 * @param card The card played.
	 * @throws ModelException if more than 4 plays are entered.
	 * @precondition Trick should contain at least one play
	 */
	void addPlay(String pPlayer, Card card) throws ModelException
	{
		if(fPlays.size()>= Constants.NUMBER_OF_PLAYERS)
		{
			throw new ModelException("More than 4 plays are entered.");
		}
		fPlays.add(new Play(pPlayer, card));
	}
	
	/**
	 * @return All the cards played in the trick. The cards are returned
	 * sorted in normal sorting order, not in the order of play.
	 * @precondition Trick should contain at least one play
	 * @postcondition sorted list of cards in the trick of type CardList
	 */
	public CardList getCards()
	{
		CardList cardList = new CardList(fPlays.size());
		
		for(int k = 0; k < fPlays.size(); k++)
		{
			cardList.add((fPlays.get(k)).getCard());
		}
		cardList.sort();
		return cardList;
	}
	
	/**
	 * @return The number of plays in the trick.
	 */
	public int cardsPlayed()
	{
		return fPlays.size();
	}
	
	/**
	 * @return The player who wins the trick to date.
	 * @throws ModelException is the trick is empty.
	 */
/*	public String getWinner() throws ModelException
	{
		String winner;
		Card maxCard;
		int count = 0;
		
		if(fPlays.size()==0) //Check if trick is empty, and return exception.
		{
			throw new ModelException("The trick is empty.");
		}
		
		Suit led = fPlays.get(0).getCard().getSuit();

		for (Play play: fPlays)
		{
			if (play.getCard().getSuit() == Suit.HEARTS) 
			{
				count++;
			}
		}
		
		if (count == 0) //Case 1: no hearts played
		{
			winner = fPlays.get(0).getPlayer();
			maxCard = (fPlays.get(0)).getCard();
			for(Play p: fPlays)
			{
				if((p.getCard()).getSuit() == led)
				{
					if(p.getCard().compareTo(maxCard) > 0)
					{
						maxCard = p.getCard(); 
						winner = p.getPlayer();
					}
				}
			}
			
		}
		else  //Case 2 : hearts is the suit led, or any hearts played at all
		{
			winner = fPlays.get(0).getPlayer();
			maxCard = fPlays.get(0).getCard();
			for(Play pl : fPlays)
			{
				if((pl.getCard()).getSuit() == Suit.HEARTS)
				{
					if((pl.getCard()).compareTo(maxCard) > 0)
					{
						maxCard = pl.getCard(); 
						winner = pl.getPlayer();
					}
				}
			}
		}
		return winner;
	} */
	
	/**
	 * @return The player who wins the trick to date.
	 * @throws ModelException is the trick is empty.
	 */
	public String getWinner() throws ModelException
	{
		String winner;
		Card maxCard;
		Card compareCard;
	
		if(fPlays.size()==0) //Check if trick is empty, and return exception.
		{
			throw new ModelException("The trick is empty.");
		}
		
		Suit led = getSuitLed();
		maxCard = (fPlays.get(0)).getCard();
		winner = new String(fPlays.get(0).getPlayer());

		for (Play play: fPlays)
		{
			compareCard = play.getCard();
			if (compareCard.getSuit().equals(led)) 
			{
				if(compareCard.compareTo(maxCard) > 0)
				{
					maxCard = compareCard;
					winner = new String(play.getPlayer());
				}
			}
		}
		return winner;
	}
	
	/**
	 * @return The suit led in this trick.
	 * @throws ModelException if this is invoked on a trick with
	 * no cards played to date.
	 */
	public Suit getSuitLed() throws ModelException
	{
		if(fPlays.size() == 0)
		{
			throw new ModelException("The trick has no plays.");
		}
		return fPlays.get(0).getCard().getSuit();
	}
	
	/**
	 * Accessor method for accessing list of Plays.
	 * @return clonedList
	 */
	public List<Play> getTrick()
	{
		List<Play> clonedList = new ArrayList<Play>(fPlays.size());
        for(Play play: fPlays)
        {
        	clonedList.add(new Play(play.getPlayer(), play.getCard()));
        }
    	return clonedList;
	}
}
