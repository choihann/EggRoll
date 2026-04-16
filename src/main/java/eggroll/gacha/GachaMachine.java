package eggroll.gacha;

import eggroll.gamepersistence.EggRoll;

import java.util.Random;

public abstract class GachaMachine {

    private static final int COST_ONE_PULL = 100;
    private static final int COST_TEN_PULL = 950;

    protected final EggRoll gameState;
    protected final Random random = new Random();

    private final String title;
    private final String description;

    protected GachaMachine(EggRoll gameState, String title, String description) {
        this.gameState = gameState;
        this.title = title;
        this.description = description;
    }

    public abstract GachaRarity determineGachaRarity();

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
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