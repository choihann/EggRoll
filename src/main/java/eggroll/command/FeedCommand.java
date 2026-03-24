package eggroll.command;

import eggroll.pet.Pet;

public class FeedCommand implements Command {
    private final Pet pet;

    public FeedCommand(Pet pet) {
        this.pet = pet;
    }

    @Override
    public void execute() {
        pet.eat();
    }
}
