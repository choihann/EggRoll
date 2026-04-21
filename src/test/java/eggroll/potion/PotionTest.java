package eggroll.potion;

import eggroll.gacha.GachaRarity;
import eggroll.pet.Cat;
import eggroll.pet.Pet;
import eggroll.pet.PetStatType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PotionTest {

    private Pet pet;

    @BeforeEach
    void setUp() {
        pet = new Cat();
    }

    private Potion makePotion(PetStatType stat, int potency) {
        return new Potion("potion-001", "Test Potion", "🧪", "A test potion", GachaRarity.Common, stat, potency);
    }

    @Test
    void testGetId() {
        assertEquals("potion-001", makePotion(PetStatType.HAPPINESS, 1).getId());
    }

    @Test
    void testGetPotionName() {
        assertEquals("Test Potion", makePotion(PetStatType.HAPPINESS, 1).getPotionName());
    }

    @Test
    void testGetPotionImage() {
        assertEquals("🧪", makePotion(PetStatType.HAPPINESS, 1).getPotionImage());
    }

    @Test
    void testGetDescription() {
        assertEquals("A test potion", makePotion(PetStatType.HAPPINESS, 1).getDescription());
    }

    @Test
    void testGetRarity() {
        assertEquals(GachaRarity.Common, makePotion(PetStatType.HAPPINESS, 1).getRarity());
    }

    @Test
    void testDrinkIncreasesHappiness() {
        int before = pet.getHappinessStat();
        makePotion(PetStatType.HAPPINESS, 1).drink(pet);
        assertTrue(pet.getHappinessStat() > before);
    }

    @Test
    void testDrinkIncreasesFullness() {
        int before = pet.getFullnessStat();
        makePotion(PetStatType.FULLNESS, 1).drink(pet);
        assertTrue(pet.getFullnessStat() > before);
    }

    @Test
    void testDrinkIncreasesEnergy() {
        int before = pet.getEnergyStat();
        makePotion(PetStatType.ENERGY, 1).drink(pet);
        assertTrue(pet.getEnergyStat() > before);
    }

    @Test
    void testDrinkIncreasesHygiene() {
        int before = pet.getHygieneStat();
        makePotion(PetStatType.HYGIENE, 1).drink(pet);
        assertTrue(pet.getHygieneStat() > before);
    }

    @Test
    void testDrinkDoesNotExceedMaxStat() {
        makePotion(PetStatType.HAPPINESS, Integer.MAX_VALUE).drink(pet);
        assertTrue(pet.getHappinessStat() <= pet.getMaxStat());
    }

    @Test
    void testDrinkOnlyAffectsTargetStat() {
        int fullnessBefore = pet.getFullnessStat();
        int energyBefore = pet.getEnergyStat();
        int hygieneBefore = pet.getHygieneStat();

        makePotion(PetStatType.HAPPINESS, 1).drink(pet);

        assertEquals(fullnessBefore, pet.getFullnessStat());
        assertEquals(energyBefore, pet.getEnergyStat());
        assertEquals(hygieneBefore, pet.getHygieneStat());
    }
}