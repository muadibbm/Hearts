package hearts.robots;

import hearts.model.IImmutableGameState;
import hearts.model.ModelException;
import hearts.util.Card;
import hearts.util.CardList;
import hearts.util.Constants;
import java.util.Random;

/**
 * AI robot will play the game correctly respecting  all the rules,
 * but will choose randomly among all legal options whenever a choice is required.
 */
public class RandomRobot implements IRobot
{

	private Random fRandom =  new Random();
	/**
	 * @param cardlist List of cards.
	 * @return Random card
	 */
	private Card pickRandomCard(CardList cardlist)
	{
		int cardIndex = fRandom.nextInt(cardlist.size());
		return cardlist.remove(cardIndex);
	}
	
	
	@Override
	public CardList getCardsToPass(CardList hand)
	{
		CardList passList = new CardList(Constants.NUMBER_OF_CARDS_PASSED);
		for(int i = 0; i<Constants.NUMBER_OF_CARDS_PASSED; i++)
		{
			passList.add(pickRandomCard(hand));
		}
		return passList;
	}

	@Override
	public Card getCardToPlay(IImmutableGameState pGame, String pPlayer, String pPassedTo, CardList hand)
	{
		
		Card cardToPlay;
		/* At least one card has been played.*/
		try
		{
			Card.Suit currentSuit = pGame.getSuitLed(); // <-- throws ModelException
			CardList cardsOfCurrentSuit = hand.getCardsOf(currentSuit);
			/* Play suits other than current suit */
			if(cardsOfCurrentSuit.isEmpty()) 
			{
				cardToPlay = pickRandomCard(hand);
			}
			/* Play current suit */
			else
			{
				cardToPlay = pickRandomCard(cardsOfCurrentSuit); 
			}
		}
		/* No suit led, start the trick. */
		catch (ModelException e)
		{
			CardList otherThanHearts = hand.getCardsNotOf(Card.Suit.HEARTS);
			/*  We can lead with hearts only when:
			 *  1) A heart has been played in previous tricks */
			if(pGame.heartsPlayed())
			{
				cardToPlay = pickRandomCard(hand);
			}
			/*  2) our hand only contains hearts. */	
			else if(otherThanHearts.isEmpty())
			{
				cardToPlay = pickRandomCard(hand);
			}
			else
			{
				cardToPlay = pickRandomCard(otherThanHearts);
			}
		}
		return cardToPlay;
	}
}
