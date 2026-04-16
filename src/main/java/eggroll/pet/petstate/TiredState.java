package eggroll.pet.petstate;

import eggroll.pet.Pet;
import eggroll.pet.PetStatType;

public class TiredState implements PetState{

    private final static int DEFAULT_INCREMENT = 1;
    private final static int TIRED_ENERGY_INCREMENT = 2;

    Pet pet;

    @Override
    public void nap(int maximumEnergy) {
        pet.increaseStat(TIRED_ENERGY_INCREMENT, PetStatType.ENERGY);
        System.out.print("You tucked in " + pet.getName() + " for a well-deserved nap. Energy is now " + pet.getEnergyStat());
    }

    @Override
    public void eat(int maximumFullness) {
        System.out.print(pet.getName() + " is too tired to eat!");
        return;
    }

    @Override
    public void play(int maximumHappiness) {
        System.out.print(pet.getName() + " is too tired to play!");
        return;
    }

    @Override
    public void bathe(int maximumHygiene) {
        if(pet.getHygieneStat() >= maximumHygiene){
            System.out.print(pet.getName() + " is clean enough.");
            return;
        }
        pet.increaseStat(DEFAULT_INCREMENT, PetStatType.HYGIENE);
        pet.increaseStat(DEFAULT_INCREMENT, PetStatType.ENERGY);
        System.out.print(pet.getName() + "enjoyed a restful bath. Hygiene is now " + pet.getHygieneStat() + " and energy is now " + pet.getEnergyStat());
    }
}
