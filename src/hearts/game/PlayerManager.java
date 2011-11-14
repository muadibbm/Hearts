package hearts.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hearts.game.GameEngine.PassingDirection;
import hearts.gameEvent.EndPassingEvent;
import hearts.gameEvent.PassEvent;
import hearts.model.IImmutableGameState;
import hearts.model.Player;
import hearts.robots.IRobot;
import hearts.util.AllCards;
import hearts.util.Card;
import hearts.util.CardList;
import hearts.util.Constants;

/**
 * Manages players objects.
 */
class PlayerManager
{
	private Map<String, Player> fPlayers;
	/* Another hash map where the keys are the same,
	 * but the players associated with them depend on
	 * the passing direction. See use in passCards & play.
	 */
	private Map<String, Player> fAssociates;
	private Map<String, Integer> fPlayerStatistic;
	private String fWinner;
	
	PlayerManager()
	{
		fPlayers = new LinkedHashMap<String, Player>(); // Linked: Preserves the order
		fAssociates = new HashMap<String, Player>();
		fPlayerStatistic = new HashMap<String, Integer>();
		fWinner =  new String();
	}


	//Just for debugging purposes
	// Basically, prints hands of all players whenever necessary
	public String toString()
	{
		String s = "";
		for(String name : fPlayers.keySet())
		{
			s = s + name + " " + fPlayers.get(name).getHand().size() + " : " + fPlayers.get(name).getHandString() + "\n" ;
		}
		return s;
	}

	/**
	 * Resets the player manager by clearing the players.
	 */
	void reset()
	{
		for(String name : fPlayers.keySet())
		{
			fPlayers.get(name).resetScore();
		}
	}
	
	/**
	 * Clear all the lists.
	 */
	void clearPlayers()
	{
		fPlayers.clear();
		fAssociates.clear();
		fPlayerStatistic.clear();
		fWinner = null;
	}

	/**
	 * Add a player in clockwise order.
	 * @param name Name of the player.
	 * @pre name != null
	 * @throws GameException Cannot add more than four players.
	 */
	void addPlayer(String name) throws GameException
	{
		if(fPlayers.size()>=Constants.NUMBER_OF_PLAYERS)
		{
			throw new GameException("Four players is the maximum.");
		}
		fPlayers.put(name, new Player(name));
		fPlayerStatistic.put(name, 0);
	}

	/**
	 * Associates players with the one in the correct
	 * direction depending on the direction in which
	 * cards are passed.
	 * 
	 * Must be called when a round is started.
	 * @param dir LEFT, RIGHT or ACROSS
	 * @param playerList List of names of players in game
	 * @pre dir != null
	 * @pre playerList != null
	 */
	void setAssociation(PassingDirection dir, List<String> playerList)
	{
		ArrayList<String> newOrder = cloneList(playerList);
		switch(dir)
		{
		case LEFT:
			Collections.rotate(newOrder, -1);
			break;
		case RIGHT:
			Collections.rotate(newOrder, 1);
			break;
		case ACROSS:
			Collections.rotate(newOrder, 2);
			break;
		default:
			break;
		}

		int index = 0;
		for(Entry<String, Player> entry : fPlayers.entrySet())
		{
			String playerName = (String) entry.getKey();
			Player associatedPlayer = fPlayers.get(newOrder.get(index));
			fAssociates.put(playerName, associatedPlayer);
			index++;
		}
	}

	/**
	 * Utility function to clone lists.
	 * @param list List to clone
	 * @pre list != null
	 */
	private static ArrayList<String> cloneList(List<String> list)
	{
		ArrayList<String> clone = new ArrayList<String>(list.size());
		for(String name: list) 	
		{
			clone.add(name);
		}	   
		return clone;
	}

	/**
	 * Add the cards to the hand of the indicated player.
	 * @param name Name of the player to set hand.
	 * @param hand CardList of the hand.
	 * @pre hand != null
	 * @pre name != null
	 */
	void setHand(String name, CardList hand)
	{
		fPlayers.get(name).addCards(hand);
	}

	/**
	 * Clear the players' hands.
	 */
	void clearHands()
	{
		for(Player player : fPlayers.values())
		{
			player.clearHand();
		}
	}

	/**
	 * Sets the score of the indicated player by adding
	 * the points in the cards of the trick.
	 * 
	 * Must be called at the end of a trick.
	 * @param name Name of the player.
	 * @param cardsOfTrick Cards at the end of a trick.
	 * @pre name != null
	 * @pre cardsOfTrick != null and cardOfTrick.size() ==4
	 * @return score of winner
	 */
	int setScore(String name, CardList cardsOfTrick)
	{	
		int score = 0;
		for(Card card: cardsOfTrick)
		{
			score += card.getPoints();
		}
		fPlayers.get(name).addToScore(score);
		return score;
	}

	/**
	 * Get the score of the specified player.
	 * @param name Name of the player.
	 * @pre name != null
	 * @return Score int.
	 */
	int getScore(String name)
	{
		Player player = fPlayers.get(name);
		return player.getScore();
	}

	/** Get the name of the winner, the current player with the least amount of points.
	 * @pre setGameWinner has been called.
	 * @return name of the winner
	 */
	String getGameWinner()
	{
		return fWinner;
	}
	
	/** Set the name of the winner, the current player with the least amount of points.
	 */
	void setGameWinner()
	{
		int playerScore;
		int minScore = Integer.MAX_VALUE;
		for(Player p : fPlayers.values())
		{
			playerScore = p.getScore();
			if(playerScore < minScore)
			{
				fWinner = p.getName();
				minScore = playerScore;
			}
		}
		fPlayerStatistic.put(fWinner, new Integer(fPlayerStatistic.get(fWinner).intValue()+1));
	}

