package eggroll.pet;

import eggroll.pet.petevolutionstrategy.EvolutionStage;
import eggroll.pet.petevolutionstrategy.EvolutionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EvolutionStrategyTest {

    Pet animalPet;

    @BeforeEach
    void setUp() {
        animalPet = new Cat();
    }

    @Test
    void testAdultPetsCannotEvolve(){
        EvolutionStrategy adultEvo = animalPet.strategyFactory.newAdultStrategy();
        animalPet.setCurrentStrategy(adultEvo);
        animalPet.setCurrentState(animalPet.getNormalState());

        assertFalse(animalPet.canEvolve());
    }

    @Test
    void testJuvenilePetsCanEvolveWhenHappinessMax(){
        EvolutionStrategy juvenileEvo = animalPet.strategyFactory.newJuvenileStrategy();
        animalPet.setCurrentStrategy(juvenileEvo);
        animalPet.setCurrentState(animalPet.getNormalState());
        animalPet.setStat(7, PetStatType.HAPPINESS);

        assertTrue(animalPet.canEvolve());
    }

    @Test
    void testJuvenilePetsCannotEvolveWhenHappinessNotMax(){
        EvolutionStrategy juvenileEvo = animalPet.strategyFactory.newJuvenileStrategy();
        animalPet.setCurrentStrategy(juvenileEvo);
        animalPet.setCurrentState(animalPet.getNormalState());

        assertFalse(animalPet.canEvolve());
    }

    @Test
    void evolveOnceEggMovesPetToAppropriateStrategy() {
        animalPet.setStat(5, PetStatType.HAPPINESS);
        animalPet.evolve();
        assertEquals(EvolutionStage.JUVENILE, animalPet.getEvolutionStage());
    }

    @Test
    void evolveTwiceEggMovesPetToAppropriateStrategy() {
        animalPet.setStat(5, PetStatType.HAPPINESS);
        animalPet.evolve();

        animalPet.setStat(7, PetStatType.HAPPINESS);
        animalPet.evolve();
        assertEquals(EvolutionStage.ADULT, animalPet.getEvolutionStage());
    }
}
