package eggroll.pet;

import eggroll.gacha.GachaRarity;

public class Cat extends Pet{
    public static final String DEFAULT_NAME = "Mittens";
    public static final String SPECIES = "Cat";

    public Cat(String name, GachaRarity rarity) {
        super(name, SPECIES, rarity);
    }

    public Cat() {
        this(DEFAULT_NAME, GachaRarity.Common);
    }
}
