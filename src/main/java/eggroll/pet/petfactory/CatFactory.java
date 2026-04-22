package eggroll.pet.petfactory;

import eggroll.gacha.GachaRarity;
import eggroll.pet.Cat;
import eggroll.pet.Pet;

import java.util.List;

public class CatFactory extends PetFactory {
    private static final List<String> COMMON_SPECIES = List.of("Mittens", "Luna", "Mochi", "Shrapnel");
    private static final List<String> RARE_SPECIES = List.of("Stellae", "River", "Beryl");
    private static final List<String> EPIC_SPECIES = List.of("Bastet", "Sphinx");

    @Override
    public Pet createPet(GachaRarity rarity) {
        String name = switch (rarity) {
            case Rare -> selectFromPetPool(RARE_SPECIES);
            case Epic -> selectFromPetPool(EPIC_SPECIES);
            default -> selectFromPetPool(COMMON_SPECIES);
        };
        return new Cat(name, rarity);
    }

}
