package eggroll.pet;

public class CatFactory extends PetFactory{

    @Override
    Pet createPet() {
        return new Cat();
    }

}
