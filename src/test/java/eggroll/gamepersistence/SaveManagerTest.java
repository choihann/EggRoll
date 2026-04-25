package eggroll.gamepersistence;

import eggroll.pet.Cat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SaveManagerTest {
    private SaveManager saveManager;
    private String saveName;

    @BeforeEach
    void setUp() {
        saveName = "test-save-" + UUID.randomUUID();
        saveManager = new SaveManager(saveName);
        saveManager.deleteSave();
    }

    @AfterEach
    void unSetUp() {
        saveManager.deleteSave();
    }

    @Test
    void testSaveDoesCreateSaveFile() {
        EggRoll game = EggRoll.newGame();

        saveManager.save(game);

        assertTrue(saveManager.saveExists());
    }

    @Test
    void testSaveExistsReturnsFalseWhenNoSaveFileExists() {
        assertFalse(saveManager.saveExists());
    }

    @Test
    void testDeleteSaveRemovesSaveFile() {
        EggRoll game = EggRoll.newGame();
        saveManager.save(game);

        assertTrue(saveManager.saveExists());

        saveManager.deleteSave();

        assertFalse(saveManager.saveExists());
    }

    @Test
    void testLoadReturnsNewGameWhenNoSaveExists() {
        EggRoll loadedGame = saveManager.load();

        assertEquals(2000, loadedGame.currency);
        assertEquals(EggRoll.ACTIONS_PER_DAY, loadedGame.actionsRemaining);
        assertEquals(1, loadedGame.dayCount);
    }

    @Test
    void testLoadReturnsSavedGameData() {
        EggRoll game = EggRoll.newGame();
        game.currency = 1234;
        game.dayCount = 7;
        game.actionsRemaining = 2;

        saveManager.save(game);

        EggRoll loadedGame = saveManager.load();

        assertEquals(1234, loadedGame.currency);
        assertEquals(7, loadedGame.dayCount);
        assertEquals(2, loadedGame.actionsRemaining);
    }

    @Test
    void testSaveAndLoadPreservesOwnedPets() {
        EggRoll game = EggRoll.newGame();
        Cat cat = new Cat();

        game.ownedPets.add(cat);

        saveManager.save(game);

        EggRoll loadedGame = saveManager.load();

        assertEquals(1, loadedGame.ownedPets.size());
        assertNotNull(loadedGame.ownedPets.get(0));
    }

    @Test
    void testLoadInitializesPetStatesAfterLoading() {
        EggRoll game = EggRoll.newGame();
        Cat cat = new Cat();

        game.ownedPets.add(cat);

        saveManager.save(game);

        EggRoll loadedGame = saveManager.load();

        assertNotNull(loadedGame.ownedPets.get(0).getUnbornState());
        assertNotNull(loadedGame.ownedPets.get(0).getNormalState());
        assertNotNull(loadedGame.ownedPets.get(0).getCurrentState());
    }
}