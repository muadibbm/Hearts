package hearts.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import hearts.robots.IRobot;
import hearts.listeners.IGameListener;
import hearts.listeners.Log;
import hearts.model.Game;
import hearts.model.ModelException;
import hearts.util.Card;
import hearts.util.CardList;
import hearts.util.Constants;
import hearts.util.Deck;
import hearts.gameEvent.EndPassingEvent;
import hearts.gameEvent.EndRoundEvent;
import hearts.gameEvent.IEvent;
import hearts.gameEvent.NewGameEvent;
import hearts.gameEvent.NewTrickEvent;
import hearts.gameEvent.NewRoundEvent;
import hearts.gameEvent.EndTrickEvent;
import hearts.gameEvent.EndGameEvent;
import hearts.gameEvent.PassEvent;
import hearts.gameEvent.PlayEvent;

/**
 * Manages the game.
 */
public final class GameEngine implements IGameSubject
{
	private static final GameEngine INSTANCE = new GameEngine();
	
	/** Passing direction cycle. */
	public enum PassingDirection
	{LEFT,RIGHT,ACROSS,NONE};
	private LinkedList<PassingDirection> fPassDir;
	private ArrayList<String> fPlayerNames;
	private ArrayList<String> fFixedPlayerNames;
	private ArrayList<String> fDealers;
	private ArrayList<IRobot> fRobots;
		
	private ArrayList<IGameListener> fListeners;
	
	private PlayerManager fPManager;
	private Game fGame;
	private Deck fDeck;
	
		
	private GameEngine()
	{	
		fGame = new Game();
		fDeck = new Deck();
		fListeners = new ArrayList<IGameListener>();
		fPManager = new PlayerManager();				// Manages player objects
		fPlayerNames = new ArrayList<String>();			// List of player names, in clockwise order
		fFixedPlayerNames = new ArrayList<String>();
		fDealers = new ArrayList<String>();
		fRobots =  new ArrayList<IRobot>();				// List of robots
		fPassDir =  new LinkedList<PassingDirection>(); // List of passing direction
		addGameListener(new Log());
	}
	
	/**
	 * Get instance object of GameEngineFacade.
	 * @return Instance
	 */
	public static GameEngine getInstance()
	{
		return INSTANCE;
	}
	
	/**
	 * Add a human player in clockwise order.
	 * @param name Name of the player.
	 * @pre name != nulls 
	 */
	public void addPlayer(String name)
	{
		fPlayerNames.add(name);
		fFixedPlayerNames.add(name);
		fDealers.add(name);
		fPManager.addPlayer(name);
		fRobots.add(null);
	}
	
	/**
	 * Add a robot player in clockwise order.
	 * @param name Name of the player.
	 * @param robotType object of type class that extends IRobot corresponding to player name
	 * @pre name != null
	 * @pre robotType != null
	 */
	public void addPlayer(String name, IRobot robotType)
	{
		fPlayerNames.add(name);
		fFixedPlayerNames.add(name);
		fDealers.add(name);
		fPManager.addPlayer(name);
		fRobots.add(robotType);
	}
	
	/**
	 * Initializes a new game.
	 */
	public void initGame()
	{
		newGame();
		newRound();
		newTrick();
	}
	
	/**
	 * Clear all the info related to the players so that
	 * new players with different names can be added.
	 */
	public void clearPlayers()
	{
		fPManager.clearPlayers();
		fPlayerNames.clear();
		fFixedPlayerNames.clear();
		fDealers.clear();
		fRobots.clear();
		fPassDir.clear();
	}
	
	/**
	 * Creates a new empty game.
	 * @pre Players have been added already.
	 */
	protected void newGame()
	{
		NewGameEvent event = new NewGameEvent();
		// Order of addition is important here.
		fPassDir.add(PassingDirection.LEFT);
		fPassDir.add(PassingDirection.RIGHT);
		fPassDir.add(PassingDirection.ACROSS);
		fPassDir.add(PassingDirection.NONE); 
		
		fPManager.reset();
		fGame.clear();
		
		notifyListeners(event);
	}
	
