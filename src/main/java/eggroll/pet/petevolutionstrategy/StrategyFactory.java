package eggroll.pet.petevolutionstrategy;

public class StrategyFactory {
    public EvolutionStrategy newUnbornStrategy() { return new UnbornEvolutionStrategy(); }
    public EvolutionStrategy newJuvenileStrategy() { return new JuvenileEvolutionStrategy(); }
    public EvolutionStrategy newAdultStrategy() { return new AdultEvolutionStrategy(); }
}
