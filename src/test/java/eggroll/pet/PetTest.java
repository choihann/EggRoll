package eggroll.pet;

import eggroll.pet.petevolutionstrategy.EvolutionStrategy;
import eggroll.pet.petstate.IPetState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class PetTest {
    Pet animalPet;

    @BeforeEach
    void setUp() {
        animalPet = new Cat();
    }

    @Test
    void testIsEgg() {
        assertTrue(animalPet.isEgg);
    }

    @Test
    void testSpawnsInUnbornState() {
        assertEquals(animalPet.getUnbornState(), animalPet.getCurrentState());
    }

    @Test
    void testIncreaseStatActuallyIncreasesStat(){
        int initialHappiness = animalPet.getHappinessStat();
        animalPet.increaseStat(1, PetStatType.HAPPINESS);
        assertEquals(initialHappiness + 1, animalPet.getHappinessStat());
    }

    @Test
    void testDecreaseStatActuallyDecreasesStat(){
        int initialHappiness = animalPet.getHappinessStat();
        animalPet.decreaseStat(1, PetStatType.HAPPINESS);
        assertEquals(initialHappiness - 1, animalPet.getHappinessStat());
    }

    @Test
    void testIsStatMax(){
        animalPet.setStat(5, PetStatType.HAPPINESS);
        assertTrue(animalPet.isStatMax());
    }


    @Test
    void testCheckIfNeedsPenaltyReturnsProperly(){
        assertEquals(false, animalPet.canEvolve());

        animalPet.setCurrentState(animalPet.getNormalState());
        animalPet.setStat(5, PetStatType.HAPPINESS);
        animalPet.setIsEgg(false);

        assertEquals(true, animalPet.canEvolve());

    }

    @Test
    void testPetInHungryStateWontBathe(){
        EvolutionStrategy juvenileEvo = animalPet.strategyFactory.newJuvenileStrategy();
        animalPet.setCurrentStrategy(juvenileEvo);
        animalPet.setCurrentState(animalPet.getHungryState());
        int hygieneBefore = animalPet.getHygieneStat();

        animalPet.bathe();
        assertTrue(animalPet.getHygieneStat() == hygieneBefore);
    }

    @Test
    void testPetInHungryStateWontHaveFullnessAsAPenalizableStat(){
        EvolutionStrategy juvenileEvo = animalPet.strategyFactory.newJuvenileStrategy();
        animalPet.setCurrentStrategy(juvenileEvo);
        animalPet.setCurrentState(animalPet.getHungryState());
        Set<PetStatType> thePenalizableStats = animalPet.getPenalizableStats();

        assertFalse(thePenalizableStats.contains(PetStatType.FULLNESS));
    }
}
