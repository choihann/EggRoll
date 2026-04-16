package eggroll.pet.petevolutionstrategy;

public class StrategyFactory {
    public IEvolutionStrategy newUnbornStrategy() { return new UnbornEvolutionStrategy(); }
    public IEvolutionStrategy newJuvenileStrategy() { return new JuvenileEvolutionStrategy(); }
    public IEvolutionStrategy newAdultStrategy() { return new AdultEvolutionStrategy(); }
}
