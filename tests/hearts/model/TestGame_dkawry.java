package hearts.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

import hearts.util.AllCards;
import hearts.util.Card;
import hearts.util.Card.Suit;
import hearts.util.CardList;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for Comp 303 (2010) Hearts M1 Game and IImmutableGameState.
 * 
 * @author dkawry
 */
public class TestGame_dkawry {

	private final String PLAYER_1 = "Player 1";
	private final String PLAYER_2 = "Player 2";
	private final String PLAYER_3 = "Player 3";
	private final String PLAYER_4 = "Player 4";

	private Game game;
	private int DEFAULT_TRICKS_COMPLETED;

	@Before
	public void setUp() {
		game = new Game();
		try {
			DEFAULT_TRICKS_COMPLETED = game.tricksCompleted();
		} catch (Exception e) {
			DEFAULT_TRICKS_COMPLETED = 0;
		}
	}

	/**
	 * Sanity check. Nothing has happened yet.
	 */
	@Test
	public void testEmptyGameState() {
		assertFalse(game.heartsPlayed());
		assertEquals(0, DEFAULT_TRICKS_COMPLETED); // it should be zero
	}

	/**
	 * Sanity check. Still nothing has happened yet.
	 */
	@Test
	public void testNothingAfterNewTrick() {
		game.newTrick();
		assertFalse(game.heartsPlayed());
		assertEquals(DEFAULT_TRICKS_COMPLETED, game.tricksCompleted()); // should
																		// still
																		// be
																		// zero
		Trick trick = game.getCurrentTrick();
		assertNotNull(trick);
		assertEquals(0, trick.cardsPlayed());
		CardList cards = trick.getCards();
		if (cards != null) {
			assertEquals(0, cards.size());
		}
	}

	@Test
	public void testCorrectSuitLed() {
		game.newTrick();
		game.addPlay(PLAYER_1, AllCards.C2C);
		assertEquals(Suit.CLUBS, game.getSuitLed());
		game.addPlay(PLAYER_2, AllCards.C2D);
		assertEquals(Suit.CLUBS, game.getSuitLed());
	}

	@Test(expected = ModelException.class)
	public void testNoSuitLed() {
		game.newTrick();
		game.getSuitLed();
	}

	@Test
	public void testTrickCompleted() {
		buildOneClubTrick();
		assertEquals(DEFAULT_TRICKS_COMPLETED + 1, game.tricksCompleted());
		game.newTrick();
		game.addPlay(PLAYER_1, AllCards.C9C);
		assertEquals(DEFAULT_TRICKS_COMPLETED + 1, game.tricksCompleted());
	}

	@Test
	public void testNoHearts() {
		buildOneClubTrick();
		assertFalse(game.heartsPlayed());
	}

	@Test
	public void testHearts() {
		buildOne3Club1HeartTrick();
		assertTrue(game.heartsPlayed());
	}

	@Test
	public void testGetLastWinnerSameSuits() {
		buildOneClubTrick();
		assertEquals(PLAYER_4, game.getLastWinner());
		// might be double penalty with trick.
		// but we can't help that when testing getLastWinner itself.
	}

	@Test
	public void testLastWinnerPartialRound() {
		buildOneClubTrick();
		String lastWinner = game.getLastWinner();
		game.newTrick();
		assertEquals(lastWinner, game.getLastWinner());
		game.addPlay(PLAYER_1, AllCards.C8C);
		assertEquals(lastWinner, game.getLastWinner());
		game.addPlay(PLAYER_2, AllCards.CKC);
		assertEquals(lastWinner, game.getLastWinner());
		game.addPlay(PLAYER_3, AllCards.C9C);
		assertEquals(lastWinner, game.getLastWinner());
	}

