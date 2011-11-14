package hearts.view;

import hearts.game.GameEngine;
import hearts.game.GameEngine.PassingDirection;
import hearts.gameEvent.EndGameEvent;
import hearts.gameEvent.EndPassingEvent;
import hearts.gameEvent.EndRoundEvent;
import hearts.gameEvent.IEvent;
import hearts.gameEvent.NewGameEvent;
import hearts.gameEvent.NewRoundEvent;
import hearts.listeners.IGameListener;
import hearts.model.Player;
import hearts.robots.EasySmartRobot;
import hearts.robots.HardSmartRobot;
import hearts.robots.MediumSmartRobot;
import hearts.robots.PredictableRobot;
import hearts.robots.RandomRobot;
import hearts.util.CardList;
import hearts.util.Constants;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.html.HTMLDocument.Iterator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.ScrollPane;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map.Entry;

/**
 * This frame holds all the components in the main window
 * it interacts with the GameEngine through listeners.
 *
 */
@SuppressWarnings("serial")
class MainGUI extends JFrame implements IGameListener
{
	public static GameEngine gGE = GameEngine.getInstance();
	public static ViewTimer gTimer;
	protected static String background;
	private static Board board;
	protected static ArrayList<String> gNames =  new ArrayList<String>();
	private static ArrayList<String> gRobotTypes = new ArrayList <String>(); 
	private static JFrame gFrame;
	private static JButton play;
	private static JButton pass;
	private static JButton logToggle;
	private static JPanel buttons;
	
	public MainGUI()
	{
		gGE.addGameListener(this);
	}
	public static String getName(int index)
	{
		return gNames.get(index);
	}
	private static void setupGameEngine()
	{
		int i = 0;
		for(String robotType : gRobotTypes)
		{
			if(robotType.equals("RandomRobot"))
			{
				gGE.addPlayer(gNames.get(i), new RandomRobot());
			}
			else if(robotType.equals("PredictableRobot"))
			{
				gGE.addPlayer(gNames.get(i), new PredictableRobot());
			}
			else if(robotType.equals("EasySmartRobot"))
			{
				gGE.addPlayer(gNames.get(i), new EasySmartRobot());
			}
			else if(robotType.equals("MediumSmartRobot"))
			{
				gGE.addPlayer(gNames.get(i), new MediumSmartRobot());
			}
			else if(robotType.equals("HardSmartRobot"))
			{
				gGE.addPlayer(gNames.get(i), new HardSmartRobot());
			}
			i++;
		}
		gGE.initGame();
	}

	/**
	 * Sets up the frame.
	 */
	private static void setupFrame()
	{
		gFrame.setTitle(ViewConstants.GAME_TITLE);
		gFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//gFrame.getContentPane().setForeground(new Color(1f, 1f, 1f));
		//gFrame.getContentPane().setBackground(ViewConstants.BACKGROUND_COLOR);
	}
	
