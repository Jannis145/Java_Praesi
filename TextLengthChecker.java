package Praesis_Jannis;

import javax.swing.JTextField;
import javax.swing.JTextArea;

public class TextLengthChecker {
    public static boolean checkRequiredLength(final JTextField textfield,
                                              final int requiredLength,
                                              final JTextArea textarea) {
        return textfield.getText() != null &&
                textfield.getText().length() >= requiredLength &&
                textarea.getText() != null;
    }
}