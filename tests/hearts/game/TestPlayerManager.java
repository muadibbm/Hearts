package hearts.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotSame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import hearts.model.Game;
import hearts.model.ImmutableGameWrapper;
import hearts.model.Player;
import hearts.model.ModelException;
import hearts.game.GameEngine.PassingDirection;
import hearts.robots.IRobot;
import hearts.robots.PredictableRobot;
import hearts.robots.RandomRobot;
import hearts.robots.HardSmartRobot;

import hearts.util.AllCards;
import hearts.util.Card;
import hearts.util.CardList;
import hearts.util.Constants;


import org.junit.Test;

public class TestPlayerManager
{
	PlayerManager testThis;
	
	@org.junit.Before public void setUp() { 
        testThis = new PlayerManager();
    }
	
	@Test
	public void testReset()
	{
		testThis.addPlayer("Player");
		Iterator<String> names = testThis.iterator();
		
		assertTrue(names.hasNext());
		testThis.reset();
		
		names = testThis.iterator();
		assertFalse(names.hasNext());
	}
	
	@Test
	public void testAddPlayer()
	{
		Iterator<String> names = testThis.iterator();
		assertFalse(names.hasNext());
		testThis.addPlayer("Player");
		names = testThis.iterator();
		assertTrue(names.hasNext());
		assertTrue(names.next().equals("Player"));
	}
	
	@Test (expected = GameException.class)
	public void testAddPlayerException()
	{
		Iterator<String> names = testThis.iterator();
		assertFalse(names.hasNext());
		
		testThis.addPlayer("Player1");
		testThis.addPlayer("Player2");
		testThis.addPlayer("Player3");
		testThis.addPlayer("Player4");
		testThis.addPlayer("5sACroud!");
	}
	
