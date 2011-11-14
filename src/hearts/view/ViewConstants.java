package hearts.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.border.TitledBorder;

public final class ViewConstants
{
	public static final Color BACKGROUND_COLOR = new Color(0.5f, 0.2f, 0.7f);
	public static final Color WHITE = new Color(1f,1f,1f);
	public static final Color PURPLE =  new Color(0.5f, 0.2f, 0.7f);
	public static final Color GREEN = new Color(50, 130, 30);
	public static final String GAME_TITLE = "GAME OF HEARTS BETA";
	public static final int WIDTH = 1010;
	public static final int HEIGHT = 767;
	public static final int LOG_WIDTH = 300;
	public static final int NEWGAME_WIDTH = 230;
	public static final int NEWGAME_HEIGHT = 330;
	public static final int LUCKYNUMBER = 13;
	static final int SL_MIN = 100;
	static final int SL_MAX = 2600;
	static final int SL_INIT = 950;

	//TIMER
	public static final int TIME = 950;
	
	//HAND
	public static final int HANDVIEW_VERTICAL_GAP = -55;
	public static final int HANDVIEW_HORIZONTAL_GAP = -45;
	public static final Font NAME_FONT = new Font("Serif", Font.BOLD, 18);
	public static final int NAME_JUSTIFICATION = TitledBorder.CENTER;
	public static final int NAME_POSITION = 0;
	public static final int CARDS_CENTER = 0;
	
	//MENU
	public static final Font NEW_GAME_WARNING_FONT = new Font("Serif", Font.PLAIN, 13);
	public static final Font OP_FONT = new Font("Serif", Font.PLAIN, 14);
	public static final String HELP = " HEARTS OVERVIEW\n" +
    " Hearts is a trick-based card game in which the goal is to get rid of your cards     \n" +
    " while avoiding points. Tricks are the groups of cards set down by players in each   \n" +
    " round. Points are scored whenever you take a trick that contains hearts or the      \n" +
    " queen of spades. Hearts is played with four players, and as soon as one player has  \n" +
    " more than 100 points, the player with the lowest score wins.                        \n" +
    "\n THE PASS\n" +
    " All the cards are dealt out one at a time, so that everyone has 13. On the first    \n" +
    " hand, after the deal, each player passes any three cards face-down to the player to \n" +
    " their left. On the second hand each player passes three cards to the player    	    \n" +
    " to their right, in the same way. On the third hand way player passes three cards to \n" +
    " the player sitting opposite. On the fourth hand no cards are passed at all. The     \n" +
    " cycle then repeats until the end of the game.\n" +
    "\n THE PLAY OF A HAND\n" +
    " The person who holds the 2 of clubs must lead it to the first trick. The other      \n" +
    " players must play a card of the suit which was led if possible. If they have a card \n" +
    " of that suit, they may play any card. The person who played the highest card of the \n" +
    " suit led wins the trick and leads to the next trick. It is illegal to lead a heart  \n" +
    " until after a heart has been played to a previous trick, unless your hand contains  \n" +
    " nothing but hearts.";
	public static final String ABOUT = " Game developed as a team project for \n" +
    " comp 303 software development course \n" +
    " at Mcgill university by \n" +
    " Éric Renaud-Houde \n" +
    " julieta jakubowicz \n" +
    " Shams Methnani \n" +
    " Mehrdad Dehdashti";
	public static final Font MENU_FONT = new Font("Serif", Font.BOLD, 13);

	//OPTIONS
	public static final int NAME_BOX_SIZE = 10;
	
}
