package eggroll.observer;

import eggroll.pet.Pet;

public interface PetObserver {
    void onPetEvent(PetEvent event, Pet pet);
}