	@Test
	public void testSetAssociation()
	{
		ArrayList<String> playerList = new ArrayList<String>();
		
		Iterator<String> names = testThis.iterator();
		assertFalse(names.hasNext());
		
		testThis.addPlayer("Player1");
		testThis.addPlayer("Player2");
		testThis.addPlayer("Player3");
		testThis.addPlayer("Player4");
		names = testThis.iterator();
		
		while (names.hasNext())
		{
			playerList.add(names.next());
		}
		
		assertEquals(playerList.get(0), "Player1");
		testThis.setAssociation(GameEngine.PassingDirection.LEFT, playerList);
		
		Map<String, Player> associates = testThis.getAssociates();
	    Iterator<Entry<String, Player>> it = associates.entrySet().iterator();

	    Entry<String, Player> pair = (Entry<String, Player>) it.next();
        String lplayerName = (String) pair.getKey();
        String lassociateName = (String) pair.getValue().toString();
	    assertEquals(lplayerName,"Player4");
	    assertEquals(lassociateName,"Player3");
	    
	    pair = (Entry<String, Player>) it.next();
        String lplayer2Name = (String) pair.getKey();
        String lassociate2Name = (String) pair.getValue().toString();
	    assertEquals(lplayer2Name,"Player3");
	    assertEquals(lassociate2Name,"Player2");
	    
	    pair = (Entry<String, Player>) it.next();
        String lplayer3Name = (String) pair.getKey();
        String lassociate3Name = (String) pair.getValue().toString();
	    assertEquals(lplayer3Name,"Player2");
	    assertEquals(lassociate3Name,"Player1");
	    
	    pair = (Entry<String, Player>) it.next();
        String lplayer4Name = (String) pair.getKey();
        String lassociate4Name = (String) pair.getValue().toString();
	    assertEquals(lplayer4Name,"Player1");
	    assertEquals(lassociate4Name,"Player4");
	    
	    testThis.setAssociation(GameEngine.PassingDirection.RIGHT, playerList);
	    associates = testThis.getAssociates();
	    it = associates.entrySet().iterator();
	    
	    pair = (Entry<String, Player>) it.next();
        String rplayerName = (String) pair.getKey();
        String rassociateName = (String) pair.getValue().toString();
	    assertEquals(rplayerName,"Player4");
	    assertEquals(rassociateName,"Player1");
	    
	    pair = (Entry<String, Player>) it.next();
        String rplayer2Name = (String) pair.getKey();
        String rassociate2Name = (String) pair.getValue().toString();
	    assertEquals(rplayer2Name,"Player3");
	    assertEquals(rassociate2Name,"Player4");
	    
	    pair = (Entry<String, Player>) it.next();
        String rplayer3Name = (String) pair.getKey();
        String rassociate3Name = (String) pair.getValue().toString();
	    assertEquals(rplayer3Name,"Player2");
	    assertEquals(rassociate3Name,"Player3");
	    
	    pair = (Entry<String, Player>) it.next();
        String rplayer4Name = (String) pair.getKey();
        String rassociate4Name = (String) pair.getValue().toString();
	    assertEquals(rplayer4Name,"Player1");
	    assertEquals(rassociate4Name,"Player2");
	    
	    testThis.setAssociation(GameEngine.PassingDirection.ACROSS, playerList);
	    associates = testThis.getAssociates();
	    it = associates.entrySet().iterator();
	    
	    pair = (Entry<String, Player>) it.next();
        String aplayerName = (String) pair.getKey();
        String aassociateName = (String) pair.getValue().toString();
	    assertEquals(aplayerName,"Player4");
	    assertEquals(aassociateName,"Player2");
	    
	    pair = (Entry<String, Player>) it.next();
        String aplayer2Name = (String) pair.getKey();
        String aassociate2Name = (String) pair.getValue().toString();
	    assertEquals(aplayer2Name,"Player3");
	    assertEquals(aassociate2Name,"Player1");
	    
	    pair = (Entry<String, Player>) it.next();
        String aplayer3Name = (String) pair.getKey();
        String aassociate3Name = (String) pair.getValue().toString();
	    assertEquals(aplayer3Name,"Player2");
	    assertEquals(aassociate3Name,"Player4");
	    
	    pair = (Entry<String, Player>) it.next();
        String aplayer4Name = (String) pair.getKey();
        String aassociate4Name = (String) pair.getValue().toString();
	    assertEquals(aplayer4Name,"Player1");
	    assertEquals(aassociate4Name,"Player3");
	    
		/**TODO: Still have to study your 
		 * code better to know how to test it. 
		 * Ill get back to it!
		 */
	}
	
	@Test
	public void testSetHand()
	{
		Map<String, Player> players = new HashMap<String, Player>();
		String name = "Player1";
		CardList hand = new CardList(10);
		hand.add(AllCards.C2C);
		hand.add(AllCards.C5C);
		
		testThis.addPlayer(name);
		testThis.setHand(name, hand);
		
		players = testThis.getPlayers();
		assertEquals(players.get(name).getHand(), hand);
		assertNotSame(players.get(name).getHand(), hand);
	}
	
	@Test
	public void testClearHands()
	{
		Map<String, Player> players = new HashMap<String, Player>();
		String name = "Player1";
		CardList hand = new CardList(10);
		hand.add(AllCards.C2C);
		
		testThis.addPlayer(name);
		testThis.setHand(name, hand);
		
		testThis.clearHands();
		players = testThis.getPlayers();
		assertTrue(players.get(name).getHand().isEmpty());
	}
	
	@Test
	public void testSetScore()
	{
		Map<String, Player> players = new HashMap<String, Player>();
		String name = "Player1";
		CardList hand = new CardList(4);
		hand.add(AllCards.C2C);
		hand.add(AllCards.C2H);
		hand.add(AllCards.C3H);
		hand.add(AllCards.C4H);
		
		testThis.addPlayer(name);
		testThis.setScore(name, hand);
		
		players = testThis.getPlayers();
		assertEquals(players.get(name).getScore(), 3);
		
		CardList hand2 = new CardList(4);
		hand2.add(AllCards.C3D);
		hand2.add(AllCards.C8H);
		hand2.add(AllCards.C9H);
		hand2.add(AllCards.CQS);
		
		testThis.setScore(name, hand2);
		
		players = testThis.getPlayers();
		assertEquals(players.get(name).getScore(), 18);
		
	}
	
