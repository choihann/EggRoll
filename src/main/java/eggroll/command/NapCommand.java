package eggroll.command;

import eggroll.pet.Pet;

public class NapCommand implements Command {
    private final Pet pet;

    public NapCommand(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void execute() {
        pet.nap();
    }
}