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

    @Override
    public boolean doActivity() {
        System.out.print(this.name + " decided to take a nap in its free time. Oh so sleepy!");
        increaseStat(STAT_INCREMENT, PetStatType.ENERGY); // the cat's activity as a LAZY animal is napping
        increaseStat(STAT_INCREMENT, PetStatType.HAPPINESS);// and the cat's activity also makes it a little happier :)
        return true;
    }
}
