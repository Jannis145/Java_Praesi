package Praesis_Jannis;

import javax.swing.*;

public class Datenschutzrichtlinien {
    public void anzeigen() {
        JFrame datenschutzrichtlinien = new JFrame("Datenschutzrichtlinien");
        JLabel inhalt = new JLabel();
        inhalt.setText("Keine der Daten werden an den Server weitergeleitet. Die Daten gelangen nicht in "
                + "das Internet. Niemand außer ihnen hat Zugang zu ihren Präsentationen.");
        datenschutzrichtlinien.add(inhalt);
        datenschutzrichtlinien.pack();
        datenschutzrichtlinien.setVisible(true);
    }
}
