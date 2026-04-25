package eggroll.pet;

import eggroll.command.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {

    Pet animalPet;

    @BeforeEach
    void setUp() {
        animalPet = new Cat();
    }

    @Test
    void testNapCommandIncreasesEnergy() {
        animalPet.setIsEgg(false);
        animalPet.setCurrentState(animalPet.getNormalState());

        int initialEnergy = animalPet.getEnergyStat();

        Command napCommand = new NapCommand(animalPet);
        napCommand.execute();

        assertEquals(initialEnergy + 1, animalPet.getEnergyStat());
    }

    @Test
    void testPlayCommandIncreasesHappiness() {
        animalPet.setIsEgg(false);
        animalPet.setCurrentState(animalPet.getNormalState());

        int initialHappiness = animalPet.getHappinessStat();

        Command playCommand = new PlayCommand(animalPet);
        playCommand.execute();

        assertEquals(initialHappiness + 1, animalPet.getHappinessStat());
    }

    @Test
    void testBatheCommandIncreasesHygiene() {
        animalPet.setIsEgg(false);
        animalPet.setCurrentState(animalPet.getNormalState());

        int initialHygiene = animalPet.getHygieneStat();

        Command batheCommand = new BatheCommand(animalPet);
        batheCommand.execute();

        assertEquals(initialHygiene + 1, animalPet.getHygieneStat());
    }

    @Test
    void testFeedCommandIncreasesFullness() {
        animalPet.setIsEgg(false);
        animalPet.setCurrentState(animalPet.getNormalState());

        int initialFullness = animalPet.getFullnessStat();

        Command feedCommand = new FeedCommand(animalPet);
        feedCommand.execute();

        assertEquals(initialFullness + 1, animalPet.getFullnessStat());
    }
}
