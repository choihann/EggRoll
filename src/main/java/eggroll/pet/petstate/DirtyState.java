package eggroll.pet.petstate;

import eggroll.pet.Pet;
import eggroll.pet.PetStatType;

import java.util.EnumSet;

public class DirtyState extends PetState {

    public DirtyState(Pet pet) {
        super(pet);
        this.penalizableStatTypes = EnumSet.of(PetStatType.ENERGY, PetStatType.FULLNESS, PetStatType.HAPPINESS); // Empty petStatType set.
    }

    @Override
    public boolean canNap() {
        return false;
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
        return true;
    }

//    @Override
//    public void nap(int maximumEnergy) {
//        System.out.print(pet.getName() + " doesn't want to ruin its bed with dirt.");
//    }
//
//    @Override
//    public void eat(int maximumFullness) {
//        System.out.print(pet.getName() + " tried to eat, but the stench distracted it.");
//    }
//
//    @Override
//    public void play(int maximumHappiness) {
//        System.out.print(pet.getName() + " is so stinky, you'd rather not play with it, actually.");
//    }
//
//    @Override
//    public void bathe(int maximumHygiene) {
//        System.out.print(pet.getName() + "had a lovely bubble bath and now has hygiene " + pet.getHygieneStat());
//    }
}
