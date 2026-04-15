package eggroll.gamepersistence;

import eggroll.pet.Pet;
import eggroll.potion.Potion;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    // TODO: Change this, honestly I did this for testing
    private static final int STARTING_CURRENCY_AMOUNT = 2000;

    public List<Pet> ownedPets = new ArrayList<>();
    public List<Potion> potionInventory = new ArrayList<>();

    public String activePetName;

    public int currency;

    public int actionsRemaining;

    public int dayCount;

    public static GameState newGame() {
        GameState state = new GameState();
        state.currency = STARTING_CURRENCY_AMOUNT;
        state.actionsRemaining = DayManager.ACTIONS_PER_DAY;
        state.dayCount = 1;
        return state;
    }
}