package eggroll.pet.petstate;

import eggroll.pet.Pet;

public class UnfitState implements PetState{
    private final static int DEFAULT_INCREMENT = 1;
    private final static int UNFIT_FITNESS_INCREMENT = 2;

    Pet pet;

    @Override
    public void nap(int maximumEnergy) {
        System.out.print(pet.getName() + " is too weak to get into bed.");
    }

    @Override
    public void eat(int maximumFullness) {
        System.out.print(pet.getName() + " is too weak to eat.");
    }

    @Override
    public void exercise(int maximumFitness) {
        pet.increaseStat(UNFIT_FITNESS_INCREMENT, "fitness");
        System.out.print(pet.getName() + "had a healthy workout session! Fitness is now " + pet.getFullnessStat());
    }

    @Override
    public void play(int maximumHappiness) {
        if(pet.getHygieneStat() >= maximumHappiness){
            System.out.print(pet.getName() + " is at the height of joy already!");
            return;
        }
        pet.increaseStat(DEFAULT_INCREMENT, "happiness");
        pet.increaseStat(DEFAULT_INCREMENT, "fitness");
        System.out.print(pet.getName() + "played with great vigor. Its fitness is now" + pet.getFitnessStat() + " and happiness is now " + pet.getHappinessStat());
    }

    @Override
    public void bathe(int maximumHygiene) {
        System.out.print(pet.getName() + " is too weak to take a bath!");
    }

    @Override
    public void evolve(int maximumHappiness) {
        System.out.print(pet.getName() + " is too weak to evolve!");
    }
}
