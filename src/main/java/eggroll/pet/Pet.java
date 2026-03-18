package eggroll.pet;

import eggroll.pet.petstate.PetState;

abstract public class Pet {
    private String name;
    private String species;
    private PetState state;
    private PetRarity rarity;
    private boolean needsPenalty;
    private int hygiene;
    private int happiness;
    private int hunger;
    private int fitness;
    private int energy;
    private int age;

    public String getName(){
        return this.name;
    }
    public String getSpecies(){
        return this.species;
    }
    public PetState getPetState(){
        return this.state;
    }
    public PetRarity getRarity(){
        return this.rarity;
    }
    public boolean needsPenalty(){
        return this.needsPenalty;
    }
    public int getHygieneStat(){
        return this.hygiene;
    }
    public int getHappinessStat(){
        return this.happiness;
    }
    public int getHungerStat(){
        return this.hunger;
    }
    public int getFitnessStat(){
        return this.fitness;
    }
    public int getEnergyStat(){
        return this.energy;
        // when a pet has no energy, they cannot do anything except sleep.
    }
    public int getAge(){
        return this.age;
    }


    abstract public boolean doActivity();
    // return a bool that indicates whether the activity was "successful" or not?
    // maybe it has some negative consequence depending on personality
    // i.e. if a pet likes to laze around, maybe it gets extra stinky because it didn't shower.

    abstract public boolean applyPenalty(boolean needsPenalty);
    // need a better bool name?
    // and a 2nd argument to determine which type of penalty to perform
    // penalty could be - hygiene
    // this method could be invoked when an activity fails and when happiness stat is low.

    public void sleep(){
        //TODO: implement sleeping
    }
    public void eat(){
        //TODO: implement eating
    }
    public void exercise(){
        //TODO: implement exercising
    }
    public void bathe(){
        //TODO: implement bathing
    }
    public void evolve(){
        //TODO: implement evolving
    }
    public void growOlder(){
        this.age += 1; // maybe would be cute, you can know how old your pet is.
    }
}
