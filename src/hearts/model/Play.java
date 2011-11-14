package hearts.model;

import hearts.util.Card;

/**
 * A player name and the card he played.
 * Immutable.
 */
public class Play implements Cloneable
{
	
	private String fPlayer;
	private Card fCard;
	
	/**
	 * @param pPlayer The player in this play.
	 * @param card The card in this play.
	 */
	public Play(String pPlayer, Card card)
	{
		this.fPlayer = pPlayer;
		this.fCard = card; 
	}
	
	/**
	 * @return The card in this play.
	 */
	public Card getCard()
	{
		return fCard;
	}
	
	/**
	 * @return The player in this play.
	 */
	public String getPlayer()
	{
		return fPlayer;
	}
}
