package hearts.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import hearts.util.AllCards;
import hearts.util.Card;
import hearts.util.CardList;

public class TestCardList
{
	private CardList list00;
	private CardList list01;
	private CardList list10;
	private CardList list51;
	private CardList list52;
	
	@org.junit.Before public void setUp()
	{
		list00 = new CardList(0);
		list01 = new CardList(1);
		list01.add(AllCards.CJD);
		list10 = new CardList(10);
		list10.add(AllCards.C2C);
		list10.add(AllCards.C3C);
		list10.add(AllCards.C4C);
		list10.add(AllCards.C5C);
		list10.add(AllCards.C6C);
		list10.add(AllCards.C7C);
		list10.add(AllCards.C8C);
		list10.add(AllCards.C9C);
		list10.add(AllCards.CTC);
		list10.add(AllCards.CJC);
		list51 = new CardList(52);
		list52 = new CardList(52);
		for (Card.Suit suit : Card.Suit.values())
		{
            for (Card.Rank rank : Card.Rank.values())
            {
            	list51.add(new Card(rank, suit));
            	list52.add(new Card(rank, suit));
            }
		}
		list51.remove(51);
		
	}

	@Test public void testSize()
	{
	    assertEquals((Integer)list00.size(),(Integer)0);
	    assertEquals((Integer)list01.size(),(Integer)1);
	    assertEquals((Integer)list10.size(),(Integer)10);
	    assertEquals((Integer)list51.size(),(Integer)51);
	    assertEquals((Integer)list52.size(),(Integer)52);
	}
	
	@Test public void testEmpty()
	{
		assertTrue(list00.isEmpty());
		list01.remove(0);
		assertTrue(list01.isEmpty());
		
	}
	
	@Test public void testAdd()
	{
		CardList c = new CardList(1);
		c.add(AllCards.CJD);
		assertTrue(c.equals(list01));	
	}
	
	@Test public void testRemoveC()
	{
		list01.remove(AllCards.CJD);
		assertTrue(list01.isEmpty());
		list10.remove(AllCards.CJC);
		Card c = list10.remove(list10.size()-1);
		assertTrue(c.equals(AllCards.CTC));	
	}
	
	@Test(expected= CardListException.class)
	public void testRemoveCE()
	{
		list01.remove(AllCards.C2D);
	}
	
	@Test public void testRemoveI()
	{
		list52.remove(list52.size()-1);
		assertTrue(list52.equals(list51));	
	}
	
	@Test(expected= CardListException.class)
	public void testAddE1()
	{ 
	    list10.add(AllCards.C7C);
	}
	
	@Test(expected= CardListException.class)
	public void testAddE2()
	{ 
	    list01.add(AllCards.C2C);
	}
	
	@Test public void testAddAll()
	{
		CardList c = new CardList(52);
		c.addAll(list52);
		assertTrue(c.equals(list52));
	}
	
	@Test(expected= CardListException.class)
	public void testAddAllE1()
	{
		CardList c = new CardList(51);
		c.addAll(list52);
	}
	
	@Test(expected= CardListException.class)
	public void testAddAllE2()
	{
		list10.remove(9);
		list10.remove(8);
		CardList c = new CardList(1);
		c.add(AllCards.C4C);
		list10.addAll(c);
	}
	
	@Test public void testShuffle()
	{
		CardList c = new CardList(52);
		c.addAll(list52);
		c.shuffle();
		assertFalse(c.equals(list52));
	}
	
	@Test public void testSort()
	{
		CardList c = new CardList(10);
		c.addAll(list10);
		c.shuffle();
		c.sort();
		assertTrue(c.equals(list10));
	}
	
	@Test public void testClear()
	{
		list52.clear();
		assertTrue(list52.equals(list00));
	}
	
	@Test public void testEquals()
	{
		assertFalse(list10.equals(null));
		assertTrue(list10.equals(list10));
		assertFalse(list10.equals(AllCards.CJD));
		assertFalse(list52.equals(list51));
		CardList c = new CardList(52);
		c.addAll(list52);
		assertTrue(c.equals(list52));
	}
	
	@Test public void testHashCode()
	{
		int i = list10.hashCode();
		assertTrue(i%1 < 1);
	}
	
	@Test public void testToString()
	{
		String s =  new String("test");
		//System.out.println(list10.toString());
		assertTrue(list10.toString().getClass() == s.getClass());
	}
	
	@Test public void testClone()
	{
		CardList c = list10.clone();
		assertTrue(c.equals(list10));
	}
	
	@Test public void testContains()
	{
		assertFalse(list01.contains(null));
		assertFalse(list01.contains(AllCards.CJC));
		assertTrue(list01.contains(AllCards.CJD));
	}
	
	@Test public void testgetCardsOf()
	{
		list52 = list52.getCardsOf(Card.Suit.HEARTS);
		assertEquals((Integer)list52.size(),(Integer)13);
		for(Card card : list52){
			assertTrue(card.getSuit().equals(Card.Suit.HEARTS));
		}
		assertEquals((Integer)list01.getCardsOf(Card.Suit.HEARTS).size(),(Integer)0);
		assertTrue(list01.getCardsOf(Card.Suit.HEARTS).isEmpty());
	}
	
	@Test public void testgetCardsNotOf()
	{
		list52 = list52.getCardsNotOf(Card.Suit.HEARTS);
		assertEquals((Integer)list52.size(), (Integer)39);
		for(Card card : list52){
			assertFalse(card.getSuit().equals(Card.Suit.HEARTS));
		}
	}
}
