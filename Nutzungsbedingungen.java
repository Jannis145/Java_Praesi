// Nutzungsbedingungen.java
package Praesis_Jannis;

import javax.swing.*;

public class Nutzungsbedingungen {
    public void anzeigen() {
        JFrame nutzungsbedingungen = new JFrame("Nutzungsbedingungen");
        JLabel inhalt = new JLabel();
        inhalt.setText("Nur für private Nutzung zugelassen. Nicht als Eigenprojekt vorstellen. \n"
                + "Datendiebstahl wird bemerkt und strafrechtlich verfolgt. Dabei ist mit einem Bußgeld von"
                + " mindestens 100€ zu rechnen.");
        nutzungsbedingungen.add(inhalt);
        nutzungsbedingungen.pack();
        nutzungsbedingungen.setVisible(true);
    }
}