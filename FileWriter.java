package Praesis_Jannis;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FileWriter {
    public void write(JTextField textfield1, JTextArea textfield2, int aktuelleFolie) throws IOException {
        String pathfortext = "H:\\Desktop\\Praesi_by_Jannis\\InhaltFolie" + aktuelleFolie + ".txt";
        String pathforueberschrift = "H:\\Desktop\\Praesi_by_Jannis\\UeberschriftFolie" + aktuelleFolie + ".txt";
        String text1 = textfield1.getText();
        String text2 = "• " + textfield2.getText();

        Files.write(Paths.get(pathfortext), text2.getBytes());
        Files.write(Paths.get(pathforueberschrift), text1.getBytes());
    }

    public void create(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        if (file.isFile() && file.canWrite() && file.canRead()) {
        	System.out.println("File " + file.getName() + " created successfully.");
        } else {
            System.err.println("Error creating file: " + file.getName());
            
        }
    }
}
