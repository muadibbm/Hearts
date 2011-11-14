package hearts.robots;

import hearts.model.IImmutableGameState;
import hearts.model.ModelException;
import hearts.util.AllCards;
import hearts.util.Card;
import hearts.util.CardList;
import hearts.util.CardListException;
import hearts.util.Constants;
import hearts.util.Card.Suit;

/**
 * AI robot will play the game correctly respecting  all the rules
 * while displaying some evidence of strategies.
 */
public class MediumSmartRobot implements IRobot
{
	
	/**
	 * uses the index of the card to add to pass list.
	 * @param pPassList the list of cards to be passed
	 * @param pCard the card to be added to pPassList
	 * @param pSuit the cards of the suit being analyzed
	 * @param pHand the player's hand
	 */
	private static void addCard(CardList pPassList, int index, CardList pSuit, CardList pHand)
	{
		if(pPassList.size() < Constants.NUMBER_OF_CARDS_PASSED)
		{
			Card tmpCard = pSuit.remove(index);
			try
			{
				pPassList.add(tmpCard);
			}
			catch(CardListException e)
			{
				return;
			}
			if(pHand.contains(tmpCard))
			{
				pHand.remove(tmpCard);
			}	
		}
	}
	
	/**
	 * uses the name of the card to add to pass list.
	 * @param pPassList the list of cards to be passed
	 * @param pCard the card to be added to pPassList
	 * @param pSuit the cards of the suit being analyzed
	 * @param pHand the player's hand
	 */
	private static void addCard(CardList pPassList, Card pCard, CardList pSuit, CardList pHand)
	{
		if(pPassList.size() < Constants.NUMBER_OF_CARDS_PASSED && pSuit.contains(pCard))
		{
			pPassList.add(pCard);
		}
	}
	
	/**
	 * Passes all cards if has size less than or equal to 3 else it will pass the 3 highest.
	 * @param pPassList the list of cards to be passed
	 * @param pSuit the cards of the suit being analyzed 
	 * @param pHand the player's hand
	 */
	private static void pass(CardList pPassList, CardList pSuit, CardList pHand)
	{
		for(Card card : pSuit)
		{
			addCard(pPassList, card, pSuit, pHand);
		}
		for(Card rCard : pPassList)
		{
			if(pHand.contains(rCard))
			{
				pHand.remove(rCard);
			}
			if(pSuit.contains(rCard))
			{
				pSuit.remove(rCard);
			}
		}
	}
	
	/**
	 * compares the top ranking cards of two suits.
	 * @param pSuitA the first suit
	 * @param pSuitB the second suit
	 * @return true if card of first suit is higher than of second suit else returns false 
	 */
	private static boolean compareHighest(CardList pSuitA, CardList pSuitB)
	{
		boolean returnValue;
		if ((pSuitA.isEmpty() && pSuitB.isEmpty()) || (pSuitA.isEmpty() && !pSuitB.isEmpty()))
		{
			return false;
		}
		if(!pSuitA.isEmpty() && pSuitB.isEmpty())
		{
			return true;
		}
		CardList tmpSuitA = pSuitA.clone(); 
		CardList tmpSuitB = pSuitB.clone();
		if ((tmpSuitA.remove(tmpSuitA.size() - 1).compareTo(tmpSuitB.remove(tmpSuitB.size() - 1))) == 1)
		{
			returnValue = true;
		}
		else
		{
			returnValue = false;
		}
		return returnValue;
	}
	
	/**
	 * compares the lowest ranking cards of two suits.
	 * @param pSuitA the first suit
	 * @param pSuitB the second suit
	 * @return true if card of first suit is lower than of second suit else returns false 
	 */
	private static boolean compareLowest(CardList pSuitA, CardList pSuitB)
	{
		boolean returnValue;
		if ((pSuitA.isEmpty() && pSuitB.isEmpty()) || (pSuitA.isEmpty() && !pSuitB.isEmpty()))
		{
			return false;
		}
		if(!pSuitA.isEmpty() && pSuitB.isEmpty())
		{
			return true;
		}
		final int FIRST_CARD = 0;
		CardList tmpSuitA = pSuitA.clone(); 
		CardList tmpSuitB = pSuitB.clone();
		if ((tmpSuitA.remove(FIRST_CARD).compareTo(tmpSuitB.remove(FIRST_CARD))) == -1)
		{
			returnValue = true;
		}
		else
		{
			returnValue = false;
		}
		return returnValue;
	}
	