	/**
	 * Creates a new empty round.
	 */
	protected void newRound()
	{
		NewRoundEvent event = new NewRoundEvent();
		fGame.clear();
		fPManager.clearHands(); // useful?
		fDeck.shuffle(); // refills and shuffle.
		System.out.println("Here");
		dealCards(event);
		fPManager.setAssociation(getPassingDirection(), fFixedPlayerNames);
		event.setPassingDirection(getPassingDirection());
		//Start automatically with C2C
		while(!fPlayerNames.get(0).equals(fPManager.firstPlayer()))
		{
			rotateToNextPlayer();
		}
		notifyListeners(event);
	}
	
	
	/**
	 * Creates a new trick and adds it to the game.
	 */
	protected void newTrick()
	{
		fGame.newTrick();
		// Last winner starts the trick
		if(fGame.tricksCompleted() > 0)
		{
			while(!fPlayerNames.get(0).equals(fGame.getLastWinner()))
			{
				rotateToNextPlayer();
			}
		}
		NewTrickEvent event = new NewTrickEvent(fPlayerNames.get(0));
		notifyListeners(event);
	}
	
	/**
	 * Deal the cards of the deck to each player.
	 * @param event New round event.
	 */
	protected void dealCards(NewRoundEvent event)
	{
		Map<String, CardList> hands = new HashMap<String, CardList>();
		for (String name : fPlayerNames)
		{
			hands.put(name, new CardList(Constants.NUMBER_OF_TRICKS));
		}
		while(!fDeck.isEmpty())
		{
			for(int i = 0; i<Constants.NUMBER_OF_PLAYERS; i++)
			{
				hands.get(fPlayerNames.get(i)).add(fDeck.draw());
			}
		}
		for(String name : fPlayerNames)
		{
			fPManager.setHand(name, hands.get(name));
			event.setHand(name, hands.get(name));
		}
		rotateDealer();
		event.setDealer(fDealers.get(0));
	}
	
	/**
	 * Cycle through the passing order: left -> right -> across -> none and back to the start.
	 */
	protected void rotatePassingDirection()
	{
		Collections.rotate(fPassDir, -1);
	}
	
	/**
	 * Change dealer role.
	 */
	protected void rotateDealer()
	{
		Collections.rotate(fDealers, -1);
	}
	
	/**
	 * Obtain the direction of the card passing.
	 * @return PassingDirection
	 */
	public PassingDirection getPassingDirection()
	{
		return fPassDir.peek();
	}
	
	/**
	 * Rotates the player names' list such that
	 * the first element is the current player.
	 * It also adjust the robot list.
	 */
	protected void rotateToNextPlayer()
	{
		Collections.rotate(fPlayerNames, -1);
		Collections.rotate(fRobots, -1);
	}
	
