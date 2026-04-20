package eggroll.pet;

import eggroll.gacha.GachaRarity;

public class Dog extends Pet{
    static protected String DEFAULT_DOG_NAME = "Archie";

    public Dog(String name, String species, GachaRarity rarity) {
        super(name, species, rarity);
    }

    public Dog(){
        this.name = DEFAULT_DOG_NAME;
        this.species = "Dog";
        this.rarity = GachaRarity.Common;

        this.unbornState = stateFactory.newUnbornState();
        this.normalState = stateFactory.newNormalState();
        this.dirtyState = stateFactory.newDirtyState();
        this.tiredState = stateFactory.newTiredState();
        this.hungryState = stateFactory.newHungryState();

        this.hygiene = STARTING_STAT;
        this.happiness = STARTING_STAT;
        this.fullness = STARTING_STAT;
        this.energy = STARTING_STAT;

        this.currentState = unbornState;
        this.needsPenalty = false;
        this.isEgg = true;
    }

    @Override
    public boolean doActivity() {
        System.out.print(this.name + " chewed on some milk bones.");
        increaseStat(STAT_INCREMENT, PetStatType.FULLNESS); // the dog's activity as a foodie is snacking.
        increaseStat(STAT_INCREMENT, PetStatType.HAPPINESS);// and the dog's activity also makes it a little happier :)
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