	@Test
	public void testGetScore()
	{
		String name = "Player1";
		CardList hand = new CardList(4);
		hand.add(AllCards.C2C);
		hand.add(AllCards.C2H);
		hand.add(AllCards.C3H);
		hand.add(AllCards.C4H);
		
		testThis.addPlayer(name);
		testThis.setScore(name, hand);
	
		assertEquals(testThis.getScore(name), 3);
		
		CardList hand2 = new CardList(4);
		hand2.add(AllCards.C3D);
		hand2.add(AllCards.C8H);
		hand2.add(AllCards.C9H);
		hand2.add(AllCards.CQS);
		
		testThis.setScore(name, hand2);
		assertEquals(testThis.getScore(name), 18);
		
	}
	
	@Test
	public void testPassCards()
	{
		/**
		 * TODO: need to do proper checks. i.e. what if the name is not actual in the game, also if the player doesn't have those cards etc
		 */
		Map<String, Player> players = new HashMap<String, Player>();
		testThis.addPlayer("Player1");
		testThis.addPlayer("Player2");
		
		ArrayList<String> playerList = new ArrayList<String>();
		
		Iterator<String> names = testThis.iterator();
		names = testThis.iterator();
		
		while (names.hasNext())
		{
			playerList.add(names.next());
		}
		
		testThis.setAssociation(GameEngine.PassingDirection.LEFT, playerList);
		
		CardList hand = new CardList(Constants.NUMBER_OF_PLAYERS);
		CardList cards = new CardList(10);
		hand.add(AllCards.C2C);
		hand.add(AllCards.C2H);
		hand.add(AllCards.C2D);
		hand.add(AllCards.C2S);
		testThis.setHand("Player1", hand);
		hand.clear();
		
		hand.add(AllCards.C3C);
		hand.add(AllCards.C3H);
		hand.add(AllCards.C3D);
		hand.add(AllCards.C3S);
		testThis.setHand("Player2", hand);
		hand.clear();
		
		hand.add(AllCards.C2C);
		hand.add(AllCards.C2H);
		hand.add(AllCards.C2D);
		testThis.passCards("Player1", hand);
		hand.clear();
		
		cards.add(AllCards.C3C);
		cards.add(AllCards.C3H);
		cards.add(AllCards.C3D);
		cards.add(AllCards.C3S);
		cards.add(AllCards.C2C);

		cards.add(AllCards.C2H);
		cards.add(AllCards.C2D);
		
		players = testThis.getPlayers();
		assertEquals(players.get("Player2").getHand(), cards);
		cards.clear();
		
		cards.add(AllCards.C2S);
		assertEquals(players.get("Player1").getHand(), cards);
		cards.clear();
		
		hand.add(AllCards.C3C);
		hand.add(AllCards.C3H);
		hand.add(AllCards.C3D);
		
		testThis.passCards("Player2", hand);
		
		
		cards.add(AllCards.C3S);
		cards.add(AllCards.C2C);

		cards.add(AllCards.C2H);
		cards.add(AllCards.C2D);
		
		players = testThis.getPlayers();
		assertEquals(players.get("Player2").getHand(), cards);
		cards.clear();
		
		cards.add(AllCards.C2S);
		cards.add(AllCards.C3C);
		cards.add(AllCards.C3H);
		cards.add(AllCards.C3D);
		
		assertEquals(players.get("Player1").getHand(), cards);
		cards.clear();
	}
	
