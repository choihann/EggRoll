package eggroll.observer;

public interface IPetObservable {
    void addObserver(PetObserver observer);

    void removeObserver(PetObserver observer);

    void notifyObservers(PetEvent event);
}
