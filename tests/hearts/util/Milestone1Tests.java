package hearts.util;

import hearts.model.*;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestCard.class,
        TestDeck.class,
        TestCardList.class,
        TestGame.class,
        TestImmutableGameWrapper.class,
        TestPlay.class,
        TestPlayer.class,
        TestTrick.class
})
public class Milestone1Tests {}

