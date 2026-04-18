package eggroll.pet.petstate;

import eggroll.pet.Pet;
import eggroll.pet.PetStatType;

import java.util.EnumSet;

public class TiredState extends PetState {

    public TiredState(Pet pet) {
        super(pet);
        this.penalizableStatTypes = EnumSet.of(PetStatType.HYGIENE, PetStatType.FULLNESS, PetStatType.HAPPINESS);
    }

    @Override
    public boolean canNap() {
        return true;
    }

    @Override
    public boolean canEat() {
        return false;
    }

    @Override
    public boolean canPlay() {
        return false;
    }

    @Override
    public boolean canBathe() {
        return false;
    }

    // commented out to save the flavor text
//    @Override
//    public void nap(int maximumEnergy) {
//        System.out.print("You tucked in " + pet.getName() + " for a well-deserved nap. Energy is now " + pet.getEnergyStat());
//    }
//
//    @Override
//    public void eat(int maximumFullness) {
//        System.out.print(pet.getName() + " is too tired to eat!");
//    }
//
//    @Override
//    public void play(int maximumHappiness) {
//        System.out.print(pet.getName() + " is too tired to play!");
//    }
//
//    @Override
//    public void bathe(int maximumHygiene) {
//        if(pet.getHygieneStat() >= maximumHygiene){
//            System.out.print(pet.getName() + " is clean enough.");
//            return;
//        }
//        System.out.print(pet.getName() + "enjoyed a restful bath. Hygiene is now " + pet.getHygieneStat() + " and energy is now " + pet.getEnergyStat());
//    }

}