	/**
	 * @return a bar of buttons
	 */
	private static void setupButtons(final JPanel log)
	{
		buttons = new JPanel(new FlowLayout());
		buttons.setVisible(true);
		play = new JButton("AUTOPLAY");
		pass = new JButton("PASS");	
		JLabel speed = new JLabel("             ");
		logToggle = new JButton("Show Log");
		JSlider slider = new JSlider(JSlider.HORIZONTAL,
                ViewConstants.SL_MIN, ViewConstants.SL_MAX, ViewConstants.SL_INIT);
		
		class SliderListener implements ChangeListener {
			
			public void stateChanged(ChangeEvent e)
			{
				JSlider source = (JSlider)e.getSource();
				if (!source.getValueIsAdjusting())
				{
			        int sp = (int)source.getValue();
			        gTimer.setDelay(sp);
			        MiddleView.setHideCardsDelay(sp);
				}
			}
		}
			

		slider.addChangeListener(new SliderListener());
		slider.setMajorTickSpacing(1);
		slider.setMinorTickSpacing(1);
		Hashtable labelTable = new Hashtable();
		labelTable.put( new Integer( ViewConstants.SL_MIN ), new JLabel("Fast") );
		labelTable.put( new Integer( ViewConstants.SL_MAX ), new JLabel("Slow") );
		slider.setLabelTable(labelTable);
		//slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		buttons.add(play);
		buttons.add(pass);
		buttons.add(logToggle);
		buttons.add(speed);
		buttons.add(slider);
		
		/**
		 * ACTION LISTENERS
		 */
		pass.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(!ViewTimer.isCardsPassed())
				{
					System.out.println(CardView.fCardsPassedList.toString());
					if(CardView.fCardsPassedList.size()==3)
					{
						gGE.passCards(gNames.get(0), CardView.fCardsPassedList);
						//pass.setEnabled(false);
					}
					else
					{
						// ERROR MESSAGE
					}
				}	
			}
		});
		
		play.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(ViewTimer.isCardsPassed())
				{
					//gTimer.start(); REMOVE THIS LINE. It's actually useless.
					gGE.play();
				}
			}

		});	
		
		logToggle.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (log.isVisible())
				{
					logToggle.setText("Show Log");
				}
				else
				{
					logToggle.setText("Hide Log");
				}
				log.setVisible(!log.isVisible());
			}
		});
	}
	
	/**
	 * @return a menu bar
	 */
	private static JMenuBar setupMenu()
	{
		JMenuBar bar = new JMenuBar();
		JMenu main = new JMenu("File");
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		main.setMnemonic(KeyEvent.VK_M);
		bar.add(main);
		bar.add(help);
		
		JMenuItem newGame = new JMenuItem("New Game");
		JMenuItem appearance = new JMenuItem("Change Appearance");
		JMenuItem exitGame = new JMenuItem("Exit");
		
		JMenuItem howToPlay = new JMenuItem("How to Play");
		JMenuItem about = new JMenuItem("About");
		
		/* TODO : add action for saveGame */
		
		newGame.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				startNewGame();
			}
		});

	
		
		appearance.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFrame AP = new JFrame();
				AP.setLayout(new BorderLayout());
				AP.setResizable(false);
				AP.setVisible(true);
				
				JPanel bg = new JPanel();
				JLabel setBG = new JLabel(" SET BACKGROUND ");
				String [] bGlist = {"Plain Green", "Plain Blue", "Felt Gray",
						"Felt Red", "Felt Lightgreen", "Custom Purple", "Custom Green",
						"Custom Flower", "Custom Wood", "Carpet"};
				final JComboBox BGbox = new JComboBox(bGlist);
				
				JButton apply = new JButton("Apply");
				apply.addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						background = (String) BGbox.getSelectedItem();
						board.repaint();
					}
				});
				
				bg.add(setBG);
				bg.add(BGbox);		
				AP.add(bg, BorderLayout.NORTH);
				AP.add(apply, BorderLayout.CENTER);
				AP.pack();
			}
		});
		
		exitGame.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				gFrame.dispose();
				System.exit(0);
			}
		});
		
		

		howToPlay.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFrame HTP = new JFrame();
				HTP.setResizable(false);
				HTP.setVisible(true);
				JTextArea howTo = new JTextArea();
				howTo.setFont(ViewConstants.MENU_FONT);
				howTo.setEditable(false);
				howTo.setText(ViewConstants.HELP);
				HTP.add(howTo);
				HTP.pack();
			}
		});
		
		about.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				JFrame about = new JFrame();
				about.setResizable(false);
				about.setVisible(true);
				JTextArea aboutText = new JTextArea();
				about.setFont(ViewConstants.MENU_FONT);
				aboutText.setEditable(false);
				aboutText.setText(ViewConstants.ABOUT);
				about.add(aboutText);
				about.pack();
			}
		});
		
		main.add(newGame);
		main.add(appearance);
		main.add(exitGame);
		help.add(howToPlay);
		help.add(about);
		
		return bar;
	}
	private static void startNewGame()
	{
		final JFrame OP = new JFrame();
		OP.setLayout(new BorderLayout());
		OP.setResizable(false);
		OP.setVisible(true);
		
		JPanel names = new JPanel();
		JLabel setPlayerName = new JLabel(" SET PLAYER NAME");
		JLabel player1ID = new JLabel("Player 1 : ");
		final JTextField player1 = new JTextField(ViewConstants.NAME_BOX_SIZE);
		player1.setText("Eric");
		JLabel player2ID = new JLabel("Player 2 : ");
		final JTextField player2 = new JTextField(ViewConstants.NAME_BOX_SIZE);
		player2.setText("Shams");
		JLabel player3ID = new JLabel("Player 3 : ");
		final JTextField player3 = new JTextField(ViewConstants.NAME_BOX_SIZE);
		player3.setText("Mehrdad");
		JLabel player4ID = new JLabel("Player 4 : ");
		final JTextField player4 = new JTextField(ViewConstants.NAME_BOX_SIZE);
		player4.setText("Juli");
		GridBagLayout gbl = new GridBagLayout();
		names.setLayout(gbl);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(3, 0, 5, 0);
		names.add(setPlayerName, c);
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, -45, 0, 0);
		names.add(player1ID, c);
		c.gridx = 1;
		c.gridy = 1;
		names.add(player1, c);
		c.gridx = 0;
		c.gridy = 2;
		names.add(player2ID, c);
		c.gridx = 1;
		c.gridy = 2;
		names.add(player2, c);
		c.gridx = 0;
		c.gridy = 3;
		names.add(player3ID, c);
		c.gridx = 1;
		c.gridy = 3;
		names.add(player3, c);
		c.gridx = 0;
		c.gridy = 4;
		names.add(player4ID, c);
		c.gridx = 1;
		c.gridy = 4;
		names.add(player4, c);
		
		
		JPanel ai = new JPanel();
		JLabel setAI = new JLabel(" SET AI ");
		String [] AIlist = {"RandomRobot","PredictableRobot","EasySmartRobot",
				"MediumSmartRobot","HardSmartRobot"};
		final JComboBox AIbox1 = new JComboBox(AIlist);
		final JComboBox AIbox2 = new JComboBox(AIlist);
		final JComboBox AIbox3 = new JComboBox(AIlist);
		final JComboBox AIbox4 = new JComboBox(AIlist);
		JLabel player1ID2 = new JLabel("Player 1 : ");
		JLabel player2ID2 = new JLabel("Player 2 : ");
		JLabel player3ID2 = new JLabel("Player 3 : ");
		JLabel player4ID2 = new JLabel("Player 4 : ");
		ai.setLayout(gbl);
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(1, -3, 1, 0);
		ai.add(setAI, c);
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(2, 13, 0, 0);
		ai.add(player1ID2, c);
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(2, 6, 0, 0);
		ai.add(AIbox1, c);
		c.gridx = 0;
		c.gridy = 2;
		c.insets = new Insets(2, 13, 0, 0);
		ai.add(player2ID2, c);
		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(2, 6, 0, 0);
		ai.add(AIbox2, c);
		c.gridx = 0;
		c.gridy = 3;
		c.insets = new Insets(2, 13, 0, 0);
		ai.add(player3ID2, c);
		c.gridx = 1;
		c.gridy = 3;
		c.insets = new Insets(2, 6, 0, 0);
		ai.add(AIbox3, c);
		c.gridx = 0;
		c.gridy = 4;
		c.insets = new Insets(2, 13, 0, 0);
		ai.add(player4ID2, c);
		c.gridx = 1;
		c.gridy = 4;
		c.insets = new Insets(2, 6, 0, 0);
		ai.add(AIbox4, c);
		
		JPanel ok = new JPanel();
		ok.setLayout(new BorderLayout());
		//JLabel warning = new JLabel(" Warning : Apply will start a new game"); 
		//warning.setFont(ViewConstants.NEW_GAME_WARNING_FONT);
		JButton apply = new JButton("Apply");
		
		apply.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				gNames.clear();
				gRobotTypes.clear();
				gGE.clearPlayers();
				String p1 = player1.getText().toString();
				String p2 = player2.getText().toString();
				String p3 = player3.getText().toString();
				String p4 = player4.getText().toString();
				
				if(p1.length() > ViewConstants.LUCKYNUMBER)
				{
					gNames.add(p1.substring(0, ViewConstants.LUCKYNUMBER));
				}
				else
				{
					gNames.add(p1);
				}
				if (p2.equals(p1))
				{
					gNames.add("Player 2");
				}
				else
				{
					if(p2.length() > ViewConstants.LUCKYNUMBER)
					{
						gNames.add(p2.substring(0, ViewConstants.LUCKYNUMBER));
					}
					else
					{
						gNames.add(p2);
					}
				}
				if (p3.equals(p1) ||
						p3.equals(p2))
				{
					gNames.add("Player 3");
				}
				else
				{
					if(p3.length() > ViewConstants.LUCKYNUMBER)
					{
						gNames.add(p3.substring(0, ViewConstants.LUCKYNUMBER));
					}
					else
					{
						gNames.add(p3);
					}
				}
				if (p4.equals(p1) ||
						p4.equals(p2)||
						p4.equals(p3))
				{
					gNames.add("Player 4");
				}
				else
				{
					if(p4.length() > ViewConstants.LUCKYNUMBER)
					{
						gNames.add(p4.substring(0, ViewConstants.LUCKYNUMBER));
					}
					else
					{
						gNames.add(p4);
					}
				}
				gRobotTypes.add(AIbox1.getSelectedItem().toString());
				gRobotTypes.add(AIbox2.getSelectedItem().toString());
				gRobotTypes.add(AIbox3.getSelectedItem().toString());
				gRobotTypes.add(AIbox4.getSelectedItem().toString());
				MiddleView.hideCards();
				setupGameEngine();
				OP.setVisible(false);
				OP.dispose();
			}
		});
		
		//ok.add(warning, BorderLayout.NORTH);
		ok.add(apply, BorderLayout.CENTER);
				
		OP.setFont(ViewConstants.OP_FONT);
		OP.add(names, BorderLayout.NORTH);
		OP.add(ai, BorderLayout.CENTER);
		OP.add(ok, BorderLayout.SOUTH);
		OP.setLocationRelativeTo(gFrame);
		OP.setSize(ViewConstants.NEWGAME_WIDTH, ViewConstants.NEWGAME_HEIGHT);
	
	}
	/**
	 * Initializes a GUI with a board, at the beginning of a game.
	 * @param hand the hand of the human player
	 */
	public static void init()
	{	
		/**
		 * FRAME, BACKGROUND AND MAIN BOARD
		 */
		gFrame = new JFrame();
		setupFrame();
		background = "Custom Purple";
		board = new Board();	
		gFrame.getContentPane().add(board, BorderLayout.CENTER);
		
		/**
		 * LOGGER
		 */
		final JPanel log = new LoggerView();
		log.setVisible(false);
		gFrame.add(log, BorderLayout.LINE_END);
		
		/**
		 * MENU
		 */
		JMenuBar bar = setupMenu();
		gFrame.setJMenuBar(bar);

		/**
		 * BUTTONS
		 */
		setupButtons(log); //idk if its the best idea to be passing in the log :/
		gFrame.getContentPane().add(buttons, BorderLayout.SOUTH);
		
		/**
		 * FRAME
		 */
		gFrame.setSize(ViewConstants.WIDTH, ViewConstants.HEIGHT);
		gFrame.setVisible(true);	
		gFrame.setResizable(false);
		
	}

	/**
	 * creates the gui for starting the game.
	 */
	public static void run()
	{
		gNames.add("Eric"); 			//1- Human player/Robot 
		gRobotTypes.add("EasySmartRobot");
		gNames.add("Shams"); 			//2- Robot
		gRobotTypes.add("RandomRobot");
		gNames.add("Mehrdad");			//2- Robot
		gRobotTypes.add("EasySmartRobot");
		gNames.add("Juli");				//2- Robot
		gRobotTypes.add("PredictableRobot");
		init();
		gTimer = new ViewTimer(ViewConstants.TIME, gNames.get(0));
		gGE = GameEngine.getInstance();
		setupGameEngine();
	}
	@Override
	public void update(IEvent event)
	{
		// TODO Auto-generated method stub
		if (event instanceof NewGameEvent)
		{
			
		}
		else if (event instanceof NewRoundEvent)
		{
			if(!((NewRoundEvent) event).getPassingDirection().equals(PassingDirection.NONE))
			{
				pass.setEnabled(true);
				play.setEnabled(false);
			}
		}
		else if (event instanceof EndPassingEvent)
		{
			pass.setEnabled(false);
			play.setEnabled(true);
		}
		
		else if (event instanceof EndGameEvent)
		{
	
			ArrayList<JLabel> labels = new ArrayList<JLabel>();
			
			int i = 0;
			String winner = ((EndGameEvent)event).getWinnerName();
			for (String name : gNames)
			{
				String score = new String();
				score = "  \t" + ((EndGameEvent) event).getPlayerScore(name);
				JLabel nameLabel = new JLabel(name);
				JLabel scoreLabel = new JLabel(score);
				if (winner.equals(name))
				{
					nameLabel.setForeground(Color.RED);
					scoreLabel.setForeground(Color.RED);
				}
				labels.add(nameLabel);
				labels.add(scoreLabel);
				i++;
			}

			JPanel names = new JPanel();
			GridBagLayout gbl = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			names.setLayout(gbl);
			
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbc.insets = new Insets(0,3,5,0);
			names.add(labels.get(0), gbc);
			
			gbc.gridx = 5;
			gbc.gridy = 1;
			names.add(labels.get(1), gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 2;
			names.add(labels.get(2), gbc);
			
			gbc.gridx = 5;
			gbc.gridy = 2;
			names.add(labels.get(3), gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 3;
			names.add(labels.get(4), gbc);
			
			gbc.gridx = 5;
			gbc.gridy = 3;
			names.add(labels.get(5), gbc);
			
			gbc.gridx = 0;
			gbc.gridy = 4;
			names.add(labels.get(6), gbc);
			
			gbc.gridx = 5;
			gbc.gridy = 4;
			names.add(labels.get(7), gbc);
			
			final JFrame endGame = new JFrame();
			JButton newGame = new JButton("Play again");
			newGame.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
				 startNewGame();
				 endGame.dispose();
				 endGame.setVisible(false);
				}
		});
			endGame.add(newGame, BorderLayout.SOUTH);
			endGame.add(names, BorderLayout.CENTER);
			endGame.setTitle("Results");
			endGame.setLocationRelativeTo(gFrame);
			
			endGame.setSize(200, 300);
			endGame.setResizable(false);
			endGame.setVisible(true);
		}

	}
}
