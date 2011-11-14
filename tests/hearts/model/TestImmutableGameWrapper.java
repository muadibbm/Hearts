package hearts.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hearts.model.ImmutableGameWrapper;
import hearts.util.AllCards;
import hearts.util.Card;
import hearts.util.CardList;


public class TestImmutableGameWrapper
{
	Game myGame, yourGame;
	IImmutableGameState myWrap, yourWrap, thirdWrap;

	
    @org.junit.Before public void setUp() 
    { 
    	myGame = new Game();
    	yourGame = new Game();
    }
	
	@Test
	public void testGetCardsAccumulated()
	{	
		myWrap = new ImmutableGameWrapper(myGame); 
		
		CardList cL1, cL2, cL3, cL4;
		myGame.newTrick();
		myGame.getCurrentTrick().addPlay("playerA", AllCards.CAC);
		myGame.getCurrentTrick().addPlay("playerA", AllCards.CAD);
		myGame.getCurrentTrick().addPlay("playerA", AllCards.CAH);
		myGame.getCurrentTrick().addPlay("playerA", AllCards.CAS);
		myGame.newTrick();
		myGame.getCurrentTrick().addPlay("playerB", AllCards.CAC);
		myGame.getCurrentTrick().addPlay("playerB", AllCards.CAD);
		myGame.getCurrentTrick().addPlay("playerB", AllCards.CAH);
		myGame.getCurrentTrick().addPlay("playerB", AllCards.CAS);
		
		myGame.newTrick();
		myGame.getCurrentTrick().addPlay("playerC", AllCards.C2C);
		myGame.getCurrentTrick().addPlay("playerC", AllCards.C2D);
		myGame.getCurrentTrick().addPlay("playerC", AllCards.C2H);
		myGame.getCurrentTrick().addPlay("playerC", AllCards.C2S);
		
		
		myWrap = new ImmutableGameWrapper(myGame); 
		cL1 = myWrap.getCardsAccumulated("playerA");
		cL2 = myWrap.getCardsAccumulated("playerB");
		cL3 = myWrap.getCardsAccumulated("playerC");
		assertTrue(cL1.equals(cL2));
		assertFalse(cL1.equals(cL3));
		
		myGame.newTrick();
		myGame.getCurrentTrick().addPlay("playerA", AllCards.C3C);
		myGame.getCurrentTrick().addPlay("playerA", AllCards.C3D);
		
		myWrap = new ImmutableGameWrapper(myGame); 
		cL4 = myWrap.getCardsAccumulated("playerA");
		assertFalse(cL1.equals(cL4));
	}
	
	@Test
	public void testGetCurrentTrick()
	{	
		Trick trick = new Trick();
		trick.addPlay("playerA", AllCards.CAC);
		trick.addPlay("playerB", AllCards.C2C);
		trick.addPlay("playerC", AllCards.C3C);
		trick.addPlay("playerD", AllCards.C4C);
		
		myGame.newTrick();
		myGame.getCurrentTrick().addPlay("playerA", AllCards.CAC);
		myGame.getCurrentTrick().addPlay("playerB", AllCards.C2C);
		myGame.getCurrentTrick().addPlay("playerC", AllCards.C3C);
		myGame.getCurrentTrick().addPlay("playerD", AllCards.C4C);
		myWrap = new ImmutableGameWrapper(myGame);
		
		assertEquals(myWrap.getCurrentTrick().getCards().toString(),trick.getCards().toString());
		boolean test = myWrap.getCurrentTrick().getCards().toString().equals(myGame.getCurrentTrick().getCards().toString());
		assertTrue(test);
		
		yourGame.newTrick();
		yourGame.getCurrentTrick().addPlay("playerA", AllCards.CAC);
		yourWrap = new ImmutableGameWrapper(yourGame);
		
		assertFalse(trick.getCards().toString().equals(yourWrap.getCurrentTrick().getCards().toString()));
		test = yourWrap.getCurrentTrick().getCards().toString().equals(myWrap.getCurrentTrick().getCards().toString());
		assertFalse(test);
	}
	
