package eggroll.gacha;

import eggroll.gamepersistence.EggRoll;
import eggroll.pet.Pet;
import eggroll.pet.petfactory.PetFactory;

public class PetGachaMachine extends GachaMachine<Pet> {

    private final PetFactory petFactory;

    public PetGachaMachine(PetFactory petFactory, EggRoll state) {
        super(state);
        this.petFactory = petFactory;
    }

    @Override
    protected Pet executePull(GachaRarity rarity) {
        Pet pet = petFactory.createPet(rarity);
        gameState.ownedPets.add(pet);
        return pet;
    }
}