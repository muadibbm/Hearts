package hearts.game;

import hearts.gameEvent.IEvent;
import hearts.listeners.IGameListener;

/**
 * Subject class.
 */
public interface IGameSubject
{
	/**
	 * Adds an Observer to notify list.
	 * @param pGameListener is the observer to add
	 */
	void addGameListener(IGameListener pGameListener);
	
	/**
	 * Removes an Observer from notify list.
	 * @param pGameListener is the observer to remove
	 */
	void removeGameListener(IGameListener pGameListener);
	
	/**
	 * Notifies all the observers.
	 * @param event Event type.
	 */
	void notifyListeners(IEvent event);

}
