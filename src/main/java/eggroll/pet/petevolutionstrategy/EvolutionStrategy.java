package eggroll.pet.petevolutionstrategy;

import eggroll.pet.Pet;
import eggroll.pet.PetStatType;

public abstract class EvolutionStrategy implements IEvolutionStrategy{
    private final int maxStat;

    protected EvolutionStrategy(int maxStat){ this.maxStat = maxStat; }

    public int getMaxStat(){ return maxStat; };

    public abstract boolean canEvolve(Pet myself);

    public int calculateStatIncrease(Pet myself, int amount, PetStatType type) {
        int max = getMaxStat();

        switch (type) {
            case HAPPINESS -> {
                return Math.min(max, myself.getHappinessStat() + amount);
            }
            case ENERGY -> {
                return Math.min(max, myself.getEnergyStat() + amount);
            }
            case FULLNESS -> {
                return Math.min(max, myself.getFullnessStat() + amount);
            }
            case HYGIENE -> {
                return Math.min(max , myself.getHygieneStat() + amount);
            }
        }
        return 0;
    }
}
