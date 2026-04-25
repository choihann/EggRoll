package eggroll.pet;

import eggroll.command.*;
import eggroll.gacha.GachaRarity;
import eggroll.pet.petevolutionstrategy.EvolutionStage;
import eggroll.pet.petevolutionstrategy.EvolutionStrategy;
import eggroll.pet.petevolutionstrategy.JuvenileEvolutionStrategy;
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
    void testOnConstructionPetsAreEggs() {
        assertTrue(animalPet.isEgg);
    }

    @Test
    void testIncreaseStatActuallyIncreasesStat(){
        int initialHappiness = animalPet.getHappinessStat();
        animalPet.increaseStat(1, PetStatType.HAPPINESS);
        assertEquals(initialHappiness + 1, animalPet.getHappinessStat());
    }

    @Test
    void testDecreaseStatActuallyDecreasesHappinessStat(){
        int initialHappiness = animalPet.getHappinessStat();
        animalPet.decreaseStat(1, PetStatType.HAPPINESS);
        assertEquals(initialHappiness - 1, animalPet.getHappinessStat());
    }

    @Test
    void testDecreaseStatActuallyDecreasesEnergyStat(){
        int initialEnergy = animalPet.getEnergyStat();
        animalPet.decreaseStat(1, PetStatType.ENERGY);
        assertEquals(initialEnergy - 1, animalPet.getEnergyStat());
    }

    @Test
    void testDecreaseStatActuallyDecreasesHygieneStat(){
        int initialHygiene = animalPet.getEnergyStat();
        animalPet.decreaseStat(1, PetStatType.HYGIENE);
        assertEquals(initialHygiene - 1, animalPet.getHygieneStat());
    }

    @Test
    void testDecreasesStatActuallyDecreasesFullnessStat(){
        int initialFullness = animalPet.getEnergyStat();
        animalPet.decreaseStat(1, PetStatType.FULLNESS);
        assertEquals(initialFullness - 1, animalPet.getFullnessStat());
    }


    @Test
    void testIsStatMax(){
        animalPet.setStat(5, PetStatType.HAPPINESS);
        assertTrue(animalPet.isStatMax());
    }


    @Test
    void testLowerRandomStatDoesNothingWithEmptyPenalizableStats() {
        int initialHappiness = animalPet.getHappinessStat();
        int initialEnergy = animalPet.getEnergyStat();
        int initialFullness = animalPet.getFullnessStat();
        int initialHygiene = animalPet.getHygieneStat();

        animalPet.lowerRandomStat(1, Set.of());

        assertEquals(initialHappiness, animalPet.getHappinessStat());
        assertEquals(initialEnergy, animalPet.getEnergyStat());
        assertEquals(initialFullness, animalPet.getFullnessStat());
        assertEquals(initialHygiene, animalPet.getHygieneStat());
    }

    @Test
    void testLowerRandomStatCanLowerHappiness() {
        int initialHappiness = animalPet.getHappinessStat();

        animalPet.lowerRandomStat(1, Set.of(PetStatType.HAPPINESS));

        assertEquals(initialHappiness - 1, animalPet.getHappinessStat());
    }

    @Test
    void testLowerRandomStatCanLowerEnergy() {
        int initialEnergy = animalPet.getEnergyStat();

        animalPet.lowerRandomStat(1, Set.of(PetStatType.ENERGY));

        assertEquals(initialEnergy - 1, animalPet.getEnergyStat());
    }

    @Test
    void testLowerRandomStatCanLowerFullness() {
        int initialFullness = animalPet.getFullnessStat();

        animalPet.lowerRandomStat(1, Set.of(PetStatType.FULLNESS));

        assertEquals(initialFullness - 1, animalPet.getFullnessStat());
    }

    @Test
    void testLowerRandomStatCanLowerHygiene() {
        int initialHygiene = animalPet.getHygieneStat();

        animalPet.lowerRandomStat(1, Set.of(PetStatType.HYGIENE));

        assertEquals(initialHygiene - 1, animalPet.getHygieneStat());
    }

    @Test
    void testLowerRandomStatOnlyLowersOneAllowedStat() {
        int initialHappiness = animalPet.getHappinessStat();
        int initialEnergy = animalPet.getEnergyStat();
        int initialFullness = animalPet.getFullnessStat();
        int initialHygiene = animalPet.getHygieneStat();

        Set<PetStatType> penalizableStats = Set.of(
                PetStatType.HAPPINESS,
                PetStatType.ENERGY
        );

        animalPet.lowerRandomStat(1, penalizableStats);

        int happinessDifference = initialHappiness - animalPet.getHappinessStat();
        int energyDifference = initialEnergy - animalPet.getEnergyStat();
        int fullnessDifference = initialFullness - animalPet.getFullnessStat();
        int hygieneDifference = initialHygiene - animalPet.getHygieneStat();

        assertEquals(1, happinessDifference + energyDifference);
        assertEquals(0, fullnessDifference);
        assertEquals(0, hygieneDifference);
    }

    @Test
    void testLowerRandomStatDoesNotGoBelowZero() {
        animalPet.setStat(0, PetStatType.HAPPINESS);

        animalPet.lowerRandomStat(1, Set.of(PetStatType.HAPPINESS));

        assertEquals(0, animalPet.getHappinessStat());
    }

    @Test
    void testGetSpeciesReturnsPetSpecies() {
        assertEquals("Cat", animalPet.getSpecies());
    }

    @Test
    void testGetPetStateReturnsCurrentState() {
        assertEquals(animalPet.getCurrentState(), animalPet.getPetState());
    }

    @Test
    void testGetIdReturnsNonNullId() {
        assertNotNull(animalPet.getId());
    }

    @Test
    void testGetIdReturnsNonEmptyId() {
        assertFalse(animalPet.getId().isEmpty());
    }

    @Test
    void testGetIdReturnsSameIdEachTime() {
        String firstId = animalPet.getId();
        String secondId = animalPet.getId();

        assertEquals(firstId, secondId);
    }

    @Test
    void testDetermineNextStateReturnsUnbornStateWhenPetIsStillEgg() {
        EvolutionStrategy juvenileEvo = animalPet.strategyFactory.newJuvenileStrategy();
        animalPet.setCurrentStrategy(juvenileEvo);
        animalPet.setCurrentState(animalPet.getNormalState());
        animalPet.setStat(0, PetStatType.HAPPINESS);

        assertEquals(animalPet.getUnbornState(), animalPet.determineNextState());
    }

    @Test
    void testDetermineNextStateReturnsHungryStateWhenFullnessIsBelowMinimum() {
        EvolutionStrategy juvenileEvo = animalPet.strategyFactory.newJuvenileStrategy();
        animalPet.setCurrentStrategy(juvenileEvo);
        animalPet.setCurrentState(animalPet.getNormalState());
        animalPet.setStat(1, PetStatType.FULLNESS);
        animalPet.setIsEgg(false);

        assertEquals(animalPet.getHungryState(), animalPet.determineNextState());
    }

    @Test
    void testDetermineNextStateReturnsTiredStateWhenEnergyIsBelowMinimum() {
        EvolutionStrategy juvenileEvo = animalPet.strategyFactory.newJuvenileStrategy();
        animalPet.setCurrentStrategy(juvenileEvo);
        animalPet.setCurrentState(animalPet.getNormalState());
        animalPet.setIsEgg(false);

        animalPet.setStat(1, PetStatType.ENERGY);

        assertEquals(animalPet.getTiredState(), animalPet.determineNextState());
    }

    @Test
    void testDetermineNextStateReturnsDirtyStateWhenHygieneIsBelowMinimum() {
        EvolutionStrategy juvenileEvo = animalPet.strategyFactory.newJuvenileStrategy();
        animalPet.setCurrentStrategy(juvenileEvo);
        animalPet.setCurrentState(animalPet.getNormalState());
        animalPet.setIsEgg(false);

        animalPet.setStat(1, PetStatType.HYGIENE);

        assertEquals(animalPet.getDirtyState(), animalPet.determineNextState());
    }

    @Test
    void testDetermineNextStateReturnsNormalStateWhenNoStatsAreBelowMinimum() {
        EvolutionStrategy juvenileEvo = animalPet.strategyFactory.newJuvenileStrategy();
        animalPet.setCurrentStrategy(juvenileEvo);
        animalPet.setCurrentState(animalPet.getNormalState());
        animalPet.setIsEgg(false);

        animalPet.setStat(3, PetStatType.FULLNESS);
        animalPet.setStat(3, PetStatType.ENERGY);
        animalPet.setStat(3, PetStatType.HYGIENE);

        assertEquals(animalPet.getNormalState(), animalPet.determineNextState());
    }

    @Test
    void testDetermineNextStateReturnsHungryStateWhenMultipleStatsAreBelowMinimum() {
        EvolutionStrategy juvenileEvo = animalPet.strategyFactory.newJuvenileStrategy();
        animalPet.setCurrentStrategy(juvenileEvo);
        animalPet.setCurrentState(animalPet.getNormalState());
        animalPet.setIsEgg(false);

        animalPet.setStat(1, PetStatType.FULLNESS);
        animalPet.setStat(1, PetStatType.ENERGY);
        animalPet.setStat(1, PetStatType.HYGIENE);

        assertEquals(animalPet.getHungryState(), animalPet.determineNextState());
    }
    @Test
    void testDetermineNextStateReturnsTiredStateWhenMultipleStatsAreBelowMinimum() {
        EvolutionStrategy juvenileEvo = animalPet.strategyFactory.newJuvenileStrategy();
        animalPet.setCurrentStrategy(juvenileEvo);
        animalPet.setCurrentState(animalPet.getNormalState());
        animalPet.setIsEgg(false);

        animalPet.setStat(3, PetStatType.FULLNESS);
        animalPet.setStat(1, PetStatType.ENERGY);
        animalPet.setStat(1, PetStatType.HYGIENE);

        assertEquals(animalPet.getTiredState(), animalPet.determineNextState());
    }

}


