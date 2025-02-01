package Praesis_Jannis;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
public class editor {
    DateiZeilenweiseAuslesenClass dateiAusleser = new DateiZeilenweiseAuslesenClass(); // Instanzvariable
    private static JTextField textfield1;
    private static JTextArea textfield2;
    private static int aktuelleFolie = 1; // Aktuelle Folie
    private static ArrayList<File> folienDateien = new ArrayList<>(); // Liste der Folien-Dateien
    private JFrame frame;
    private AtomicInteger atint = new AtomicInteger(0); // AtomicInteger für die aktuelle Folie in der Präsentation

    public static void main(String[] args) {
        new editor(); // Erstelle eine Instanz von editor
    }

    // Konstruktor
    public editor() {
        frame = new JFrame("Editor Folie " + aktuelleFolie);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initGuiForEditor(frame);
        
        // Füge eine Standardfolie hinzu
        folienDateien.add(new File("H:\\Desktop\\Praesi_by_Jannis\\InhaltFolie0.txt"));
        folienDateien.add(new File("H:\\Desktop\\Praesi_by_Jannis\\UeberschriftFolie0.txt"));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void initGuiForEditor(final JFrame frame) { // Nicht statisch
        final JMenuBar options = new JMenuBar() {
            /****/
			private static final long serialVersionUID = 1L;

			@Override
            public Dimension getPreferredSize() {
                Dimension original = super.getPreferredSize();
                return new Dimension(original.width, 25);
            }
        };

        final Border foptions = new LineBorder(Color.blue);
        options.setBorder(foptions);

        final JMenu optionen = new JMenu("Optionen");
        final JMenu foliewaehlen = new JMenu("Folie Wählen");
        final JMenuItem ueberschrift = new JMenuItem("Bitte auswählen:");
        final JMenuItem newfolie = new JMenuItem("Neue Folie");
        final JMenuItem speichern = new JMenuItem("Fortschritt speichern");
        final JMenuItem abrufen = new JMenuItem("Fortschritt abrufen");
        final JMenuItem uploadButton = new JMenuItem("Bild einfügen");
        optionen.addSeparator();

        JMenu rechtliches = new JMenu("Rechtliches");
        final JMenuItem nutzungsbedingungen = new JMenuItem("Nutzungsbedingungen");
        final JMenuItem datenschutzrichtlinien = new JMenuItem("Datenschutzrichtlinien");
        rechtliches.add(nutzungsbedingungen);
        rechtliches.add(datenschutzrichtlinien);

        optionen.add(ueberschrift);
        optionen.add(newfolie);
        optionen.add(uploadButton);
        optionen.add(rechtliches);
        options.add(optionen);

        options.add(foliewaehlen);
        updateFolieMenu(foliewaehlen); // Füge die Menü-Items hinzu

        final JLabel label = new JLabel("Überschrift:");
        final JLabel label2 = new JLabel("Inhalt:");
        textfield1 = new JTextField(40);
        textfield2 = new JTextArea(18, 50);
        textfield2.setFont(new Font("Arial", Font.PLAIN, 30));
        String ersterText = dateiAusleser.ladeDatei("H:\\Desktop\\Praesi_by_Jannis\\UeberschriftFolie0.txt");
        textfield1.setText(ersterText);
        String ersterInhalt = dateiAusleser.ladeDatei("H:\\Desktop\\Praesi_by_Jannis\\InhaltFolie0.txt");
        textfield2.setText(ersterInhalt);
        textfield2.setEditable(true);
        textfield1.setEditable(true);
        JLabel imageLabel = new JLabel("Kein Bild ausgewählt.");

        final JButton button = new JButton("Gültigkeit testen");
        final JButton start = new JButton("Präsentation starten");
        final JButton verzweiflung = new JButton("Schließen");
        button.setEnabled(false);

        textfield2.addKeyListener(new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                button.setEnabled(TextLengthChecker.checkRequiredLength(textfield1, 2, textfield2));
            }
        });

