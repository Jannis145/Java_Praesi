package Praesis_Jannis;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class DateiLadenActionListener implements ActionListener {
    private DateiZeilenweiseAuslesenClass dateiAusleser;
    private JTextArea textfield2;
    private AtomicInteger atint;
    private ArrayList<File> folienDateien;

    public DateiLadenActionListener(DateiZeilenweiseAuslesenClass dateiAusleser, JTextArea textfield2, AtomicInteger atint, ArrayList<File> folienDateien) {
        this.dateiAusleser = dateiAusleser;
        this.textfield2 = textfield2;
        this.atint = atint;
        this.folienDateien = folienDateien;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Implementiere hier die Logik für das Laden der Datei
        try {
            File datei = folienDateien.get(atint.get() - 1); // Beispiel für den Zugriff auf die aktuelle Folie
            Scanner scanner = new Scanner(datei);
            StringBuilder inhalt = new StringBuilder();

            while (scanner.hasNextLine()) {
                inhalt.append(scanner.nextLine()).append("\n");
            }

            textfield2.setText(inhalt.toString().trim());
            scanner.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