	@Test
	public void testCorrectCardsAccumWinner() {
		buildOneClubTrick();
		// whoever the winner was, he better have all the cards!
		CardList cardList = game.getCardsAccumulated(game.getLastWinner());
		assertNotNull(cardList);
		assertEquals(4, cardList.size());
		assertTrue(cardList.contains(AllCards.C2C));
		assertTrue(cardList.contains(AllCards.C4C));
		assertTrue(cardList.contains(AllCards.C7C));
		assertTrue(cardList.contains(AllCards.CJC));
	}
	
	@Test
	public void testCorrectMultiRound() {
		game.newTrick();
		game.addPlay(PLAYER_1, AllCards.CAH);
		game.addPlay(PLAYER_2, AllCards.CKH);
		game.addPlay(PLAYER_3, AllCards.CQH);
		game.addPlay(PLAYER_4, AllCards.C2H);
		game.newTrick();
		game.addPlay(PLAYER_1, AllCards.CTH);
		game.addPlay(PLAYER_2, AllCards.C9H);
		game.addPlay(PLAYER_3, AllCards.C8H);
		game.addPlay(PLAYER_4, AllCards.C7H);
		game.newTrick();
		game.addPlay(PLAYER_1, AllCards.C6H);
		game.addPlay(PLAYER_2, AllCards.C5H);
		game.addPlay(PLAYER_3, AllCards.C4H);
		game.addPlay(PLAYER_4, AllCards.C3H);
		game.newTrick();
		game.addPlay(PLAYER_1, AllCards.CJH);
		game.addPlay(PLAYER_2, AllCards.C2C);
		game.addPlay(PLAYER_3, AllCards.C2D);
		game.addPlay(PLAYER_4, AllCards.C2S);
		game.newTrick();
		game.addPlay(PLAYER_1, AllCards.CQS);
		game.addPlay(PLAYER_2, AllCards.CJS);
		game.addPlay(PLAYER_3, AllCards.CTS);
		game.addPlay(PLAYER_4, AllCards.C9S);
		
		assertEquals(20, game.getCardsAccumulated(PLAYER_1).size());
		assertEquals(26, game.getScore(PLAYER_1));
	}
	
	@Test
	public void testCorrectCardsAccumLoser() {
		buildOneClubTrick();
		// whoever the losers were, they better have none of the cards!
		for (String loser : getLosers()) {
			CardList cardList = game.getCardsAccumulated(loser);
			if (cardList == null) {
				continue; /* ok */
			}
			assertEquals(0, cardList.size());
		}
	}

	@Test
	public void testCorrectScoreAfterOneRound() {
		build4HeartsTrick();
		assertEquals(4, game.getScore(game.getLastWinner()));
		for (String losers : getLosers()) {
			assertEquals(0, game.getScore(losers));
		}
	}

	@Test
	public void testQueenOfSpades() {
		game.newTrick();
		game.addPlay(PLAYER_1, AllCards.CQS);
		game.addPlay(PLAYER_2, AllCards.C2H);
		game.addPlay(PLAYER_3, AllCards.C3H);
		game.addPlay(PLAYER_4, AllCards.C4H);
		assertEquals(16, game.getScore(game.getLastWinner()));
	}

	/**
	 * To make sure points aren't being cleared.
	 */
	@Test
	public void testCorrectScoresThreeRounds() {
		build4HeartsTrick();
		build4HeartsTrick2();
		build4HeartsTrick3();
		for (String loser : getLosers()) {
			assertEquals(0, game.getScore(loser));
		}

		assertEquals(12, game.getScore(game.getLastWinner()));
	}

	@Test
	public void testClear() {
		game.clear(); // sanity check, this should always work (no exception)
		build4HeartsTrick();
		game.clear();
		assertEquals(0, game.tricksCompleted());
	}

	// --------------------------- Immutable State

	@Test
	public void testNoTricksNonNullState() {
		assertNotNull(game.getImmutableState()); // easiest test to pass
	}

	@Test
	public void testNoHeartsInStateWhenEmpty() {
		assertFalse(game.getImmutableState().heartsPlayed());
	}

