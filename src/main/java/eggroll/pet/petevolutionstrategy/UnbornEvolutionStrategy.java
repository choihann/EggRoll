package eggroll.pet.petevolutionstrategy;

import eggroll.observer.PetEvent;
import eggroll.pet.Pet;
import eggroll.pet.PetStatType;

public class UnbornEvolutionStrategy extends EvolutionStrategy {
    private final static int MAX_STAT = 5;

    protected UnbornEvolutionStrategy() {
        super(MAX_STAT);
    }

    @Override
    public boolean canEvolve(Pet myself) {
        if(myself.getHappinessStat() == MAX_STAT){
            return true;
        }
        return false;
    }
}
