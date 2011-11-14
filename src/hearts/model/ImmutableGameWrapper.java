package hearts.model;

import java.util.List;

import hearts.util.CardList;
import hearts.util.Card.Suit;

/**
 * Reference to a game, with no mutators.
 */
public class ImmutableGameWrapper implements IImmutableGameState
{
	private Game fGame;
	
	/**
	 * Create a new ImmutableGameWrapper object.
	 * @param pGame new Game object to pass to field fGame
	 */
	public ImmutableGameWrapper(Game pGame)
	{
		fGame = pGame;
	}
	
	@Override
	public CardList getCardsAccumulated(String pPlayer)
	{	
		return fGame.getCardsAccumulated(pPlayer).clone();
	}
	
	@Override
	public Trick getCurrentTrick()
	{
		Trick nTrick = new Trick();
		List<Play> plays = fGame.getCurrentTrick().getTrick();
		for (Play play: plays)
		{
			nTrick.addPlay(play.getPlayer(), play.getCard());
		}
		return nTrick;
	}
	
	@Override
	public int getScore(String pPlayer)
	{
		return fGame.getScore(pPlayer);
	}
	
	@Override
	public Suit getSuitLed() throws ModelException
	{
		return fGame.getSuitLed();
	}

	@Override
	public boolean heartsPlayed()
	{
		return fGame.heartsPlayed();
	}

	@Override
	public int tricksCompleted()
	{
		return fGame.tricksCompleted();
	}

}
