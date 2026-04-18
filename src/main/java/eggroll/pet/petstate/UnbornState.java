package eggroll.pet.petstate;

import eggroll.pet.Pet;
import eggroll.pet.PetStatType;

import java.util.EnumSet;
import java.util.Set;

public class UnbornState extends PetState {
    // Can only be played with. Other stats don't go down while in this state.
    private final EnumSet<PetStatType> penalizableStatTypes;

    public UnbornState(Pet pet) {
        super(pet);
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
