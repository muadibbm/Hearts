package hearts.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import hearts.gameEvent.EndGameEvent;
import hearts.gameEvent.EndPassingEvent;
import hearts.gameEvent.EndTrickEvent;
import hearts.gameEvent.IEvent;
import hearts.gameEvent.NewGameEvent;
import hearts.gameEvent.NewRoundEvent;
import hearts.gameEvent.NewTrickEvent;
import hearts.gameEvent.PassEvent;
import hearts.gameEvent.PlayEvent;
import hearts.listeners.IGameListener;
import hearts.util.CardList;

public class LoggerView extends JPanel implements IGameListener 
{
	private JTextField fTextField;
    private JTextArea fTextArea;
    private int fTrickCount = 0;
	
	public LoggerView()
	{
        super(new FlowLayout());
        MainGUI.gGE.addGameListener(this);
        fTextArea = new JTextArea(41,20);
        fTextArea.setEditable(false);
        fTextArea.setVisible(true);
        JScrollPane scrollPane = new JScrollPane(fTextArea);
        add(scrollPane, BorderLayout.CENTER);
        setOpaque(false);
	}
	
	@Override
	public void update(IEvent event)
	{
		StringBuffer action =  new StringBuffer();
		/*
		 * In GUI, you only check instanceof event
		 * types which are useful to a particular observer.
		 */
		if(event instanceof NewTrickEvent)
		{
			fTrickCount++;
			action.append("NEW TRICK -----"+fTrickCount+"-----");
			fTextArea.append(action.toString()+"\n");
			//action.append("NEW TRICK starting with player : "+((NewTrickEvent)event).getCurrentPlayer());
		}
		else if (event instanceof NewRoundEvent)
		{
			Iterator<Entry<String, CardList>> it = ((NewRoundEvent) event).getHandIterator();
			action.append("NEW ROUND\nDeck shuffled\nDeal cards\n");
			action.append("Dealer : "+((NewRoundEvent) event).getDealer());
			fTrickCount = 0;
			fTextArea.append(action.toString()+"\n");
		}
//		else if (event instanceof EndTrickEvent)
//		{
//			action.append("END TRICK\n");
//			Iterator<Entry<String, Integer>> it = ((EndTrickEvent) event).getScoreIterator();
//			while(it.hasNext())
//			{
//				Entry<String, Integer> playerWithScore = it.next();
//				action.append(playerWithScore.getKey()+" : ");
//				action.append(playerWithScore.getValue().toString()+"\n");
//			}
//			fTextArea.append(action.toString());
//		}
		else if (event instanceof NewGameEvent)
		{
			action.append("______________________________\n");
			action.append("NEW GAME\n");
			fTextArea.append(action.toString()+"\n");
		}
		else if (event instanceof EndGameEvent)
		{
			action.append("END GAME ");
			action.append(((EndGameEvent) event).getWinnerName()+" is game winner");
			action.append(" with score : "+((EndGameEvent) event).getWinnerScore());
			fTextArea.append(action.toString()+"\n");
		}
//		else if (event instanceof PassEvent)
//		{
//			action.append("Pass "+((PassEvent) event).getCardsPassed().toString()+
//							" : "+((PassEvent) event).getFrom()+" -> "+
//							((PassEvent) event).getTo());
//		}
//		else if (event instanceof EndPassingEvent)
//		{
//			Iterator<Entry<String, CardList>> it = ((EndPassingEvent) event).getHandIterator();
//			while(it.hasNext())
//			{
//				Entry<String, CardList> playerWithHand = it.next();
//				action.append(playerWithHand.getKey()+" : ");
//				action.append(playerWithHand.getValue().toString()+"\n");
//			}
//		}
		else if (event instanceof PlayEvent)
		{
			action.append(((PlayEvent) event).getName()+" plays "+((PlayEvent) event).getCard().toString());
			fTextArea.append(action.toString()+"\n");
		}
	}

}
