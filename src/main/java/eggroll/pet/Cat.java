package eggroll.pet;

import eggroll.gacha.GachaRarity;

public class Cat extends Pet{
    static protected String DEFAULT_CAT_NAME = "Mittens";
    private final Integer UNIQUE_MAX_ENERGY = 7;

    public Cat(String name, String species, GachaRarity rarity, PetPersonality personality) {
        super(name, species, rarity, personality);
    }

    public Cat(){
        this.name = DEFAULT_CAT_NAME;
        this.species = "Cat";
        this.rarity = GachaRarity.Common;
        this.personality = PetPersonality.LAZY;

        this.hygiene = DEFAULT_STARTING_STAT;
        this.happiness = DEFAULT_STARTING_STAT;
        this.fullness = DEFAULT_STARTING_STAT;
        this.energy = DEFAULT_STARTING_STAT;
        this.currentState = unbornState;
        this.age = 0;
        this.needsPenalty = false;
        this.isEgg = true;
    }

    @Override
    public boolean doActivity() {
        System.out.print(this.name + " decided to take a nap in its free time. Oh so sleepy!");
        increaseStat(DEFAULT_STAT_INCREMENT, PetStatType.ENERGY); // the cat's activity as a LAZY animal is napping
        increaseStat(DEFAULT_STAT_INCREMENT, PetStatType.HAPPINESS);// and the cat's activity also makes it a little happier :)
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

    @Override
    public void nap(){
        if (currentState != null) {
            currentState.nap(UNIQUE_MAX_ENERGY);
        }
    }

}
