package hearts.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import hearts.model.Trick;
import hearts.util.Card;
import hearts.util.CardList;

public class TestTrick
{
	private Trick trick;
	private CardList cardlist;
		
	@Test (expected = ModelException.class)
	public void TestAddPlay()
	{
		trick = new Trick();
		
		trick.addPlay("player", hearts.util.AllCards.CAC);
		trick.addPlay("player", hearts.util.AllCards.C2C);
		trick.addPlay("player", hearts.util.AllCards.C3C);
		trick.addPlay("player", hearts.util.AllCards.C4C);
		
		trick.addPlay("player", hearts.util.AllCards.C5C);
	}
	
	@Test
	public void testgetCards()
	{
		trick = new Trick();
		cardlist = new CardList(4);
		
		cardlist.add(hearts.util.AllCards.C5C);
		cardlist.add(hearts.util.AllCards.C9C);
		cardlist.add(hearts.util.AllCards.CQC);
		cardlist.add(hearts.util.AllCards.CAC);
		
		trick.addPlay("player", hearts.util.AllCards.C9C);
		trick.addPlay("player", hearts.util.AllCards.CAC);
		trick.addPlay("player", hearts.util.AllCards.C5C);
		trick.addPlay("player", hearts.util.AllCards.CQC);
		
		assertEquals(trick.getCards().size(), 4);
		for(int i = 0; i < trick.cardsPlayed(); i++)
		{
			assertEquals(trick.getCards().toString(), cardlist.toString());
		}
		
		cardlist.remove(hearts.util.AllCards.CQC);
		assertFalse((trick.getCards()).equals(cardlist));
		
		cardlist = null;
		assertFalse((trick.getCards()).equals(cardlist));
	}
	
	@Test 
	public void testCardsPlayed()
	{
		trick = new Trick();
		assertEquals(trick.cardsPlayed(), 0);
		
		trick.addPlay("player", hearts.util.AllCards.C5C);
		assertEquals(trick.cardsPlayed(), 1);
		
		trick.addPlay("player", hearts.util.AllCards.C6C);
		trick.addPlay("player", hearts.util.AllCards.C7C);
		trick.addPlay("player", hearts.util.AllCards.C5C);
		assertEquals(trick.cardsPlayed(), 4);		
	}
	
	@Test (expected = ModelException.class)
	public void testGetWinnerException()
	{
		trick = new Trick();
		trick.getWinner();		
	}
	
	@Test
	public void testGetWinner()
	{
		trick = new Trick();
		
		trick.addPlay("playerA", hearts.util.AllCards.CAC);
		trick.addPlay("playerB", hearts.util.AllCards.C2C);
		trick.addPlay("playerC", hearts.util.AllCards.C3C);
		trick.addPlay("playerD", hearts.util.AllCards.C4C);
		
		assertEquals(trick.getWinner(), "playerA");	
		
		trick = new Trick();
		trick.addPlay("playerA", hearts.util.AllCards.C2C);
		trick.addPlay("playerB", hearts.util.AllCards.C4C);
		trick.addPlay("playerC", hearts.util.AllCards.C3C);
		trick.addPlay("playerD", hearts.util.AllCards.CAC);
		
		assertEquals(trick.getWinner(), "playerD");	
		
	}
	
	@Test (expected = ModelException.class)
	public void testGetSuitLedException()
	{
		trick = new Trick();
		trick.getSuitLed();
	}
	
	@Test 
	public void testGetSuitLed()
	{
		trick = new Trick();
		
		trick.addPlay("playerA", hearts.util.AllCards.CAC);
		trick.addPlay("playerB", hearts.util.AllCards.C2C);
		trick.addPlay("playerC", hearts.util.AllCards.C3C);
		trick.addPlay("playerD", hearts.util.AllCards.C4C);
		
		assertEquals(trick.getSuitLed(), Card.Suit.CLUBS);
	}
	
	@Test 
	public void testGetTrick()
	{
		trick = new Trick();
		List<Play> plays = new ArrayList<Play>();
		
		plays.add(new Play("playerA", hearts.util.AllCards.CAC));
		plays.add(new Play("playerB", hearts.util.AllCards.C2C));
		plays.add(new Play("playerC", hearts.util.AllCards.C3C));
		plays.add(new Play("playerD", hearts.util.AllCards.C4C));
		
		trick.addPlay("playerA", hearts.util.AllCards.CAC);
		trick.addPlay("playerB", hearts.util.AllCards.C2C);
		trick.addPlay("playerC", hearts.util.AllCards.C3C);
		trick.addPlay("playerD", hearts.util.AllCards.C4C);
		
		for(int i = 0; i < trick.cardsPlayed(); i++)
		{
			assertEquals(trick.getTrick().get(i).getPlayer(),plays.get(i).getPlayer());
		}
	}
}
