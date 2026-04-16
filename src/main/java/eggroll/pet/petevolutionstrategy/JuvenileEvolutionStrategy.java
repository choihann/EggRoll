package eggroll.pet.petevolutionstrategy;

import eggroll.pet.Pet;
import eggroll.pet.PetStatType;

public class JuvenileEvolutionStrategy extends EvolutionStrategy {
    public final static int MAX_STAT = 7;
    public final static int EVOLUTION_AGE = 5;

    protected JuvenileEvolutionStrategy() {
        super(MAX_STAT);
    }

    @Override
    public boolean canEvolve(Pet myself) {
        if(myself.getHappinessStat() == MAX_STAT && myself.getAge() == EVOLUTION_AGE && myself.getCurrentState() == myself.getNormalState()){
            return true;
        }
        return false;
    }
}
