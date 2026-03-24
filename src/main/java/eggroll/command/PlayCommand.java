package eggroll.command;

import eggroll.pet.Pet;

public class PlayCommand implements Command {
    private final Pet pet;

    public PlayCommand(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void execute() {
        pet.play();
    }
}