        // ActionListener für Menü-Optionen
        
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Wähle ein Bild aus");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Bilder (JPG, PNG, GIF)", "jpg", "jpeg", "png", "gif"));

            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                
                // Lade und zeige das Bild
                ImageIcon imageIcon = new ImageIcon(filePath);
                Image image = imageIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(image));
                imageLabel.setText(""); // Entferne den Standardtext
            }
        });
        
        nutzungsbedingungen.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                Nutzungsbedingungen nb = new Nutzungsbedingungen();
                nb.anzeigen();
            }
        });

        datenschutzrichtlinien.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                Datenschutzrichtlinien dr = new Datenschutzrichtlinien();
                dr.anzeigen();
            }
        });

        speichern.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                speichereInhalte(); // Aufruf der Speichermethode
            }
        });

        abrufen.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                ladeInhalte();
            }
        });

        newfolie.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                aktuelleFolie++;
                frame.setTitle("Editor Folie "+ aktuelleFolie);
                File neueFolie = new File("H:\\Desktop\\Praesi_by_Jannis\\InhaltFolie" + aktuelleFolie + ".txt");
                File neueFolieu = new File("H:\\Desktop\\Praesi_by_Jannis\\UeberschriftFolie" + aktuelleFolie + ".txt");
                folienDateien.add(neueFolie);
                folienDateien.add(neueFolieu);

                try {
                    neueFolie.createNewFile();
                    neueFolieu.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                // Inhalte der neuen Folie laden
                ladeInhalte();
                updateFolieMenu(foliewaehlen); // Aktualisiere die Menü-Items nach dem Hinzufügen
            }
        });

        optionen.add(abrufen);
        optionen.add(speichern);

        frame.setJMenuBar(options);

        // Layout Panels
        final JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS)); // Vertikales Layout
        inputPanel.add(label);
        inputPanel.add(textfield1);
        inputPanel.add(label2);
        inputPanel.add(new JScrollPane(textfield2)); // ScrollPane für TextArea
        inputPanel.add(imageLabel);


        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(button);
        buttonPanel.add(start);
        buttonPanel.add(verzweiflung);

        // ActionListener für Buttons
        button.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                JFrame Meldung = new JFrame("Gültigkeitskontrolle");
                Meldung.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                JLabel Kontrolle = new JLabel("Text ist gültig!");
                Meldung.add(Kontrolle);
                Meldung.pack();
                Meldung.setVisible(true);
            }
        });

        // Hier wird der DateiLadenActionListener verwendet
        start.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                // Erstelle eine neue Instanz von BorderLayoutDemo oder deinem Präsentationsfenster
                BorderLayoutDemo praesi = new BorderLayoutDemo();
                
                // Erstelle eine Liste, die alle Inhalte der Folien enthält
                ArrayList<String> folienInhalte = new ArrayList<>();
                for (int i = 0; i < folienDateien.size(); i += 2) {
                    String inhalt = dateiAusleser.ladeDatei(folienDateien.get(i).getPath());
                    String ueberschrift = dateiAusleser.ladeDatei(folienDateien.get(i + 1).getPath());
                    folienInhalte.add(ueberschrift);
                    folienInhalte.add(inhalt);
                }
                
                // Hier solltest du das neue Fenster initialisieren
                praesi.ueberschrift(folienInhalte.get(0));
                praesi.text(folienInhalte.get(1));
                praesi.bild(imageLabel);
                praesi.naechste("Nächste Folie", atint, folienInhalte);
                praesi.zurueck(atint, folienInhalte);
                praesi.starten();
            }
        });


        verzweiflung.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                JDialog fortfahren = new JDialog();
                fortfahren.setLayout(new FlowLayout());
                fortfahren.add(new JLabel("Wollen Sie das Programm beenden?"));
                JButton jaButton = new JButton("Ja");
                JButton neinButton = new JButton("Nein");
                fortfahren.add(jaButton);
                fortfahren.add(neinButton);
                fortfahren.setSize(300, 100);
                fortfahren.setLocationRelativeTo(null);
                fortfahren.setVisible(true);

                jaButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        fortfahren.dispose();
                        frame.dispose();
                    }
                });

                neinButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        fortfahren.dispose();
                    }
                });
            }
        });

        // Haupt-Frame Layout
        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(inputPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(contentPanel);
        frame.pack();

        // Inhalte der ersten Folie laden
        ladeInhalte();
    }

    private void updateFolieMenu(JMenu foliewaehlen) { // Nicht statisch
        foliewaehlen.removeAll(); // Löscht bestehende Menü-Items
        for (int i = 1; i <= folienDateien.size() / 2 + 1; i++) {
            final int folienNummer = i;
            JMenuItem folienItem = new JMenuItem("Folie " + i);
            folienItem.addActionListener(new ActionListener() {
                public void actionPerformed(final ActionEvent e) {
                    aktuelleFolie = folienNummer;
                    frame.setTitle("Editor Folie " + aktuelleFolie); // Aktualisiert den Fenstertitel
                    ladeInhalte(); // Inhalte der gewählten Folie laden
                }
            });
            foliewaehlen.add(folienItem);
        }
    }
    
    private void ladeAlleVorhandenenFolien() {
        String folienPfad = "H:\\Desktop\\Praesi_by_Jannis";
        File ordner = new File(folienPfad);

        if (!ordner.exists() || !ordner.isDirectory()) {
            System.err.println("Das Verzeichnis existiert nicht: " + folienPfad);
            return;
        }

        File[] dateien = ordner.listFiles();
        if (dateien != null) {
            for (File datei : dateien) {
                String dateiName = datei.getName();
                if (dateiName.matches("InhaltFolie\\d+\\.txt") || dateiName.matches("UeberschriftFolie\\d+\\.txt")) {
                    folienDateien.add(datei);
                    System.out.println("Datei hinzugefügt: " + dateiName);
                }
            }
        }

        if (folienDateien.isEmpty()) {
            System.err.println("Keine Dateien im Verzeichnis gefunden, die dem Muster entsprechen.");
        } else {
            folienDateien.sort((f1, f2) -> f1.getName().compareTo(f2.getName()));
            System.out.println(folienDateien.size() + " Dateien geladen.");
        }
    }

    private void ladeInhalte() {
    	if (folienDateien.isEmpty()) {
    	    System.out.println("Keine Dateien gefunden. Erstelle Standard-Folien...");
    	    try {
    	    	File debug = new File("H:\\Desktop\\Praesi_by_Jannis\\InhaltFolie1.txt");
    	    	if(!debug.exists()) {
    	        File standardInhalt = new File("H:\\Desktop\\Praesi_by_Jannis\\InhaltFolie0.txt");
    	        File standardUeberschrift = new File("H:\\Desktop\\Praesi_by_Jannis\\UeberschriftFolie0.txt");
    	    	
    	        if (standardInhalt.createNewFile() && standardUeberschrift.createNewFile()) {
    	            folienDateien.add(standardInhalt);
    	            folienDateien.add(standardUeberschrift);
    	            System.out.println("Standard-Folien wurden erstellt.");
    	        }
    	    	}
    	    } catch (IOException e) {
    	    	System.err.println("Hat nicht geklappt. Zeile 332.");
    	        e.printStackTrace();
    	        
    	    }
    	}



        // Implementiere hier das Laden der Inhalte für die aktuelle Folie
        File inhaltFile = folienDateien.get((aktuelleFolie -1) * 2); // Inhalt Datei
        File ueberschriftFile = folienDateien.get((aktuelleFolie - 1) * 2 + 1); // Überschrift Datei

        // Inhalte aus den Dateien laden
        StringBuilder inhalt = new StringBuilder();
        StringBuilder ueberschrift = new StringBuilder(); 

        {
        	try (BufferedReader brInhalt = new BufferedReader(new FileReader(inhaltFile));
             BufferedReader brUeberschrift = new BufferedReader(new FileReader(ueberschriftFile))) {
             
            String line;
            while ((line = brInhalt.readLine()) != null) {
                inhalt.append(line).append("\n");
            }
            while ((line = brUeberschrift.readLine()) != null) {
                ueberschrift.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Inhalte in die Textfelder setzen
        textfield1.setText(ueberschrift.toString().trim());
        textfield2.setText(inhalt.toString().trim());
        }
    }

    private void speichereInhalte() {
        // Implementiere hier das Speichern der Inhalte der aktuellen Folie
        File inhaltFile = folienDateien.get((aktuelleFolie - 1) * 2); // Inhalt Datei
        File ueberschriftFile = folienDateien.get((aktuelleFolie - 1) * 2 + 1); // Überschrift Datei
        
        try { 
        	FileWriter inhaltWriter = new FileWriter();
        	inhaltWriter.write(textfield1, textfield2, aktuelleFolie); 
        }
            
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
