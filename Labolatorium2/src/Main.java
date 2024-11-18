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

        Animal dog = new Animal("Reksio", "Pies", AnimalCondition.ZDROWE, 13, 1200.0);

        Animal cat = new Animal("Raku Chan", "Kot", AnimalCondition.ZDROWE, 9, 500.0);

        Animal szynszyla = new Animal("Śmietnik", "Tajpan Pustynny", AnimalCondition.CHORE, 2, 2100.0);


        shelterA.addAnimal(dog);

        shelterA.addAnimal(cat);

        shelterA.addAnimal(szynszyla);


        shelterA.summary();

        manager.summary();


    }
}