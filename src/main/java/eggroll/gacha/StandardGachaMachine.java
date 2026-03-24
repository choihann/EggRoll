package eggroll.gacha;

import eggroll.gamepersistence.GameState;
import eggroll.pet.PetRarity;
import eggroll.pet.petfactory.PetFactory;

import java.util.Random;

public class StandardGachaMachine extends GachaMachine {

    private final Random random = new Random();

    public StandardGachaMachine(PetFactory factory, GameState gameState) {
        super(factory, gameState, "The Egg Gacha", "Roll for a mystery egg!");
    }

    @Override
    public PetRarity determinePetRarity() {
        int roll = random.nextInt(100); // 0–99

        if (roll < 50) return PetRarity.Common; // 50%
        if (roll < 85) return PetRarity.Rare; // 35%
        return PetRarity.Epic; // 15%
    }
}