	@Test
	public void testSameTrick() {
		game.newTrick();
		game.addPlay(PLAYER_3, AllCards.C2S);
		game.addPlay(PLAYER_4, AllCards.C2D);
		game.addPlay(PLAYER_1, AllCards.C3C);
		Trick gameTrick = game.getCurrentTrick();
		Trick stateTrick = game.getImmutableState().getCurrentTrick();
		assertTrue(checkEquals(gameTrick, stateTrick));
	}

	@Test
	public void testStateSuiteLed() {
		game.newTrick();
		game.addPlay(PLAYER_3, AllCards.C2S);
		game.addPlay(PLAYER_1, AllCards.C2D);
		IImmutableGameState state = game.getImmutableState();
		assertEquals(Suit.SPADES, state.getSuitLed());
	}

	@Test
	public void testStateHeartsPlayedFalse() {
		game.newTrick();
		game.addPlay(PLAYER_3, AllCards.C2S);
		game.addPlay(PLAYER_1, AllCards.C2D);
		IImmutableGameState state = game.getImmutableState();
		assertFalse(state.heartsPlayed());
	}

	@Test
	public void testStateHeartsPlayedTrue() {
		game.newTrick();
		game.addPlay(PLAYER_3, AllCards.C2H);
		game.addPlay(PLAYER_1, AllCards.C2D);
		IImmutableGameState state = game.getImmutableState();
		assertTrue(state.heartsPlayed());
	}

	@Test
	public void testStateScoreAccumHeartsWinner() {
		build4HeartsTrick();
		IImmutableGameState state = game.getImmutableState();
		assertEquals(4, state.getScore(game.getLastWinner()));
	}

	@Test
	public void testStateScoreAccumClubWinner() {
		buildOneClubTrick();
		buildOneDiamondTrick();
		IImmutableGameState state = game.getImmutableState();
		assertEquals(0, state.getScore(PLAYER_1));
		assertEquals(0, state.getScore(PLAYER_2));
		assertEquals(0, state.getScore(PLAYER_3));
		assertEquals(0, state.getScore(PLAYER_4));
	}

	@Test
	public void testStateCardsAccumWinner() {
		build4HeartsTrick();
		IImmutableGameState state = game.getImmutableState();
		CardList list = state.getCardsAccumulated(game.getLastWinner());
		assertNotNull(list);
		assertEquals(4, list.size());
		assertTrue(list.contains(AllCards.C7H));
		assertTrue(list.contains(AllCards.CTH));
		assertTrue(list.contains(AllCards.C4H));
		assertTrue(list.contains(AllCards.CJH));
	}

	@Test
	public void testStateCardsAccumLoser() {
		build4HeartsTrick();
		IImmutableGameState state = game.getImmutableState();
		for (String loser : getLosers()) {
			CardList list = state.getCardsAccumulated(loser);
			if (list == null) {
				continue;
			}
			assertEquals(0, list.size());
		}
	}

	@Test
	public void testStateCardsImmutViaState() {
		build4HeartsTrick();
		IImmutableGameState state1 = game.getImmutableState();
		CardList list1 = state1.getCardsAccumulated(PLAYER_1);
		assertNotNull(list1);
		try {
			list1.add(AllCards.CKH);
		} catch (Exception e) {
			// alright. nothing we can do here
			return;
		}
		IImmutableGameState state2 = game.getImmutableState();
		CardList list2 = state2.getCardsAccumulated(PLAYER_1);
		assertFalse(checkEquals(list1, list2));
	}

	@Test
	public void testStateCardsImmutViaGame() {
		build4HeartsTrick();
		IImmutableGameState state1 = game.getImmutableState();
		CardList list1 = state1.getCardsAccumulated(PLAYER_1);
		assertNotNull(list1);
		try {
			list1.add(AllCards.CKH);
		} catch (Exception e) {
			// alright. nothing we can do here
			return;
		}
		CardList list2 = game.getCardsAccumulated(PLAYER_1);
		assertFalse(checkEquals(list1, list2));
	}

