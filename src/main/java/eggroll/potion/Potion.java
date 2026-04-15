package eggroll.potion;

import eggroll.gacha.GachaRarity;
import eggroll.pet.Pet;
import eggroll.pet.PetStatType;
import org.slf4j.Logger;


public class Potion implements IPotion {
    static Logger logger = org.slf4j.LoggerFactory.getLogger(Potion.class);
    // TODO: Replace the emojis with the images
    private final String id;
    private final String name;
    private final String emoji;
    private final String description;
    private final GachaRarity rarity;
    private final PetStatType statToIncrease;
    private final int potency;

    public Potion(String id, String name, String emoji, String description, GachaRarity rarity, PetStatType statToIncrease, int potency) {
        this.id = id;
        this.name = name;
        this.emoji = emoji;
        this.description = description;
        this.rarity = rarity;
        this.statToIncrease = statToIncrease;
        this.potency = potency;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getPotionName() {
        return name;
    }

    @Override
    public String getPotionImage() {
        return emoji;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public GachaRarity getRarity() {
        return rarity;
    }

    @Override
    public void drink(Pet pet) {
        pet.increaseStat(potency, statToIncrease);
        logger.info("[Potion] Used {} on {} - {} increased by {}", getPotionName(), pet.getName(), statToIncrease, potency);
    }
}
