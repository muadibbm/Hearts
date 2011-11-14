package dummies;

import hearts.game.GameEngine;
import hearts.game.GameException;
import hearts.robots.EasySmartRobot;
import hearts.robots.HardSmartRobot;
import hearts.robots.MediumSmartRobot;
import hearts.robots.RandomRobot;
import hearts.robots.PredictableRobot;
import hearts.util.Constants;


/**
 * Runs automated games.
 */
class Driver
{
//	/**
//	 * Main.
//	 * @param args
//	 */
//	public static void main(String [] args)
//	{
//		final int NB_OF_GAMES = 100;
//		
//		/* Bonus Problem D - Adjustable hardness */
//		final int DIFFICULTY_LEVEL = 1;
//		final int LEVEL3 = 100;
//		final int LEVEL2 = 65;
//		final int LEVEL1 = 32;
//		
//		GameEngine fGE = GameEngine.getInstance();
//		fGE.addPlayer("Random1", new RandomRobot());
//		fGE.addPlayer("Random2", new RandomRobot());
//		fGE.addPlayer("Predictable", new PredictableRobot());
//		/* -------------------------------------------------------
//		/* Easy won 6820
//		*  Medium won 7284
//		*  Smart won 8258 
//		*  ------------------------------------------------------- */
//		String smartType;
//		if(DIFFICULTY_LEVEL <= LEVEL1)
//		{
//			smartType = "EasySmart";
//			fGE.addPlayer(smartType, new EasySmartRobot());
//		}
//		else if(LEVEL1 < DIFFICULTY_LEVEL && DIFFICULTY_LEVEL <= LEVEL2)
//		{
//			smartType = "MediumSmart";
//			fGE.addPlayer(smartType, new MediumSmartRobot());
//		}
//		else if(LEVEL2 < DIFFICULTY_LEVEL && DIFFICULTY_LEVEL <= LEVEL3)
//		{
//			smartType = "HardSmart";
//			fGE.addPlayer(smartType, new HardSmartRobot());
//		} 
//		
//		int [] gameStatistics = new int [Constants.NUMBER_OF_PLAYERS];
//		final int three = 3;
//		
//		for(int i = 0; i < NB_OF_GAMES; i++)
//		{
//			fGE.initGame();
//			
//			boolean gameOver = false;
//			while(!gameOver)
//			{
//				try
//				{
//					fGE.play();
//				}
//				catch(GameException ge)
//				{
//					gameOver = true;
//				}
//			}
//			gameStatistics[0] = fGE.getStatistic("Random1");
//			gameStatistics[1] = fGE.getStatistic("Random2");
//			gameStatistics[2] = fGE.getStatistic("Predictable");
//			gameStatistics[three] = fGE.getStatistic(smartType);
//			System.out.println("-------------- "+(i+1)+" game(s)"+" --------------\n");
//		}
//		System.out.println("GAME Statistics : ");
//		System.out.println("Random1 won " + gameStatistics[0] + " games.");
//		System.out.println("Random2 won " + gameStatistics[1] + " games.");
//		System.out.println("Predictable won " + gameStatistics[2] + " games.");
//		System.out.println(smartType + " won " + gameStatistics[three] + " games.");
//	}
}
