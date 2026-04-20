package eggroll.gacha;

import eggroll.gamepersistence.EggRoll;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class GachaMachine {

    private static final int COST_ONE_PULL = 100;
    private static final int COST_TEN_PULL = 950;

    private static final int COMMON_THRESHOLD = 50;
    private static final int RARE_THRESHOLD = 85;
    private static final int RARITY_ROLL_BOUND = 100;
    private static final int TEN_PULL_COUNT = 10;

    protected final EggRoll gameState;
    protected final Random random = new Random();

    protected GachaMachine(EggRoll gameState) {
        this.gameState = gameState;
    }

    public final Object pullOne() {
        if (!canAffordOnePull()) return null;
        chargeOnePull();
        return executePull(determineGachaRarity());
    }

    public final List<?> pullTen() {
        if (!canAffordTenPull()) return List.of();
        chargeTenPull();

        List<Object> results = new ArrayList<>();
        for (int i = 0; i < TEN_PULL_COUNT; i++) {
            results.add(executePull(determineGachaRarity()));
        }
        return results;
    }

    protected abstract Object executePull(GachaRarity rarity);

    public GachaRarity determineGachaRarity() {
        int roll = random.nextInt(RARITY_ROLL_BOUND);
        if (roll < COMMON_THRESHOLD) return GachaRarity.Common;
        if (roll < RARE_THRESHOLD) return GachaRarity.Rare;
        return GachaRarity.Epic;
    }

    public boolean canAffordOnePull() {
        return gameState.currency >= getCostOnePull();
    }

    public boolean canAffordTenPull() {
        return gameState.currency >= getCostTenPull();
    }

    protected void chargeOnePull() {
        gameState.currency -= getCostOnePull();
    }

    protected void chargeTenPull() {
        gameState.currency -= getCostTenPull();
    }

    public int getCostOnePull() {
        return COST_ONE_PULL;
    }

    public int getCostTenPull() {
        return COST_TEN_PULL;
    }
}