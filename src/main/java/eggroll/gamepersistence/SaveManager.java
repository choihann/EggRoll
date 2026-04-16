package eggroll.gamepersistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import eggroll.pet.Cat;
import eggroll.pet.Pet;
import eggroll.potion.Potion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class SaveManager {

    public static final Path SAVE_PATH = Path.of("src", "main", "resources", "save.json");

    private static final Gson GSON = buildGson();

    public static void save(EggRoll state) {
        try {
            Files.createDirectories(SAVE_PATH.getParent());
            Files.writeString(SAVE_PATH, GSON.toJson(state));
        } catch (IOException ioException) {
            System.err.println("[SaveManager] Failed to save: " + ioException.getMessage());
        }
    }

    public static EggRoll load() {
        if (!Files.exists(SAVE_PATH)) {
            System.out.println("[SaveManager] No save found — starting a new game.");
            return EggRoll.newGame();
        }
        try {
            String json = Files.readString(SAVE_PATH);
            return GSON.fromJson(json, EggRoll.class);
        } catch (IOException ioException) {
            System.err.println("[SaveManager] Failed to load: " + ioException.getMessage());
            return EggRoll.newGame();
        }
    }

    public static void deleteSave() {
        try {
            Files.deleteIfExists(SAVE_PATH);
        } catch (IOException ioException) {
            System.err.println("[SaveManager] Failed to delete save: " + ioException.getMessage());
        }
    }

    private static Gson buildGson() {
        RuntimeTypeAdapterFactory<Pet> petAdapter = RuntimeTypeAdapterFactory.of(Pet.class, "type")
                .registerSubtype(Cat.class, "Cat");
        // .registerSubtype(Dog.class, "Dog") or whatever we decide on new stuff to be

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