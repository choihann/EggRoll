package eggroll.pet.petstate;

public class StateFactory { // simple state factory to avoid new usage
    public UnbornState newUnbornState() {
        return new UnbornState();
    }

    public DirtyState newDirtyState() {
        return new DirtyState();
    }

    public TiredState newTiredState() {
        return new TiredState();
    }

    public NormalState newNormalState() {
        return new NormalState();
    }

    public HungryState newHungryState() {
        return new HungryState();
    }
}
