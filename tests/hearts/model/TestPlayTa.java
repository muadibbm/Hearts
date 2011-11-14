package hearts.model;

import static org.junit.Assert.*;
import static hearts.util.AllCards.C2C;
import org.junit.Before;
import org.junit.Test;

public class TestPlayTa
{
	private Play fPlay;
	
	@Before
	public void setup()
	{		
		fPlay = new Play("testplayer", C2C);
	}
	
	@Test
	public void testGetCard()
	{
		assertEquals(C2C, fPlay.getCard());
	}
	
	@Test
	public void testGetPlayer()
	{
		assertEquals("testplayer", fPlay.getPlayer());
	}
	
}
