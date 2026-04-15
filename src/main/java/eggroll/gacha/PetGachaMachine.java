package eggroll.gacha;

import eggroll.gamepersistence.GameState;
import eggroll.pet.Pet;
import eggroll.pet.petfactory.PetFactory;

import java.util.ArrayList;
import java.util.List;

public class PetGachaMachine extends GachaMachine {

    private final PetFactory petFactory;

    public PetGachaMachine(PetFactory petFactory, GameState state) {
        super(state, "Egg Gacha", "Hatch mystery pets!");
        this.petFactory = petFactory;
    }

    // TODO: Magic number alert, we should probably talk about gacha rates
    @Override
    public GachaRarity determineGachaRarity() {
        int roll = random.nextInt(100);
        if (roll < 50) return GachaRarity.Common;
        if (roll < 85) return GachaRarity.Rare;
        return GachaRarity.Epic;
    }

    public Pet pullOne() {
        if (!canAffordOnePull()) return null;

        chargeOnePull();

        GachaRarity rarity = determineGachaRarity();
        Pet pet = petFactory.createPet(rarity);

        gameState.ownedPets.add(pet);
        return pet;
    }

    public List<Pet> pullTen() {
        if (!canAffordTenPull()) return List.of();

        chargeTenPull();

        List<Pet> results = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            GachaRarity rarity = determineGachaRarity();
            Pet pet = petFactory.createPet(rarity);

            gameState.ownedPets.add(pet);
            results.add(pet);
        }

        return results;
    }
}