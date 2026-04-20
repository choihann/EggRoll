package eggroll.pet.petstate;

import eggroll.pet.PetStatType;

import java.util.EnumSet;
import java.util.Set;

abstract public class PetState implements IPetState{
    protected EnumSet<PetStatType> penalizableStatTypes;

    public PetState() {
        this.penalizableStatTypes =  EnumSet.allOf(PetStatType.class);
    }
    public Set<PetStatType> getPenalizableStats(){
        return EnumSet.copyOf(penalizableStatTypes);
    };
    abstract public boolean canNap();
    abstract public boolean canEat();
    abstract public boolean canPlay();
    abstract public boolean canBathe();
}
