package eggroll.pet.petstate;

import eggroll.pet.PetStatType;

import java.util.EnumSet;

public class UnbornState extends PetState {
    // Can only be played with. Other stats don't go down while in this state.
    public UnbornState() {
        this.penalizableStatTypes = EnumSet.noneOf(PetStatType.class); // Empty petStatType set.
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
        return true;
    }

    @Override
    public boolean canBathe() {
        return false;
    }

}
