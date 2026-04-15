package eggroll.pet.petstate;

import eggroll.pet.Pet;
import eggroll.pet.PetStatType;

public class NormalState implements PetState{
    private final static int DEFAULT_INCREMENT = 1;

    Pet pet;

    @Override
    public void nap(int maximumEnergy) {
        if(pet.getEnergyStat() >= maximumEnergy){
            System.out.print(pet.getName() + " is not tired enough to sleep.");
            return;
        }
        pet.increaseStat(DEFAULT_INCREMENT, PetStatType.ENERGY);
        System.out.print("You tucked in " + pet.getName() + " for a little nap. Energy is now " + pet.getEnergyStat());
    }

    @Override
    public void eat(int maximumFullness) {
        if(pet.getEnergyStat() >= maximumFullness){
            System.out.print(pet.getName() + " can't manage another single, solitary bite!");
            return;
        }
        pet.increaseStat(DEFAULT_INCREMENT, PetStatType.HUNGER);
        System.out.print("You fed " + pet.getName() + ". Fullness is now " + pet.getFullnessStat());
    }

    @Override
    public void exercise(int maximumFitness) {
        if(pet.getFitnessStat() >= maximumFitness){
            System.out.print(pet.getName() + " is tired of the gym.");
            return;
        }
        pet.increaseStat(DEFAULT_INCREMENT, PetStatType.FITNESS);
        System.out.print(pet.getName() + " got to movin'! Fitness is now " + pet.getFitnessStat());
    }

    @Override
    public void play(int maximumHappiness) {
        if(pet.getHappinessStat() >= maximumHappiness){
            System.out.print(pet.getName() + " is at the height of joy, no more playtime.");
            return;
        }
        pet.increaseStat(DEFAULT_INCREMENT, PetStatType.HAPPINESS);
        System.out.print("You spent quality time with " + pet.getName() + ". Happiness is now " + pet.getEnergyStat());
    }

    @Override
    public void bathe(int maximumHygiene) {
        if(pet.getHygieneStat() >= maximumHygiene){
            System.out.print(pet.getName() + " is clean enough.");
            return;
        }
        pet.increaseStat(DEFAULT_INCREMENT, PetStatType.HYGIENE);
        System.out.print("WASH MY BELLAY! said " + pet.getName() + ". Its hygiene is now " + pet.getHygieneStat());
    }

    @Override
    public void evolve(int maximumHappiness) {

    }
}
