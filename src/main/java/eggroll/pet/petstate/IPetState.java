package eggroll.pet.petstate;

import eggroll.pet.PetStatType;

import java.util.Set;

public interface IPetState {
    public boolean canNap();
    public boolean canEat();
    public boolean canPlay();
    public boolean canBathe();
}
