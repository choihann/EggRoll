package eggroll.pet.petfactory;

import eggroll.pet.Pet;
import eggroll.pet.PetRarity;

abstract public class PetFactory {
    public abstract Pet createPet(PetRarity rarity);
}
