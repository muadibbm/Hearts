package hearts.robots;

import hearts.model.Game;
import hearts.model.ImmutableGameWrapper;
import hearts.model.Player;
import hearts.robots.RandomRobot;
import hearts.util.AllCards;
import hearts.util.Card;
import hearts.util.CardList;
import hearts.util.Constants;

import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

public class TestRandomRobot
{
	RandomRobot robot;
	
	@org.junit.Before public void setUp() { 
		robot = new RandomRobot();
    }
	
	@Test
	public void testGetCardsToPass()
	{
		CardList cardlist = new CardList(Constants.NUMBER_OF_TRICKS);
		CardList passList = new CardList(Constants.NUMBER_OF_CARDS_PASSED);
		CardList original = new CardList(Constants.NUMBER_OF_TRICKS);
		
		cardlist.add(AllCards.C2C);
		cardlist.add(AllCards.C3C);
		cardlist.add(AllCards.C4C);
		cardlist.add(AllCards.C2D);
		cardlist.add(AllCards.CJD);
		cardlist.add(AllCards.C2H);
		cardlist.add(AllCards.C2S);
		
		original = cardlist.clone();
		passList = robot.getCardsToPass(cardlist);
		
		assertEquals(passList.size(), Constants.NUMBER_OF_CARDS_PASSED);
		for (Card card : passList)
		{
			assertFalse(cardlist.contains(card));
			assertTrue(original.contains(card));
		}
	}

	@Test
	public void testGetCardToPlay()
	{
		Card cardPlayed;
		Game game = new Game();
		CardList cardlist = new CardList(Constants.NUMBER_OF_TRICKS);
		CardList original = new CardList(Constants.NUMBER_OF_TRICKS);
		
		
		cardlist.add(AllCards.C2C);
		cardlist.add(AllCards.C3C);
		cardlist.add(AllCards.C4C);
		cardlist.add(AllCards.C2D);
		cardlist.add(AllCards.CJD);
		cardlist.add(AllCards.C2H);
		cardlist.add(AllCards.C2S);
		
		original = cardlist.clone();
		cardPlayed = robot.getCardToPlay(new ImmutableGameWrapper(game),"Player1", "Player2", cardlist);
		
		assertTrue(original.contains(cardPlayed));
	}
}
