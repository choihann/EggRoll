package eggroll.pet;

import eggroll.pet.petstate.PetState;

import java.util.Random;

abstract public class Pet implements IPet{
    protected static int DEFAULT_MAX_STAT = 5;

    protected String name;
    protected String species;
    protected PetState state;
    protected PetRarity rarity;
    protected PetPersonality personality;
    protected boolean needsPenalty;
    protected int hygiene;
    protected int happiness;
    protected int fullness;
    protected int fitness;
    protected int energy;
    protected int age;

    private Random random = new Random(); // will have to encapsulate this, is used for random decreases

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
    public PetPersonality getPetPersonality(){ return this.personality; }
    public int getHygieneStat(){
        return this.hygiene;
    }
    public int getHappinessStat(){
        return this.happiness;
    }
    public int getFullnessStat(){
        return this.fullness;
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
    // or if a pet's stat is low at the end of the day.

    public void sleep(){
        //TODO: implement sleeping
        energy += 1;
    }
    public void eat(){
        //TODO: implement eating
        fullness += 1;
    }
    public void exercise(){
        //TODO: implement exercising
        fitness += 1;
    }
    public void bathe(){
        //TODO: implement bathing
        hygiene += 1;
    }
    public void evolve(){
        //TODO: implement evolving
    }

    protected static int DEFAULT_MAX_STAT = 5;

    public void recoverRandomStat(int amount) {
        if (hygiene == DEFAULT_MAX_STAT &&
                happiness == DEFAULT_MAX_STAT &&
                fullness == DEFAULT_MAX_STAT &&
                fitness == DEFAULT_MAX_STAT &&
                energy == DEFAULT_MAX_STAT) {
            return;
        }

        while (true) {
            int choice = (int)(Math.random() * 5);

            switch (choice) {
                case 0:
                    if (hygiene < DEFAULT_MAX_STAT) {
                        hygiene = Math.min(DEFAULT_MAX_STAT, hygiene + amount);
                        return;
                    }
                    break;

                case 1:
                    if (happiness < DEFAULT_MAX_STAT) {
                        happiness = Math.min(DEFAULT_MAX_STAT, happiness + amount);
                        return;
                    }
                    break;

                case 2:
                    if (fullness < DEFAULT_MAX_STAT) {
                        fullness = Math.min(DEFAULT_MAX_STAT, fullness + amount);
                        return;
                    }
                    break;

                case 3:
                    if (fitness < DEFAULT_MAX_STAT) {
                        fitness = Math.min(DEFAULT_MAX_STAT, fitness + amount);
                        return;
                    }
                    break;

                case 4:
                    if (energy < DEFAULT_MAX_STAT) {
                        energy = Math.min(DEFAULT_MAX_STAT, energy + amount);
                        return;
                    }
                    break;
            }
        }
    }

    public void lowerRandomStat(int amount) {
        if (hygiene == 0 && happiness == 0 && fullness == 0 && fitness == 0 && energy == 0) {
            return;
        }

        while (true) {
            int choice = (int)(Math.random() * 5);

            switch (choice) {
                case 0:
                    if (hygiene > 0) {
                        hygiene = Math.max(0, hygiene - amount);
                        return;
                    }
                    break;
                case 1:
                    if (happiness > 0) {
                        happiness = Math.max(0, happiness - amount);
                        return;
                    }
                    break;
                case 2:
                    if (fullness > 0) {
                        fullness = Math.max(0, fullness - amount);
                        return;
                    }
                    break;
                case 3:
                    if (fitness > 0) {
                        fitness = Math.max(0, fitness - amount);
                        return;
                    }
                    break;
                case 4:
                    if (energy > 0) {
                        energy = Math.max(0, energy - amount);
                        return;
                    }
                    break;
            }
        }
    }

    public void growOlder(){
        this.age += 1; // maybe would be cute, you can know how old your pet is.
    }
    public void play(){
        if(happiness >= DEFAULT_MAX_STAT){
            happiness += 1;
        }
        lowerRandomStat(1); //happiness is a valuable stat, so playing has to come at a cost..
    }
}
