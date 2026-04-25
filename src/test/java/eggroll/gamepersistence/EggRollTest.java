package eggroll.gamepersistence;

import eggroll.command.Command;
import eggroll.pet.Cat;
import eggroll.pet.Pet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EggRollTest {
    private EggRoll game;
    private Pet activePet;

    @BeforeEach
    void setUp() {
        game = EggRoll.newGame();
        activePet = new Cat();
    }

    @Test
    void testNewGameStartsWithAppropriateCurrency() {
        assertEquals(2000, game.currency);
    }

    @Test
    void testNewGameStartsWithCorrectActionsRemaining() {
        assertEquals(EggRoll.ACTIONS_PER_DAY, game.actionsRemaining);
    }

    @Test
    void testNewGameStartsOnDayOne() {
        assertEquals(1, game.dayCount);
    }

    @Test
    void testNewGameStartsWithNoOwnedPets() {
        assertTrue(game.ownedPets.isEmpty());
    }

    @Test
    void testNewGameStartsWithNoPotions() {
        assertTrue(game.potionInventory.isEmpty());
    }

    @Test
    void testExecuteActionRunsCommand() {
        final boolean[] commandWasRun = {false};

        Command command = () -> commandWasRun[0] = true;

        game.executeAction(command, activePet);

        assertTrue(commandWasRun[0]);
    }

    @Test
    void testExecuteActionDecreasesActionsRemaining() {
        Command command = () -> {};

        int initialActionsRemaining = game.actionsRemaining;

        game.executeAction(command, activePet);

        assertEquals(initialActionsRemaining - 1, game.actionsRemaining);
    }

    @Test
    void testExecuteActionDoesNothingWhenNoActionsRemaining() {
        game.actionsRemaining = 0;

        final boolean[] commandWasRun = {false};

        Command command = () -> commandWasRun[0] = true;

        game.executeAction(command, activePet);

        assertFalse(commandWasRun[0]);
        assertEquals(0, game.actionsRemaining);
    }

    @Test
    void testExecuteActionAdvancesDayWhenActionsRunOut() {
        Command command = () -> {};

        game.executeAction(command, activePet);
        game.executeAction(command, activePet);
        game.executeAction(command, activePet);

        assertEquals(2, game.dayCount);
        assertEquals(EggRoll.ACTIONS_PER_DAY, game.actionsRemaining);
    }

    @Test
    void testExecuteActionOnlyAdvancesDayAfterThreeActions() {
        Command command = () -> {};

        game.executeAction(command, activePet);
        game.executeAction(command, activePet);

        assertEquals(1, game.dayCount);
        assertEquals(1, game.actionsRemaining);
    }

    @Test
    void testExecuteActionCanRunMultipleCommandsBeforeAdvancingDay() {
        final int[] commandRunCount = {0};

        Command command = () -> commandRunCount[0]++;

        game.executeAction(command, activePet);
        game.executeAction(command, activePet);
        game.executeAction(command, activePet);

        assertEquals(3, commandRunCount[0]);
        assertEquals(2, game.dayCount);
    }

    @Test
    void testAdvanceDayGrantsCurrencyWhenPetIsInNormalState() {
        activePet.setIsEgg(false);
        activePet.setCurrentState(activePet.getNormalState());

        int initialCurrency = game.currency;

        Command command = () -> {};

        game.executeAction(command, activePet);
        game.executeAction(command, activePet);
        game.executeAction(command, activePet);

        assertEquals(initialCurrency + 50, game.currency);
    }
}