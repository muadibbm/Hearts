package hearts.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import hearts.model.Player;
import hearts.util.CardList;
import hearts.util.CardListException;
import hearts.util.Card.Suit;

public class TestPlayer
{
	Player juli, juli2, empty;
	
	@org.junit.Before public void setUp() 
	{ 
	juli = new Player("juli");
	juli2 = new Player("juli");
	empty = new Player("");
	}
	
	@Test
	public void testPlayer()
	{
		assertEquals(juli.getHand().size(),0);
	}

	@Test
	public void testGetName()
	{
		assertEquals(juli.getName(),"juli");
		assertEquals(juli2.getName(),juli.getName());
		assertEquals(empty.getName(),"");
	}
	
	@Test
	public void testToString()
	{
		assertEquals(juli.toString(),juli.getName());
		assertEquals(juli.toString(),"juli");
		assertEquals(juli.toString(),juli2.toString());
		assertEquals(empty.toString(),"");
	}
	
	@Test
	public void testClearHand()
	{
		juli.clearHand();

		juli.addCard(hearts.util.AllCards.C2C);
		juli.addCard(hearts.util.AllCards.C5C);
		juli.clearHand();
		assertEquals(juli.getHand().size(),0);	
	}
	
	@Test
	public void testAddCards()
	{
		CardList hand = new CardList(10);
		hand.add(hearts.util.AllCards.C2C);
		hand.add(hearts.util.AllCards.C5C);

		juli.addCards(hand);
		assertEquals(juli.getHand().toString(),hand.toString());
		assertEquals(juli.getHand().size(),2);
				
		CardList empty = new CardList(10);
		juli.clearHand();
		juli.addCards(empty);
		assertEquals(juli.getHand().size(),0);			
	}
	
	@Test(expected=CardListException.class)
	public void testCardListExceptionForAddCards()
	{
		CardList hand = new CardList(10);
		hand.add(hearts.util.AllCards.C2C);
		hand.add(hearts.util.AllCards.C5C);

		juli.addCards(hand);
		juli.addCards(hand);
	}
	
	@Test
	public void testGetHandString()
	{
		CardList hand = new CardList(10);
		hand.add(hearts.util.AllCards.C2C);
		hand.add(hearts.util.AllCards.C5C);

		juli.addCards(hand);
		juli2.addCards(hand);
		assertEquals(juli.getHandString(),juli2.getHandString());
		juli.clearHand();
		juli2.clearHand();
		assertEquals(juli.getHandString(),juli2.getHandString());
	}
	
	@Test
	public void testRemoveCards() 
	{
		CardList hand = new CardList(10);
		CardList subHand = new CardList(10);
		hand.add(hearts.util.AllCards.C2C);
		hand.add(hearts.util.AllCards.C5C);
		subHand.add(hearts.util.AllCards.C2C);
		
		juli.addCards(hand);
		juli.removeCards(subHand);
		assertEquals(juli.getHand().size(),1);
		
		juli.clearHand();
		juli.addCards(hand);
		juli.removeCards(hand);
		assertEquals(juli.getHand().size(),0);
	}
	
	@Test(expected=ModelException.class)
	public void testModelExceptionForRemoveCards()
	{
		CardList hand = new CardList(10);
		hand.add(hearts.util.AllCards.C2C);
		hand.add(hearts.util.AllCards.C5C);

		juli.addCards(hand);
		juli.removeCards(hand);
		juli.removeCards(hand);
	}
	
	@Test
	public void testAddCard() 
	{
		juli.addCard(hearts.util.AllCards.C2C);
		juli.addCard(hearts.util.AllCards.C5C);
		assertEquals(juli.getHand().size(),2);
		
		juli.addCard(hearts.util.AllCards.C5C);
	}
	
	@Test(expected=ModelException.class)
	public void testModelExceptionForAddCard()
	{
		CardList full = new CardList(13);
		full.add(hearts.util.AllCards.C2C);
		full.add(hearts.util.AllCards.C3C);
		full.add(hearts.util.AllCards.C4C);
		full.add(hearts.util.AllCards.C5C);
		full.add(hearts.util.AllCards.C6C);
		full.add(hearts.util.AllCards.C7C);
		full.add(hearts.util.AllCards.C8C);
		full.add(hearts.util.AllCards.C9C);
		full.add(hearts.util.AllCards.CTC);
		full.add(hearts.util.AllCards.CJC);
		full.add(hearts.util.AllCards.CQC);
		full.add(hearts.util.AllCards.CKC);
		full.add(hearts.util.AllCards.CAC);

		juli.addCards(full);
		juli.addCard(hearts.util.AllCards.C2H);
	}
	
	@Test
	public void testRemoveCard()
	{
		juli.addCard(hearts.util.AllCards.C2H);
		juli.removeCard(hearts.util.AllCards.C2H);
		assertEquals(juli.getHand().size(),0);
	}
	
	@Test(expected=ModelException.class)
	public void testModelExceptionForRemoveCard()
	{
		juli.removeCard(hearts.util.AllCards.C2H);
	}
	
	@Test
	public void testGetHand()
	{
		CardList hand = new CardList(10);
		hand.add(hearts.util.AllCards.C2C);
		hand.add(hearts.util.AllCards.C5C);

		juli.addCards(hand);
		assertEquals(juli.getHand().toString(),hand.toString());
		boolean test = juli.getHand().toString().equals(hand.toString());
		assertTrue(test);
	}
	
	@Test
	public void testGetScore()
	{
		assertEquals(juli.getScore(), 0);
		
		juli.addToScore(3);
		assertEquals(juli.getScore(),3);
	}
	
	@Test
	public void testResetScore()
	{
		juli.resetScore();
		assertEquals(juli.getScore(),0);
		
		juli.addToScore(3);
		juli.resetScore();
		assertEquals(juli.getScore(),0);
	}
	
	@Test
	public void testGetCardsFor()
	{
		CardList hand = new CardList(10);
		hand.add(hearts.util.AllCards.C2C);
		hand.add(hearts.util.AllCards.C5C);
		juli.addCards(hand);

		assertEquals(juli.getCardsFor(Suit.CLUBS).toString(),hand.toString());
		assertEquals(juli.getCardsFor(Suit.CLUBS).size(),2);
		assertEquals(juli.getCardsFor(Suit.HEARTS).size(),0);
	}
	
	@Test
	public void testAddToScore()
	{
		juli.addToScore(3);
		assertEquals(juli.getScore(),3);
		juli.addToScore(2);
		assertEquals(juli.getScore(),5);
		juli.addToScore(0);
		assertEquals(juli.getScore(),5);
	}
}
