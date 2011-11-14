package hearts.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestDeck
{
	private Deck deck;
	private Deck deck2;
	
	@Test
	public void testShuffle()
	{
		boolean shuffled = false;
		deck = new Deck();
		deck2 = new Deck();
		deck.shuffle();
		for(int i = 0; i < deck.size(); i++)
			if(deck.draw() != deck2.draw())
			{
				shuffled = true;
			}
		assertTrue(shuffled);
	}
	
	@Test(expected=hearts.util.EmptyDeckException.class)
	public void testDrawException()
	{
		deck = new Deck();
		for(int i = 0; i < Constants.NUMBER_OF_CARDS; i++)
			deck.draw();
		deck.draw();
	}
	
	@Test
	public void testDraw()
	{
		deck = new Deck();
	    assertEquals(deck.draw(), hearts.util.AllCards.C2C);
	}
	
	@Test
	public void testSize()
	{
		deck = new Deck();
		assertEquals(deck.size(), 52);
		deck.shuffle();
		assertEquals(deck.size(), 52);
	}
	
	@Test
	public void testIsEmpty()
	{
		deck = new Deck();
		for(int i = 0; i < Constants.NUMBER_OF_CARDS; i++)
			deck.draw();
		assertTrue(deck.isEmpty());
	}
	
}
