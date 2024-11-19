public class Main {

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(() -> {

            LoginScreen loginScreen = new LoginScreen();

            loginScreen.show();

        });

        ShelterManager manager = new ShelterManager();

        manager.addShelter("Schronisko A", 10);

        manager.addShelter("Schronisko B", 5);


        AnimalShelter shelterA = manager.getShelter("Schronisko A");

        Animal szynszyla = new Animal("Michaś", "Tajpan Pustynny", AnimalCondition.CHORE, 2, 2100.0);

        Animal dog = new Animal("Reksio", "Pies", AnimalCondition.ZDROWE, 13, 1200.0);

        Animal cat = new Animal("Raku Chan", "Kot", AnimalCondition.ZDROWE, 10, 500.0);


        AnimalShelter shelterB = manager.getShelter("Schronisko B");

        Animal dogulla = new Animal("Dogullson", "Pies", AnimalCondition.ZDROWE, 4, 800.0);

        Animal hamster = new Animal("Pipson", "Gryzoń", AnimalCondition.CHORE, 1, 200.0);

        shelterB.addAnimal(dogulla);

        shelterB.addAnimal(hamster);


        shelterA.addAnimal(dog);

        shelterA.addAnimal(cat);

        shelterA.addAnimal(szynszyla);


        shelterA.summary();

        shelterB.summary();

        manager.summary();


    }
}
