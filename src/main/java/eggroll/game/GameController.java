package eggroll.game;

import eggroll.command.*;
import eggroll.gacha.GachaMachine;
import eggroll.gacha.StandardGachaMachine;
import eggroll.gamepersistence.DayManager;
import eggroll.gamepersistence.GameState;
import eggroll.gamepersistence.SaveManager;
import eggroll.pet.petfactory.CatFactory;
import eggroll.pet.Pet;
import eggroll.ui.CollectionPanel;
import eggroll.ui.MainWindow;

import java.util.List;

public class GameController {
    //  wiring game logic to UI
    private final MainWindow window;
    private final GameState state;
    private final DayManager dayManager;
    private final GachaMachine gachaMachine;

    public GameController(MainWindow window) {
        this.window = window;
        this.state = SaveManager.load();
        this.dayManager = new DayManager(state);
        this.gachaMachine = new StandardGachaMachine(new CatFactory(), state);

        wireActions();
        wireGacha();
        refreshAll();
    }

    public void startGame() {
        window.setVisible(true);
    }

    private void wireActions() {
        window.actionPanel.setFeedAction(feedPet -> runCommand(new FeedCommand(activePet())));
        window.actionPanel.setPlayAction(playPet -> runCommand(new PlayCommand(activePet())));
        window.actionPanel.setNapAction(napPet -> runCommand(new NapCommand(activePet())));
        window.actionPanel.setBatheAction(bathePet -> runCommand(new BatheCommand(activePet())));

    }

    private void runCommand(Command command) {
        if (activePet() == null) return;
        dayManager.executeAction(command, activePet());
        refreshPetView();
        refreshOverlay();
    }

    private void wireGacha() {
        window.gachaPanel.setRollOneAction(event -> {
            Pet gachaResult = gachaMachine.pullOne();
            if (gachaResult == null) {
                window.navBarOverlay.setNotification("Not enough coins!");
                return;
            }
            SaveManager.save(state);
            window.navBarOverlay.setNotification("");
            window.gachaPanel.showResult(
                    "egg", gachaResult.getName(), gachaResult.getSpecies(), gachaResult.getRarity().name(), null
            );

            window.gachaPanel.applyCanAfford(state.currency);
            refreshOverlay();
            refreshCollection();
        });

        window.gachaPanel.setRollTenAction(event -> {
            List<Pet> gachaResults = gachaMachine.pullTen();
            if (gachaResults.isEmpty()) {
                window.navBarOverlay.setNotification("Not enough coins!");
                return;
            }
            SaveManager.save(state);
            window.navBarOverlay.setNotification("");

            Pet lastResult = gachaResults.get(gachaResults.size() - 1);
            window.gachaPanel.showResult(
                    "egg",
                    lastResult.getName(),
                    lastResult.getSpecies(),
                    lastResult.getRarity().name(),
                    "+" + gachaResults.size() + " pets added to your collection!"
            );
            window.gachaPanel.applyCanAfford(state.currency);
            refreshOverlay();
            refreshCollection();
        });
    }

    private void refreshAll() {
        refreshOverlay();
        refreshPetView();
        refreshCollection();
        window.gachaPanel.applyCanAfford(state.currency);
        window.gachaPanel.updatePity(0, 50); // TODO: wire real pity counter when added
    }

    private void refreshOverlay() {
        window.navBarOverlay.refreshCoins(state.currency);
        Pet pet = activePet();
        if (pet != null) {
            window.navBarOverlay.refreshActivePet(pet.getName(), "placeholder"); // TODO: replace placeholder
        } else {
            window.navBarOverlay.refreshActivePet(null, null);
        }
    }

    private void refreshPetView() {
        Pet pet = activePet();
        if (pet == null) return;
        window.petView.updatePetIdentity(
                pet.getName(),
                pet.getSpecies(),
                "egg",
                pet.getRarity().name(),
                "🥚"
        );
        window.petView.updateStats(
                scaledPetStatusBar(pet.getFullnessStat()),
                scaledPetStatusBar(pet.getHappinessStat()),
                scaledPetStatusBar(pet.getEnergyStat()),
                scaledPetStatusBar(pet.getHygieneStat())
        );
        window.petView.updateLevel(pet.getAge(), 0);
        window.petView.updateStateLabel(currentMoodEmoji(pet));
    }

    private void refreshCollection() {
        if (state.ownedPets == null) return;
        List<CollectionPanel.PetCardData> cards = state.ownedPets.stream()
                .map(pet -> new CollectionPanel.PetCardData(
                        pet.getName(),
                        pet.getName(),
                        pet.getSpecies(),
                        "Baby",
                        pet.getRarity().name(),
                        "🥚",
                        pet.getName().equals(state.activePetName)
                )).toList();
        window.collectionPanel.refreshCollection(cards);

        window.collectionPanel.setSelectListener(petName -> {
            state.activePetName = petName;
            SaveManager.save(state);
            refreshAll();
        });
    }

    private Pet activePet() {
        if (state.ownedPets == null || state.activePetName == null) return null;
        return state.ownedPets.stream()
                .filter(pet -> state.activePetName.equals(pet.getName()))
                .findFirst().orElse(null);
    }

    private int scaledPetStatusBar(int raw) {
        return (int) ((raw / (float) Pet.getDefaultMaxStat()) * 100);
    }

    private String currentMoodEmoji(Pet pet) {
        if (pet.getHappinessStat() >= Pet.getDefaultMaxStat()) return "😊 Happy";
        if (pet.getEnergyStat() <= Pet.getDefaultMinimumStat()) return "😴 Tired";
        if (pet.getFullnessStat() <= Pet.getDefaultMinimumStat()) return "🍖 Hungry";
        if (pet.getHygieneStat() <= Pet.getDefaultMinimumStat()) return "🛁 Dirty";
        return "😊 Content";
    }

}
