package eggroll.potion;

import eggroll.gacha.GachaRarity;
import eggroll.pet.PetStatType;

import java.util.List;
import java.util.Random;

public class PotionFactory {

    private final Random random = new Random();

    private static final List<PotionData> COMMON = List.of(
            new PotionData("potion_joy_common", "Joy Potion", "🧃", "Boosts happiness", PetStatType.HAPPINESS, 1),
            new PotionData("potion_energy_common", "Energy Potion", "⚡", "Boosts energy", PetStatType.ENERGY, 1),
            new PotionData("potion_fullness_common", "Fullness Potion", "🍵", "Boosts fullness", PetStatType.HUNGER, 1),
            new PotionData("potion_hygiene_common", "Hygiene Potion", "🫧", "Boosts hygiene", PetStatType.HYGIENE, 1)
    );

    private static final List<PotionData> RARE = List.of(
            new PotionData("potion_joy_rare", "Elixir of Joy", "💜", "Greatly boosts happiness", PetStatType.HAPPINESS, 3),
            new PotionData("potion_energy_rare", "Elixir of Energy", "💙", "Greatly boosts energy", PetStatType.ENERGY, 3),
            new PotionData("potion_fullness_rare", "Elixir of Fullness", "🧡", "Greatly boosts fullness", PetStatType.HUNGER, 3),
            new PotionData("potion_hygiene_rare", "Elixir of Hygiene", "🩵", "Greatly boosts hygiene", PetStatType.HYGIENE, 3)
    );

    private static final List<PotionData> EPIC = List.of(
            new PotionData("potion_joy_epic", "Essence of Euphoria", "✨", "Massively boosts happiness", PetStatType.HAPPINESS, 5),
            new PotionData("potion_energy_epic", "Essence of Vigor", "🌟", "Massively boosts energy", PetStatType.ENERGY, 5),
            new PotionData("potion_fullness_epic", "Essence of Abundance", "🏆", "Massively boosts fullness", PetStatType.HUNGER, 5),
            new PotionData("potion_hygiene_epic", "Essence of Purity", "💎", "Massively boosts hygiene", PetStatType.HYGIENE, 5)
    );

    public Potion createPotion(GachaRarity rarity) {
        PotionData data = pickPotion(rarity);
        return new Potion(
                data.id,
                data.potionName,
                data.emoji,
                data.description,
                rarity,
                data.statToIncrease,
                data.potency
        );
    }

    // helper
    private PotionData pickPotion(GachaRarity rarity) {
        List<PotionData> pool = switch (rarity) {
            case Rare -> RARE;
            case Epic -> EPIC;
            default -> COMMON;
        };
        return pool.get(random.nextInt(pool.size()));
    }

    // to hold the data
    private record PotionData(
            String id,
            String potionName,
            String emoji,
            String description,
            PetStatType statToIncrease,
            int potency
    ) {
    }
}