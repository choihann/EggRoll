import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import eggroll.pet.Pet;

public class PetTest {
    Pet animalPet;

    @BeforeEach
    void setUp() {
        animalPet = new Pet("Animal Pet");
    }

    @Test
    void testIsEgg() {
        assertTrue(animalPet.isEgg());
    }

    @Test
    void testSpawnsInUnbornState() {
        assertEquals(animalPet.unbornState, animalPet.getCurrentState);
    }

    @Test
    void testIncreaseStatActuallyIncreasesStat(){
        int initialHappiness = animalPet.getHappinessStat();
        animalPet.increaseStat(1,"Happiness");
        assertEquals(initialHappiness + 1, animalPet.getHappinessStat());
    }

    @Test
    void testDecreaseStatActuallyDecreasesStat(){
        int initialHappiness = animalPet.getHappinessStat();
        animalPet.decreaseStat(1, "Happiness");
        assertEquals(initialHappiness - 1, animalPet.getHappinessStat());
    }

    @Test
    void testIsStatMax(){
        animalPet.setStat(5, "happiness");
        assertTrue(animalPet.isStatMax);
    }

}
