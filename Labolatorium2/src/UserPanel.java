import javax.swing.*;

import java.awt.*;

import java.awt.event.*;

import java.util.ArrayList;


public class UserPanel {

    private ShelterManager manager;

    private Point mouseClickPoint;


    public UserPanel() {

        manager = new ShelterManager();

        // Przykładowe dane

        manager.addShelter("Schronisko A", 10);

        manager.addShelter("Schronisko B", 5);

    }


    public void show() {

        JFrame frame = new JFrame();

        frame.setSize(800, 500);

        frame.setUndecorated(true);

        frame.setLocationRelativeTo(null);


        // Umożliwienie przeciągania okna

        frame.addMouseListener(new MouseAdapter() {

            @Override

            public void mousePressed(MouseEvent e) {

                mouseClickPoint = e.getPoint();

            }

        });


        frame.addMouseMotionListener(new MouseMotionAdapter() {

            @Override

            public void mouseDragged(MouseEvent e) {

                Point currentLocation = e.getLocationOnScreen();

                frame.setLocation(currentLocation.x - mouseClickPoint.x, currentLocation.y - mouseClickPoint.y);

            }

        });


        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.setBackground(Color.WHITE);

        frame.add(mainPanel);


        // Nagłówek

        JPanel headerPanel = new JPanel();

        headerPanel.setBackground(Color.WHITE);


        JLabel headerLabel = new JLabel("USER PANEL", SwingConstants.CENTER);

        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));

        headerLabel.setForeground(new Color(45, 85, 255));

        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        headerPanel.add(headerLabel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);


        // Tabela schronisk

        ShelterTableModel shelterTableModel = new ShelterTableModel(new ArrayList<>(manager.getShelters().values()));

        JTable shelterTable = new JTable(shelterTableModel);

        shelterTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        shelterTable.setRowHeight(30);

        shelterTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));

        shelterTable.getTableHeader().setBackground(new Color(230, 230, 230));


        ShelterTableModel.enableSorting(shelterTable, shelterTableModel);

        JScrollPane shelterScrollPane = new JScrollPane(shelterTable);

        mainPanel.add(shelterScrollPane, BorderLayout.CENTER);


        // Panel przycisków

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        buttonPanel.setBackground(Color.WHITE);

        RoundedButton viewAnimalsButton = new RoundedButton("Pokaż zwierzęta");

        RoundedButton adoptAnimalButton = new RoundedButton("Adoptuj zwierzę");

        RoundedButton closeButton = new RoundedButton("X");

        styleButton(viewAnimalsButton, new Color(45, 85, 255), Color.WHITE);

        styleButton(adoptAnimalButton, new Color(100, 200, 150), Color.WHITE);

        styleButton(closeButton, new Color(255, 0, 0, 180), Color.WHITE);


        viewAnimalsButton.addActionListener(e -> {

            int selectedRow = shelterTable.getSelectedRow();

            if (selectedRow >= 0) {

                String shelterName = (String) shelterTable.getValueAt(selectedRow, 0);

                AnimalShelter shelter = manager.getShelter(shelterName);

                new AnimalPanel(shelter).show();

            } else {

                JOptionPane.showMessageDialog(frame, "Wybierz schronisko, aby zobaczyć zwierzęta!");

            }

        });

        closeButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(viewAnimalsButton);

        buttonPanel.add(adoptAnimalButton);

        buttonPanel.add(closeButton); // Dodanie przycisku zamknięcia do dolnego panelu

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

    }


    // Stylizacja przycisków

    private void styleButton(RoundedButton button, Color bgColor, Color fgColor) {

        button.setFont(new Font("Segoe UI", Font.BOLD, 16));

        button.setForeground(fgColor);

        button.setBackground(bgColor);

        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        button.addMouseListener(new MouseAdapter() {

            @Override

            public void mouseEntered(MouseEvent e) {

                button.setBackground(bgColor.darker());

            }

            @Override

            public void mouseExited(MouseEvent e) {

                button.setBackground(bgColor);

            }


            @Override

            public void mousePressed(MouseEvent e) {

                button.setBackground(bgColor.brighter());

            }

        });
    }

    // Niestandardowa klasa przycisku z zaokrąglonymi rogami

    static class RoundedButton extends JButton {

        public RoundedButton(String text) {

            super(text);

            setFocusPainted(false);

            setContentAreaFilled(false);

            setBorderPainted(false);

        }


        @Override

        protected void paintComponent(Graphics g) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());

            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

            g2.setColor(getForeground());

            FontMetrics fm = g2.getFontMetrics();

            int textWidth = fm.stringWidth(getText());

            int textHeight = fm.getAscent();

            g2.drawString(getText(), (getWidth() - textWidth) / 2, (getHeight() + textHeight) / 2 - 3);

            g2.dispose();

        }
    }
}
