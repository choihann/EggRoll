package eggroll.pet;

import eggroll.pet.petstate.PetState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void testQueueingStatesActuallyQueuesStatesToThePet(){
        animalPet.pushToQueuedStates(animalPet.getDirtyState());
        animalPet.pushToQueuedStates(animalPet.getTiredState());
        animalPet.pushToQueuedStates(animalPet.getSadState());
        animalPet.pushToQueuedStates(animalPet.getHungryState());
        animalPet.pushToQueuedStates(animalPet.getNormalState());

        Queue<PetState> expectedStates = new LinkedList<>();
        expectedStates.add(animalPet.getHungryState());
        expectedStates.add(animalPet.getDirtyState());
        expectedStates.add(animalPet.getTiredState());
        expectedStates.add(animalPet.getSadState());
        expectedStates.add(animalPet.getNormalState());

        assertTrue(animalPet.getQueuedStates().containsAll(expectedStates));
    }

    @Test
    void testPollingQueueStatesActuallyPolls(){
        animalPet.pushToQueuedStates(animalPet.getNormalState());
        animalPet.setCurrentState(animalPet.popOffQueuedState());

        assertEquals(animalPet.getNormalState(), animalPet.getCurrentState());
    }

    @Test
    void testCheckIfNeedsPenaltyReturnsProperly(){
        assertEquals(false, animalPet.canEvolve());

        animalPet.setCurrentState(animalPet.getNormalState());
        animalPet.setStat(5, PetStatType.HAPPINESS);
        animalPet.setIsEgg(false);

        assertEquals(true, animalPet.canEvolve());

    }
}
