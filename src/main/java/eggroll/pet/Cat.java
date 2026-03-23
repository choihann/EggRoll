package eggroll.pet;

import eggroll.pet.petstate.UnbornState;

public class Cat extends Pet{
    static protected String DEFAULT_CAT_NAME = "Mittens";
    private final Integer MAX_ENERGY = 7;

    public Cat(String name, String species, PetRarity rarity, PetPersonality personality) {
        super(name, species, rarity, personality);
    }

    @Override
    public boolean doActivity() {
        System.out.print(this.name + " decided to take a nap in its free time. Oh so sleepy!");
        increaseStat(DEFAULT_STAT_INCREMENT, "energy"); // the cat's activity as a LAZY animal is napping
        increaseStat(DEFAULT_STAT_INCREMENT, "happiness");// and the cat's activity also makes it a little happier :)
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
        currentState.nap(MAX_ENERGY);
    }

}
