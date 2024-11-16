import javax.swing.*;

import java.awt.*;

public class AnimalPanel {

    private AnimalShelter shelter;


    public AnimalPanel(AnimalShelter shelter) {

        this.shelter = shelter;

    }

    public void show() {

        JFrame frame = new JFrame("Zwierzęta w schronisku: " + shelter.getShelterName());

        frame.setSize(600, 400);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        JPanel panel = new JPanel(new BorderLayout());

        frame.add(panel);


        JTable animalTable = new JTable(new AnimalTableModel(shelter.getAnimals()));

        JScrollPane animalScrollPane = new JScrollPane(animalTable);


        panel.add(animalScrollPane, BorderLayout.CENTER);

        frame.setVisible(true);

    }
}
