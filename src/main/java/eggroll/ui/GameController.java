package eggroll.ui;

import eggroll.command.*;
import eggroll.gacha.PetGachaMachine;
import eggroll.gacha.PotionGachaMachine;
import eggroll.gamepersistence.EggRoll;
import eggroll.gamepersistence.SaveManager;
import eggroll.pet.Pet;
import eggroll.pet.petfactory.CatFactory;
import eggroll.potion.Potion;
import eggroll.potion.PotionFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GameController {
    //  wiring game logic to UI
    private final MainWindow window;
    private final EggRoll state;
    private final PetGachaMachine petGacha;
    private final PotionGachaMachine potionGacha;

    public GameController(MainWindow window) {
        this.window = window;
        this.state = SaveManager.load();
        // TODO: New is bad?
        this.petGacha = new PetGachaMachine(new CatFactory(), state);
        this.potionGacha = new PotionGachaMachine(new PotionFactory(), state);

        wireActions();
        wireGacha();
        refreshAll();
        if (activePet() != null) {
            attachObservers(activePet());
        }
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
        state.executeAction(command, activePet());
        refreshOverlay();
    }

    private void wireGacha() {
        window.gachaPanel.setPetRollOneAction(event -> {
            Pet pet = petGacha.pullOne();

            if (pet == null) {
                window.navBarOverlay.setNotification("Not enough coins!");
                return;
            }

            SaveManager.save(state);
            window.navBarOverlay.setNotification("");

            window.gachaPanel.showPetResult(
                    "🥚",
                    pet.getName(),
                    pet.getSpecies(),
                    pet.getRarity().name(),
                    null
            );

            window.gachaPanel.applyCanAfford(state.currency);
            refreshOverlay();
            refreshCollection();
        });

        window.gachaPanel.setPetRollTenAction(event -> {
            List<Pet> pets = petGacha.pullTen();

            if (pets.isEmpty()) {
                window.navBarOverlay.setNotification("Not enough coins!");
                return;
            }

            SaveManager.save(state);
            window.navBarOverlay.setNotification("");

            Pet last = pets.get(pets.size() - 1);

            window.gachaPanel.showPetResult(
                    "🥚",
                    last.getName(),
                    last.getSpecies(),
                    last.getRarity().name(),
                    "+" + pets.size() + " pets added to your collection!"
            );

            window.gachaPanel.applyCanAfford(state.currency);
            refreshOverlay();
            refreshCollection();
        });

        window.gachaPanel.setPotionRollOneAction(event -> {
            Potion potion = potionGacha.pullOne();

            if (potion == null) {
                window.navBarOverlay.setNotification("Not enough coins!");
                return;
            }

            SaveManager.save(state);
            window.navBarOverlay.setNotification("");

            window.gachaPanel.showPotionResult(
                    potion.getPotionImage(),
                    potion.getPotionName(),
                    "Potion",
                    potion.getRarity().name(),
                    potion.getDescription()
            );

            window.gachaPanel.applyCanAfford(state.currency);
            refreshOverlay();
            refreshInventory();
        });

        window.gachaPanel.setPotionRollTenAction(event -> {
            List<Potion> potions = potionGacha.pullTen();

            if (potions.isEmpty()) {
                window.navBarOverlay.setNotification("Not enough coins!");
                return;
            }

            SaveManager.save(state);
            window.navBarOverlay.setNotification("");

            Potion last = potions.get(potions.size() - 1);

            window.gachaPanel.showPotionResult(
                    last.getPotionImage(),
                    last.getPotionName(),
                    "Potion",
                    last.getRarity().name(),
                    "+" + potions.size() + " potions added to your inventory!"
            );

            window.gachaPanel.applyCanAfford(state.currency);
            refreshOverlay();
            refreshInventory();
        });
    }

    private void refreshAll() {
        refreshOverlay();
        refreshPetView();
        refreshCollection();
        refreshInventory();
        window.gachaPanel.applyCanAfford(state.currency);
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
        // TODO: LEVEL?
        // window.petView.updateLevel(pet.getAge(), 0);
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
            attachObservers(activePet());
            SaveManager.save(state);
            refreshAll();
        });
    }

    private void refreshInventory() {
        if (state.potionInventory == null) return;

        Map<String, Long> potionQuantityById = state.potionInventory.stream()
                .collect(Collectors.groupingBy(Potion::getId, Collectors.counting()));

        List<InventoryPanel.ItemData> potions = state.potionInventory.stream()
                .collect(Collectors.toMap(
                        Potion::getId,
                        representativePotion -> representativePotion,
                        (keptPotion, ignoredPotion) -> keptPotion
                ))
                .values()
                .stream()
                .map(representativePotion -> new InventoryPanel.ItemData(
                        representativePotion.getId(),
                        representativePotion.getPotionName(),
                        representativePotion.getPotionImage(),
                        representativePotion.getDescription(),
                        potionQuantityById.get(representativePotion.getId()).intValue()
                ))
                .toList();

        window.inventoryPanel.refreshInventory(potions);

        window.inventoryPanel.setUseListener(itemId -> {
            Potion potion = state.potionInventory.stream()
                    .filter(p -> p.getId().equals(itemId))
                    .findFirst()
                    .orElse(null);

            if (potion == null) return;

            Pet pet = activePet();
            if (pet == null) return;

            potion.drink(pet);

            state.potionInventory.remove(potion);

            SaveManager.save(state);

            refreshInventory();
            refreshPetView();
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

    // TODO: replace later
    private String currentMoodEmoji(Pet pet) {
        if (pet.getHappinessStat() >= Pet.getDefaultMaxStat()) return "😊 Happy";
        if (pet.getEnergyStat() <= Pet.getDefaultMinimumStat()) return "😴 Tired";
        if (pet.getFullnessStat() <= Pet.getDefaultMinimumStat()) return "🍖 Hungry";
        if (pet.getHygieneStat() <= Pet.getDefaultMinimumStat()) return "🛁 Dirty";
        return "😊 Content";
    }

    private void attachObservers(Pet pet) {
        pet.addObserver(window.petView);
        pet.addObserver(window.actionPanel);
    }

}
