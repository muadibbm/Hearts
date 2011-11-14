package hearts.listeners;

import hearts.gameEvent.IEvent;

/**
 * Observer for Logger class.
 */
public interface IGameListener
{
	/**
	 * update method.
	 * @param event is the action
	 */
	void update(IEvent event);
}
