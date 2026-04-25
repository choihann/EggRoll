package eggroll.pet;

import eggroll.gacha.GachaRarity;

public class Dog extends Pet{
    public static final String DEFAULT_NAME = "Archie";
    public static final String SPECIES = "Dog";

    public Dog(String name, GachaRarity rarity) {
        super(name, SPECIES, rarity);
    }

}
