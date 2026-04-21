package eggroll.pet.petfactory;

import eggroll.gacha.GachaRarity;
import eggroll.pet.Pet;

import java.util.List;
import java.util.Random;

abstract public class PetFactory {
    private static final Random random = new Random();

    public abstract Pet createPet(GachaRarity rarity);

    protected <T> T selectFromPetPool(List<T> pool) {
        return pool.get(random.nextInt(pool.size()));
    }
}