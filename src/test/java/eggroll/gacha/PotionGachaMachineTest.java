package eggroll.gacha;

import eggroll.gamepersistence.EggRoll;
import eggroll.potion.Potion;
import eggroll.potion.PotionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PotionGachaMachineTest {
    private EggRoll gameState;
    private GachaMachine gachaMachine;

    @BeforeEach
    void setUpUsingPotionFactory() {
        gameState = EggRoll.newGame();
        gachaMachine = new PotionGachaMachine(new PotionFactory(), gameState);
    }

    @Test
    void pullOneReturnsPotion() {
        Object result = gachaMachine.pullOne();
        assertInstanceOf(Potion.class, result);
    }

    @Test
    void pullOneReturnedPotionIsInInventory() {
        Potion potion = (Potion) gachaMachine.pullOne();
        assertTrue(gameState.potionInventory.contains(potion));
    }

    @Test
    void pullTenReturnsAllPotions() {
        gameState.currency = 1000;
        List<?> results = gachaMachine.pullTen();
        assertTrue(results.stream().allMatch(r -> r instanceof Potion));
    }

    @Test
    void pullTenReturnedPotionsAreAllInInventory() {
        gameState.currency = 1000;
        List<?> results = gachaMachine.pullTen();
        assertTrue(results.stream().allMatch(r -> gameState.potionInventory.contains(r)));
    }
}