	/**
	 * Get the statistic of the specified player.
	 * @param name Name of the player.
	 * @return Number of games won.
	 */
	int getStatistic(String name)
	{
		return fPlayerStatistic.get(name);
	}

	/**
	 * Subtracts 26 from player's score if he/she has shot the moon
	 * Because 26 points have already been added, actually
	 * subtracts those, and another 26 for a total of 52.
	 * @param name of winner
	 */
	public void shootTheMoon(String name)
	{
		/*TODO -26 pts or +26 pts to others.
		 * Choice for human, how?
		 * Automatic for robots.
		 */
		if(fPlayers.get(name).getScore() < Constants.NUMBER_OF_CARDS)
		{
			fPlayers.get(name).resetScore();
		}
		else
		{
			int score = fPlayers.get(name).getScore()-Constants.NUMBER_OF_CARDS;
			fPlayers.get(name).resetScore();
			fPlayers.get(name).addToScore(score);
		}
	}


	/**
	 * Pass cards function for the human.
	 * @param name Name of the player.
	 * @param card Card to be passed.
	 * @pre name != null and is the name of a player in the game
	 * @pre cardsPassed != null and are in the players hand
	 * @return Pass event with passing information
	 */
	PassEvent passCards(String name, CardList cardsPassed)
	{
		Player fromPlayer = fPlayers.get(name);
		Player toPlayer = fAssociates.get(name);
		fromPlayer.removeCards(cardsPassed);
		toPlayer.addCardsBuffer(cardsPassed);
		PassEvent event = new PassEvent();
		event.setPlayers(name, toPlayer.getName());
		event.setCardsPassed(cardsPassed.clone());
		return event;
	}

	/**
	 * The indicated player passes three cards to his
	 * associated player.
	 * 
	 * Must be called at the beginning of a round.
	 * @param name Name of the player.
	 * @param robotType Type of the robot used.
	 * @pre setAssociation has been called.
	 * @pre name != null
	 * @pre robotType != null
	 * @return Pass event with passing information
	 */
	PassEvent passCards(String name, IRobot robotType)
	{
		CardList cardsPassed = new CardList(Constants.NUMBER_OF_CARDS_PASSED);
		Player fromPlayer = fPlayers.get(name);
		Player toPlayer = fAssociates.get(name);

		cardsPassed.addAll(robotType.getCardsToPass(fromPlayer.getHand()));
		fromPlayer.removeCards(cardsPassed);
		toPlayer.addCardsBuffer(cardsPassed);
		PassEvent event = new PassEvent();
		event.setPlayers(name, toPlayer.getName());
		event.setCardsPassed(cardsPassed.clone());
		return event;
	}

	/**
	 * Passes the buffer into the hand, clears the buffer.
	 * @param name Name of the player
	 * @param event Pass event for setting the hands.
	 */
	void passBufferToHand(String name, EndPassingEvent event)
	{
		CardList cardsReceived = new CardList(Constants.NUMBER_OF_CARDS_PASSED);
		cardsReceived = fPlayers.get(name).getHandBuffer();
		fPlayers.get(name).addCards(cardsReceived);
		fPlayers.get(name).clearHandBuffer();
		event.setHand(name, fPlayers.get(name).getHand().clone());
	}

	/**
	 * Play function for the human.
	 * @param name Name of the player.
	 * @param card Card to be played.
	 * @pre name != null and is the name of a player in the game
	 * @pre card != null
	 * @return card played
	 */
	Card play(String name, Card card)
	{
		Player player = fPlayers.get(name);
		Card newCard = card;
		System.out.println("name: " + player);
		if(player.getHand().contains(AllCards.C2C))
		{
			newCard = AllCards.C2C;
		}
		player.removeCard(newCard);
		return newCard;
	}

	/**
	 * The indicated player plays a card.
	 * @param name Name of the player.
	 * @param robotType Type of the robot used.
	 * @param state State of the game.
	 * @pre name != null
	 * @pre robotType != null
	 * @pre state != null
	 * @return Card played.
	 */
	Card play(String name, IRobot robotType, IImmutableGameState state)
	{
		Player player = fPlayers.get(name);
		String passedTo = fAssociates.get(name).getName();
		Card cardPlayed;
		if(player.getHand().contains(AllCards.C2C)) /* has the two of clubs then it is forced to play it */
		{
			cardPlayed = AllCards.C2C;
		}
		else
		{
			cardPlayed = robotType.getCardToPlay(state, name, passedTo, player.getHand());
		}
		player.removeCard(cardPlayed);
		return cardPlayed;
	}

	/**
	 * 
	 * @return name of player with C2C.
	 */
	public String firstPlayer()
	{
		String name = new String();

		search:
			for(Player player : fPlayers.values())
			{
				for (Card card : player.getHand())
				{
					if (card.equals(hearts.util.AllCards.C2C))
					{
						name = player.getName();
						break search;
					}
				}
			}
		return name;
	}


	/**
	 * @return the iterator of the player names.
	 */
	Iterator<String> iterator()
	{
		return fPlayers.keySet().iterator();
	}

	/**
	 * Note: The following methods are for testing purposes.
	 */

	/**
	 * @return a clone of fAssociates.
	 * TODO: currently this is mutable as player is returned
	 */
	Map<String, Player> getAssociates()
	{
		return new HashMap<String, Player>(fAssociates);
	}

	/**
	 * @return a clone of fPlayers.
	 * TODO: currently this is mutable as player is returned
	 */
	Map<String, Player> getPlayers()
	{
		return new LinkedHashMap<String, Player>(fPlayers);
	}
}
