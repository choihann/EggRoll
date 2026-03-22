package eggroll.pet.petstate;

import eggroll.pet.Pet;

public class DirtyState implements PetState{
    private final static int DEFAULT_INCREMENT = 1;
    private final static int DIRTY_HYGIENE_INCREMENT = 2;

    Pet pet;

    @Override
    public void nap(int maximumEnergy) {
        System.out.print(pet.getName() + " doesn't want to ruin its bed with dirt.");
        return;
    }

    @Override
    public void eat(int maximumFullness) {
        System.out.print(pet.getName() + " tried to eat, but the stench distracted it.");
    }

    @Override
    public void exercise(int maximumFitness) {
        System.out.print(pet.getName() + " would rather shower.");
    }

    @Override
    public void play(int maximumHappiness) {
        System.out.print(pet.getName() + " is so stinky, you'd rather not play with it, actually.");
    }

    @Override
    public void bathe(int maximumHygiene) {
        pet.increaseStat(DIRTY_HYGIENE_INCREMENT, "hygiene");
        System.out.print(pet.getName() + "had a lovely bubble bath and now has hygiene " + pet.getHygieneStat());
    }

    @Override
    public void evolve(int maximumHappiness) {
        System.out.print(pet.getName() + " is too dirty to evolve!");
    }
}
