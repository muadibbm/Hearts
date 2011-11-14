package hearts.model;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class TestPlay
{
	private Play play;
	@Test
	public void testGetCard()
	{
		play = new Play("player", hearts.util.AllCards.CAC);
		assertEquals(play.getCard().toString(), hearts.util.AllCards.CAC.toString());
	}
	
	@Test 
	public void testGetPlayer()
	{
		play = new Play("player", hearts.util.AllCards.CAC);
		assertEquals(play.getPlayer(), "player");
	}
}
