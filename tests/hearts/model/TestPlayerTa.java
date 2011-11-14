package hearts.model;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import hearts.util.Card;
import hearts.util.CardList;
import hearts.util.Constants;
import hearts.util.Card.Suit;

import static hearts.util.AllCards.C2C;
import static hearts.util.AllCards.C3C;
import static hearts.util.AllCards.CAD;
import static hearts.util.AllCards.C3D;
import static hearts.util.AllCards.C4C;
import static hearts.util.AllCards.C5C;
import static hearts.util.AllCards.C5H;
import static hearts.util.AllCards.C6S;
import static hearts.util.AllCards.C8D;
import static hearts.util.AllCards.C9H;
import static hearts.util.AllCards.CAH;
import static hearts.util.AllCards.CJS;
import static hearts.util.AllCards.CAC;
import static hearts.util.AllCards.CKC;
import static hearts.util.AllCards.CKH;
import static hearts.util.AllCards.CQS;

public class TestPlayerTa
{
	private Player fPlayer;
	
	@Before
	public void setup()
	{		
		fPlayer = new Player("testplayer");
	}
	
	@Test
	public void testGetName()
	{
		assertEquals("testplayer", fPlayer.getName());
	}
	
	@Test
	public void testToString()
	{
		assertEquals("testplayer", fPlayer.toString());
	}
	
	@Test
	public void testClearHand()
	{
		fPlayer.clearHand();
		assertEquals(0, fPlayer.getHand().size());
		fPlayer.addCard(C2C);
		fPlayer.clearHand();
		assertEquals(0, fPlayer.getHand().size());
	}
	
	@Test
	public void testAddCard()
	{
		fPlayer.clearHand();
		fPlayer.addCard(C2C);
		assertEquals(1, fPlayer.getHand().size());
		assertTrue(fPlayer.getHand().contains(C2C));
		fPlayer.addCard(CAD);
		assertEquals(2, fPlayer.getHand().size());
		assertTrue(fPlayer.getHand().contains(CAD));
	}
	
	@Test(expected=hearts.model.ModelException.class)
	public void testAddCard2()
	{
		fPlayer.clearHand();
		fPlayer.addCard(C2C);
		fPlayer.addCard(C3D);
		fPlayer.addCard(C4C);
		fPlayer.addCard(C5H);
		fPlayer.addCard(C5C);
		fPlayer.addCard(C6S);
		fPlayer.addCard(C8D);
		fPlayer.addCard(C9H);
		fPlayer.addCard(CJS);
		fPlayer.addCard(CQS);
		fPlayer.addCard(CKC);
		fPlayer.addCard(CAC);
		fPlayer.addCard(CKH);
		assertEquals(Constants.NUMBER_OF_TRICKS, fPlayer.getHand().size());
		fPlayer.addCard(CAD);
	}
	
	/*@Test
	public void testGetHandString()
	{
		fPlayer.clearHand();
		fPlayer.addCard(C2C);
		assertEquals("2C", fPlayer.getHandString());
		fPlayer.addCard(CAD);
		assertEquals("2C AD", fPlayer.getHandString());
	}*/
	
	@Test
	public void testAddCards()
	{
		fPlayer.clearHand();
		CardList cList = new CardList(Constants.NUMBER_OF_TRICKS);
		cList.add(C2C);
		fPlayer.addCards(cList);
		assertEquals(1, fPlayer.getHand().size());
		assertTrue(fPlayer.getHand().contains(C2C));
		fPlayer.clearHand();
		cList.add(CAD);
		cList.add(CJS);
		fPlayer.addCards(cList);
		assertEquals(3, fPlayer.getHand().size());
		assertTrue(fPlayer.getHand().contains(C2C));
		assertTrue(fPlayer.getHand().contains(CAD));
		assertTrue(fPlayer.getHand().contains(CJS));
	}
	
	@Test
	public void testRemoveCards()
	{
		fPlayer.clearHand();
		CardList cList = new CardList(Constants.NUMBER_OF_TRICKS);
		cList.add(C2C);
		cList.add(C3D);
		cList.add(C4C);
		cList.add(C5H);
		fPlayer.addCards(cList);
		assertEquals(4, fPlayer.getHand().size());
		fPlayer.removeCards(cList);
		assertEquals(0, fPlayer.getHand().size());
	}
	
	@Test(expected=hearts.model.ModelException.class)
	public void testRemoveCards2()
	{
		fPlayer.clearHand();
		CardList cList = new CardList(Constants.NUMBER_OF_TRICKS);
		cList.add(C2C);
		cList.add(C3D);
		cList.add(C4C);
		cList.add(C5H);
		fPlayer.addCards(cList);
		cList.add(CJS);
		assertEquals(4, fPlayer.getHand().size());
		fPlayer.removeCards(cList);
	}
	
	@Test
	public void testRemoveCard()
	{
		fPlayer.clearHand();
		fPlayer.addCard(C2C);
		fPlayer.removeCard(C2C);
		assertEquals(0, fPlayer.getHand().size());
		assertFalse(fPlayer.getHand().contains(C2C));
	}
	
	@Test(expected=hearts.model.ModelException.class)
	public void testRemoveCard2()
	{
		fPlayer.clearHand();
		fPlayer.addCard(C2C);
		assertEquals(1, fPlayer.getHand().size());
		fPlayer.removeCard(CAD);
		assertEquals(1, fPlayer.getHand().size());	
		assertTrue(fPlayer.getHand().contains(C2C));
	}
	
	@Test
	public void testAddToScore()
	{
		assertEquals(0, fPlayer.getScore());
		fPlayer.addToScore(3);
		assertEquals(3, fPlayer.getScore());
		fPlayer.addToScore(-1);
		assertEquals(2, fPlayer.getScore());
	}
	
	@Test
	public void testResetScore()
	{
		assertEquals(0, fPlayer.getScore());
		fPlayer.addToScore(3);
		assertEquals(3, fPlayer.getScore());
		fPlayer.addToScore(-1);
		assertEquals(2, fPlayer.getScore());
		fPlayer.resetScore();
		assertEquals(0, fPlayer.getScore());
	}
	
	@Test
	public void testGetCardsFor()
	{
		fPlayer.clearHand();
		CardList lC = new CardList(4);
		lC.add(CQS);
		lC.add(C5C);
		lC.add(CAH);
		lC.add(C3C);
		fPlayer.addCards(lC);
		CardList lR = fPlayer.getCardsFor(Suit.CLUBS);
		assertEquals(2, lR.size());
		assertTrue(lR.contains(C3C));
		assertTrue(lR.contains(C5C));
		lR = fPlayer.getCardsFor(Suit.DIAMONDS);
		assertEquals(0, lR.size());
		lR =  fPlayer.getCardsFor(Suit.HEARTS);
		assertEquals(1, lR.size());
		assertTrue(lR.contains(CAH));
		lR =  fPlayer.getCardsFor(Suit.SPADES);
		assertEquals(1, lR.size());
		assertTrue(lR.contains(CQS));
	}
	
	@Test
	public void testGetHand()
	{
		fPlayer.clearHand();
		CardList lC = new CardList(3);
		lC.add(C3C);
		lC.add(CQS);
		fPlayer.addCards(lC);
		CardList lClone = fPlayer.getHand();
		assertFalse(lC == lClone);
		Iterator<Card> lCards = lClone.iterator();
		assertTrue(C3C == lCards.next());
		assertTrue(CQS == lCards.next());
	}
}
