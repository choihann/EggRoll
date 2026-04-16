package eggroll.pet.petstate;

public interface PetState {
    public void nap(int maximumEnergy);
    public void eat(int maximumFullness);
    public void play(int maximumHappiness);
    public void bathe(int maximumHygiene);
    public void changeState(int minimumStat);
}
