package eggroll.command;

import eggroll.pet.Pet;

public class BatheCommand implements Command {
    private final Pet pet;

    public BatheCommand(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void execute() {
        pet.bathe();
    }
}