	/**
	 * Analyzes the spade cards in player's hand and decides what to pass.
	 * @param pPassList the list of cards to be passed
	 * @param pSuit the suit to be analyzed in this case spades
	 * @param pHand the player's hand
	 */
	private static void analyzeSpades(CardList pPassList, CardList pSuit, CardList pHand)
	{
		final int five = 5;
		final int four = 4;
		CardList hearts = pHand.getCardsOf(Card.Suit.HEARTS);
		final boolean shootTheMoon = hearts.contains(AllCards.CAH) && hearts.contains(AllCards.CKH) && 
				               hearts.contains(AllCards.CQH) && hearts.size() >= four;
		
		if (!pSuit.isEmpty()) 
		{
			/* has CQS */
			if(pSuit.contains(AllCards.CQS))
			{
				/* if does not have three highest hearts plus a minor one then 
				   try to pass CQS else keep all for shooting the moon */
				if(!shootTheMoon)
				{
					/* Only has the CQS => pass CQS */
					if(pSuit.size() == 1) 
					{
						addCard(pPassList, AllCards.CQS, pSuit, pHand);
					}
					/* has CAS and CKS and CQS and more than 2 minor spades => pass CAS and CKS but keep CQS */
					else if(pSuit.contains(AllCards.CAS) && pSuit.contains(AllCards.CKS))
					{
						addCard(pPassList, AllCards.CAS, pSuit, pHand);
						addCard(pPassList, AllCards.CKS, pSuit, pHand);
						/* has less than 2 minor spades => also pass CQS */
						if(pSuit.size() < five)
						{
							addCard(pPassList, AllCards.CQS, pSuit, pHand);
						}
					}
					/* has CAS and CQS and more than 2 minor spades => pass CAS and keep CQS */
					else if(pSuit.contains(AllCards.CAS))
					{
						addCard(pPassList, AllCards.CAS, pSuit, pHand);
						/* has less than 2 minor spades => also pass CQS */
						if(pSuit.size() < four)
						{
							addCard(pPassList, AllCards.CQS, pSuit, pHand);
						}
					}
					/* has CKS and CQS and more than 2 minor spades => pass CKS and keep CQS */
					else if(pSuit.contains(AllCards.CKS))
					{	
						addCard(pPassList, AllCards.CKS, pSuit, pHand);
						/* has less than 2 minor spades => also pass CQS */
						if(pSuit.size() < four)
						{
							addCard(pPassList, AllCards.CQS, pSuit, pHand);
						}
					}
					/* has CQS with minor spades */
					else
					{
						/* has less than 3 minor spades => pass CQS */ 
						if(pSuit.size() <= four)
						{
							addCard(pPassList, AllCards.CQS, pSuit, pHand);
						}
					}
				}/* else keep CQS for shooting the moon */
			}
			/* does not have CQS */
			else 
			{
				/* has CAS and CKS => pass CAS and CKS */
				if(pSuit.contains(AllCards.CAS) && pSuit.contains(AllCards.CKS))
				{
					addCard(pPassList, AllCards.CAS, pSuit, pHand);
					addCard(pPassList, AllCards.CKS, pSuit, pHand);
				}
				/* has CAS => pass CAS */
				else if(pSuit.contains(AllCards.CAS))
				{
					addCard(pPassList, AllCards.CAS, pSuit, pHand);
				}
				/* has CKS => pass CKS */
				else if(pSuit.contains(AllCards.CKS))
				{
					addCard(pPassList, AllCards.CKS, pSuit, pHand);
				}
				/* has only minor spades => if size less than or equals to 3 try to make the suit void */
				else
				{
					if(pSuit.size() < four && pPassList.size() < 0)
					{
						pass(pPassList, pSuit, pHand);
					}
				}
			}
		}/* does not have spades => do nothing */
	}
	
	
	
