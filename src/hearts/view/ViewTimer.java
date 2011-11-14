package hearts.view;

import hearts.game.GameEngine.PassingDirection;
import hearts.gameEvent.EndPassingEvent;
import hearts.gameEvent.EndRoundEvent;
import hearts.gameEvent.EndTrickEvent;
import hearts.gameEvent.IEvent;
import hearts.gameEvent.NewGameEvent;
import hearts.gameEvent.NewRoundEvent;
import hearts.gameEvent.NewTrickEvent;
import hearts.gameEvent.PlayEvent;
import hearts.listeners.IGameListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * Timer class.
 */
public class ViewTimer implements ActionListener, IGameListener
{
	private static boolean gCardsPassed;
	private String fHumanName;
	private Timer fTimer;
	/**
	 * Construct a timer object to manage robot actions.
	 * @param time Interval for the timer.
	 * @param humanName Name of the human player.
	 */
	public ViewTimer(int time, String humanName)
	{
		MainGUI.gGE.addGameListener(this);
		fTimer = new Timer(time, this);
		fHumanName = humanName;
		gCardsPassed = false;
	}
	
//	/**
//	 * Timer starts (again).
//	 */
//	public void start()
//	{
//		fTimer.start();
//	}
//	
//	/**
//	 * Timer stop.
//	 */
//	public void stop()
//	{
//		fTimer.stop();
//	}
	
	/**
	 * Set the interval between each timer event.
	 * @param delay Milliseconds.
	 */
	public void setDelay(int delay)
	{
		fTimer.setInitialDelay(delay);
		fTimer.setDelay(delay);
	}
	
	/**
	 * Set to true if humans has passed its cards.
	 * @param cardsPassed true
	 */
	public static void cardsPassed(boolean cardsPassed)
	{
		gCardsPassed = cardsPassed;
	}
	
	/**
	 * If the cards have been passed.
	 * @return true if cards have been passed.
	 */
	public static boolean isCardsPassed()
	{
		return gCardsPassed;
	}

	
	@Override
	public void actionPerformed(ActionEvent e)
	{
//		System.out.println("timer!");
		MainGUI.gGE.play();
	}

	@Override
	public void update(IEvent event)
	{
		if (event instanceof NewGameEvent)
		{
			fTimer.stop();
			fHumanName = MainGUI.getName(0);
			gCardsPassed = false;
			
		}
		
		else if(event instanceof PlayEvent)
		{
			System.out.println("name: " + ((PlayEvent) event).getName());
			if(((PlayEvent) event).isHuman() ||
					((PlayEvent) event).getName().equals(MainGUI.getName(0)))
			{
				// Starts right after the human play.
				Board.gHand1.setTurnImage(false);
				Board.gHand2.setTurnImage(true);
				Board.gHand3.setTurnImage(false);
				Board.gHand4.setTurnImage(false);
				CardView.fHumanTurn = false;
				fTimer.start();
				
			}
			else if (((PlayEvent) event).getName().equals(MainGUI.getName(1)))
			{
				Board.gHand1.setTurnImage(false);
				Board.gHand2.setTurnImage(false);
				Board.gHand3.setTurnImage(true);
				Board.gHand4.setTurnImage(false);
				CardView.fHumanTurn = false;
			}
			else if (((PlayEvent) event).getName().equals(MainGUI.getName(2)))
			{
				Board.gHand1.setTurnImage(false);
				Board.gHand2.setTurnImage(false);
				Board.gHand3.setTurnImage(false);
				Board.gHand4.setTurnImage(true);
				CardView.fHumanTurn = false;
			}
			else if (((PlayEvent) event).getName().equals(MainGUI.getName(3)))
			{
				// Stop right before the human play.
				Board.gHand1.setTurnImage(true);
				Board.gHand2.setTurnImage(false);
				Board.gHand3.setTurnImage(false);
				Board.gHand4.setTurnImage(false);
				
				CardView.fHumanTurn = true;
				fTimer.stop();
			}
		}
		else if (event instanceof NewTrickEvent)
		{
			if(gCardsPassed)
			{
				/* If player4 ended the last trick (timer was stopped),
				 * then start timer for new trick
				 * except if the human is leading.
				 */
				if(!((NewTrickEvent) event).getCurrentPlayer().equals(MainGUI.getName(0)))
				{
					if(!fTimer.isRunning())
					{
						Board.gHand1.setTurnImage(false);
//						System.out.println("p2");
						CardView.fHumanTurn = false;
						
						/**
						 * Check for current player to set Turn image on
						 */
						String currentPlayer = ((NewTrickEvent) event).getCurrentPlayer();
						System.out.println("Current player: " + currentPlayer);
						if (currentPlayer.equals(MainGUI.getName(1)))
						{
							Board.gHand2.setTurnImage(true);	
						}
						else if (currentPlayer.equals(MainGUI.getName(2)))
						{
							Board.gHand3.setTurnImage(true);	
						}
						else
						{
							Board.gHand4.setTurnImage(true);	
						}
			
						fTimer.start();
						//CardView.fHumanTurn = false;
					}
				}
				else
				{
					//Human
					Board.gHand1.setTurnImage(true);
					Board.gHand2.setTurnImage(false);
					Board.gHand3.setTurnImage(false);
					Board.gHand4.setTurnImage(false);
					fTimer.stop();
					CardView.fHumanTurn = true;
				}
			}
		}
		else if(event instanceof NewRoundEvent)
		{
			CardView.fHumanTurn = false;
			if(((NewRoundEvent) event).getPassingDirection().equals(PassingDirection.NONE))
			{
//				System.out.println("p3");
				fTimer.start();
				gCardsPassed = true;
			}
			else
			{
				System.out.println("I sense a disturbance in the force: " + fHumanName);
				MainGUI.gGE.passCardsRobot(fHumanName);
			}
		}
		else if (event instanceof EndTrickEvent)
		{
			Board.gHand1.setTurnImage(false);
			Board.gHand2.setTurnImage(false);
			Board.gHand3.setTurnImage(false);
			Board.gHand4.setTurnImage(false);
		}
		else if(event instanceof EndPassingEvent)
		{
			fTimer.start();
			gCardsPassed = true;
		}
		else if(event instanceof EndRoundEvent)
		{
			fTimer.stop();
			gCardsPassed = false;
		}
		else if (event instanceof NewGameEvent)
		{
			fTimer.stop();
			gCardsPassed = false;
			//CardView.fHumanTurn = true;
		}
	}

}
