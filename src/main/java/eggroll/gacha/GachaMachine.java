package eggroll.gacha;

import eggroll.pet.Pet;
import eggroll.pet.PetRarity;

import java.util.List;

public abstract class GachaMachine {
    private List<Pet> petsInRotation; //a list of potential pets you can get from this gacha!
    private String description;
    private String title;

    public Pet pullOne(){
        // TODO: return a random pet in an unborn egg state by calling petfactory's createpet
        // potentially given a rarity enum
    };
    public Pet pullFive(){
        // TODO: return 5 pets instead of one
    }
    abstract public PetRarity detertimePetRarity();
    // called by pull, based off of this gacha machine's particular rates and available pets
}
