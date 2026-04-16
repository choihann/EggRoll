package eggroll.pet;

import eggroll.gacha.GachaRarity;
import eggroll.observer.PetEvent;
import eggroll.observer.PetObserver;
import eggroll.pet.petevolutionstrategy.*;
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
    protected PetState hungryState;

    protected PetState currentState;

    private IEvolutionStrategy currentEvolutionStrategy;
    private StrategyFactory strategyFactory;

    protected Queue<PetState> queuedStates = new LinkedList<>();

    protected String name;
    protected String species;
    protected GachaRarity rarity;
    protected boolean needsPenalty;
    protected int hygiene;
    protected int happiness;
    protected int fullness;
    protected int energy;
    protected int age;

    public boolean isEgg;

    private transient Random random = new Random(); // will have to encapsulate this, is used for random decreases

    public Pet(String name, String species, GachaRarity rarity) {
        this.name = name;
        this.species = species;
        this.rarity = rarity;

        this.hygiene = DEFAULT_STARTING_STAT;
        this.happiness = DEFAULT_STARTING_STAT;
        this.fullness = DEFAULT_STARTING_STAT;
        this.energy = DEFAULT_STARTING_STAT;

        this.currentState = unbornState;
        this.currentEvolutionStrategy = strategyFactory.newUnbornStrategy();
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

        this.hygiene = DEFAULT_STARTING_STAT;
        this.happiness = DEFAULT_STARTING_STAT;
        this.fullness = DEFAULT_STARTING_STAT;
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
    public int getHygieneStat(){
        return this.hygiene;
    }
    public int getHappinessStat(){
        return this.happiness;
    }
    public int getFullnessStat(){
        return this.fullness;
    }
    public int getEnergyStat(){
        return this.energy;
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

    public void setCurrentStrategy(IEvolutionStrategy newIEvolutionStrategy) {
        this.currentEvolutionStrategy = newIEvolutionStrategy;
        //do observers need to know when a pet evolves?
        // probably..?
        //notifyObservers(PetEvent.EVOLUTION_OCCURED);
    }

    public void setStat(int amount, PetStatType type) {
        switch (type) {
            case HAPPINESS -> this.happiness = amount;
            case ENERGY -> this.energy = amount;
            case FULLNESS -> this.fullness = amount;
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

    public void increaseStat(int amount, PetStatType type) {
        switch (type) {
            case HAPPINESS -> {
                this.happiness = currentEvolutionStrategy.calculateStatIncrease(this, amount, type);
                notifyObservers(PetEvent.HAPPINESS_CHANGED);
            }
            case ENERGY -> {
                this.energy = currentEvolutionStrategy.calculateStatIncrease(this, amount, type);
                notifyObservers(PetEvent.ENERGY_CHANGED);
            }
            case FULLNESS -> {
                this.fullness = currentEvolutionStrategy.calculateStatIncrease(this, amount, type);
                notifyObservers(PetEvent.FULLNESS_CHANGED);
            }
            case HYGIENE -> {
                this.hygiene = currentEvolutionStrategy.calculateStatIncrease(this, amount, type);
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
            case ENERGY -> {
                this.energy = Math.max(0, this.energy - amount);
                notifyObservers(PetEvent.ENERGY_CHANGED);
            }
            case FULLNESS -> {
                this.fullness = Math.max(0, this.fullness - amount);
                notifyObservers(PetEvent.FULLNESS_CHANGED);
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
        if (hygiene == 0 && happiness == 0 && fullness == 0 && energy == 0) {
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
                    if (energy > 0) {
                        energy = Math.max(0, energy - amount);
                        return;
                    }
                    break;
            }
        }
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

    public boolean canEvolve() {
        return currentEvolutionStrategy.canEvolve(this);
    }

    public void evolve(){
        if(currentEvolutionStrategy.canEvolve(this)){
            changeStrategy();
        }
    };

    private void changeStrategy(){
        if(currentEvolutionStrategy instanceof UnbornEvolutionStrategy){
                setCurrentStrategy((strategyFactory.newJuvenileStrategy()));
        } else if (currentEvolutionStrategy instanceof JuvenileEvolutionStrategy) {
            setCurrentStrategy((strategyFactory.newAdultStrategy()));
        } else {
            return; // must already be an adult.
        }
    }

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
        if(happiness <= DEFAULT_MINIMUM_STAT){
            this.queuedStates.add(sadState);
        }
        if(
            hygiene > DEFAULT_MINIMUM_STAT &&
            energy > DEFAULT_MINIMUM_STAT &&
            fullness > DEFAULT_MINIMUM_STAT &&
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
