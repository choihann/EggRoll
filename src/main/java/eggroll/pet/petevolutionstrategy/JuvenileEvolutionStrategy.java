package eggroll.pet.petevolutionstrategy;

import eggroll.pet.Pet;
import eggroll.pet.PetStatType;

public class JuvenileEvolutionStrategy extends EvolutionStrategy {
    public final static int MAX_STAT = 7;

    protected JuvenileEvolutionStrategy() {
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
