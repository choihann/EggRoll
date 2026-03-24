package eggroll.gacha;

import eggroll.gamepersistence.GameState;
import eggroll.pet.Pet;
import eggroll.pet.PetRarity;
import eggroll.pet.petfactory.PetFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class GachaMachine {
    public static final int COST_ONE_PULL = 100;
    public static final int COST_TEN_PULL = 950;
    private final PetFactory petFactory;
    private final GameState gameState;
    private String description;
    private String title;

    public int getCostOnePull() {
        return COST_ONE_PULL;
    }

    public int getCostTenPull() {
        return COST_TEN_PULL;
    }

    protected GachaMachine(PetFactory petFactory, GameState gameState, String title, String description) {
        this.petFactory = petFactory;
        this.gameState = gameState;
        this.title = title;
        this.description = description;
    }

    public Pet pullOne() {
        if (gameState.currency < COST_ONE_PULL) return null;
        gameState.currency -= COST_ONE_PULL;
        Pet pet = petFactory.createPet(determinePetRarity());
        gameState.ownedPets.add(pet);
        return pet;
    }

    public List<Pet> pullTen() {
        if (gameState.currency < COST_TEN_PULL) return List.of();
        gameState.currency -= COST_TEN_PULL;
        List<Pet> results = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Pet pet = petFactory.createPet(determinePetRarity());
            gameState.ownedPets.add(pet);
            results.add(pet);
        }
        return results;
    }

    public abstract PetRarity determinePetRarity();
    // called by pull, based off of this gacha machine's particular rates and available pets

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean canAffordOnePull() {
        return gameState.currency >= COST_ONE_PULL;
    }

    public boolean canAffordTenPull() {
        return gameState.currency >= COST_TEN_PULL;
    }

    public int getCurrency() {
        return gameState.currency;
    }

}
