package hearts.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import hearts.model.Game;
import hearts.util.Card;
import hearts.util.CardList;
import hearts.util.AllCards;

public class TestGame
{
	private Game testThis;
    
    @org.junit.Before public void setUp() { 
        testThis = new Game();
    }

	@Test
	public void testGetImmutableState()
	{
		ImmutableGameWrapper immutable;
		String s1, s2;
		testThis.newTrick();
		testThis.getCurrentTrick().addPlay("pPlayer", AllCards.C2C);
		immutable =  (ImmutableGameWrapper) testThis.getImmutableState();
		s1 = testThis.getCurrentTrick().getCards().toString();
		s2 = immutable.getCurrentTrick().getCards().toString();
		assertTrue(s1.equals(s2));
	}

	@Test
	public void testClear()
	{	
		testThis.newTrick();
		testThis.getCurrentTrick().addPlay("pPlayer", AllCards.C2C);
		assertEquals(testThis.tricksCompleted(), 0);
		testThis.clear();
		System.out.println(testThis.tricksCompleted());
		assertEquals(testThis.tricksCompleted(), 0);
		//Try to clear when empty
		testThis.clear();
		assertEquals(testThis.tricksCompleted(), 0);
	}
	
	@Test
	public void testGetCurrentTrick()
	{	
		String compareMe, same, different;
		
		Trick trick = new Trick();
		trick.addPlay("playerA", AllCards.CAC);
		trick.addPlay("playerB", AllCards.C2C);
		trick.addPlay("playerC", AllCards.C3C);
		trick.addPlay("playerD", AllCards.C4C);
		
		testThis.newTrick();
		testThis.getCurrentTrick().addPlay("playerA", AllCards.CAC);
		testThis.getCurrentTrick().addPlay("playerB", AllCards.C2C);
		testThis.getCurrentTrick().addPlay("playerC", AllCards.C3C);
		testThis.getCurrentTrick().addPlay("playerD", AllCards.C4C);
		
		compareMe = trick.getCards().toString();
		same = testThis.getCurrentTrick().getCards().toString();
		
		assertTrue(compareMe.equals(same));
		
		testThis.newTrick();
		testThis.getCurrentTrick().addPlay("playerA", AllCards.CAC);
		
		different = testThis.getCurrentTrick().getCards().toString();
		
		assertFalse(compareMe.equals(different));
	}
	
	@Test
	public void testTricksCompleted()
	{	
		assertEquals(testThis.tricksCompleted(),0);
		testThis.newTrick();
		testThis.getCurrentTrick().addPlay("pPlayer", AllCards.C2C);
		testThis.newTrick();
		testThis.getCurrentTrick().addPlay("pPlayer", AllCards.C2D);
		testThis.newTrick();
		testThis.getCurrentTrick().addPlay("pPlayer", AllCards.C2H);
		assertEquals(testThis.tricksCompleted(),3);
	}
	
	@Test
	public void testGetLastWinner()
	{	
		testThis.newTrick();
		
		testThis.getCurrentTrick().addPlay("playerA", AllCards.CAC);
		testThis.getCurrentTrick().addPlay("playerB", AllCards.C2C);
		testThis.getCurrentTrick().addPlay("playerC", AllCards.C3C);
		testThis.getCurrentTrick().addPlay("playerD", AllCards.C4C);
		
		assertEquals(testThis.getLastWinner(), "playerA");
		
		testThis.newTrick();
		
		//M1 corrections: getLastWinner should still work even after I made a new empty trick.
		// TA IS RIGHT> This doesn't work because Game inherits ModelExc from Trick if 'trick is empty'. We thought
		// it was supposed to respect this exception, not cover it?
		assertEquals(testThis.getLastWinner(),"playerA");
		//
	}
	
	@Test
	public void testHeartsPlayed()
	{	
		assertFalse(testThis.heartsPlayed());
		
		testThis.newTrick();
		
		testThis.getCurrentTrick().addPlay("playerA", AllCards.CAC);
		assertFalse(testThis.heartsPlayed());
		
		testThis.getCurrentTrick().addPlay("playerA", AllCards.CAH);
		
		assertTrue(testThis.heartsPlayed());
		
		testThis.newTrick();
		assertTrue(testThis.heartsPlayed());
		
		testThis.getCurrentTrick().addPlay("playerA", AllCards.CAS);

		assertTrue(testThis.heartsPlayed());
		
	}
	
