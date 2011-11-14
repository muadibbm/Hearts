package hearts.view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import hearts.game.GameEngine;
import hearts.util.AllCards;
import hearts.util.Card;
import hearts.util.Card.Suit;
import hearts.util.CardImages;
import hearts.util.CardList;
import hearts.util.CardListException;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class CardView extends JLabel implements MouseListener
{
	private Card fCard;
	boolean fIsHuman = false;
	static boolean fUnlocked = true;
	static boolean fHeartsPlayed = false;
	static boolean fHasCardsOfLeadingSuit = false;
	static boolean fHumanTurn = false;
	private boolean fPassSelected;
	private boolean fMouseEntered;
	static Suit fSuitLed;
	static CardList fCardsPassedList; //cleared in handview at each EndRoundEvent
	private String fName;
	
	/**
	 * Constructor.
	 * @param name of player
	 */
	public CardView()
	{
		super();
		addMouseListener(this);
		fCardsPassedList = new CardList(4);
		fPassSelected = false;
		fMouseEntered = false;
		//this.fName = name;
	}
	
	public void showCardHorizantal(boolean show)
	{
		fIsHuman = show;
		if(!show)
		{
			super.setIcon(CardImages.getBackHorizontal());	
		}
	}
	
	public void showCardVertical(boolean show)
	{
		fIsHuman = show;
		if(!show)
		{
			super.setIcon(CardImages.getBackVertical());	
		}
	}
	
	/**
	 * Setters
	 */
	public void setCard(Card card)
	{
		super.setIcon(CardImages.getCard(card));
		this.fCard = card;
		repaint();
	}
	
	public void setArrow(GameEngine.PassingDirection passingDirection)
	{
		switch(passingDirection)
		{
		case LEFT :
			super.setIcon(CardImages.arrowLeft());
			break;
		case RIGHT :
			super.setIcon(CardImages.arrowRight());
			break;
		case ACROSS :
			super.setIcon(CardImages.arrowCross());
			break;
		default :
			break;
		}
	}
	
	public void setTurnImage()
	{
		super.setIcon(CardImages.turnImage()); 
	}
	
	public Suit getSuit()
	{
		return fCard.getSuit();
	}
	
	/**
	 * Getters
	 */
	public String getCardName()
	{
		return fCard.toString();
	}
	
	public void setName(String name)
	{
		fName = name;
	}
	public String getPlayer()
	{
		return fName;
	}
	
	protected void setPassed(boolean setting)
	{
		fPassSelected = setting;
	}
	
	public Card getCard()
	{
		return fCard;
	}

	@Override
	public void repaint()
	{
		super.repaint();
		fMouseEntered = false;
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
	
	}
	
	/*
	 * mousePressed is the standard choice.
	 * Much more responsive than mouseClicked.
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		System.out.println("cardview thinks name is: "+ fName);
		if(ViewTimer.isCardsPassed()) // Cards are passed : play state
		{
			if(fIsHuman && fHumanTurn)
			{
				/**TODO
				 * this is done for now but I want to clean it up later - Shams
				 */
				/*System.out.println("Hearts played: "+fHeartsPlayed);
				System.out.println("Leading suit: "+ fSuitLed);
				System.out.println("Unlocked: "+ fUnlocked);*/
				if (fUnlocked)
				{
					if(!fCard.getSuit().equals(Card.Suit.HEARTS) || fHeartsPlayed)
					{
						MainGUI.gGE.play(fName,fCard);
						//MainGUI.plays();
						repaint();
						/*System.out.println("Hearts played2: "+ fHeartsPlayed);*/
					}
				}
				else
				{
					/*System.out.println("Has suit: " + fHasCardsOfLeadingSuit);*/
					if (fCard.getSuit().equals(fSuitLed))
					{
						MainGUI.gGE.play(fName,fCard);
						//MainGUI.plays();
						repaint();
					}
					else if (!fHasCardsOfLeadingSuit)
					{
						MainGUI.gGE.play(fName,fCard);
						//MainGUI.plays();
						repaint();
					}
				}
			}	
		}
		else // cards are being passed : passing state
		{
			if (!fPassSelected && fCardsPassedList.size() < 3) {
				fPassSelected = true;
				/*setLocation(getX(), getY()-5);
				repaint();*/
				try
				{
					fCardsPassedList.add(fCard);
				}
				catch (CardListException arg0)
				{

				}
			}
			else
			{
				if (fPassSelected && fCardsPassedList.size() > 0)
				{
					fPassSelected = false;
					/*setLocation(getX(), getY()+5);
					repaint();*/
					/* FIXME : results in runTimer error */
					fCardsPassedList.remove(fCard);
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
		if(fIsHuman && !fPassSelected && !fMouseEntered)
		{
			setLocation(getX(), getY()-5);
			repaint();
		}
		fMouseEntered = true;
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
		if(fIsHuman && !fPassSelected && fMouseEntered)
		{
			setLocation(getX(), getY()+5);
			repaint();
		}
		fMouseEntered = false;
	}
}
