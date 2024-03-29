package pl.edu.pw.zes2.geoedu.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class AddPanel extends JPanel {

    static String questionsFileName = "Data\\tests\\wszystkie_pytania.txt";
    static String testFileName;

    JPanel addPanelMainPanel;
    JPanel addPanelAddQuestionPanel;
    static JPanel addPanelCardLayout;
    static JLabel title;

    boolean isOpen = false;

    public AddPanel() {
        super(new BorderLayout());
        setBackground(Theme.LIGHT_BLUE);

        title = new JLabel("Dodaj testy", JLabel.CENTER);
        title.setBorder(new EmptyBorder(Theme.STANDARD_UNIT / 2,
                Theme.STANDARD_UNIT,
                Theme.STANDARD_UNIT / 2,
                Theme.STANDARD_UNIT));
        title.setForeground(Theme.WHITE);
        title.setBackground(Theme.DARK_BLUE);
        title.setOpaque(true);
        title.setFont(Theme.LARGE_FONT.deriveFont(Font.BOLD));
        add(title, BorderLayout.PAGE_START);

        addPanelCardLayout = new JPanel(new CardLayout());
        addPanelCardLayout.setBorder(new EmptyBorder(Theme.STANDARD_UNIT * 2,
                Theme.STANDARD_UNIT * 6,
                Theme.STANDARD_UNIT * 4,
                Theme.STANDARD_UNIT * 6));
        addPanelCardLayout.setBackground(Theme.LIGHT_BLUE);
        add(addPanelCardLayout, BorderLayout.CENTER);

        final var addQuestionButton = new JButton("Dodaj własne pytanie");
        addQuestionButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        addQuestionButton.setForeground(Theme.WHITE);
        addQuestionButton.setBackground(Theme.DARK_VIOLET);
        addQuestionButton.setBorder(new EmptyBorder(Theme.STANDARD_UNIT,
                Theme.STANDARD_UNIT,
                Theme.STANDARD_UNIT,
                Theme.STANDARD_UNIT));
        addQuestionButton.setHorizontalAlignment(JButton.LEFT);
        addQuestionButton.setPreferredSize(new Dimension(250, 100));

        addQuestionButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ((CardLayout) addPanelCardLayout.getLayout()).show(addPanelCardLayout, "1");
                title.setText("Dodaj zadanie");
            }
        });

        addPanelMainPanel = new JPanel();
        addPanelMainPanel.setBackground(Theme.LIGHT_BLUE);
        addPanelMainPanel.setLayout(new BoxLayout(addPanelMainPanel, BoxLayout.PAGE_AXIS));

        addPanelMainPanel.add(addQuestionButton);

        addPanelCardLayout.add(addPanelMainPanel, "0");    

        addPanelAddQuestionPanel = new JPanel();
        addPanelAddQuestionPanel.setBackground(Theme.LIGHT_BLUE);
        
        JComboBox chooseTestBox = createTestDropdown("Wybierz test");

        JTextField[] textFieldsArray = new JTextField[6];

        JCheckBox questionTypeCheckbox = new JCheckBox("Zadanie otwarte");
        addPanelAddQuestionPanel.add(questionTypeCheckbox);

        questionTypeCheckbox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {

                    for (int i = 0; i < 4; i++) {
                        addPanelAddQuestionPanel.getComponent(i + 4).setVisible(false);
                    }
                    isOpen = true;

                } else {
                    for (int i = 0; i < 4; i++) {
                        addPanelAddQuestionPanel.getComponent(i + 4).setVisible(true);
                    }
                    isOpen = false;
                }
            }
        });

        JButton fileButton = new JButton("Wybierz zdjęcie (opcjonalne)...");

        FileListener listener = new FileListener(fileButton);
        fileButton.addActionListener(listener);
        addPanelAddQuestionPanel.add(fileButton);

        textFieldsArray[0] = createTextField("Treść pytania:", addPanelAddQuestionPanel);
        textFieldsArray[1] = createTextField("Odpowiedź A:", addPanelAddQuestionPanel);
        textFieldsArray[2] = createTextField("Odpowiedź B:", addPanelAddQuestionPanel);
        textFieldsArray[3] = createTextField("Odpowiedź C:", addPanelAddQuestionPanel);
        textFieldsArray[4] = createTextField("Odpowiedź D:", addPanelAddQuestionPanel);
        textFieldsArray[5] = createTextField("Poprawna Odpowiedź:", addPanelAddQuestionPanel);

        final var createQuestionButton = new JButton("Stwórz pytanie");
        createQuestionButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
        createQuestionButton.setForeground(Theme.WHITE);
        createQuestionButton.setBackground(Theme.DARK_VIOLET);
        createQuestionButton.setBorder(new EmptyBorder(Theme.STANDARD_UNIT / 2,
                Theme.STANDARD_UNIT,
                Theme.STANDARD_UNIT / 2,
                Theme.STANDARD_UNIT));
        createQuestionButton.setHorizontalAlignment(JButton.CENTER);

        createQuestionButton.addActionListener((ActionEvent e) -> {
            testFileName = "Data\\tests\\" + (String) chooseTestBox.getSelectedItem() + ".txt";
            try {
                writeQuestionToFile(textFieldsArray, fileButton.getText(), testFileName, isOpen);
                writeQuestionToFile(textFieldsArray, fileButton.getText(), questionsFileName, isOpen);
                resetAddQuestionFields(textFieldsArray);
            } catch (Exception ex) {
                Logger.getLogger(AddPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        addPanelAddQuestionPanel.add(createQuestionButton);

        addPanelAddQuestionPanel.add(Box.createRigidArea(new Dimension(0, Theme.STANDARD_UNIT)));
        addPanelAddQuestionPanel.setLayout(new GridLayout(0, 1, Theme.STANDARD_UNIT, Theme.STANDARD_UNIT / 3));

        addPanelCardLayout.add(addPanelAddQuestionPanel, "1");

    }

    static void writeQuestionToFile(JTextField[] textFieldsArray, String imgPath, String fileName, boolean isOpen) throws Exception {
        String correctAnswer;

        if (imgPath.equals("Wybierz zdjęcie (opcjonalne)...")) {
            imgPath = "Data\\photos\\Qmark.png";
        }

        if (!isOpen) {

            for (int i = 0; i < textFieldsArray.length; i++) {
                if (textFieldsArray[i].getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Wszystkie pola muszą być wypełnione!");
                    throw new RuntimeException("Wszystkie pola muszą być wypełnione!");
                }
            }

            try {
                correctAnswer = getCorrectAnswerNumber(textFieldsArray[5].getText());
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, "Zła wartość w polu 'Poprawna odpowiedź'");
                resetAddQuestionFields(textFieldsArray);
                throw new RuntimeException("Zła wartość w polu 'Poprawna odpowiedź'");
            }

            try {
                writeStringToFile(fileName, "\n" + textFieldsArray[0].getText() + ";;");
                writeStringToFile(fileName, correctAnswer + ";;");
                writeStringToFile(fileName, imgPath.substring(imgPath.indexOf("Data")) + ";;");
                writeStringToFile(fileName, isOpen + ";;");
                writeStringToFile(fileName, textFieldsArray[1].getText() + ";;");
                writeStringToFile(fileName, textFieldsArray[2].getText() + ";;");
                writeStringToFile(fileName, textFieldsArray[3].getText() + ";;");
                writeStringToFile(fileName, textFieldsArray[4].getText() + "\n");
            } catch (IOException ex) {

            }

        } else if (isOpen) {

            if (textFieldsArray[0].getText().equals("") || textFieldsArray[5].getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Wszystkie pola muszą być wypełnione!");
                throw new RuntimeException("Wszystkie pola muszą być wypełnione!");
            }

            try {
                writeStringToFile(fileName, "\n" + textFieldsArray[0].getText() + ";;");
                writeStringToFile(fileName, "1" + ";;");
                writeStringToFile(fileName, imgPath.substring(imgPath.indexOf("Data")) + ";;");
                writeStringToFile(fileName, isOpen + ";;");
                writeStringToFile(fileName, textFieldsArray[5].getText());
            } catch (IOException ex) {

            }
        }

        if (fileName.equals(questionsFileName)) {
            JOptionPane.showMessageDialog(null, "Dodano zadanie");
        }
    }

    public static void resetAddQuestionFields(JTextField[] textFieldsArray) {
        for (int i = 0; i < textFieldsArray.length; i++) {
            textFieldsArray[i].setText("");
        }
    }

    public static String getCorrectAnswerNumber(String answerLetter) throws IllegalArgumentException {
        String correctAnswer;

        switch (answerLetter) {
            case "A" ->
                correctAnswer = "1";
            case "B" ->
                correctAnswer = "2";
            case "C" ->
                correctAnswer = "3";
            case "D" ->
                correctAnswer = "4";
            default -> {
                throw new IllegalArgumentException("Zła wartość w polu 'Poprawna odpowiedź'");
            }
        }
        return correctAnswer;
    }

    public static void writeStringToFile(String fileName, String text) throws IOException {
        try (FileWriter fw = new FileWriter(fileName, true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter out = new PrintWriter(bw)) {
            out.print(text);
            out.close();
            bw.close();
        } catch (IOException ex) {
            throw new IOException("Can't open file!");
        }
    }

    String[] getAvailableTests() {
        String dirName = "Data\\tests\\";
        File dir = new File(dirName);
        File[] listOfFiles = dir.listFiles();
        
        String[] testNames = new String[listOfFiles.length - 1];

        for (int i = 0, j=0; i < listOfFiles.length; i++) {
            if (!listOfFiles[i].getName().equals("wszystkie_pytania.txt")) {
                testNames[j++] = listOfFiles[i].getName().split(".txt")[0];
            } 
        }
        return testNames;
    }

    JComboBox createTestDropdown(String labelText) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(0,1,0, 0));
        jPanel.setBackground(Theme.LIGHT_BLUE);
        
        String[] availableTests = getAvailableTests();

        JLabel jLabel = new JLabel(labelText);
        jLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JComboBox chooseTestBox = new JComboBox(availableTests);

        jPanel.add(jLabel);
        jPanel.add(chooseTestBox);

        addPanelAddQuestionPanel.add(jPanel);

        return chooseTestBox;
    }

    JTextField createTextField(String labelText, JPanel panel) {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(0,1,0, 0));
        jPanel.setBackground(Theme.LIGHT_BLUE);

        JLabel jLabel = new JLabel();
        jLabel.setText(labelText);
        JTextField textField = new JTextField(30);

        jPanel.add(jLabel);
        jPanel.add(textField);

        panel.add(jPanel);
        return textField;
    }

    private static class FileListener implements ActionListener {

        private final JButton button;
        private String filePath;

        public FileListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("Data\\photos\\"));
            int i = fileChooser.showOpenDialog(this.button);

            if (i == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                this.filePath = file.getPath();
                try {
                    BufferedImage bimg = ImageIO.read(new File(filePath));
                    button.setText(filePath);
                } catch (Exception ex) {

                }

            }
        }

        public String getFilePath() {
            return this.filePath;
        }

    }

}
