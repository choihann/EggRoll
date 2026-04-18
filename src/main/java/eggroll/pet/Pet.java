package eggroll.pet;

import eggroll.gacha.GachaRarity;
import eggroll.observer.IPetObservable;
import eggroll.observer.PetEvent;
import eggroll.observer.PetObserver;
import eggroll.pet.petevolutionstrategy.*;
import eggroll.pet.petstate.IPetState;
import eggroll.pet.petstate.PetState;
import eggroll.pet.petstate.StateFactory;

import java.util.*;

abstract public class Pet implements IPet, IPetObservable {
    protected final static int STARTING_STAT = 3;
    protected final static int MINIMUM_STAT = 2;
    protected final static int STAT_INCREMENT = 1;
    protected final static int STAT_DECREMENT = 1;
    private transient List<PetObserver> petObservers = new ArrayList<>();

    public static int getStartingStat() {
        return STARTING_STAT;
    }

    public static int getMinimumStat() {
        return MINIMUM_STAT;
    }

    protected PetState unbornState;
    protected PetState normalState;
    protected PetState dirtyState;
    protected PetState tiredState;
    protected PetState sadState;
    protected PetState hungryState;

    protected PetState currentState;

    protected EvolutionStrategy currentEvolutionStrategy;
    protected StrategyFactory strategyFactory;
    protected StateFactory stateFactory;

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

        this.unbornState = stateFactory.newUnbornState(this);
        this.normalState = stateFactory.newNormalState(this);
        this.dirtyState = stateFactory.newDirtyState(this);
        this.tiredState = stateFactory.newTiredState(this);
        this.hungryState = stateFactory.newHungryState(this);

        this.hygiene = STARTING_STAT;
        this.happiness = STARTING_STAT;
        this.fullness = STARTING_STAT;
        this.energy = STARTING_STAT;

        this.currentState = unbornState;
        this.currentEvolutionStrategy = strategyFactory.newUnbornStrategy();
        this.needsPenalty = false;
        this.isEgg = true;
    }

    protected Pet() {
    }

    public Pet(String name, GachaRarity rarity) {
        this.name = name;
        this.species = "NULL";
        this.rarity = rarity;

        this.hygiene = STARTING_STAT;
        this.happiness = STARTING_STAT;
        this.fullness = STARTING_STAT;
        this.energy = STARTING_STAT;

        this.currentState = unbornState;
        this.needsPenalty = false;
        this.isEgg = true;
    }

    public String getName(){
        return this.name;
    }
    public String getSpecies(){
        return this.species;
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
    public Set<PetStatType> getPenalizableStats() {return this.currentState.getPenalizableStats();}

    public void setCurrentState(PetState newState) {
        this.currentState = newState;
        notifyObservers(PetEvent.STATE_CHANGED);
    }

    public void setCurrentStrategy(EvolutionStrategy newEvolutionStrategy) {
        this.currentEvolutionStrategy = newEvolutionStrategy;
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

    public boolean applyPenalty(boolean needsPenalty){
        if(needsPenalty){
            lowerRandomStat(STAT_DECREMENT, currentState.getPenalizableStats());
        }
    }

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

    public int getMaxStat(){
        return currentEvolutionStrategy.getMaxStat();
    }

    public boolean isStatMax(){
        if(this.happiness >= currentEvolutionStrategy.getMaxStat()){
            return true;
        }
        return false;
    }

    public void lowerRandomStat(int amount, Set<PetStatType> penalizableStats) {
        if (penalizableStats.isEmpty()) {
            return;
        }

        int choice = (int) (Math.random() * penalizableStats.size());
        int i = 0;

        for (PetStatType statType : penalizableStats) {
            if (i == choice) {
                switch (statType) {
                    case HYGIENE:
                        hygiene = Math.max(0, hygiene - amount);
                        return;
                    case HAPPINESS:
                        happiness = Math.max(0, happiness - amount);
                        return;
                    case FULLNESS:
                        fullness = Math.max(0, fullness - amount);
                        return;
                    case ENERGY:
                        energy = Math.max(0, energy - amount);
                        return;
                }
            }
            i++;
        }
    }

    public void nap(){
        if (currentState.canNap()) {
            increaseStat(STAT_INCREMENT, PetStatType.ENERGY);
        }
    }
    public void eat(){
        if (currentState.canEat()) {
            increaseStat(STAT_INCREMENT, PetStatType.FULLNESS);
        }
    }
    public void play(){
        if (currentState.canPlay()) {
            increaseStat(STAT_INCREMENT, PetStatType.HAPPINESS);
        }
    }
    public void bathe(){
        if (currentState.canBathe()) {
            increaseStat(STAT_INCREMENT, PetStatType.HYGIENE);
        }
    }

    public boolean canEvolve() {
        return currentEvolutionStrategy.canEvolve(this);
    }

    public void evolve(){
        if(canEvolve()){
            changeStrategy();
        }
    };

    private void changeStrategy(){
        if(currentEvolutionStrategy instanceof UnbornEvolutionStrategy){
                setCurrentStrategy((strategyFactory.newJuvenileStrategy()));
                setIsEgg(false);
        } else if (currentEvolutionStrategy instanceof JuvenileEvolutionStrategy) {
            setCurrentStrategy((strategyFactory.newAdultStrategy()));
        } else {
            return; // must already be an adult.
        }
    }

    public PetState determineNextState(){ // Branch logic hard-codes a priority,
        // Unborn tree
        if(this.happiness == currentEvolutionStrategy.getMaxStat() && isEgg){ // "evolve" the state part of the pet.
            return this.normalState;
        } else if (isEgg) {
            return this.unbornState;
        }
        // Alive tree
        if(this.fullness < MINIMUM_STAT ){ // #1 Hunger
            return this.hungryState;
        } else if ( this.energy < MINIMUM_STAT ) { // #2 Tiredness
            return this.tiredState;
        } else if ( this.hygiene < MINIMUM_STAT ) { // #3 Dirtiness
            return this.dirtyState;
        } else { // if none of those stats are below threshhold, we are good.
            return this.normalState;
        }
    }
    public void advanceState(){ // call this at the beginning of each turn?
        setCurrentState(determineNextState());
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

    // TODO: Should notify be public? It was private, but then I implemented IPetObservable, so I couldn't keep it private
    // TODO: So I was considering whether or not I should remove it from IPetObservable to keep it private on pet
    // TODO: after all, observers shouldn't be triggered externally
    public void notifyObservers(PetEvent event) {
        if (petObservers == null) {
            return;
        }

        for (PetObserver observer : petObservers) {
            observer.onPetEvent(event, this);
        }
    }
}
