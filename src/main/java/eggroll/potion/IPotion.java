package eggroll.potion;

import eggroll.gacha.GachaRarity;

public interface IPotion {
    String getId();

    String getPotionName();

    String getPotionImage();

    String getDescription();

    GachaRarity getRarity();

    void drink(eggroll.pet.Pet pet);
}