	@Test
	public void testNewTrick()
	{	
		assertEquals(testThis.tricksCompleted(), 0);
		
		testThis.newTrick();
		assertEquals(testThis.tricksCompleted(), 1);
		
		for (int i = 1; i < 20; i++)
		{
			testThis.newTrick();
		}
		
		assertEquals(testThis.tricksCompleted(), 13);
	}
	
	@Test
	public void testGetCardsAccumulated()
	{	
		CardList cL1, cL2, cL3, cL4;
		testThis.newTrick();
		testThis.getCurrentTrick().addPlay("playerA", AllCards.CAC);
		testThis.getCurrentTrick().addPlay("playerA", AllCards.CAD);
		testThis.getCurrentTrick().addPlay("playerA", AllCards.CAH);
		testThis.getCurrentTrick().addPlay("playerA", AllCards.CAS);
		testThis.newTrick();
		testThis.getCurrentTrick().addPlay("playerB", AllCards.CAC);
		testThis.getCurrentTrick().addPlay("playerB", AllCards.CAD);
		testThis.getCurrentTrick().addPlay("playerB", AllCards.CAH);
		testThis.getCurrentTrick().addPlay("playerB", AllCards.CAS);
		
		testThis.newTrick();
		testThis.getCurrentTrick().addPlay("playerC", AllCards.C2C);
		testThis.getCurrentTrick().addPlay("playerC", AllCards.C2D);
		testThis.getCurrentTrick().addPlay("playerC", AllCards.C2H);
		testThis.getCurrentTrick().addPlay("playerC", AllCards.C2S);
		
		cL1 = testThis.getCardsAccumulated("playerA");
		cL2 = testThis.getCardsAccumulated("playerB");
		cL3 = testThis.getCardsAccumulated("playerC");
		assertTrue(cL1.equals(cL2));
		assertFalse(cL1.equals(cL3));
		
		testThis.newTrick();
		testThis.getCurrentTrick().addPlay("playerA", AllCards.C3C);
		testThis.getCurrentTrick().addPlay("playerA", AllCards.C3D);
		
		cL4 = testThis.getCardsAccumulated("playerA");
		assertFalse(cL1.equals(cL4));
	}
	
	@Test
	public void testGetScore()
	{	
		testThis.newTrick();
		
		testThis.getCurrentTrick().addPlay("playerA", AllCards.CAC);
		testThis.getCurrentTrick().addPlay("playerB", AllCards.C2C);
		testThis.getCurrentTrick().addPlay("playerC", AllCards.C3C);
		testThis.getCurrentTrick().addPlay("playerD", AllCards.C4C);
		
		assertEquals(testThis.getScore("playerA"), 0);
		assertEquals(testThis.getScore("playerB"), 0);
		assertEquals(testThis.getScore("playerC"), 0);
		assertEquals(testThis.getScore("playerD"), 0);
		
		testThis.newTrick();
		testThis.getCurrentTrick().addPlay("playerA", AllCards.CAC);
		testThis.getCurrentTrick().addPlay("playerB", AllCards.CAH);
		testThis.getCurrentTrick().addPlay("playerC", AllCards.C3C);
		testThis.getCurrentTrick().addPlay("playerD", AllCards.CQS);
		
		assertEquals(testThis.getScore("playerB"), 1);
		assertEquals(testThis.getScore("playerD"), 13);
	}
	
	@Test
	public void testAddPlay()
	{	
		String compareMe, same, different;
		
		Trick trick = new Trick();
		trick.addPlay("playerA", AllCards.CAC);
		trick.addPlay("playerB", AllCards.CAS);
		trick.addPlay("playerC", AllCards.CAD);

		
		testThis.newTrick();
		testThis.addPlay("playerA", AllCards.CAC);
		testThis.addPlay("playerB", AllCards.CAS);
		testThis.addPlay("playerC", AllCards.CAD);
		
		compareMe = trick.getCards().toString();
		same = testThis.getCurrentTrick().getCards().toString();
		
		assertTrue(compareMe.equals(same));
		
		testThis.addPlay("playerD", AllCards.CAH);
		different = testThis.getCurrentTrick().getCards().toString();
		
		assertFalse(compareMe.equals(different));
	}
	
	@Test (expected = ModelException.class)
	public void testAddPlayException()
	{	
		testThis.clear();
		testThis.addPlay("playerA", AllCards.CAC);
	}
	
	@Test
	public void testGetSuitLed()
	{	
		testThis.newTrick();
		testThis.addPlay("playerA", AllCards.CAC);
		
		assertEquals(Card.Suit.CLUBS, testThis.getSuitLed());
	}
	
	@Test (expected = ModelException.class)
	public void testGetSuitLedException()
	{	
		testThis.getSuitLed();
	}
}
