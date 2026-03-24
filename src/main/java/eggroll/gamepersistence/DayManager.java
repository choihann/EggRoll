package eggroll.gamepersistence;

import eggroll.command.Command;
import eggroll.pet.Pet;

public class DayManager {
    public static final int ACTIONS_PER_DAY = 3;

    private final GameState state;

    public DayManager(GameState state) {
        this.state = state;
    }

    public void executeAction(Command command, Pet activePet) {
        if (state.actionsRemaining <= 0) {
            System.out.println("[DayManager] No actions remaining — advance the day first.");
            return;
        }

        command.execute();
        activePet.lowerRandomStat(1);

        state.actionsRemaining--;

        if (state.actionsRemaining <= 0) {
            advanceDay(activePet);
        }

        SaveManager.save(state);

    }

    private void advanceDay(Pet activePet) {
        state.dayCount++;
        state.actionsRemaining = ACTIONS_PER_DAY;
        System.out.println("[DayManager] Day " + state.dayCount + " begins!");
    }

    public int getActionsRemaining() {
        return state.actionsRemaining;
    }

    public int getDayCount() {
        return state.dayCount;
    }

    public boolean isDayOver() {
        return state.actionsRemaining <= 0;
    }

}