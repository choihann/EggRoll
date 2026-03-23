package eggroll.pet;

import eggroll.pet.petstate.UnbornState;

public class Cat extends Pet{
    static protected String DEFAULT_CAT_NAME = "Mittens";
    private final Integer MAX_ENERGY = 7;

    public Cat(){
        this.personality = PetPersonality.LAZY;
        this.name = DEFAULT_CAT_NAME;
        this.species = "Cat";
        this.needsPenalty = false;
        this.rarity = PetRarity.Common;
    }

    @Override
    public boolean doActivity() {
        System.out.print(this.name + " decided to take a nap in its free time. Oh so sleepy!");
        energy += 1; // the cat's activity as a LAZY animal is napping
        happiness += 1;
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
    public boolean checkIfNeedsPenalty(){
        if(hygiene == 0 || fitness == 0 || fullness == 0 || energy == 1){
            return true;
        }
        return false;
    }

}
