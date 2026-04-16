package eggroll.pet.petstate;

import eggroll.pet.Pet;
import eggroll.pet.PetStatType;

public class UnbornState implements PetState{
    // All pets have the same behavior in Unborn state
    // Can only be played with. Other stats don't go down while in this state.
    private final static int DEFAULT_INCREMENT = 1;

    Pet pet;

    public UnbornState(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void nap(int maximumEnergy) {
    }

    @Override
    public void eat(int maximumFullness) {
    }

    @Override
    public void play(int maximumHappiness) {
        if(pet.getHappinessStat() >= maximumHappiness){
            System.out.print(pet.getName() + " would rather crack open its shell...!");
            return;
        }
        pet.increaseStat(DEFAULT_INCREMENT, PetStatType.HAPPINESS);
        System.out.print("You rolled around " + pet.getName() + ". Happiness is now " + pet.getHappinessStat());
    }

    @Override
    public void bathe(int maximumHygiene) {
    }

    @Override
    public void changeState(int minimumStat) {
        // TODO
    }
}
