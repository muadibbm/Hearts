package hearts.view;

import hearts.gameEvent.EndPassingEvent;
import hearts.gameEvent.EndRoundEvent;
import hearts.gameEvent.EndTrickEvent;
import hearts.gameEvent.IEvent;
import hearts.gameEvent.NewGameEvent;
import hearts.gameEvent.NewRoundEvent;
import hearts.gameEvent.NewTrickEvent;
import hearts.gameEvent.PassEvent;
import hearts.gameEvent.PlayEvent;
import hearts.listeners.IGameListener;
import hearts.util.Card;
import hearts.util.Card.Suit;
import hearts.util.CardList;
import hearts.util.Constants;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Player hand.
 */
@SuppressWarnings("serial")
public class HandView extends JPanel implements IGameListener
{
	private ArrayList<CardView> fCardViews = new ArrayList<CardView>(Constants.NUMBER_OF_TRICKS+3); // A buffer of 3 is need to pass cards around
	private ArrayList<Card> fCards = new ArrayList<Card>(Constants.NUMBER_OF_TRICKS+3);
	private boolean fIsVertical;
	private boolean fIsHuman;
	private CardList fHumanBuffer;
	private Suit fSuitLed;
	private String fName;
	private Integer fScore = 0;
	private JPanel fCardsPanel;
	private JPanel fInfoPanel;
	private CardView fTurnImage = new CardView();
	
	/**
	 * Construct a panel for the hand cards.
	 * @param name Name of the player
	 * @param human Is human?
	 * @param vertical Is vertical?
	 */
	public HandView(String name, boolean human, boolean vertical)
	{
		super();
		MainGUI.gGE.addGameListener(this);
		fName = name;
		fIsHuman = human;
		fIsVertical = vertical;
		setVisible(true);
		setOpaque(false);
		fCardsPanel = new JPanel();
		fInfoPanel = new JPanel();
		
		
		if (fIsVertical)
		{
			// FIXME messy with buffer of 3 extra cards
			setPreferredSize(new Dimension(108, 1));
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			setAlignmentX(Component.CENTER_ALIGNMENT);
			add(fInfoPanel);
			add(fCardsPanel);
			//FlowLayout flowlayout = new FlowLayout();
			GridLayout gridlayout = new GridLayout(16, 1);
			gridlayout.setVgap(ViewConstants.HANDVIEW_VERTICAL_GAP);
			fCardsPanel.setLayout(gridlayout);
			//fCardsPanel.setPreferredSize(new Dimension(130, 300));
			fCardsPanel.setOpaque(false);
			
		}
		else
		{
			setPreferredSize(new Dimension(1, 163));
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			setAlignmentY(Component.CENTER_ALIGNMENT);
			add(fInfoPanel);
			add(fCardsPanel);
			FlowLayout layout = new FlowLayout();
			layout.setHgap(ViewConstants.HANDVIEW_HORIZONTAL_GAP);
			fCardsPanel.setLayout(layout);
			fCardsPanel.setOpaque(false);
		}
		fCardsPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		JLabel score = new JLabel(); 
		fTurnImage.setTurnImage();
		fTurnImage.setVisible(false);
		score.setText("Score : "+fScore.toString());
		score.setFont(new Font("Serif", Font.BOLD, 15));
		fInfoPanel.setLayout(new FlowLayout());
		fInfoPanel.add(score);
		fInfoPanel.add(fTurnImage);
		fInfoPanel.setOpaque(false);
		fInfoPanel.getComponent(0).setForeground(ViewConstants.WHITE);
		newHand();
	}
	
	/**
	 * @param name is the name to set
	 */
	public void setName(String name)
	{
		fName = name;
	}
	
	public void setTurnImage(boolean bool)
	{
		fTurnImage.setVisible(bool);
	}
/*	
	public boolean containsSuit(Suit suit)
	{
		boolean result = false;
		if (fIsHuman)
		{
			for (CardView cardView : fCardViews)
			{
				if (cardView.getSuit().equals(suit))
				{
					result = true;
				}
			}
		}
		
		return result;
	}*/
	
	private void newHand()
	{
		int i = 0;
		while(i < Constants.NUMBER_OF_TRICKS)
		{
			CardView cv = new CardView();
			cv.setName(fName);
			if(fIsVertical)
			{
				cv.showCardVertical(false);
			}
			else
			{
				cv.showCardHorizantal(false);
			}
			fCardViews.add(cv);
			fCardsPanel.add(cv);
			i++;
		}
	}
	
	private void setHand(CardList pCards)
	{
		int i = 0;
		for(Card card : pCards)
		{
			fCardViews.get(i).setVisible(true);
			fCardViews.get(i).setCard(card);
			fCards.add(card);
			if(fIsVertical)
			{
				fCardViews.get(i).showCardVertical(fIsHuman);
			}
			else
			{
				fCardViews.get(i).showCardHorizantal(fIsHuman);
			}
			i++;
		}
	}
	
