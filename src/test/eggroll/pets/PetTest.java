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
    void isEgg() {
        assertTrue(animalPet.isEgg());
    }

    @Test
    void spawnsInUnbornState() {
        assertEquals(animalPet.unbornState, animalPet.getCurrentState);
    }

}
