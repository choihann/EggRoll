package eggroll.pet.petstate;

public class NormalState extends PetState {

    @Override
    public boolean canNap() {
        return true;
    }

    @Override
    public boolean canEat() {
        return true;
    }

    @Override
    public boolean canPlay() {
        return true;
    }

    @Override
    public boolean canBathe() {
        return true;
    }

//    @Override
//    public void nap(int maximumEnergy) {
//        if(pet.getEnergyStat() >= maximumEnergy){
//            System.out.print(pet.getName() + " is not tired enough to sleep.");
//            return;
//        }
//        System.out.print("You tucked in " + pet.getName() + " for a little nap. Energy is now " + pet.getEnergyStat());
//    }
//
//    @Override
//    public void eat(int maximumFullness) {
//        if(pet.getEnergyStat() >= maximumFullness){
//            System.out.print(pet.getName() + " can't manage another single, solitary bite!");
//            return;
//        }
//        System.out.print("You fed " + pet.getName() + ". Fullness is now " + pet.getFullnessStat());
//    }
//
//    @Override
//    public void play(int maximumHappiness) {
//        if(pet.getHappinessStat() >= maximumHappiness){
//            System.out.print(pet.getName() + " is at the height of joy, no more playtime.");
//            return;
//        }
//        System.out.print("You spent quality time with " + pet.getName() + ". Happiness is now " + pet.getEnergyStat());
//    }
//
//    @Override
//    public void bathe(int maximumHygiene) {
//        if(pet.getHygieneStat() >= maximumHygiene){
//            System.out.print(pet.getName() + " is clean enough.");
//            return;
//        }
//        System.out.print("WASH MY BELLAY! said " + pet.getName() + ". Its hygiene is now " + pet.getHygieneStat());
//    }
}