	private void setCard(Card card) //FIXME Pass shows cards
	{
		boolean isFull = true;
		for(CardView cv : fCardViews)
		{
			if(!cv.isVisible())
			{
				cv.setCard(card);
				cv.setVisible(true);
				if(fIsVertical)
				{
					cv.showCardVertical(fIsHuman);
				}
				else
				{
					cv.showCardHorizantal(fIsHuman);
				}
				isFull = false;
				break;
			}
		}
		if(isFull)
		{
			CardView cv = new CardView();
			cv.setName(fName);
			cv.setCard(card);
			fCardViews.add(cv);
			if(fIsVertical)
			{
				cv.showCardVertical(fIsHuman);
			}
			else
			{
				cv.showCardHorizantal(fIsHuman);
			}
			fCardsPanel.add(cv);
		}
		fCards.add(card);
		fCardsPanel.repaint();
		setHasSuitLed();
	}
	
	/**
	 * Hides a card image.
	 * @param card to hide
	 * @pre card is in list
	 */
	private void hideCard(Card card)
	{
		for(CardView cv : fCardViews)
		{
			if(cv.getCardName().equals(card.toString()))
			{
				cv.setVisible(false);
				fCardsPanel.repaint();
				break;
			}
		}
		fCards.remove(card);
		setHasSuitLed();
	}
	
	
	private void hideCards()
	{
		for(CardView cv : fCardViews)
		{
			cv.setVisible(false);
			fCardsPanel.repaint();
			fCards.clear();
		}
	}

	private void setHasSuitLed()
	{
		if (fIsHuman)
		{
			/**TODO
			 * this is done for now but I want to clean it up later - Shams
			 */
			for (Card card : fCards)
			{
				if (card.getSuit().equals(fSuitLed))
				{
					CardView.fHasCardsOfLeadingSuit = true;
				}
			}
		}
	}

	@Override
	public void update(IEvent event)
	{
		if (event instanceof NewRoundEvent)
		{
			CardView.fHeartsPlayed = false;
			fCards.clear();
			System.out.println(fName);
			CardList newHand = ((NewRoundEvent) event).getPlayerHand(fName);
			newHand.sort();
			setHand(newHand);
		}
		/*else if (event instanceof NewTrickEvent)
		{
			
		}*/
		else if(event instanceof PassEvent)
		{
			CardList cardsPassed = ((PassEvent) event).getCardsPassed();
			/*
			 * Remove the cards passed. Since this is applied on all
			 * instances of this object, it will find the player with the
			 * cards and remove them.
			 */
			for(Card card : cardsPassed)
			{
				hideCard(card);
			}
			repaint();			
		}
		else if (event instanceof EndPassingEvent)
		{
			for(String name : MainGUI.gNames)
			{
				if(name.equals(fName))
				{
					hideCards();
					CardList newHand = ((EndPassingEvent) event).getHand(name);
					newHand.sort();
					setHand(newHand);
					fCardsPanel.repaint();
					
					//This is so that the passed cards can "float" when mouse hovers..
					for (Card card : newHand)
					{
						for (CardView cardView : fCardViews)
						{
							if (card.toString().equals(cardView.getCardName()))
							{
								cardView.setPassed(false);
							}
						}
					}
				}
			}
		}
		else if (event instanceof PlayEvent)
		{
			CardView.fUnlocked = false;
			fSuitLed = ((PlayEvent) event).getSuitLed();
			CardView.fSuitLed = ((PlayEvent) event).getSuitLed();
			Card cardPlayed = ((PlayEvent) event).getCard();
			fCards.remove(cardPlayed);
			//if hearts broken
			if (cardPlayed.getSuit().equals(Card.Suit.HEARTS))
			{
				CardView.fHeartsPlayed = true;
			}
			
			String eventPlayerName = ((PlayEvent) event).getName();
			if(eventPlayerName.equals(fName))
			{
				//turnImage.setVisible(false);
				hideCard(cardPlayed);
			}
			setHasSuitLed();
		}
		else if(event instanceof EndTrickEvent)
		{
			CardView.fUnlocked = true;
			CardView.fHasCardsOfLeadingSuit = false;
			fScore = ((EndTrickEvent) event).getPlayerScore(fName);
			((JLabel) fInfoPanel.getComponent(0)).setText("Score : "+fScore.toString()); 
		}
		else if (event instanceof EndRoundEvent)
		{
			CardView.fCardsPassedList.clear();
		}
		else if (event instanceof NewGameEvent)
		{
			CardView.fUnlocked = true;
			CardView.fCardsPassedList.clear();
			fTurnImage.setVisible(false);
			
			for(CardView cv : fCardViews)
			{
				cv.setName(fName);
			}
			//reset score displayed on board
			((JLabel) fInfoPanel.getComponent(0)).setText("Score : "+ 0); 
		}
	}
}
