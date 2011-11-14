package hearts.model;

import hearts.util.Card;
import hearts.util.CardList;
import hearts.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores a list of up to 13 rounds. Works with player names instead of players
 * to prevent users access to player state.
 */
public class Game
{
	private List<Trick> fRoundList = new ArrayList<Trick>();
	private Map<String, Integer> fTrickWinners = new HashMap<String, Integer>();
	private List<String> fWinnersNames = new ArrayList<String>();
	
	/**
	 * @return A reference to a version of this objects that does not 
	 * allow classes outside this package to modify the state of the game
	 * or any object transitively reachable through it.
	 */
	public IImmutableGameState getImmutableState()
	{
		return new ImmutableGameWrapper(this);
	}
	
	/**
	 * Manages pairs of winner names and their scores.
	 * @param name of winner
	 * @param points scored
	 */
	public void putWinner(String name, Integer points)
	{
		fWinnersNames.add(name);
		fTrickWinners.put(name, points);
	}
	
	/**
	 * Finds if any player has shot the moon,
	 * ie collected all 13 score cards.
	 * @pre all 13 tricks are completed
	 * @return name of player who has shot the moon.
	 * @throws ModelException if no winner for shoot the moon was found
	 */
	public String shootTheMoon() throws ModelException
	{
		String first = fWinnersNames.get(0);	
		int score = 0;
		
		for(String name : fWinnersNames)
		{
			if(!name.equals(first))
			{
				throw new ModelException("Winner not found");
			}
			else
			{
				score += fTrickWinners.get(name);
			}	
		}
		if (score == Constants.MAX_SCORE)
		{
			return first;
		}
		else
		{
			throw new ModelException("Winner not found");
		}
	}
	
	/**
	 * Resets the game to a new, empty state.
	 */
	public void clear()
	{
		fRoundList.clear();
	}
	
	/**
	 * @return The trick currently being played.
	 * @precondition At least one trick has been added to Game.
	 */
	public Trick getCurrentTrick()
	{
		return fRoundList.get(fRoundList.size()-1);
	}
	
	/**
	 * @return The number of tricks completed (4 cards played).
	 */
	public int tricksCompleted()
	{
		if (!fRoundList.isEmpty())
		{
			if (getCurrentTrick().getTrick().size() == Constants.NUMBER_OF_PLAYERS)
			{
				return fRoundList.size();
			}
			else
			{
				return fRoundList.size()-1;
			}
		}
		return 0;
	}
	

	/**
	 * @return The name of the winner of the last complete trick.
	 * @throws ModelException if no trick has been added to the game yet, or if there is only one empty trick
	 * @precondition At least one trick has been added to Game.
	 */
	public String getLastWinner() throws ModelException
	{
		if (fRoundList.isEmpty())
		{
			throw new ModelException("No trick has been added to the game yet");
		}
		else 
		{
			try
			{
				if (fRoundList.get(fRoundList.size()-1).cardsPlayed() == 4)
				{
					return fRoundList.get(fRoundList.size()-1).getWinner();
				}
				return fRoundList.get(fRoundList.size()-2).getWinner();
			}
			catch (ModelException e)
			{
				if (fRoundList.size()==1)
				{
					throw new ModelException("This game has only one empty trick. No last player");
				}
				else
				{
					return fRoundList.get(fRoundList.size()-2).getWinner();
				}
			}
		}
	}
	
	/**
	 * @return true if a Hearts card has been played so far.
	 */
	public boolean heartsPlayed()
	{
		if(fRoundList.isEmpty())
		{
			return false;
		}
		 // Each trick has 4 cards. Max size of heartslist so far will be 4 * fRoundList.size
		int maxSize = fRoundList.size() * Constants.NUMBER_OF_TRICKS;
		CardList heartsList = new CardList(maxSize);
		for(Trick round : fRoundList)
		{
			//Add all cards of suit HEARTS
			heartsList.addAll(round.getCards().getCardsOf(Card.Suit.HEARTS));
		}
		
		//If hearts have been played, heartsList will not be empty.
		return !heartsList.isEmpty();
	}
	
	/**
	 * Adds a new, empty trick to the game stack.
	 */
	public void newTrick()
	{
		if(fRoundList.size() < Constants.NUMBER_OF_TRICKS)
		{
			fRoundList.add(new Trick());
		}
	}
	
	/**
	 * Returns all the cards accumulated by a player by winning tricks.
	 * @param pPlayer The name of the player to query.
	 * @return CardList containing all the cards won by the player.
	 * @precondition At least one trick has been added to Game.
	 */
	public CardList getCardsAccumulated(String pPlayer)
	{
		int maxSize = fRoundList.size() * Constants.NUMBER_OF_TRICKS; // 4 cards per trick
		CardList wonCardList = new CardList(maxSize);
		
		for(Trick round : fRoundList)
		{
			if(round.getWinner().equals(pPlayer))
			{
				wonCardList.addAll(round.getCards()); //add all cards from each round
			}
		}
		return wonCardList;
	}
	
	/**
	 * Returns the current score of a player in this game. 
	 * @param pPlayer The name of the player to query.
	 * @return The trick score accumulated in this game.
	 * @precondition At least one trick has been added to Game.
	 */
	public int getScore(String pPlayer)
	{
		int score = 0;
		for(Trick trick : fRoundList)
		{
			ArrayList<Play> plays = (ArrayList<Play>) trick.getTrick();
			for(Play play : plays)
			{
				if(trick.getWinner().equals(pPlayer))
				{
					score += play.getCard().getPoints();
				}
			}
		}
		return score;
	}
	
	/**
	 * Adds a play to the current trick.
	 * @param pPlayer The name of the player making the play.
	 * @param pCard The card played by the player.
	 * @throws ModelException if no trick has been added to the game yet.
	 */
	public void addPlay(String pPlayer, Card pCard) throws ModelException
	{
		//Try to add using Trick.addPlay
		try 
		{
			if (fRoundList.isEmpty())
			{
				throw new ModelException("No trick has been added.");
			}
			getCurrentTrick().addPlay(pPlayer, pCard);
		}
		catch (ModelException mE)
		{
			throw new ModelException("Can't add play");
		}
	}
	
	/**
	 * @return The suit led in this trick.
	 * @throws ModelException If there is no play (so no suit was led).
	 * @precondition At least one trick has been added to Game.
	 */
	public Card.Suit getSuitLed() throws ModelException
	{
		int indexOfTrick = fRoundList.size() - 1;
		Card.Suit result;
		try
		{
			if (indexOfTrick >= 0)
			{
				result = fRoundList.get(indexOfTrick).getSuitLed();
			}
			else
			{
				throw new ModelException("There is no play in this trick.");
			}
		}
		catch(ModelException mE)
		{
			throw new ModelException("There is no play in this trick.");
		}
		return result;
	}
}