	@Test
	public void testPassCardsRobot()
	{
		Map<String, Player> players = new HashMap<String, Player>();
		
		CardList hand = new CardList(Constants.NUMBER_OF_PLAYERS);
		hand.add(AllCards.C2C);
		hand.add(AllCards.C2H);
		hand.add(AllCards.C2D);
		
		IRobot robot = new PredictableRobot();
		
		testThis.addPlayer("Player1");
		testThis.addPlayer("Player2");
		ArrayList<String> playerList = new ArrayList<String>();
		Iterator<String> names = testThis.iterator();
		names = testThis.iterator();
		while (names.hasNext())
		{
			playerList.add(names.next());
		}
		
		testThis.setAssociation(GameEngine.PassingDirection.LEFT, playerList);
		testThis.setHand("Player1", hand);
		testThis.passCards("Player1", robot);
	
		players = testThis.getPlayers();
		
		CardList compare1 = new CardList(Constants.NUMBER_OF_PLAYERS);
		compare1 = players.get("Player2").getHand();
		compare1.sort();
		hand.sort();
		assertEquals(compare1, hand);
		hand.clear();
		assertEquals(players.get("Player1").getHand(), hand);
	}
	
	@Test
	public void testFirstPlayer()
	{
		CardList hand = new CardList(13);
		testThis.addPlayer("Player1");
		testThis.addPlayer("Player2");
		testThis.addPlayer("Player3");
		testThis.addPlayer("Player4");
		
		hand.add(AllCards.C2C);
		hand.add(AllCards.C2D);
		hand.add(AllCards.C2S);
		hand.add(AllCards.C2H);
		hand.add(AllCards.C3C);
		testThis.setHand("Player1", hand);
		hand.clear();
		
		hand.add(AllCards.C4C);
		hand.add(AllCards.C4D);
		testThis.setHand("Player2", hand);
		hand.clear();
		
		hand.add(AllCards.C4S);
		hand.add(AllCards.C4H);
		testThis.setHand("Player3", hand);
		hand.clear();
		
		hand.add(AllCards.C5S);
		hand.add(AllCards.C5H);
		testThis.setHand("Player4", hand);
		hand.clear();
		String first = testThis.firstPlayer();
		assertEquals(first, "Player1");
		
		testThis.clearHands();
		
		hand.add(AllCards.C2C);
		hand.add(AllCards.C2D);
		hand.add(AllCards.C2S);
		hand.add(AllCards.C2H);
		hand.add(AllCards.C3C);
		testThis.setHand("Player4", hand);
		hand.clear();
		
		String first2 = testThis.firstPlayer();
		assertEquals(first2, "Player4");
	}
	
	@Test
	public void testPlay()
	{
		/**
		 * TODO: again check if player exists and if he has the cards he wishes to play
		 */
		Map<String, Player> players = new HashMap<String, Player>();
		CardList hand = new CardList(4);
		hand.add(AllCards.C2C);
		hand.add(AllCards.C2H);
		hand.add(AllCards.C3H);
		hand.add(AllCards.C4H);
		
		testThis.addPlayer("Player1");
		testThis.setHand("Player1", hand);
		
		players = testThis.getPlayers();
		assertEquals(players.get("Player1").getHand(), hand);
		
		testThis.play("Player1", AllCards.C2C);
		hand.remove(AllCards.C2C);
		assertEquals(players.get("Player1").getHand(), hand);
	}
	
	@Test
	public void testPlayRobot()
	{
		Game game = new Game();
		game.newTrick();
		ImmutableGameWrapper iGame = new ImmutableGameWrapper(game);
		IRobot robot = new PredictableRobot();
		Map<String, Player> players = new HashMap<String, Player>();
		CardList hand = new CardList(4);
		hand.add(AllCards.C2C);
		hand.add(AllCards.C2H);
		hand.add(AllCards.C3H);
		hand.add(AllCards.C4H);
		
		testThis.addPlayer("Player1");
		testThis.addPlayer("Player2");
		
		ArrayList<String> playerList = new ArrayList<String>();
		
		Iterator<String> names = testThis.iterator();
		names = testThis.iterator();
		
		while (names.hasNext())
		{
			playerList.add(names.next());
		}
		
		/**
		 * TODO: fix various check errors
		 */
		testThis.setHand("Player1", hand);
		testThis.setAssociation(GameEngine.PassingDirection.LEFT, playerList);
		players = testThis.getPlayers();
		assertEquals(players.get("Player1").getHand(), hand);
		
		testThis.play("Player1", robot, iGame);
		hand.remove(AllCards.C2C);
		assertEquals(players.get("Player1").getHand(), hand);
	}
}
