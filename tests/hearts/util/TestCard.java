package hearts.util;

import static hearts.util.AllCards.C2C;
import static hearts.util.AllCards.C3C;
import static hearts.util.AllCards.C2H;
import static hearts.util.AllCards.C2S;
import static hearts.util.AllCards.C3D;
import static hearts.util.AllCards.C3H;
import static hearts.util.AllCards.C4D;
import static hearts.util.AllCards.C4H;
import static hearts.util.AllCards.C5C;
import static hearts.util.AllCards.CAC;
import static hearts.util.AllCards.CAD;
import static hearts.util.AllCards.CAH;
import static hearts.util.AllCards.CAS;
import static hearts.util.AllCards.CJC;
import static hearts.util.AllCards.CJH;
import static hearts.util.AllCards.CKD;
import static hearts.util.AllCards.CKH;
import static hearts.util.AllCards.CKS;
import static hearts.util.AllCards.CQD;
import static hearts.util.AllCards.CQH;
import static hearts.util.AllCards.CQS;
import static hearts.util.AllCards.CTC;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import hearts.util.Card;

public class TestCard
{
	@Test
	public void testToString()
	{
		assertEquals( "ACE of CLUBS", CAC.toString());
		assertEquals( "THREE of CLUBS", C3C.toString());
		assertEquals( "TEN of CLUBS", CTC.toString());
		assertEquals( "JACK of CLUBS", CJC.toString());
		assertEquals( "QUEEN of HEARTS", CQH.toString());
		assertEquals( "KING of SPADES", CKS.toString());
		assertEquals( "QUEEN of DIAMONDS", CQD.toString());
	}
	
	@Test
	public void testToCompactString()
	{
		assertEquals( "AC", CAC.toCompactString());
		assertEquals( "3C", C3C.toCompactString());
		assertEquals( "TC", CTC.toCompactString());
		assertEquals( "JH", CJH.toCompactString());
		assertEquals( "QD", CQD.toCompactString());
		assertEquals( "KS", CKS.toCompactString());
	}
	
	@Test
	public void testCompareTo()
	{
		assertTrue( CAC.compareTo(C2C) > 0);
		assertTrue( C2C.compareTo(C2C) == 0);
		assertTrue( C2C.compareTo(CAC) < 0);
		assertTrue( CAC.compareTo(C2C) > 0);
		assertTrue( C2C.compareTo(C2H) < 0);
		assertTrue( C3D.compareTo(C3H) < 0);
		assertTrue( C3H.compareTo(C3D) > 0);
		assertTrue( C3H.compareTo(C4H) < 0);
	}
	
	@Test
	public void testGetSuit()
	{
		assertEquals( Card.Suit.CLUBS, CAC.getSuit() );
		assertEquals( Card.Suit.DIAMONDS, CAD.getSuit() );
		assertEquals( Card.Suit.HEARTS, CAH.getSuit() );
		assertEquals( Card.Suit.SPADES, CAS.getSuit() );
	}
	
	@Test
	public void testGetPoints()
	{
		assertEquals( 0, CAC.getPoints() );
		assertEquals( 0, CKD.getPoints() );
		assertEquals( 13, CQS.getPoints() );
		assertEquals( 0, C2S.getPoints() );
		assertEquals( 1, CKH.getPoints() );
	}
	
	@Test
	public void testGetRank()
	{
		assertEquals( Card.Rank.ACE, CAC.getRank() );
		assertEquals( Card.Rank.FOUR, C4D.getRank() );
		assertEquals( Card.Rank.JACK, CJH.getRank() );
		assertEquals( Card.Rank.QUEEN, CQS.getRank() );
	}
	
	@Test
	public void testEquals()
	{
		assertTrue( CAC.equals( CAC ));
		assertTrue( CAC.equals( new Card(Card.Rank.ACE, Card.Suit.CLUBS )));
		assertFalse( CAC.equals( CAH ));
		assertFalse( CKH.equals( C4H ));
		assertFalse( CKH.equals( null ));
		assertFalse( CKH.equals("KING OF HEARTS"));
	}
	
	@Test
	public void testHashCode()
	{
		assertTrue( CAC.hashCode() == new Card(Card.Rank.ACE, Card.Suit.CLUBS ).hashCode());
		assertFalse( CAC.hashCode() == CAH.hashCode() );
		assertEquals( C5C.getSuit().ordinal()*13 + C5C.getRank().ordinal(), C5C.hashCode() );
	}
}
