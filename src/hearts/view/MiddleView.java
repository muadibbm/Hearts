package hearts.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import hearts.game.GameEngine;
import hearts.gameEvent.EndPassingEvent;
import hearts.gameEvent.EndTrickEvent;
import hearts.gameEvent.IEvent;
import hearts.gameEvent.NewGameEvent;
import hearts.gameEvent.NewRoundEvent;
import hearts.gameEvent.PassEvent;
import hearts.gameEvent.PlayEvent;
import hearts.listeners.IGameListener;
import hearts.util.Card;
import hearts.util.Constants;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Trick cards, in the middle of the board.
 */
@SuppressWarnings("serial")
public class MiddleView extends JPanel implements IGameListener
{
	private static ArrayList<CardView> gCardViews = new ArrayList<CardView>(Constants.NUMBER_OF_PLAYERS);
	private static CardView gArrow;
	private static Timer gEndTrickTimer;
	/**
	 * Construct a panel for the cards played at each trick.
	 */
	MiddleView()
	{
			super();
			MainGUI.gGE.addGameListener(this);
			gArrow = new CardView();
			gArrow.setVisible(false);
			add(gArrow);
			gEndTrickTimer = new Timer(1500, new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e)
				{
					hideCards();
					gEndTrickTimer.stop();
				}
			});
			
			/* TODO : make the cards places fixed initialy */
			setUpView();
