package eggroll.pet.petevolutionstrategy;

import eggroll.pet.Pet;
import eggroll.pet.PetStatType;

public class AdultEvolutionStrategy extends EvolutionStrategy {
    protected final static int MAX_STAT = 10;

    protected AdultEvolutionStrategy() {
        super(MAX_STAT);
    }

    @Override
    public boolean canEvolve(Pet myself) {
        return false;
    }
}
