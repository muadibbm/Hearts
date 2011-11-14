package hearts.game;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;

import org.junit.Test;
import hearts.game.GameEngine;
//import hearts.game.PlayerManager;
//import hearts.game.GameEngine.PassingDirection;
import hearts.robots.*;
import hearts.util.AllCards;


public class TestGameEngine
{
	GameEngine test;
	
	@org.junit.Before public void setUp() 
	{ 
        test = GameEngine.getInstance();
    }
	
	@Test
	public void testAddPlayer(String name)  //HOW TO CHECK??
	{
		//testAddPlayer(String name)
		test.addPlayer("me");
		
		//testAddPlayer(String name, IRobot robotType)
		test.addPlayer("me", new HardSmartRobot());
		test.addPlayer("me", new RandomRobot());
		test.addPlayer("me", new PredictableRobot());
	}
	
	@Test
	/* 
	 * @precondition Players have been added already.
	 */
	public void testNewGame() //HOW TO CHECK??
	{
		test.addPlayer("me", new RandomRobot());
		test.addPlayer("you", new RandomRobot());
		test.addPlayer("her", new RandomRobot());
		test.addPlayer("him", new RandomRobot());
		test.initGame();
	}
	
	@Test
	public void testDealCards()  //CAN'T, private method
	{
	}
	
	@Test
	public void testNewTrick() //CAN'T, private method
	{
		
	}
	
	@Test
	public void testEndTrick() //CAN'T, private method
	{
		
	}
	
	@Test
	public void testRotatePassingDirections() //CAN'T, private method
	{
		
	}
	
	@Test
	public void testGetPassingDirection() //CAN'T, private method
	{

	}
	
	@Test
	public void testRotateToNextPlayer() //CAN'T, private method
	{
		
	}
	
	@Test
	public void testTrickIsOVer() //CAN'T, private method
	{
		
	}
	
	@Test
	public void testRoundIsOver() //CAN'T, private method
	{
		
	}
	
	@Test
	public void testGameIsOver()  //CAN'T, private method
	{
		
	}

	@Test
	public void testPlaySetup()
	{
		
	}
	
	@Test
	public void testPassCards()
	{
		
	}
	
	@Test
	public void testPlay()
	{
		test.addPlayer("me", new RandomRobot());
		test.addPlayer("you", new RandomRobot());
		test.addPlayer("her", new RandomRobot());
		test.addPlayer("him", new RandomRobot());
		
		//Test play()
		test.play();
		
		//Test play(String name, Card card)
		test.play("me", AllCards.CAC);
	}
	
}

