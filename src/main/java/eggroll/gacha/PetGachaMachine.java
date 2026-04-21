package eggroll.gacha;

import eggroll.gamepersistence.EggRoll;
import eggroll.pet.Pet;
import eggroll.pet.petfactory.PetFactory;

import java.util.List;
import java.util.Random;

public class PetGachaMachine extends GachaMachine<Pet> {
    private final List<PetFactory> factories;
    private final Random random = new Random();

    public PetGachaMachine(List<PetFactory> factories, EggRoll state) {
        super(state);
        this.factories = factories;
    }

    @Override
    protected Pet executePull(GachaRarity rarity) {
        PetFactory factory = factories.get(random.nextInt(factories.size()));
        Pet pet = factory.createPet(rarity);
        gameState.ownedPets.add(pet);
        return pet;
    }
}