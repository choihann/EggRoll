package eggroll.pet;

import eggroll.pet.petevolutionstrategy.EvolutionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PetStateTest {
    Pet animalPet;

    @BeforeEach
    void setUp() {
        animalPet = new Cat();
    }

    @Test
    void testSpawnsInUnbornState() {
        assertEquals(animalPet.getUnbornState(), animalPet.getCurrentState());
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
        assertTrue(animalPet.currentState.canEat());
        assertFalse(animalPet.currentState.canBathe());
        assertFalse(animalPet.currentState.canPlay());
        assertFalse(animalPet.currentState.canNap());
    }

    @Test
    void testPetInDirtyStateReturnsProperCommandPermissions(){
        EvolutionStrategy juvenileEvo = animalPet.strategyFactory.newJuvenileStrategy();
        animalPet.setCurrentStrategy(juvenileEvo);
        animalPet.setCurrentState(animalPet.getDirtyState());
        Set<PetStatType> thePenalizableStats = animalPet.getPenalizableStats();

        assertFalse(thePenalizableStats.contains(PetStatType.HYGIENE));
        assertTrue(animalPet.currentState.canBathe());
        assertFalse(animalPet.currentState.canEat());
        assertFalse(animalPet.currentState.canPlay());
        assertFalse(animalPet.currentState.canNap());
    }

    @Test
    void testPetInNormalStateReturnsProperCommandPermissions(){
        EvolutionStrategy juvenileEvo = animalPet.strategyFactory.newJuvenileStrategy();
        animalPet.setCurrentStrategy(juvenileEvo);
        animalPet.setCurrentState(animalPet.getNormalState());
        Set<PetStatType> thePenalizableStats = animalPet.getPenalizableStats();

        assertTrue(thePenalizableStats.contains(PetStatType.FULLNESS));
        assertTrue(thePenalizableStats.contains(PetStatType.ENERGY));
        assertTrue(thePenalizableStats.contains(PetStatType.HYGIENE));
        assertTrue(thePenalizableStats.contains(PetStatType.HAPPINESS));

        assertTrue(animalPet.currentState.canBathe());
        assertTrue(animalPet.currentState.canEat());
        assertTrue(animalPet.currentState.canPlay());
        assertTrue(animalPet.currentState.canNap());
    }

    @Test
    void testPetInTiredStateReturnsProperCommandPermissions(){
        EvolutionStrategy juvenileEvo = animalPet.strategyFactory.newJuvenileStrategy();
        animalPet.setCurrentStrategy(juvenileEvo);
        animalPet.setCurrentState(animalPet.getTiredState());
        Set<PetStatType> thePenalizableStats = animalPet.getPenalizableStats();

        assertFalse(thePenalizableStats.contains(PetStatType.ENERGY));
        assertTrue(animalPet.currentState.canNap());
        assertFalse(animalPet.currentState.canEat());
        assertFalse(animalPet.currentState.canPlay());
        assertFalse(animalPet.currentState.canBathe());
    }

    @Test
    void testPetInUnbornStateReturnsProperCommandPermissions(){
        EvolutionStrategy unbornEvo = animalPet.strategyFactory.newUnbornStrategy();
        animalPet.setCurrentStrategy(unbornEvo);
        animalPet.setCurrentState(animalPet.getUnbornState());
        Set<PetStatType> thePenalizableStats = animalPet.getPenalizableStats();

        assertFalse(thePenalizableStats.contains(PetStatType.FULLNESS));
        assertFalse(thePenalizableStats.contains(PetStatType.ENERGY));
        assertFalse(thePenalizableStats.contains(PetStatType.HYGIENE));
        assertFalse(thePenalizableStats.contains(PetStatType.HAPPINESS));

        assertFalse(animalPet.currentState.canBathe());
        assertFalse(animalPet.currentState.canEat());
        assertTrue(animalPet.currentState.canPlay());
        assertFalse(animalPet.currentState.canNap());
    }
}
