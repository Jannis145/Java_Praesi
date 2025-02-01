package Praesis_Jannis;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.*;

@SuppressWarnings("unused")
public class BorderLayoutDemo {
    private JFrame frame;
    private JTextArea textArea;
    private AtomicInteger folienIndex;

    public BorderLayoutDemo() {
        frame = new JFrame("Präsentation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textArea = new JTextArea();
        /*textArea.getFont();
        textArea.setFont(arial);
        textArea.setF*/
        textArea.setEditable(false);
        frame.add(textArea, BorderLayout.CENTER);
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void ueberschrift(String ueberschrift) {
        frame.setTitle(ueberschrift);
    }

    public void text(String inhalt) {
        // Setze den Text im Textbereich
    	textArea.setText(inhalt); 
    	//textArea.setBackground(Color.BLUE);
    	Font font = new Font("Arial", Font.PLAIN, 40);
    	textArea.setFont(font);
    	
    }
    
    public void bild(JLabel bild) {
    	JLabel bild1 = new JLabel(bild.getIcon());
    	frame.add(bild1, BorderLayout.SOUTH);
    }

    public void naechste(String buttonText, AtomicInteger index, ArrayList<String> folienInhalte) {
        JButton nextButton = new JButton(buttonText);
        nextButton.addActionListener(e -> {
            // Implementiere die Logik für die nächste Folie
            if (index.get() < folienInhalte.size() - 2) {
                index.incrementAndGet();
                text(folienInhalte.get(index.get() * 2 + 1)); // Inhalt
                ueberschrift(folienInhalte.get(index.get() * 2)); // Überschrift

            }
            else {
            	nextButton.setEnabled(false);
            }
        });
        frame.add(nextButton, BorderLayout.LINE_END);
    }

    public void zurueck(AtomicInteger index, ArrayList<String> folienInhalte) {
        JButton zurueck = new JButton("Zurück");
        zurueck.addActionListener(e -> {
            // Implementiere die Logik für die vorherige Folie
            if (index.get() > 0) {
                index.decrementAndGet();
                text(folienInhalte.get(index.get() * 2 + 1)); // Inhalt
                ueberschrift(folienInhalte.get(index.get() * 2)); // Überschrift

            }
        });
        frame.add(zurueck, BorderLayout.LINE_START);
    }
    
    private void updateButtonStatus(JButton nextButton, JButton prevButton, AtomicInteger index, int totalFolien) {
        nextButton.setEnabled(index.get() < totalFolien - 1);
        prevButton.setEnabled(index.get() > 0);
    }


    public void starten() {
        frame.setVisible(true); // Stellt sicher, dass das Fenster sichtbar ist
    }
}
