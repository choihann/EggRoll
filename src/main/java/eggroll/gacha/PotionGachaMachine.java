package eggroll.gacha;

import eggroll.gamepersistence.GameState;
import eggroll.potion.Potion;
import eggroll.potion.PotionFactory;

import java.util.ArrayList;
import java.util.List;

public class PotionGachaMachine extends GachaMachine {

    private final PotionFactory potionFactory;

    public PotionGachaMachine(PotionFactory potionFactory, GameState state) {
        super(state, "Potion Gacha", "Get some potions!");
        this.potionFactory = potionFactory;
    }

    // TODO: Magic number alert, discuss rates
    @Override
    public GachaRarity determineGachaRarity() {
        int roll = random.nextInt(100);
        if (roll < 50) return GachaRarity.Common;
        if (roll < 85) return GachaRarity.Rare;
        return GachaRarity.Epic;
    }

    public Potion pullOne() {
        if (!canAffordOnePull()) return null;

        chargeOnePull();

        GachaRarity rarity = determineGachaRarity();
        Potion potion = potionFactory.createPotion(rarity);

        gameState.potionInventory.add(potion);
        return potion;
    }

    public List<Potion> pullTen() {
        if (!canAffordTenPull()) return List.of();

        chargeTenPull();

        List<Potion> results = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            GachaRarity rarity = determineGachaRarity();
            Potion potion = potionFactory.createPotion(rarity);

            gameState.potionInventory.add(potion);
            results.add(potion);
        }

        return results;
    }
}