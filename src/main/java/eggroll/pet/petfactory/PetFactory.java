package eggroll.pet.petfactory;

import eggroll.gacha.GachaRarity;
import eggroll.pet.Pet;

abstract public class PetFactory {
    public abstract Pet createPet(GachaRarity rarity);
}
