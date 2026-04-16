package eggroll.pet.petstate;

import eggroll.pet.Pet;
import eggroll.pet.PetStatType;

public class HungryState implements PetState {
    private final static int DEFAULT_INCREMENT = 1;
    private final static int HUNGRY_FULLNESS_INCREMENT = 2;

    Pet pet;

    @Override
    public void nap(int maximumEnergy) {
        System.out.print(pet.getName() + " is too hungry and can't fall asleep!");
    }

    @Override
    public void eat(int maximumFullness) {
        pet.increaseStat(HUNGRY_FULLNESS_INCREMENT, PetStatType.FULLNESS);
        System.out.print(pet.getName() + "had a feast fit for kings. Fullness is now " + pet.getFullnessStat());
    }

    @Override
    public void play(int maximumHappiness) {
        System.out.print(pet.getName() + " is too hungry to play!");
    }

    @Override
    public void bathe(int maximumHygiene) {
        System.out.print(pet.getName() + " is too hungry to take a bath!");
    }

    @Override
    public void changeState(int minimumStat) {
        // TODO: PetState has this
    }
}