	@Override
	public CardList getCardsToPass(CardList hand)
	{
		CardList passList = new CardList(Constants.NUMBER_OF_CARDS_PASSED);
		CardList spadeCards = hand.getCardsOf(Card.Suit.SPADES);
		spadeCards.sort();
		CardList heartCards = hand.getCardsOf(Card.Suit.HEARTS);
		heartCards.sort();
		CardList diamondCards = hand.getCardsOf(Card.Suit.DIAMONDS);
		diamondCards.sort();
		CardList clubCards = hand.getCardsOf(Card.Suit.CLUBS);
		clubCards.sort();

		final int four = 4;
		final int three = 3;
		
		/* --- Try to make minor suits void  --- */
		
		/* pass all diamond cards if has less than or equal to 3 */
		if(!diamondCards.isEmpty() && diamondCards.size() <= three)
		{
			pass(passList, diamondCards, hand);
		}
		/* pass all club cards if has less than or equal to 3 */
		if(!clubCards.isEmpty() && clubCards.size() <= three)
		{
			pass(passList, clubCards, hand);
		}
		
		/* --- Analyze the Spade Suit --- */	
		
		analyzeSpades(passList, spadeCards, hand);
		
		/* --- Analyze the Heart Suit --- */		
		
		if (!heartCards.isEmpty()) 
		{
			/* does not have high hearts CAH, CKH, CQH and minor hearts => try to pass them */
			if(!(heartCards.contains(AllCards.CAH) && heartCards.contains(AllCards.CKH) &&
					heartCards.contains(AllCards.CQH) && heartCards.size() >= four))
			{
				addCard(passList, AllCards.CAH, heartCards, hand);
				addCard(passList, AllCards.CKH, heartCards, hand);
				addCard(passList, AllCards.CQH, heartCards, hand);
				addCard(passList, AllCards.CJH, heartCards, hand);
			}
			/* else if has high hearts and at least one minor heart => keep for SHOOTING THE MOON */				
		}
		
		/* --- Analyze the Minor Suit --- */
		
		/* has diamonds */
		if (!diamondCards.isEmpty()) 
		{
			/* has clubs = > pass the highest of both */
			if(!clubCards.isEmpty())
			{
				for(int i = 0; i < three; i++)
				{
					if(compareHighest(diamondCards, clubCards))
					{
						addCard(passList, diamondCards.size() - 1, diamondCards, hand);
					}
					else
					{
						addCard(passList, clubCards.size() - 1, clubCards, hand);
					}
				}		
			}
			/* has no clubs => pass the highest of diamonds */
			else
			{			
				for(int i = 0; i < three; i++)
				{
					addCard(passList, diamondCards.size() - 1, diamondCards, hand);
				}
			}
		}	
		/* has no diamonds => pass the highest of clubs */
		else if (!clubCards.isEmpty())
		{
			for(int i = 0; i < three; i++)
			{
				addCard(passList, clubCards.size() - 1, clubCards, hand);
			}
		}
		if(passList.size() != Constants.NUMBER_OF_CARDS_PASSED)
		{
			for(int i = 0; i < three; i++)
			{
					addCard(passList, hand.size() - 1, hand, hand);
			}
		}
		
		return passList;
	}
	
	/**
	 * Tries to collect all the hearts.
	 * @param pGame immutable state of the game
	 * @param pHand hand of the player
	 * @pre pHand contains CQS, CAH, CKH, CQH and another heart
	 * @return the card to be played
	 */
	public static Card shootTheMoon(IImmutableGameState pGame, CardList pHand)
	{
		Card cardToPlay;
		final int FIRST_CARD = 0;
		CardList spades = pHand.getCardsOf(Card.Suit.SPADES);
		spades.sort();
		CardList hearts = pHand.getCardsOf(Card.Suit.HEARTS);
		hearts.sort();
		CardList diamonds = pHand.getCardsOf(Card.Suit.DIAMONDS);
		diamonds.sort();
		CardList clubs = pHand.getCardsOf(Card.Suit.CLUBS);
		clubs.sort();
		/* At least one card has been played.*/
		try
		{
			Card.Suit currentSuit = pGame.getSuitLed(); // <-- throws ModelException
			CardList cardsOfCurrentSuit = pHand.getCardsOf(currentSuit);
			/* does not have current suit => play lowest of all cards */
			if(cardsOfCurrentSuit.isEmpty()) 
			{
				/* has at least one of the minor suits => play the lowest */ 
				if(!diamonds.isEmpty() || !clubs.isEmpty())
				{
					/* diamonds has lower than clubs => play lowest of diamonds */
					if(compareLowest(diamonds, clubs))
					{
						cardToPlay = diamonds.remove(FIRST_CARD);
					}
					/* clubs has lower than diamonds => play lowest of clubs */
					else 
					{
						cardToPlay = clubs.remove(FIRST_CARD);
					}
				}
				/* does not have minor suits => play lowest of spades */
				else if(!spades.isEmpty())
				{
					cardToPlay = spades.remove(FIRST_CARD);
				}
				/* does not have spades => play lowest of hearts */
				else
				{
					cardToPlay = hearts.remove(FIRST_CARD);
				}
			}
			/* has current suit => play highest to win the trick */
			else
			{
				cardToPlay = cardsOfCurrentSuit.remove(cardsOfCurrentSuit.size() - 1); 
			}
		}
		/* No suit led, start the trick. */
		catch (ModelException e)
		{
			CardList otherThanHearts = pHand.getCardsNotOf(Card.Suit.HEARTS);
			/*  We can lead with hearts only when:
			 *  1) A heart has been played in previous tricks */
			if(pGame.heartsPlayed())
			{
				if(!hearts.isEmpty())
				{
					cardToPlay = hearts.remove(hearts.size() - 1);
				}
				else
				{
					cardToPlay = otherThanHearts.remove(otherThanHearts.size() - 1);
				}
			}
			/*  2) our hand only contains only hearts => play highest */	
			else if(otherThanHearts.isEmpty())
			{
				cardToPlay = pHand.remove(pHand.size() - 1);
			}
			else
			{
				cardToPlay = otherThanHearts.remove(otherThanHearts.size() - 1);
			}
		}
		return cardToPlay;
	}

