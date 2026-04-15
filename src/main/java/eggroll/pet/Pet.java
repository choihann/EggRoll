package eggroll.pet;

import eggroll.gacha.GachaRarity;
import eggroll.observer.PetEvent;
import eggroll.observer.PetObserver;
import eggroll.pet.petstate.PetState;

import java.util.*;

abstract public class Pet implements IPet{
    protected final static int DEFAULT_STARTING_STAT = 3;
    protected final static int DEFAULT_MAX_STAT = 5;
    protected final static int DEFAULT_MINIMUM_STAT = 2;
    protected final static int DEFAULT_STAT_INCREMENT = 1;
    protected final static int DEFAULT_EVOLUTION_AGE = 5;
    private transient List<PetObserver> petObservers = new ArrayList<>();

    public static int getDefaultStartingStat() {
        return DEFAULT_STARTING_STAT;
    }

    public static int getDefaultMaxStat() {
        return DEFAULT_MAX_STAT;
    }

    public static int getDefaultMinimumStat() {
        return DEFAULT_MINIMUM_STAT;
    }

    public static int getDefaultStatIncrement() {
        return DEFAULT_STAT_INCREMENT;
    }

    protected PetState unbornState;
    protected PetState normalState;
    protected PetState dirtyState;
    protected PetState tiredState;
    protected PetState sadState;
    protected PetState unfitState;
    protected PetState hungryState;

    protected PetState currentState;

    protected Queue<PetState> queuedStates = new LinkedList<>();

    protected String name;
    protected String species;
    protected GachaRarity rarity;
    protected PetPersonality personality;
    protected boolean needsPenalty;
    protected int hygiene;
    protected int happiness;
    protected int fullness;
    protected int fitness;
    protected int energy;
    protected int age;

    public boolean isEgg;

    private transient Random random = new Random(); // will have to encapsulate this, is used for random decreases

