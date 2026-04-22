package eggroll.pet.petfactory;

import eggroll.gacha.GachaRarity;
import eggroll.pet.Dog;
import eggroll.pet.Pet;

import java.util.List;

public class DogFactory extends PetFactory {
    private static final List<String> COMMON_SPECIES = List.of("Biscuit", "Daisy", "Coco", "Archie");
    private static final List<String> RARE_SPECIES = List.of("Ranger", "Bruno", "Bella");
    private static final List<String> EPIC_SPECIES = List.of("Fenrir", "Anubis");


    @Override
    public Pet createPet(GachaRarity rarity) {
        String name = switch (rarity) {
            case Rare -> selectFromPetPool(RARE_SPECIES);
            case Epic -> selectFromPetPool(EPIC_SPECIES);
            default -> selectFromPetPool(COMMON_SPECIES);
        };
        return new Dog(name, rarity);
    }
}
