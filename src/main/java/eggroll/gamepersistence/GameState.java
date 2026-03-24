package eggroll.gamepersistence;

import eggroll.pet.Pet;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    public List<Pet> ownedPets = new ArrayList<>();

    public String activePetName;

    public int currency;

    public int actionsRemaining;

    public int dayCount;

    public static GameState newGame() {
        GameState state = new GameState();
        state.currency = 100;
        state.actionsRemaining = DayManager.ACTIONS_PER_DAY;
        state.dayCount = 1;
        return state;
    }
}