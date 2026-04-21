package eggroll.gamepersistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import eggroll.pet.Cat;
import eggroll.pet.Dog;
import eggroll.pet.Pet;
import eggroll.potion.Potion;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class SaveManager {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(SaveManager.class);


    private static final String SAVE_DIR = "src/main/resources/saves/";
    private static final Gson GSON = buildGson();
    private final Path savePath;

    public SaveManager(String saveName) {
        this.savePath = Path.of(SAVE_DIR + saveName + ".json");
    }

    public void save(EggRoll state) {
        try {
            Files.createDirectories(savePath.getParent());
            Files.writeString(savePath, GSON.toJson(state));
        } catch (IOException ioException) {
            logger.error("[SaveManager] Failed to save: {}", ioException.getMessage());
        }
    }

    public boolean saveExists() {
        return Files.exists(savePath);
    }

    public EggRoll load() {
        if (!Files.exists(savePath)) {
            logger.info("[SaveManager] No save found — starting a new game.");
            return EggRoll.newGame();
        }
        try {
            String json = Files.readString(savePath);
            EggRoll state = GSON.fromJson(json, EggRoll.class);
            state.ownedPets.forEach(Pet::initializeStates);
            return state;
        } catch (IOException ioException) {
            logger.error("[SaveManager] Failed to load: {}", ioException.getMessage());
            return EggRoll.newGame();
        }
    }

    public void deleteSave() {
        try {
            Files.deleteIfExists(savePath);
        } catch (IOException ioException) {
            logger.error("[SaveManager] Failed to delete save: {}", ioException.getMessage());
        }
    }

    private static Gson buildGson() {
        RuntimeTypeAdapterFactory<Pet> petAdapter = RuntimeTypeAdapterFactory.of(Pet.class, "type")
                .registerSubtype(Cat.class, "Cat")
                .registerSubtype(Dog.class, "Dog");

        RuntimeTypeAdapterFactory<Potion> potionAdapter =
                RuntimeTypeAdapterFactory.of(Potion.class, "type")
                        .registerSubtype(Potion.class, "Potion");

        return new GsonBuilder()
                .registerTypeAdapterFactory(petAdapter)
                .registerTypeAdapterFactory(potionAdapter)
                .setPrettyPrinting()
                .create();
    }
}