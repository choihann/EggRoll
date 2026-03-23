package eggroll.pet.petstate;

public interface PetState {
    public void nap(int maximumEnergy);
    public void eat(int maximumFullness);
    public void exercise(int maximumFitness);
    public void play(int maximumHappiness);
    public void bathe(int maximumHygiene);
    public void evolve(int maximumHappiness);
    //public void changeState(int minimumStat);
}
