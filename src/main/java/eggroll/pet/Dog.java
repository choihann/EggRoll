package eggroll.pet;

import eggroll.gacha.GachaRarity;

public class Dog extends Pet{
    public static final String DEFAULT_NAME = "Archie";
    public static final String SPECIES = "Dog";

    public Dog(String name, GachaRarity rarity) {
        super(name, SPECIES, rarity);
    }

    public Dog() {
        this(DEFAULT_NAME, GachaRarity.Common);
    }

    // TODO: Left this untouched, but I'm not really sure what this does
    @Override
    public boolean doActivity() {
        System.out.print(this.name + " chewed on some milk bones.");
        increaseStat(STAT_INCREMENT, PetStatType.FULLNESS); // the dog's activity as a foodie is snacking.
        increaseStat(STAT_INCREMENT, PetStatType.HAPPINESS); // and the dog's activity also makes it a little happier :)
        return true;
    }

    @Override
    public boolean applyPenalty(boolean needsPenalty) {
        if(needsPenalty){
            happiness =- 1;
            return true;
        }
        return false;
    }
}
