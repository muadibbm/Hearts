package hearts.listeners;

import java.util.Iterator;
import java.util.Map.Entry;

import hearts.gameEvent.EndPassingEvent;
import hearts.gameEvent.IEvent;
import hearts.gameEvent.NewTrickEvent;
import hearts.gameEvent.NewRoundEvent;
import hearts.gameEvent.EndTrickEvent;
import hearts.gameEvent.EndGameEvent;
import hearts.gameEvent.PassEvent;
import hearts.gameEvent.PlayEvent;
import hearts.util.CardList;

import org.apache.log4j.Logger;

/**
 * Observer Implementation class to log events in the game.
 */
public class Log implements IGameListener
{
	private static Logger gLog;
	private int fTrickCount = 0;

	/**
	 * LoggerClass constructor.
	 */
	public Log()
	{
		gLog = Logger.getLogger(Log.class);
	}
	
	/**
	 * reacts to the action.
	 * @param event is the action which occurred
	 */
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
			//action.append("NEW TRICK starting with player : "+((NewTrickEvent)event).getCurrentPlayer());
		}
		else if (event instanceof NewRoundEvent)
		{
			Iterator<Entry<String, CardList>> it = ((NewRoundEvent) event).getHandIterator();
			action.append(" \nNEW ROUND. Deck shuffled. Deal cards.\n");
			while(it.hasNext())
			{
				Entry<String, CardList> playerWithHand = it.next();
				action.append(playerWithHand.getKey()+" : ");
				action.append(playerWithHand.getValue().toString()+"\n");
			}
			action.append("Dealer : "+((NewRoundEvent) event).getDealer());
			fTrickCount = 0;
		}
		else if (event instanceof EndTrickEvent)
		{
			action.append("END TRICK ");
			Iterator<Entry<String, Integer>> it = ((EndTrickEvent) event).getScoreIterator();
			while(it.hasNext())
			{
				Entry<String, Integer> playerWithScore = it.next();
				action.append(playerWithScore.getKey()+" : ");
				action.append(playerWithScore.getValue().toString()+"    ");
			}
		}
		else if (event instanceof EndGameEvent)
		{
			action.append("END GAME ");
			action.append(((EndGameEvent) event).getWinnerName()+" is game winner");
			action.append(" with score : "+((EndGameEvent) event).getWinnerScore());
		}
		else if (event instanceof PassEvent)
		{
			action.append("Pass "+((PassEvent) event).getCardsPassed().toString()+
							" : "+((PassEvent) event).getFrom()+" -> "+
							((PassEvent) event).getTo());
		}
		else if (event instanceof EndPassingEvent)
		{
			Iterator<Entry<String, CardList>> it = ((EndPassingEvent) event).getHandIterator();
			while(it.hasNext())
			{
				Entry<String, CardList> playerWithHand = it.next();
				action.append(playerWithHand.getKey()+" : ");
				action.append(playerWithHand.getValue().toString()+"\n");
			}
		}
		else if (event instanceof PlayEvent)
		{
			action.append(((PlayEvent) event).getName()+" plays "+((PlayEvent) event).getCard().toString());
		}
		
		gLog.info(action.toString());
    }

}
