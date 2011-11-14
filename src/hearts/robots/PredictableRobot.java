package hearts.robots;

import hearts.model.IImmutableGameState;
import hearts.model.ModelException;
import hearts.util.Card;
import hearts.util.CardList;
import hearts.util.Constants;

/**
 * AI robot will play the game correctly respecting  all the rules,
 * always using the same strategy.
 */
public class PredictableRobot implements IRobot
{
	
	private Card pickPreditcedCard(CardList cardlist, int index)
	{
		return cardlist.remove(index); 
	}
	
	@Override
	public CardList getCardsToPass(CardList hand) 
	// no rules for being passed is stated by the prof except in order, is this correct ?
	{
		CardList passList = new CardList(Constants.NUMBER_OF_CARDS_PASSED);
		hand.sort();
		for(int i = 0; i<Constants.NUMBER_OF_CARDS_PASSED; i++)
		{
			passList.add(pickPreditcedCard(hand, 0));
		}
		return passList;
	}

	@Override
	public Card getCardToPlay(IImmutableGameState pGame, String pPlayer, String pPassedTo, CardList hand)
	{
		final int NUMBER_OF_PLAYS_PLAYED = 3;
		final int FIRST_CARD = 0;
		hand.sort();
		Card cardToPlay;
		/* At least one card has been played.*/
		try
		{
			Card.Suit currentSuit = pGame.getSuitLed(); // <-- throws ModelException
			CardList cardsOfCurrentSuit = hand.getCardsOf(currentSuit);
			if(cardsOfCurrentSuit.isEmpty())//does not have the current suit
			{
				//at least one more player will play
				if(pGame.getCurrentTrick().cardsPlayed() < NUMBER_OF_PLAYS_PLAYED)
				{
					cardToPlay = pickPreditcedCard(hand,  FIRST_CARD); //Play the lowest Card
				}
				//other players have already played
				else
				{
					cardToPlay = pickPreditcedCard(hand, hand.size() - 1); //Play the highest Card
				}
				
			}
			else //does have the current suit then pick the highest card that is guaranteed not to win the trick
			{
				cardsOfCurrentSuit.sort();
				
				CardList cardsPlayed = pGame.getCurrentTrick().getCards(); //already sorted
				Card highestCard = cardsPlayed.remove(cardsPlayed.size()-1); //highest card that is played
				CardList lessThanHighest = new CardList(cardsOfCurrentSuit.size()); 
				//all the cards in player's hand less than the highest card that is played
				
				for (Card card : cardsOfCurrentSuit)
				{
					if(card.compareTo(highestCard) == -1) //less than highest Card
					{
						lessThanHighest.add(card);
					}
				}
				/* has less than the highest card that is played then play the highest card 
				 * that does not win the trick */
				if(!lessThanHighest.isEmpty())
				{
					lessThanHighest.sort();
					cardToPlay = pickPreditcedCard(lessThanHighest, lessThanHighest.size() - 1);
				}
				/* does not have less than highest then play the lowest card */
				else 
				{
					cardToPlay = pickPreditcedCard(cardsOfCurrentSuit, FIRST_CARD);
				}
			}
		}
		/* No suit led, start the trick. */
		catch (ModelException e)
		{
			CardList otherThanHearts = hand.getCardsNotOf(Card.Suit.HEARTS);
			/*  We can lead with hearts only when:
			    A heart has been played in previous tricks or our hand only contains hearts. */
			if(pGame.heartsPlayed() || otherThanHearts.isEmpty())
			{
				cardToPlay = pickPreditcedCard(hand, FIRST_CARD); //Play the lowest Card
			}
			/* no hearts have been played */
			else 
			{
				cardToPlay = pickPreditcedCard(otherThanHearts, FIRST_CARD); //Play lowest Card other than hearts
			}
		}
		return cardToPlay;
	}

}