	@Test
	public void testGetScore()
	{
		myGame.newTrick();	
		myGame.getCurrentTrick().addPlay("playerA", AllCards.CAC);
		myGame.getCurrentTrick().addPlay("playerB", AllCards.C2C);
		myGame.getCurrentTrick().addPlay("playerC", AllCards.C3C);
		myGame.getCurrentTrick().addPlay("playerD", AllCards.C4C);
		
		myWrap = new ImmutableGameWrapper(myGame);
		assertEquals(myWrap.getScore("playerA"),0);
		assertEquals(myWrap.getScore("playerB"),0);
		assertEquals(myWrap.getScore("playerC"),0);
		assertEquals(myWrap.getScore("playerD"),0);
		
		myGame.newTrick();
		myGame.getCurrentTrick().addPlay("playerA", AllCards.CAC);
		myGame.getCurrentTrick().addPlay("playerB", AllCards.CAH);
		myGame.getCurrentTrick().addPlay("playerC", AllCards.C3C);
		myGame.getCurrentTrick().addPlay("playerD", AllCards.CQS);
		
		myWrap = new ImmutableGameWrapper(myGame);
		assertEquals(myWrap.getScore("playerB"), 1);
		boolean test = new Integer(myWrap.getScore("playerB")).equals(new Integer(myWrap.getScore("playerA")));
		assertFalse(test);
		assertEquals(myWrap.getScore("playerD"), 13);	
	}
	
	@Test
	public void testGetSuitLed() //throws ModelException
	{
		myGame.newTrick();
		myGame.addPlay("playerA", AllCards.CAC);
		myWrap = new ImmutableGameWrapper(myGame);		
		assertEquals(Card.Suit.CLUBS, myWrap.getSuitLed());
		
		myGame.addPlay("playerB", AllCards.CAD);
		myWrap = new ImmutableGameWrapper(myGame);		
		assertEquals(Card.Suit.CLUBS, myWrap.getSuitLed());
		
		myGame.addPlay("playerC", AllCards.CAH);
		myWrap = new ImmutableGameWrapper(myGame);
		assertEquals(Card.Suit.CLUBS.toString(), myWrap.getSuitLed().toString());

	}


	@Test
	public void testHeartsPlayed()
	{
		/** TODO: finish */

		myGame.newTrick();
		myGame.getCurrentTrick().addPlay("playerA", AllCards.CAC);
		myGame.getCurrentTrick().addPlay("playerB", AllCards.C2C);
		myGame.getCurrentTrick().addPlay("playerC", AllCards.C3C);
		myGame.getCurrentTrick().addPlay("playerD", AllCards.C4C);
		myWrap = new ImmutableGameWrapper(myGame);
		
		assertFalse(myWrap.heartsPlayed());
	}

	
	
	@Test
	public void testTricksCompleted()
	{	
		myWrap = myGame.getImmutableState();
		yourWrap = yourGame.getImmutableState();
		assertEquals(myWrap.tricksCompleted(),yourWrap.tricksCompleted());
		
		myGame.newTrick();
		myWrap = myGame.getImmutableState();
		assertEquals(myWrap.tricksCompleted(),	1);
		
		myGame.clear();
		myGame.newTrick();
		myGame.newTrick();
		myGame.newTrick();
		myGame.newTrick();		
		yourGame.newTrick();
		yourGame.newTrick();
		yourGame.newTrick();
		yourGame.newTrick();
		myWrap = myGame.getImmutableState();
		yourWrap = yourGame.getImmutableState();
		assertEquals(myWrap.tricksCompleted(),yourWrap.tricksCompleted()); 
		
		myGame.clear();
		yourGame.clear();
		myWrap = myGame.getImmutableState();
		yourWrap = myGame.getImmutableState();
		assertEquals(myWrap.tricksCompleted(),yourWrap.tricksCompleted());
		assertEquals(myWrap.tricksCompleted(),0);
	}
}