    public Pet(String name, String species, GachaRarity rarity, PetPersonality personality) {
        this.name = name;
        this.species = species;
        this.rarity = rarity;
        this.personality = personality;

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

    protected Pet() {
    }

    public Pet(String name, GachaRarity rarity) {
        this.name = name;
        this.species = "NULL";
        this.rarity = rarity;
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

    public String getName(){
        return this.name;
    }
    public String getSpecies(){
        return this.species;
    }
    public Queue<PetState> getQueuedStates(){
        return queuedStates;
    }
    public PetState getPetState(){
        return this.currentState;
    }

    public GachaRarity getRarity() {
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
    public PetState getUnbornState(){
        return this.unbornState;
    }
    public PetState getNormalState(){
        return this.normalState;
    }
    public PetState getDirtyState(){
        return this.dirtyState;
    }
    public PetState getSadState(){
        return this.sadState;
    }
    public PetState getTiredState(){
        return this.tiredState;
    }
    public PetState getUnfitState(){
        return this.unfitState;
    }
    public PetState getCurrentState(){
        return this.currentState;
    }
    public PetState getHungryState(){
        return this.hungryState;
    }

    public void setCurrentState(PetState newState) {
        this.currentState = newState;
        notifyObservers(PetEvent.STATE_CHANGED);
    }

    public void setStat(int amount, PetStatType type) {
        switch (type) {
            case HAPPINESS -> this.happiness = amount;
            case FITNESS -> this.fitness = amount;
            case ENERGY -> this.energy = amount;
            case HUNGER -> this.fullness = amount;
            case HYGIENE -> this.hygiene = amount;
        }
    }

    public void setIsEgg(boolean newEggStatus) {
        //mostly for testing
        this.isEgg = newEggStatus;
    }

    abstract public boolean doActivity();
    // return a bool that indicates whether the activity was "successful" or not?
    // maybe it has some negative consequence depending on personality
    // i.e. if a pet likes to laze around, maybe it gets extra stinky because it didn't shower.

    abstract public boolean applyPenalty(boolean needsPenalty);

    // TODO: I just assumed MAX_STAT meant this is the cap, not sure if this is the interpretation we're going for
    public void increaseStat(int amount, PetStatType type) {
        switch (type) {
            case HAPPINESS -> {
                this.happiness = Math.min(DEFAULT_MAX_STAT, this.happiness + amount);
                notifyObservers(PetEvent.HAPPINESS_CHANGED);
            }
            case FITNESS -> {
                this.fitness = Math.min(DEFAULT_MAX_STAT, this.fitness + amount);
                notifyObservers(PetEvent.FITNESS_CHANGED);
            }
            case ENERGY -> {
                this.energy = Math.min(DEFAULT_MAX_STAT, this.energy + amount);
                notifyObservers(PetEvent.ENERGY_CHANGED);
            }
            case HUNGER -> {
                this.fullness = Math.min(DEFAULT_MAX_STAT, this.fullness + amount);
                notifyObservers(PetEvent.HUNGER_CHANGED);
            }
            case HYGIENE -> {
                this.hygiene = Math.min(DEFAULT_MAX_STAT, this.hygiene + amount);
                notifyObservers(PetEvent.HYGIENE_CHANGED);
            }
        }
    }

    public void decreaseStat(int amount, PetStatType type) {
        switch (type) {
            case HAPPINESS -> {
                this.happiness = Math.max(0, this.happiness - amount);
                notifyObservers(PetEvent.HAPPINESS_CHANGED);
            }
            case FITNESS -> {
                this.fitness = Math.max(0, this.fitness - amount);
                notifyObservers(PetEvent.FITNESS_CHANGED);
            }
            case ENERGY -> {
                this.energy = Math.max(0, this.energy - amount);
                notifyObservers(PetEvent.ENERGY_CHANGED);
            }
            case HUNGER -> {
                this.fullness = Math.max(0, this.fullness - amount);
                notifyObservers(PetEvent.HUNGER_CHANGED);
            }
            case HYGIENE -> {
                this.hygiene = Math.max(0, this.hygiene - amount);
                notifyObservers(PetEvent.HYGIENE_CHANGED);
            }
        }
    }

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

    public boolean isStatMax(){
        if(this.happiness >= DEFAULT_MAX_STAT){
            return true;
        }
        return false;
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

    public boolean canEvolve() {
        if(this.currentState == unbornState && this.happiness == DEFAULT_MAX_STAT){
            return true; // hatching is a little different than evolving
        }
        if(this.currentState == normalState && this.age >= DEFAULT_EVOLUTION_AGE && this.happiness == DEFAULT_MAX_STAT){
            return true;
        }
        return false;
    }

    public void growOlder(){
        this.age += 1;
    }

    public void nap(){
        if (currentState != null) {
            currentState.nap(DEFAULT_MAX_STAT);
        }
    }
    public void eat(){
        if (currentState != null) {
            currentState.eat(DEFAULT_MAX_STAT);
        }
    }
    public void exercise(){
        if (currentState != null) {
            currentState.exercise(DEFAULT_MAX_STAT);
        }
    }
    public void play(){
        if (currentState != null) {
            currentState.play(DEFAULT_MAX_STAT);
        }
    }
    public void bathe(){
        if (currentState != null) {
            currentState.bathe(DEFAULT_MAX_STAT);
        }
    }
    public void evolve(){
        currentState.evolve(DEFAULT_MAX_STAT);
    };

    public void pushToQueuedStates(PetState state){
        if(isEgg){
            this.queuedStates.add(unbornState);
            return; //eggs can't be in any other state.
        }
        if(hygiene <= DEFAULT_MINIMUM_STAT){
            this.queuedStates.add(dirtyState);
        }
        if(fullness <= DEFAULT_MINIMUM_STAT){
            this.queuedStates.add(hungryState);
        }
        if(energy <= DEFAULT_MINIMUM_STAT){
            this.queuedStates.add(tiredState);
        }
        if(fitness <= DEFAULT_MINIMUM_STAT){
            this.queuedStates.add(unfitState);
        }
        if(happiness <= DEFAULT_MINIMUM_STAT){
            this.queuedStates.add(sadState);
        }
        if(
            hygiene > DEFAULT_MINIMUM_STAT &&
            energy > DEFAULT_MINIMUM_STAT &&
            fullness > DEFAULT_MINIMUM_STAT &&
            fitness > DEFAULT_MINIMUM_STAT &&
            happiness > DEFAULT_MINIMUM_STAT){
                this.queuedStates.add(normalState);
        }
    }

    public PetState popOffQueuedState(){
        PetState newPetState = queuedStates.poll();
        return newPetState;
    }

    public boolean checkIfNeedsPenalty(){
        if(currentState != normalState && !isEgg){
            return false;
        }
        return true;
    }

    public void addObserver(PetObserver observer) {
        if (petObservers == null) {
            petObservers = new ArrayList<>();
        }
        petObservers.add(observer);
    }

    public void removeObserver(PetObserver observer) {
        if (petObservers != null) {
            petObservers.remove(observer);
        }
    }

    private void notifyObservers(PetEvent event) {
        if (petObservers == null) {
            return;
        }

        for (PetObserver observer : petObservers) {
            observer.onPetEvent(event, this);
        }
    }
}
