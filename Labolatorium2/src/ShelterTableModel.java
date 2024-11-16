import javax.swing.table.AbstractTableModel;
import javax.swing.JTable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ShelterTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Nazwa", "Obecna liczba", "Maksymalna liczba"};
    private List<AnimalShelter> shelters;
    private List<AnimalShelter> allShelters; // Pełna lista schronisk dla filtrowania

    public ShelterTableModel(List<AnimalShelter> shelters) {
        this.shelters = new ArrayList<>(shelters);
        this.allShelters = new ArrayList<>(shelters);
    }

    @Override
    public int getRowCount() {
        return shelters.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AnimalShelter shelter = shelters.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> shelter.getShelterName();
            case 1 -> shelter.getCurrentCapacity();
            case 2 -> shelter.getMaxCapacity();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public void sortByColumn(int columnIndex) {
        Comparator<AnimalShelter> comparator = switch (columnIndex) {
            case 0 -> Comparator.comparing(AnimalShelter::getShelterName);
            case 1 -> Comparator.comparingInt(AnimalShelter::getCurrentCapacity);
            case 2 -> Comparator.comparingInt(AnimalShelter::getMaxCapacity);
            default -> throw new IllegalArgumentException("Nieznana kolumna: " + columnIndex);
        };

        Collections.sort(shelters, comparator);
        fireTableDataChanged();
    }

    public static void enableSorting(JTable table, ShelterTableModel model) {
        table.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int column = table.columnAtPoint(e.getPoint());
                model.sortByColumn(column);
            }
        });
    }

    // Metoda filtrowania po nazwie
    public void filterByName(String query) {
        if (query == null || query.isEmpty()) {
            shelters = new ArrayList<>(allShelters); // Resetuje filtr
        } else {
            shelters = allShelters.stream()
                    .filter(shelter -> shelter.getShelterName().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toList());
        }
        fireTableDataChanged();
    }

    // Metoda filtrowania po stanie
    public void filterByState(String state) {
        if ("Wszystkie".equals(state)) {
            shelters = new ArrayList<>(allShelters); // Resetuje filtr
        } else if ("Puste".equals(state)) {
            shelters = allShelters.stream()
                    .filter(shelter -> shelter.getCurrentCapacity() == 0)
                    .collect(Collectors.toList());
        } else if ("Pełne".equals(state)) {
            shelters = allShelters.stream()
                    .filter(shelter -> shelter.getCurrentCapacity() >= shelter.getMaxCapacity())
                    .collect(Collectors.toList());
        }
        fireTableDataChanged();
    }
}