	/**
	 * Checks if the trick is completed.
	 * @return true if trick is over.
	 */
	protected boolean trickIsOver()
	{
		if(fGame.getCurrentTrick().cardsPlayed() >= Constants.NUMBER_OF_PLAYERS)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * Ends the current trick.
	 */
	protected void endTrick()
	{
		EndTrickEvent event = new EndTrickEvent();
		// Compile trick score.
		int points = fPManager.setScore(fGame.getCurrentTrick().getWinner(), fGame.getCurrentTrick().getCards());
		fGame.putWinner(fGame.getCurrentTrick().getWinner(), points);
		for(String name : fPlayerNames)
		{
			event.setPlayerScore(name, fPManager.getScore(name));
		}
		notifyListeners(event);
	}
	
	/**
	 * Checks if all the tricks are completed
	 * to determine whether the round is over.
	 * @return true if round is over.
	 */
	protected boolean roundIsOver()
	{
		if(fGame.tricksCompleted() >= Constants.NUMBER_OF_TRICKS)
		{
			return true;
		}
		return false;
	}
	
	/**
	 * When round is over, checks if any player has shot the moon.
	 * @pre round is over
	 */
	protected void endRound()
	{
		rotatePassingDirection();
		String winner;
		try
		{
			winner = fGame.shootTheMoon();
			fPManager.shootTheMoon(winner);
		}
		catch (ModelException e)
		{	
		}
		notifyListeners(new EndRoundEvent());
	}

	/**
	 * Checks if a player has reached score 100
	 * to determine whether the game is over.
	 * @return true if game is over.
	 */
	protected boolean gameIsOver()
	{
		for(String name: fPlayerNames)
		{
			if(fPManager.getScore(name) >= Constants.MAX_SCORE) //more than 100, game stops.
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * End current game by setting the winner.
	 */
	protected void endGame()
	{
		fPManager.setGameWinner();
		String winner = fPManager.getGameWinner();
		EndGameEvent event = new EndGameEvent(winner, fPManager.getScore(winner));
		for(String name : fPlayerNames)
		{
			event.setPlayerScore(name, fPManager.getScore(name));
		}
		notifyListeners(event);
	}
	
	/**
	 * Plays, and updating the game with
	 * new trick/round/game only if necessary.
	 * Ends when the game ends.
	 * @throws GameException if the game is over.
	 */
	protected void playSetup() throws GameException
	{
		if(trickIsOver())
		{
			endTrick();
			if(roundIsOver())
			{
				endRound();
				if(gameIsOver())
				{
					endGame();
					return;
				}
				newRound();
			}
			newTrick();
		}
	}
	
	
	/**
	 * Pass all the cards of the robot players.
	 * @param humanName Name of the human (not passing cards).
	 */
	public void passCardsRobot(String humanName)
	{
		for(int i = 0; i<Constants.NUMBER_OF_PLAYERS; i++)
		{
			String name = fPlayerNames.get(i);
			if(!name.equals(humanName))
			{
				PassEvent event = fPManager.passCards(name, fRobots.get(i));
				notifyListeners(event);
			}
		}
	}
	
	
	/**
	 * Pass cards function for the human.
	 * @param name Name of the player.
	 * @param cardsPassed Card to be passed.
	 * @pre name != null
	 * @pre cardsPassed != null
	 */
	public void passCards(String name, CardList cardsPassed)
	{
		PassEvent event = fPManager.passCards(name, cardsPassed);
		notifyListeners(event);
		passBuffers();
		//Start automatically with C2C
		while(!fPlayerNames.get(0).equals(fPManager.firstPlayer()))
		{
			rotateToNextPlayer();
		}
	}
	
	/**
	 * Distribute all the passing buffers back.
	 */
	protected void passBuffers()
	{
		EndPassingEvent event = new EndPassingEvent();
		for(int i = 0; i<Constants.NUMBER_OF_PLAYERS; i++)
		{
			fPManager.passBufferToHand(fPlayerNames.get(i), event);
		}
		notifyListeners(event);
	}
	
	/**
	 * Automated play.
	 */
	public void play()
	{
		String name = fPlayerNames.get(0);
		Card cardPlayed = fPManager.play(name, fRobots.get(0), fGame.getImmutableState());
		fGame.addPlay(name, cardPlayed);
		rotateToNextPlayer();
		notifyListeners(new PlayEvent(name, cardPlayed, fGame.getCurrentTrick().getSuitLed()));
		playSetup();
	}
	
	/**
	 * Human play.
	 * @param name Name of player to play
	 * @param card Card to play
	 * @pre name != null
	 * @pre card != null
	 */
	public void play(String name, Card card)
	{
		Card cardPlayed = fPManager.play(name, card);
		fGame.addPlay(name, cardPlayed);
		rotateToNextPlayer();
		PlayEvent event = new PlayEvent(name, cardPlayed, fGame.getCurrentTrick().getSuitLed());
		event.setHuman(true);
		notifyListeners(event);
		playSetup();
	}
	
	/**
	 * Get the statistics of the specified player.
	 * @param name Name of the player.
	 * @return The number of games won.
	 */
	public int getStatistic(String name)
	{
		return fPManager.getStatistic(name);
	}
	
	/**
	 * registers a listener and adds it to the list.
	 * @param pListener to add to list of listeners.
	 */
	public void addGameListener(IGameListener pListener)
	{
		fListeners.add(pListener);
		//notifyListeners(pListener.getClass().getCanonicalName()+" added to game listeners.");
	}
	
	/**
	 * unregisters a listener from the list.
	 * @param pListener to remove to list of listeners.
	 */
	public void removeGameListener(IGameListener pListener)
	{
		int i = fListeners.indexOf(pListener);
		if (i >= 0)
		{
			fListeners.remove(i);
		}
	}
	
	/**
	 * iterates through list of listeners and calls the update method on each.
	 * Right now only updates the log.
	 * Will be expanded for GUI implementation.
	 * @param event Type of event
	 */
	public void notifyListeners(IEvent event)
	{
		for(IGameListener listener : fListeners)
		{
			listener.update(event);
		}
	}
}
