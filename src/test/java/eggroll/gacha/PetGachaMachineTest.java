package eggroll.gacha;

import eggroll.gamepersistence.EggRoll;
import eggroll.pet.Pet;
import eggroll.pet.petfactory.CatFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PetGachaMachineTest {

    private EggRoll gameState;
    private GachaMachine gachaMachine;

    @BeforeEach
    void setUp() {
        gameState = EggRoll.newGame();
        gachaMachine = new PetGachaMachine(new CatFactory(), gameState);
    }

    @Test
    void pullOneReturnsPet() {
        assertInstanceOf(Pet.class, gachaMachine.pullOne());
    }

    @Test
    void pullOneAddsOnePetToOwnedPets() {
        int petsBefore = gameState.ownedPets.size();
        gachaMachine.pullOne();
        assertEquals(petsBefore + 1, gameState.ownedPets.size());
    }

    @Test
    void pullOneReturnedPetIsInOwnedPets() {
        Pet pet = (Pet) gachaMachine.pullOne();
        assertTrue(gameState.ownedPets.contains(pet));
    }

    @Test
    void pullOneDoesNotAddPetWhenCannotAfford() {
        gameState.currency = gachaMachine.getCostOnePull() - 1;
        int petsBefore = gameState.ownedPets.size();
        gachaMachine.pullOne();
        assertEquals(petsBefore, gameState.ownedPets.size());
    }

    @Test
    void pullTenReturnsExactlyTenItems() {
        List<?> results = gachaMachine.pullTen();
        assertEquals(10, results.size());
    }

    @Test
    void pullTenReturnsAllPets() {
        List<?> results = gachaMachine.pullTen();
        assertTrue(results.stream().allMatch(r -> r instanceof Pet));
    }

    @Test
    void pullTenAddsExactlyTenPetsToOwnedPets() {
        int petsBefore = gameState.ownedPets.size();
        gachaMachine.pullTen();
        assertEquals(petsBefore + 10, gameState.ownedPets.size());
    }

    @Test
    void pullTenReturnedPetsAreAllInOwnedPets() {
        List<?> results = gachaMachine.pullTen();
        assertTrue(results.stream().allMatch(r -> gameState.ownedPets.contains(r)));
    }

    @Test
    void pullTenDoesNotAddPetsWhenCannotAfford() {
        gameState.currency = gachaMachine.getCostTenPull() - 1;
        int petsBefore = gameState.ownedPets.size();
        gachaMachine.pullTen();
        assertEquals(petsBefore, gameState.ownedPets.size());
    }
}