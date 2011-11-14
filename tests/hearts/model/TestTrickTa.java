package hearts.model;

import static hearts.util.AllCards.C2C;
import static hearts.util.AllCards.C3D;
import static hearts.util.AllCards.C4C;
import static hearts.util.AllCards.C5C;
import static hearts.util.AllCards.C5H;
import static hearts.util.AllCards.CAC;
import static hearts.util.AllCards.CKC;
import static hearts.util.AllCards.CKH;
import static hearts.util.AllCards.CQS;
import static org.junit.Assert.assertEquals;
import hearts.util.Card;
import hearts.util.Card.Suit;
import hearts.util.CardList;
import hearts.util.Deck;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class TestTrickTa
{
	private Deck fDeck;
	
	@Before
	public void setup()
	{
		fDeck = new Deck();
		fDeck.shuffle();
	}
	
	@Test(expected=hearts.model.ModelException.class)
	public void testNormalPlay()
	{
		Trick lT = new Trick();
		assertEquals(0, lT.cardsPlayed());
		lT.addPlay("Player1", C2C);
		assertEquals(1, lT.cardsPlayed());
		lT.addPlay("Player2", C4C);
		assertEquals(2, lT.cardsPlayed());
		lT.addPlay("Player3", C5C);
		assertEquals(3, lT.cardsPlayed());
		lT.addPlay("Player4", CAC);
		assertEquals(4, lT.cardsPlayed());
		lT.addPlay("Player5", CKC);
	}
	
	@Test
	public void testSuitNotFollowed2()
	{
		Trick lT = new Trick();
		assertEquals(0, lT.cardsPlayed());
		lT.addPlay("Player1", C2C);
		assertEquals(1, lT.cardsPlayed());
		lT.addPlay("Player2", C4C);
		assertEquals(2, lT.cardsPlayed());
		lT.addPlay("Player3", C5H);
		assertEquals(3, lT.cardsPlayed());
		lT.addPlay("Player4", CAC);
		assertEquals(4, lT.cardsPlayed());
	}
	
	@Test
	public void testGetCards()
	{
		Trick lT = new Trick();
		assertEquals(0, lT.cardsPlayed());
		lT.addPlay("Player1", C2C);
		assertEquals(1, lT.cardsPlayed());
		lT.addPlay("Player2", C4C);
		assertEquals(2, lT.cardsPlayed());
		lT.addPlay("Player3", C5H);
		assertEquals(3, lT.cardsPlayed());
		lT.addPlay("Player4", CAC);
		assertEquals(4, lT.cardsPlayed());
		CardList lList = lT.getCards();
		Iterator<Card> i = lList.iterator();
		assertEquals(C2C, i.next());
		assertEquals(C4C, i.next());
		assertEquals(CAC, i.next());
		assertEquals(C5H, i.next());
	}
	
	@Test(expected=hearts.model.ModelException.class)
	public void testGetSuitLed()
	{
		Trick lT = new Trick();
		lT.getSuitLed();
	}
	
	@Test
	public void testGetSuitLed2()
	{
		Trick lT = new Trick();
		lT.addPlay("Player1", C2C);
		lT.addPlay("Player2", C3D);
		lT.addPlay("Player3", C5H);
		lT.addPlay("Player4", CQS);
		Suit sL = lT.getSuitLed();
		assertEquals(Suit.CLUBS, sL);
	}
	
	@Test
	public void testGetWinner1()
	{
		Trick lT = new Trick();
		lT.addPlay("A", C2C);
		lT.addPlay("B", C3D);
		lT.addPlay("C", C4C);
		lT.addPlay("D", C5C);
		assertEquals("D", lT.getWinner());
	}
	
	@Test
	public void testGetWinner2()
	{
		Trick lT = new Trick();
		lT.addPlay("A", C2C);
		lT.addPlay("B", C3D);
		lT.addPlay("C", C5H);
		lT.addPlay("D", C5C);
		assertEquals("D", lT.getWinner());
	}
	
	@Test
	public void testGetWinner3()
	{
		Trick lT = new Trick();
		lT.addPlay("A", C2C);
		lT.addPlay("B", C3D);
		lT.addPlay("C", C5H);
		lT.addPlay("D", CKH);
		assertEquals("A", lT.getWinner());
	}
	
	@Test(expected=hearts.model.ModelException.class)
	public void testGetWinner4()
	{
		new Trick().getWinner();
	}
}
