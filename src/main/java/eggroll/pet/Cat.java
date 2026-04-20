package eggroll.pet;

import eggroll.gacha.GachaRarity;

public class Cat extends Pet{
    static protected String DEFAULT_CAT_NAME = "Mittens";

    public Cat(String name, String species, GachaRarity rarity) {
        super(name, species, rarity);
    }

    public Cat(){
        this.name = DEFAULT_CAT_NAME;
        this.species = "Cat";
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
        this.currentEvolutionStrategy = strategyFactory.newUnbornStrategy();
        this.needsPenalty = false;
        this.isEgg = true;
    }

    @Override
    public boolean doActivity() {
        System.out.print(this.name + " decided to take a nap in its free time. Oh so sleepy!");
        increaseStat(STAT_INCREMENT, PetStatType.ENERGY); // the cat's activity as a LAZY animal is napping
        increaseStat(STAT_INCREMENT, PetStatType.HAPPINESS);// and the cat's activity also makes it a little happier :)
        return true;
    }
}
