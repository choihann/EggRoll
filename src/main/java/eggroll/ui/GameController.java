package eggroll.ui;

import eggroll.command.*;
import eggroll.gacha.PetGachaMachine;
import eggroll.gacha.PotionGachaMachine;
import eggroll.gamepersistence.EggRoll;
import eggroll.gamepersistence.SaveManager;
import eggroll.pet.Pet;
import eggroll.pet.petfactory.CatFactory;
import eggroll.pet.petfactory.DogFactory;
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
        this.petGacha = new PetGachaMachine(List.of(new CatFactory(), new DogFactory()), state);
        this.potionGacha = new PotionGachaMachine(new PotionFactory(), state);

        wireActions();
        wireGacha();
        wireCollection();
        refreshAll();
        if (activePet() != null) {
            setActivePet(activePet());
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

    private void wireCollection() {
        window.collectionPanel.setSelectListener(petName -> {
            Pet selected = state.ownedPets.stream()
                    .filter(pet -> petName.equals(pet.getId()))
                    .findFirst().orElse(null);
            if (selected != null) {
                setActivePet(selected);
                SaveManager.save(state);
            }
        });
    }

    private void runCommand(Command command) {
        if (activePet() == null) return;
        state.executeAction(command, activePet());
        refreshOverlay();
    }

    private <T> void handlePullOneResult(T result, Runnable showResult, Runnable refresh) {
        if (result == null) {
            window.navBarOverlay.setNotification("Not enough coins!");
            return;
        }

        SaveManager.save(state);
        window.navBarOverlay.setNotification("");
        showResult.run();
        window.gachaPanel.applyCanAfford(state.currency);
        refreshOverlay();
        refresh.run();
    }

    private <T> void handlePullTenResult(List<T> results, Runnable showResult, Runnable refresh) {
        if (results.isEmpty()) {
            window.navBarOverlay.setNotification("Not enough coins!");
            return;
        }

        SaveManager.save(state);
        window.navBarOverlay.setNotification("");
        showResult.run();
        window.gachaPanel.applyCanAfford(state.currency);
        refreshOverlay();
        refresh.run();
    }

    private void wireGacha() {
        window.gachaPanel.setPetRollOneAction(event -> {
            Pet pet = petGacha.pullOne(); // no cast
            handlePullOneResult(pet, () ->
                            window.gachaPanel.showPetResult("🥚", pet.getName(), pet.getSpecies(), pet.getRarity().name(), null),
                    this::refreshCollection
            );
        });

        window.gachaPanel.setPetRollTenAction(event -> {
            List<Pet> pets = petGacha.pullTen();
            Pet last = pets.isEmpty() ? null : pets.get(pets.size() - 1);
            handlePullTenResult(pets, () -> window.gachaPanel.showPetResult("🥚", last.getName(), last.getSpecies(), last.getRarity().name(), "+" + pets.size() + " pets added to your collection!"),
                    this::refreshCollection
            );
        });

        window.gachaPanel.setPotionRollOneAction(event -> {
            Potion potion = potionGacha.pullOne();
            handlePullOneResult(potion, () -> window.gachaPanel.showPotionResult(potion.getPotionImage(), potion.getPotionName(), "Potion", potion.getRarity().name(), potion.getDescription()),
                    this::refreshInventory
            );
        });

        window.gachaPanel.setPotionRollTenAction(event -> {
            List<Potion> potions = potionGacha.pullTen();
            Potion last = potions.isEmpty() ? null : potions.get(potions.size() - 1);
            handlePullTenResult(potions, () ->
                            window.gachaPanel.showPotionResult(last.getPotionImage(), last.getPotionName(), "Potion", last.getRarity().name(), "+" + potions.size() + " potions added to your inventory!"),
                    this::refreshInventory
            );
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
                pet.getEvolutionStage().name(),
                pet.getRarity().name(),
                "🥚"
        );
        window.petView.updateStats(
                scaledPetStatusBar(pet.getFullnessStat(), pet),
                scaledPetStatusBar(pet.getHappinessStat(), pet),
                scaledPetStatusBar(pet.getEnergyStat(), pet),
                scaledPetStatusBar(pet.getHygieneStat(), pet)
        );
        window.petView.updateStateLabel(currentMoodEmoji(pet));
        window.actionPanel.applyPetState(pet.getPetState());
    }

    private void refreshCollection() {
        if (state.ownedPets == null) return;
        List<CollectionPanel.PetCardData> cards = state.ownedPets.stream()
                .map(pet -> new CollectionPanel.PetCardData(
                        pet.getId(),
                        pet.getName(),
                        pet.getSpecies(),
                        pet.getEvolutionStage().name(),
                        pet.getRarity().name(),
                        "🥚",
                        pet.getId().equals(state.activePetId)
                )).toList();
        window.collectionPanel.refreshCollection(cards);
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
        if (state.ownedPets == null || state.activePetId == null) return null;
        return state.ownedPets.stream()
                .filter(pet -> state.activePetId.equals(pet.getId()))
                .findFirst().orElse(null);
    }

    private int scaledPetStatusBar(int raw, Pet pet) {
        return (int) ((raw / (float) pet.getMaxStat()) * 100);
    }

    // TODO: replace later
    private String currentMoodEmoji(Pet pet) {
        if (pet.getHappinessStat() >= Pet.getMinimumStat()) return "😊 Happy";
        if (pet.getEnergyStat() <= Pet.getMinimumStat()) return "😴 Tired";
        if (pet.getFullnessStat() <= Pet.getMinimumStat()) return "🍖 Hungry";
        if (pet.getHygieneStat() <= Pet.getMinimumStat()) return "🛁 Dirty";
        return "😊 Content";
    }

    private void setActivePet(Pet newPet) {
        Pet previousPet = activePet();
        if (previousPet != null) {
            previousPet.removeObserver(window.petView);
            previousPet.removeObserver(window.actionPanel);
        }
        state.activePetId = newPet.getId();
        newPet.addObserver(window.petView);
        newPet.addObserver(window.actionPanel);
        refreshAll();
    }

}
