package eggroll.gacha;

import eggroll.gamepersistence.EggRoll;
import eggroll.pet.petfactory.CatFactory;
import eggroll.pet.petfactory.DogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GachaMachineTest {

    private EggRoll gameState;
    private GachaMachine gachaMachine;

    @BeforeEach
    void setUp() {
        gameState = EggRoll.newGame();
        gachaMachine = new PetGachaMachine(List.of(new CatFactory(), new DogFactory()), gameState);
    }

    @Test
    void pullOneDeductsCorrectCost() {
        int currencyBeforePull = gameState.currency;
        gachaMachine.pullOne();
        assertEquals(currencyBeforePull - gachaMachine.getCostOnePull(), gameState.currency);
    }

    @Test
    void pullTenDeductsCorrectCost() {
        int currencyBeforePull = gameState.currency;
        gachaMachine.pullTen();
        assertEquals(currencyBeforePull - gachaMachine.getCostTenPull(), gameState.currency);
    }

    @Test
    void pullOneReturnsNullWhenCannotAfford() {
        gameState.currency = gachaMachine.getCostOnePull() - 1;
        assertNull(gachaMachine.pullOne());
    }

    @Test
    void pullTenReturnsEmptyWhenCannotAfford() {
        gameState.currency = gachaMachine.getCostTenPull() - 1;
        assertTrue(gachaMachine.pullTen().isEmpty());
    }

    @Test
    void pullOneDoesNotDeductWhenCannotAfford() {
        gameState.currency = gachaMachine.getCostOnePull() - 1;
        int currencyBefore = gameState.currency;
        gachaMachine.pullOne();
        assertEquals(currencyBefore, gameState.currency);
    }

    @Test
    void pullTenDoesNotDeductWhenCannotAfford() {
        gameState.currency = gachaMachine.getCostTenPull() - 1;
        int currencyBefore = gameState.currency;
        gachaMachine.pullTen();
        assertEquals(currencyBefore, gameState.currency);
    }

    @Test
    void canAffordOnePullReturnsTrueWhenExactAmount() {
        gameState.currency = gachaMachine.getCostOnePull();
        assertTrue(gachaMachine.canAffordOnePull());
    }

    @Test
    void canAffordOnePullReturnsFalseWhenOneCoinShort() {
        gameState.currency = gachaMachine.getCostOnePull() - 1;
        assertFalse(gachaMachine.canAffordOnePull());
    }

    @Test
    void canAffordTenPullReturnsTrueWhenExactAmount() {
        gameState.currency = gachaMachine.getCostTenPull();
        assertTrue(gachaMachine.canAffordTenPull());
    }

    @Test
    void canAffordTenPullReturnsFalseWhenOneCoinShort() {
        gameState.currency = gachaMachine.getCostTenPull() - 1;
        assertFalse(gachaMachine.canAffordTenPull());
    }

    @Test
    void determineGachaRarityOnlyReturnsValidRarities() {
        for (int i = 0; i < 1000; i++) {
            GachaRarity rarity = gachaMachine.determineGachaRarity();
            assertNotNull(rarity);
            assertTrue(rarity == GachaRarity.Common || rarity == GachaRarity.Rare || rarity == GachaRarity.Epic);
        }
    }
}