//			GridBagLayout layout = new GridBagLayout();
//			//setForeground(new Color(0f, 0f, 0f));
//			setLayout(layout);
//			GridBagConstraints c = new GridBagConstraints();
//			setOpaque(false);
//			int i = 0;
//			while(i < Constants.NUMBER_OF_PLAYERS)
//			{
//				CardView card = new CardView();
//				card.setName(MainGUI.getName(i));
//				card.showCardHorizantal(false);
//				card.setVisible(false);
//				gCardViews.add(card);
//				switch(i)
//				{
//					case 0:
//						c.anchor = GridBagConstraints.SOUTH;
//						c.gridx = 1;
//						c.gridy = 2;
//						c.insets = new Insets(-48, 2, 2, 2);
//						break;
//					case 1:
//						c.anchor = GridBagConstraints.WEST;
//						c.gridx = 0;
//						c.gridy = 1;
//						c.insets = new Insets(2, 2, 2, 2);
//						break;
//					case 2:
//						c.anchor = GridBagConstraints.NORTH;
//						c.gridx = 1;
//						c.gridy = 0;
//						c.insets = new Insets(2, 2, -48, 2);
//						break;
//
//					case 3:
//						c.anchor = GridBagConstraints.EAST;
//						c.gridx = 2;
//						c.gridy = 1;
//						c.insets = new Insets(2, 2, 2, 2);
//						break;
//				default:
//					break;
//				}
//				add(card, c);
//				i++;
//			}			
	}
	
	/**
	 * Set the delay for the hide card timer.
	 * @param delay Milliseconds.
	 */
	public static void setHideCardsDelay(int delay)
	{
		gEndTrickTimer.setInitialDelay(delay);
		gEndTrickTimer.setDelay(delay);
	}
	
	private void setUpView()
	{
		gCardViews.clear();

		GridBagLayout layout = new GridBagLayout();
		//setForeground(new Color(0f, 0f, 0f));
		setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		setOpaque(false);
		int i = 0;
		while(i < Constants.NUMBER_OF_PLAYERS)
		{
			CardView card = new CardView();
			card.setName(MainGUI.getName(i));
			card.showCardHorizantal(false);
			card.setVisible(false);
			gCardViews.add(card);
			switch(i)
			{
				case 0:
					c.anchor = GridBagConstraints.SOUTH;
					c.gridx = 1;
					c.gridy = 2;
					c.insets = new Insets(-48, 2, 2, 2);
					break;
				case 1:
					c.anchor = GridBagConstraints.WEST;
					c.gridx = 0;
					c.gridy = 1;
					c.insets = new Insets(2, 2, 2, 2);
					break;
				case 2:
					c.anchor = GridBagConstraints.NORTH;
					c.gridx = 1;
					c.gridy = 0;
					c.insets = new Insets(2, 2, -48, 2);
					break;

				case 3:
					c.anchor = GridBagConstraints.EAST;
					c.gridx = 2;
					c.gridy = 1;
					c.insets = new Insets(2, 2, 2, 2);
					break;
			default:
				break;
			}
			add(card, c);
			i++;
		}
	}
		
	private void setCard(Card card, String name)
	{
		for(CardView cv : gCardViews)
		{
			if(cv.getPlayer().equals(name))
			{
				cv.setCard(card);
				cv.setVisible(true);
				break;
			}
		}
	}
	/**
	 * Hide the cards in middle view.
	 * (Equivalent to reset)
	 */
	protected static void hideCards()
	{
		for(CardView cv : gCardViews)
		{
			cv.setVisible(false);
		}
	}
	
	/**
	 * wait for n time.
	 * @param n of type int
	 */
	public static void wait(int n)
	{
        long t0; 
        long t1;
        t0 = System.currentTimeMillis();
        do
        {
            t1 = System.currentTimeMillis();
        }
        while (t1-t0 < 1000);
	}

	@Override
	public void update(IEvent event)
	{
		if (event instanceof NewGameEvent)
		{
			if(gEndTrickTimer.isRunning())
			{
				gEndTrickTimer.stop();
				hideCards();
			}
			setUpView();
//			gCardViews.clear();
//
//			GridBagLayout layout = new GridBagLayout();
//			//setForeground(new Color(0f, 0f, 0f));
//			setLayout(layout);
//			GridBagConstraints c = new GridBagConstraints();
//			setOpaque(false);
//			int i = 0;
//			while(i < Constants.NUMBER_OF_PLAYERS)
//			{
//				CardView card = new CardView();
//				card.setName(MainGUI.getName(i));
//				card.showCardHorizantal(false);
//				card.setVisible(false);
//				gCardViews.add(card);
//				switch(i)
//				{
//					case 0:
//						c.anchor = GridBagConstraints.SOUTH;
//						c.gridx = 1;
//						c.gridy = 2;
//						c.insets = new Insets(-48, 2, 2, 2);
//						break;
//					case 1:
//						c.anchor = GridBagConstraints.WEST;
//						c.gridx = 0;
//						c.gridy = 1;
//						c.insets = new Insets(2, 2, 2, 2);
//						break;
//					case 2:
//						c.anchor = GridBagConstraints.NORTH;
//						c.gridx = 1;
//						c.gridy = 0;
//						c.insets = new Insets(2, 2, -48, 2);
//						break;
//
//					case 3:
//						c.anchor = GridBagConstraints.EAST;
//						c.gridx = 2;
//						c.gridy = 1;
//						c.insets = new Insets(2, 2, 2, 2);
//						break;
//				default:
//					break;
//				}
//				add(card, c);
//				i++;
//			}
		}
		else if(event instanceof NewRoundEvent)
		{
			if(gEndTrickTimer.isRunning())
			{
				gEndTrickTimer.stop();
				hideCards();
			}
			gArrow.setArrow(((NewRoundEvent) event).getPassingDirection());
			if(!((NewRoundEvent) event).getPassingDirection().equals(GameEngine.PassingDirection.NONE))
			{
				gArrow.setVisible(true);	
			}
			//wait(600);
		}
		else if(event instanceof EndPassingEvent)
		{
			 //FIXME Shouldn't we avoid using this?
			wait(500);
			gArrow.setVisible(false);
		}
		else if (event instanceof EndTrickEvent)
		{
			gEndTrickTimer.start();
		}
		else if (event instanceof PlayEvent)
		{
			if(gEndTrickTimer.isRunning())
			{
				gEndTrickTimer.stop();
				hideCards();
			}
			Card cardPlayed = ((PlayEvent) event).getCard();
			String name = ((PlayEvent) event).getName();
			setCard(cardPlayed, name);
		}
	}
}