	/**
	 * Helper method. PLAYER_4 wins the trick.
	 */
	private void buildOneClubTrick() {
		game.newTrick();
		game.addPlay(PLAYER_1, AllCards.C2C);
		game.addPlay(PLAYER_2, AllCards.C4C);
		game.addPlay(PLAYER_3, AllCards.C7C);
		game.addPlay(PLAYER_4, AllCards.CJC);
	}

	/**
	 * Helper method. PLAYER_2 wins the trick
	 */
	private void buildOneDiamondTrick() {
		game.newTrick();
		game.addPlay(PLAYER_1, AllCards.C2D);
		game.addPlay(PLAYER_2, AllCards.CKD);
		game.addPlay(PLAYER_3, AllCards.C7D);
		game.addPlay(PLAYER_4, AllCards.CJD);
	}

	/**
	 * Helper method. PLAYER_4 wins the trick.
	 */
	private void buildOne3Club1HeartTrick() {
		game.newTrick();
		game.addPlay(PLAYER_1, AllCards.C2C);
		game.addPlay(PLAYER_2, AllCards.C4C);
		game.addPlay(PLAYER_3, AllCards.C7H);
		game.addPlay(PLAYER_4, AllCards.CJC);
	}

	/**
	 * Helper method. PLAYER_1 wins the trick.
	 */
	private void build4HeartsTrick() {
		game.newTrick();
		game.addPlay(PLAYER_1, AllCards.CJH);
		game.addPlay(PLAYER_2, AllCards.C4H);
		game.addPlay(PLAYER_3, AllCards.C7H);
		game.addPlay(PLAYER_4, AllCards.CTH);
	}

	/**
	 * Helper method. PLAYER_1 wins the trick.
	 */
	private void build4HeartsTrick2() {
		game.newTrick();
		game.addPlay(PLAYER_1, AllCards.C6H);
		game.addPlay(PLAYER_2, AllCards.C3H);
		game.addPlay(PLAYER_3, AllCards.C2H);
		game.addPlay(PLAYER_4, AllCards.C5H);
	}

	/**
	 * Helper method. PLAYER_1 wins the trick.
	 */
	private void build4HeartsTrick3() {
		game.newTrick();
		game.addPlay(PLAYER_1, AllCards.CKH);
		game.addPlay(PLAYER_2, AllCards.C9H);
		game.addPlay(PLAYER_3, AllCards.C8H);
		game.addPlay(PLAYER_4, AllCards.CQH);
	}

	private boolean checkEquals(Trick trick1, Trick trick2) {
		if (trick1 == trick2) {
			return true;
		}
		if (trick1.cardsPlayed() != trick2.cardsPlayed()) {
			return false;
		}
		if (trick1.getWinner() == null || trick2.getWinner() == null) {
			if (trick2.getWinner() != trick2.getWinner()) {
				return false;
			}
		}
		if (!trick1.getWinner().equals(trick2.getWinner())) {
			return false;
		}
		return checkEquals(trick1.getCards(), trick2.getCards());
	}

	// weak equality to avoid penalizing sorting confusion
	private boolean checkEquals(CardList cl1, CardList cl2) {
		if (cl1 == cl2) {
			return true;
		}
		if (cl1.size() != cl2.size()) {
			return false;
		}
		boolean b = true;
		for (Card c1 : cl2) {
			if (!cl2.contains(c1)) {
				b = false;
			}
		}

		return b;
	}

	private Set<String> getLosers() {
		HashSet<String> losers = new HashSet<String>();
		losers.add(PLAYER_1);
		losers.add(PLAYER_2);
		losers.add(PLAYER_3);
		losers.add(PLAYER_4);
		losers.remove(game.getLastWinner());
		return losers;
	}
}
