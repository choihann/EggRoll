package eggroll;

import eggroll.gacha.StandardGachaMachine;
import eggroll.gamepersistence.GameState;
import eggroll.pet.Pet;
import eggroll.pet.PetRarity;
import eggroll.pet.petfactory.CatFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GachaTest {
    private GameState gameState;
    private StandardGachaMachine gachaMachine;

    @BeforeEach
    void setUp() { // TODO: Change CatFactory to other factories
        gameState = GameState.newGame();
        gachaMachine = new StandardGachaMachine(new CatFactory(), gameState);
    }

    @Test
    void pullOneDeductsCorrectCost() {
        int currencyBeforePull = gameState.currency;
        gachaMachine.pullOne();
        assertEquals(currencyBeforePull - gachaMachine.getCostOnePull(), gameState.currency);
    }

    @Test
    void pullOneAddsOnePetToOwnedPets() {
        int numPetsBefore = gameState.ownedPets.size();
        gachaMachine.pullOne();
        assertEquals(numPetsBefore + 1, gameState.ownedPets.size());
    }

    @Test
    void pullOneReturnsNullWhenHasNoMoney() {
        gameState.currency = gachaMachine.getCostOnePull() - 1;
        Pet result = gachaMachine.pullOne();
        assertNull(result);
    }

    @Test
    void pullTenAddsExactlyTenPets() {
        gameState.currency = 1000;
        int petsBefore = gameState.ownedPets.size();
        gachaMachine.pullTen();
        assertEquals(petsBefore + 10, gameState.ownedPets.size());
    }

    @Test
    void doesNotDeductCurrencyWhenCannotAfford() {
        gameState.currency = 0;
        int petsBefore = gameState.ownedPets.size();
        gachaMachine.pullTen();
        assertEquals(0, gameState.currency);
        assertEquals(petsBefore, gameState.ownedPets.size());
    }

    @Test
    void onlyValidRarityReturned() {
        for (int i = 0; i < 1000; i++) {
            PetRarity rarity = gachaMachine.determinePetRarity();
            assertNotNull(rarity);
            assertTrue(rarity == PetRarity.Common || rarity == PetRarity.Rare || rarity == PetRarity.Epic);
        }
    }
}
