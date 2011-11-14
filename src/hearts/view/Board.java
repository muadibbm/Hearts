package hearts.view;

import hearts.gameEvent.IEvent;
import hearts.gameEvent.NewGameEvent;
import hearts.listeners.IGameListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

@SuppressWarnings("serial")
/**
 * Represents the main board, which holds all players' hands and the cards being played
 */
public class Board extends JPanel implements IGameListener
{
	protected static HandView gHand1;
	protected static HandView gHand2;
	protected static HandView gHand3;
	protected static HandView gHand4;
	private static MiddleView gMiddle;

	/**
	 * Constructor of main board of game.
	 */
	public Board()
	{
		super(new BorderLayout());
		//setForeground(new Color(0f, 0f, 0f));
		//setBackground(ViewConstants.BACKGROUND_COLOR);
		setVisible(true);
		MainGUI.gGE.addGameListener(this);
		/**
		 * Clockwise from Southernmost.
		 */
		gHand1 = new HandView(MainGUI.getName(0), true, false);
		gHand2 = new HandView(MainGUI.getName(1), false, true);
		gHand3 = new HandView(MainGUI.getName(2), false, false);
		gHand4 = new HandView(MainGUI.getName(3), false, true);
		gMiddle = new MiddleView();
		
		setBorders();

		/**
		 * Clockwise from Southernmost.
		 */
		add(gHand1, BorderLayout.SOUTH);
		add(gHand2, BorderLayout.WEST);
		add(gHand3, BorderLayout.NORTH);	
		add(gHand4, BorderLayout.EAST);
		add(gMiddle, BorderLayout.CENTER);

	}
	
	private void setBorders()
	{
		String name = MainGUI.getName(0);
		gHand1.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(), name, 
				ViewConstants.NAME_JUSTIFICATION, ViewConstants.NAME_POSITION,
				ViewConstants.NAME_FONT, ViewConstants.WHITE));
		name = MainGUI.getName(1);
		gHand2.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(), name, 
				ViewConstants.NAME_JUSTIFICATION, ViewConstants.NAME_POSITION,
				ViewConstants.NAME_FONT, ViewConstants.WHITE));
		name = MainGUI.getName(2);
		gHand3.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(), name, 
				ViewConstants.NAME_JUSTIFICATION, ViewConstants.NAME_POSITION,
				ViewConstants.NAME_FONT, ViewConstants.WHITE));
		name = MainGUI.getName(3);
		gHand4.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEmptyBorder(), name, 
				ViewConstants.NAME_JUSTIFICATION, ViewConstants.NAME_POSITION,
				ViewConstants.NAME_FONT, ViewConstants.WHITE));
	}

	@Override
	public void paintComponent(Graphics g) 
    {
		//super.paintComponent(g);
        Image img = new ImageIcon("background/BK_"+MainGUI.background+".jpg").getImage();
        g.drawImage(img, 0, 0, null);
    }

	@Override
	public void update(IEvent event)
	{
		// TODO Auto-generated method stub
		if (event instanceof NewGameEvent)
		{
			gHand1.setName(MainGUI.getName(0));
			gHand2.setName(MainGUI.getName(1));
			gHand3.setName(MainGUI.getName(2));
			gHand4.setName(MainGUI.getName(3));
			setBorders();
		}
	}
}
