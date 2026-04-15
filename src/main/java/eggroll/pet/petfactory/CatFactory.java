package eggroll.pet.petfactory;

import eggroll.gacha.GachaRarity;
import eggroll.pet.Cat;
import eggroll.pet.Pet;

import java.util.List;
import java.util.Random;

public class CatFactory extends PetFactory {
    private final Random random = new Random();
    private static final List<String> COMMON_SPECIES = List.of("Cat");
    private static final List<String> RARE_SPECIES = List.of("Cat"); // replace with rare pets when added
    private static final List<String> EPIC_SPECIES = List.of("Cat"); // replace this as well

    @Override
    public Pet createPet(GachaRarity rarity) {
        String petSpecies = pickSpecies(rarity);
        return makeCat(petSpecies, rarity);
    }

    // helpers
    private String pickSpecies(GachaRarity rarity) {
        List<String> speciesPool = switch (rarity) {
            case Rare -> RARE_SPECIES;
            case Epic -> EPIC_SPECIES;
            default -> COMMON_SPECIES;
        };
        return speciesPool.get(random.nextInt(speciesPool.size()));
    }

    private Pet makeCat(String species, GachaRarity rarity) {
        return switch (species.toLowerCase()) {
            case "cat" -> new Cat();
            default -> new Cat(); // populate this once we have more species of cat
        };

    }

}
