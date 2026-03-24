package eggroll.pet;

import java.util.List;
import java.util.Random;

import java.util.List;
import java.util.Random;

public class DogFactory {
        private final Random random = new Random();
        private static final List<String> COMMON_SPECIES = List.of("DOG");
        private static final List<String> RARE_SPECIES = List.of("DOG"); // replace with rare pets when added
        private static final List<String> EPIC_SPECIES = List.of("DOG"); // replace this as well

        //@Override
        public Pet createPet(PetRarity rarity) {
            String petSpecies = pickSpecies(rarity);
            return makeDog(petSpecies, rarity);
        }

        // helpers
        private String pickSpecies(PetRarity rarity) {
            List<String> speciesPool = switch (rarity) {
                case Rare -> RARE_SPECIES;
                case Epic -> EPIC_SPECIES;
                default -> COMMON_SPECIES;
            };
            return speciesPool.get(random.nextInt(speciesPool.size()));
        }

        private Pet makeDog(String species, PetRarity rarity) {
            return switch (species.toLowerCase()) {
                case "dog" -> new Dog();
                default -> new Dog(); // populate this once we have more species of cat
            };

        }

}
