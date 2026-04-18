package eggroll.pet.petstate;

import eggroll.pet.Pet;

public class StateFactory { // simple state factory to avoid new usage
    public UnbornState newUnbornState(Pet pet) {
        return new UnbornState(pet);
    }
    public DirtyState newDirtyState(Pet pet) {
        return new DirtyState(pet);
    }
    public TiredState newTiredState(Pet pet) {
        return new TiredState(pet);
    }
    public NormalState newNormalState(Pet pet) {
        return new NormalState(pet);
    }
    public HungryState newHungryState(Pet pet) {
        return new HungryState(pet);
    }
}
