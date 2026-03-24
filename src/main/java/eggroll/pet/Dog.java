package eggroll.pet;

public class Dog extends Pet{
    static protected String DEFAULT_DOG_NAME = "Archie";
    private final Integer UNIQUE_MAX_FULLNESS = 7;

    public Dog(String name, String species, PetRarity rarity, PetPersonality personality) {
        super(name, species, rarity, personality);
    }

    public Dog(){
        this.name = DEFAULT_DOG_NAME;
        this.species = "Dog";
        this.rarity = PetRarity.Common;
        this.personality = PetPersonality.FOODIE;

        this.hygiene = DEFAULT_STARTING_STAT;
        this.happiness = DEFAULT_STARTING_STAT;
        this.fullness = DEFAULT_STARTING_STAT;
        this.fitness = DEFAULT_STARTING_STAT;
        this.energy = DEFAULT_STARTING_STAT;

        this.currentState = unbornState;
        this.age = 0;
        this.needsPenalty = false;
        this.isEgg = true;
    }

    @Override
    public boolean doActivity() {
        System.out.print(this.name + " chewed on some milk bones.");
        increaseStat(DEFAULT_STAT_INCREMENT, "fullness"); // the dog's activity as a foodie is snacking.
        increaseStat(DEFAULT_STAT_INCREMENT, "happiness");// and the dog's activity also makes it a little happier :)
        return true;
    }

    @Override
    public boolean applyPenalty(boolean needsPenalty) {
        if(needsPenalty){
            happiness =- 1;
            return true;
        }
        return false;
    }

    @Override
    public void eat(){
        currentState.eat(UNIQUE_MAX_FULLNESS);
    }
}
