package eggroll.gamepersistence;

import eggroll.command.Command;
import eggroll.pet.Pet;
import eggroll.potion.Potion;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class EggRoll {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(EggRoll.class);

    // TODO: Change this, honestly I did this for testing
    private static final int STARTING_CURRENCY_AMOUNT = 2000;
    public static final int ACTIONS_PER_DAY = 3;

    public List<Pet> ownedPets = new ArrayList<>();
    public List<Potion> potionInventory = new ArrayList<>();

    public String activePetId;

    public int currency;

    public int actionsRemaining;

    public int dayCount;

    public static EggRoll newGame() {
        EggRoll state = new EggRoll();
        state.currency = STARTING_CURRENCY_AMOUNT;
        state.actionsRemaining = ACTIONS_PER_DAY;
        state.dayCount = 1;
        return state;
    }

    public void executeAction(Command command, Pet activePet) {
        if (actionsRemaining <= 0) {
            logger.info("[DayManager] No actions remaining — advance the day first.");
            return;
        }

        command.execute();
        actionsRemaining--;

        if (actionsRemaining <= 0) {
            advanceDay(activePet);
        }
    }

    private void advanceDay(Pet activePet) {
        dayCount++;
        actionsRemaining = ACTIONS_PER_DAY;
        activePet.evolve();
        activePet.advanceState();
        activePet.lowerRandomStat(1, activePet.getPenalizableStats());
        logger.info("[DayManager] Day {} begins!", dayCount);
    }

}