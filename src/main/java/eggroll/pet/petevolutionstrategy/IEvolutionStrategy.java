package eggroll.pet.petevolutionstrategy;

import eggroll.pet.Pet;
import eggroll.pet.PetStatType;

public interface IEvolutionStrategy {
    boolean canEvolve(Pet pet);
    int calculateStatIncrease(Pet pet, int amount, PetStatType type);
}
