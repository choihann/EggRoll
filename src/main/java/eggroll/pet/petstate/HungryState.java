package eggroll.pet.petstate;

import eggroll.pet.Pet;
import eggroll.pet.PetStatType;

import java.util.EnumSet;

public class HungryState extends PetState {

    public HungryState(Pet pet) {
        super(pet);
        this.penalizableStatTypes = EnumSet.of(PetStatType.HYGIENE, PetStatType.ENERGY, PetStatType.HAPPINESS);
    }

    @Override
    public boolean canNap() {
        return false;
    }

    @Override
    public boolean canEat() {
        return true;
    }

    @Override
    public boolean canPlay() {
        return false;
    }

    @Override
    public boolean canBathe() {
        return false;
    }

//    @Override
//    public void nap(int maximumEnergy) {
//        System.out.print(pet.getName() + " is too hungry and can't fall asleep!");
//    }
//
//    @Override
//    public void eat(int maximumFullness) {
//        System.out.print(pet.getName() + "had a feast fit for kings. Fullness is now " + pet.getFullnessStat());
//    }
//
//    @Override
//    public void play(int maximumHappiness) {
//        System.out.print(pet.getName() + " is too hungry to play!");
//    }
//
//    @Override
//    public void bathe(int maximumHygiene) {
//        System.out.print(pet.getName() + " is too hungry to take a bath!");
//    }

}
