package eggroll.gacha;

import eggroll.pet.Pet;
import eggroll.pet.PetFactory;
import eggroll.pet.PetRarity;

import java.util.ArrayList;
import java.util.List;

public abstract class GachaMachine {
    private final PetFactory petFactory;
    private String description;
    private String title;

    protected GachaMachine(PetFactory petFactory, String title, String description) {
        this.petFactory = petFactory;
        this.title = title;
        this.description = description;
    }

    public Pet pullOne(){
        // TODO: return a random pet in an unborn egg state by calling petfactory's createpet
        PetRarity rarity = determinePetRarity();
        return petFactory.createPet(rarity);
    };

    public List<Pet> pullFive() {
        // TODO: return 5 pets instead of one
        List<Pet> gachaResults = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            gachaResults.add(pullOne());
        }
        return gachaResults;
    }

    public abstract PetRarity determinePetRarity();
    // called by pull, based off of this gacha machine's particular rates and available pets

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
