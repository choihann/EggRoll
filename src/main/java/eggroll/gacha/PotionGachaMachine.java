package eggroll.gacha;

import eggroll.gamepersistence.EggRoll;
import eggroll.potion.Potion;
import eggroll.potion.PotionFactory;

public class PotionGachaMachine extends GachaMachine<Potion> {

    private final PotionFactory potionFactory;

    public PotionGachaMachine(PotionFactory potionFactory, EggRoll state) {
        super(state);
        this.potionFactory = potionFactory;
    }

    @Override
    protected Potion executePull(GachaRarity rarity) {
        Potion potion = potionFactory.createPotion(rarity);
        gameState.potionInventory.add(potion);
        return potion;
    }
}