	@Override
	public Card getCardToPlay(IImmutableGameState pGame, String pPlayer, String pPassedTo, CardList hand)
	{
		Card cardToPlay;
		final int FIRST_CARD = 0;
		final int four = 4;
		CardList spades = hand.getCardsOf(Card.Suit.SPADES);
		spades.sort();
		CardList hearts = hand.getCardsOf(Card.Suit.HEARTS);
		hearts.sort();
		CardList diamonds = hand.getCardsOf(Card.Suit.DIAMONDS);
		diamonds.sort();
		CardList clubs = hand.getCardsOf(Card.Suit.CLUBS);
		clubs.sort();
		final boolean shootTheMoon = hearts.contains(AllCards.CAH) && hearts.contains(AllCards.CKH) && 
        hearts.contains(AllCards.CQH) && hearts.size() >= four;
		
        /* If has CQS and four hearts including CAH, CKH and CQH => shoot the moon */
		if(spades.contains(AllCards.CQS) && shootTheMoon)
		{
			cardToPlay = shootTheMoon(pGame, hand);
		}
		/* play the standard */
		else 
		{
		 /* At least one card has been played.*/
		 try
		 {
			Card.Suit suitLed = pGame.getSuitLed(); /* throws ModelException if suitLed is empty */ 
			CardList cardsOfSuitLed = hand.getCardsOf(suitLed);
			/* does not have the current suit played */
			if(cardsOfSuitLed.isEmpty())
			{
					/* has hearts => breaks hearts by playing the highest */
					if(!hearts.isEmpty())
					{
						cardToPlay = hearts.remove(hearts.size()-1);
					}
					/* try to make the singleton suits other than hearts void */
					else if(spades.size() == 1)
					{
						cardToPlay = spades.remove(FIRST_CARD);
					}
					else if(clubs.size() == 1)
					{
						cardToPlay = clubs.remove(FIRST_CARD);
					}
					/* does not have hearts or singleton suits => play the highest of suits other than hearts */
					else
					{
						/* diamonds has higher than clubs */
						if(compareHighest(diamonds, clubs))
						{
							/* spades has higher than clubs and diamonds => play highest of spades */
							if(compareHighest(spades, diamonds) && !spades.isEmpty())
							{
								cardToPlay = spades.remove(spades.size() - 1);
							}
							/* diamonds has higher than clubs and spades => play highest of diamonds */
							else if(!diamonds.isEmpty())
							{
								cardToPlay = diamonds.remove(diamonds.size() - 1);
							}
							else if(!spades.isEmpty())
							{
								cardToPlay = spades.remove(spades.size() - 1);
							}
							else
							{
								cardToPlay = clubs.remove(spades.size() - 1);
							}
						}
						/* clubs has higher than diamonds */
						else
						{
							/* spades has higher than clubs and diamonds => play highest of spades */
							if(compareHighest(spades, clubs) && !spades.isEmpty())
							{
								cardToPlay = spades.remove(spades.size() - 1);
							}
							/* clubs has higher than spadess and diamonds => play highest of clubs */
							else if(!clubs.isEmpty())
							{
								cardToPlay = clubs.remove(clubs.size() - 1);
							}
							else if(!spades.isEmpty())
							{
								cardToPlay = spades.remove(spades.size() - 1);
							}
							else
							{
								cardToPlay = diamonds.remove(diamonds.size() - 1);
							}
						}
					}
				
			}
			/* has the current suit played */
			else
			{
				cardsOfSuitLed.sort();
				CardList cardsPlayed = pGame.getCurrentTrick().getCards(); //already sorted
				Card highestCard = cardsPlayed.remove(cardsPlayed.size()-1); //highest card that is played
				CardList lessThanHighest = new CardList(cardsOfSuitLed.size()); 
				//all the cards in player's hand less than the highest card that is played
				
				/* If it is the first Trick and clubs are not empty => play the highest clubs
				 * inorder to take the lead */
				if(pGame.tricksCompleted() == 0 && !clubs.isEmpty())
				{
					return clubs.remove(clubs.size() - 1);
				}			
				for (Card card : cardsOfSuitLed)
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
					cardToPlay = lessThanHighest.remove(lessThanHighest.size() - 1);
				}
				/* does not have less than highest then play the lowest card that is higher */
				else 
				{
					cardToPlay = cardsOfSuitLed.remove(FIRST_CARD);
				}
			}
		 }
		 /* No suit led, start the trick. */
		 catch (ModelException e)
		 {
			/* does not have other than hearts => play the lowest heart */
		 	if(hand.getCardsNotOf(Suit.HEARTS).size() == 0)
			{
				cardToPlay = hearts.remove(FIRST_CARD);
			}
			/* hearts have been broken => either play hearts or other than hearts */
			else if(pGame.heartsPlayed())
			{
				/* lowest hearts is lower than all the lower cards in other suits */
				if(compareLowest(hearts, spades) && compareLowest(hearts, diamonds) &&
						compareLowest(hearts, clubs))
				{
					cardToPlay = hearts.remove(FIRST_CARD);
				}
				else
				{
					/* diamonds has lower than clubs */
					if(compareLowest(diamonds, clubs))
					{
						/* spades has lower than clubs and diamonds => play lowest of spades */
						if(compareLowest(spades, diamonds))
						{
							cardToPlay = spades.remove(FIRST_CARD);
						}
						/* diamonds has lower than clubs and spades => play lowest of diamonds */
						else 
						{
							cardToPlay = diamonds.remove(FIRST_CARD);
						}
					}
					/* clubs has lower than diamonds */
					else
					{
						/* spades has lower than clubs and diamonds => play lowest of spades */
						if(compareLowest(spades, clubs))
						{
							cardToPlay = spades.remove(FIRST_CARD);
						}
						/* clubs has lower than spades and diamonds => play lowest of clubs */
						else 
						{
							cardToPlay = clubs.remove(FIRST_CARD);
						}
					}
				}
			}
			/* hearts have not been played => play lowest of suits other than hearts */
			else
			{
				/* it is the second Trick => play the highest of either clubs or diamonds 
				 * inorder to take the lead */
				if((pGame.tricksCompleted() < 2) && (!diamonds.isEmpty() || !clubs.isEmpty()))
				{
					if(compareHighest(diamonds, clubs))
					{
						cardToPlay = diamonds.remove(diamonds.size() - 1);
					}
					else
					{
						cardToPlay = clubs.remove(clubs.size() - 1);
					}
					return cardToPlay;
				}
				/* diamonds has lower than clubs */
				if(compareLowest(diamonds, clubs))
				{
					/* spades has lower than clubs and diamonds => play lowest of spades */
					if(compareLowest(spades, diamonds))
					{
						cardToPlay = spades.remove(FIRST_CARD);
					}
					/* diamonds has lower than clubs and spades => play lowest of diamonds */
					else 
					{
						cardToPlay = diamonds.remove(FIRST_CARD);
					}
				}
				/* clubs has lower than diamonds */
				else
				{
					/* spades has lower than clubs and diamonds => play lowest of spades */
					if(compareLowest(spades, clubs))
					{
						cardToPlay = spades.remove(FIRST_CARD);
					}
					/* clubs has lower than spades and diamonds => play lowest of clubs */
					else 
					{
						cardToPlay = clubs.remove(FIRST_CARD);
					}
				} 
			}		
		 }
		}
		return cardToPlay;
	}

}
