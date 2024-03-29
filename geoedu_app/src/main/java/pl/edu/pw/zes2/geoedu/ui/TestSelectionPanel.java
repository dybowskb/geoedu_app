package pl.edu.pw.zes2.geoedu.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import pl.edu.pw.zes2.geoedu.core.Question;
import pl.edu.pw.zes2.geoedu.core.QuestionFileFactory;
import pl.edu.pw.zes2.geoedu.core.Test;

public class TestSelectionPanel extends JPanel {

    private JPanel buttonsPanel;
    private JScrollPane scrollPane;

    private List<JButton> buttons;

    public TestSelectionPanel() {
        buttons = new ArrayList<>();
        setLayout(new BorderLayout());

        final var title = new JLabel("Wybór testu", JLabel.CENTER);
        title.setBorder(new EmptyBorder(Theme.STANDARD_UNIT / 2,
                Theme.STANDARD_UNIT,
                Theme.STANDARD_UNIT / 2,
                Theme.STANDARD_UNIT));
        title.setForeground(Theme.WHITE);
        title.setBackground(Theme.DARK_BLUE);
        title.setOpaque(true);
        title.setFont(Theme.LARGE_FONT.deriveFont(Font.BOLD));
        add(title, BorderLayout.PAGE_START);

        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());
        buttonsPanel.setBackground(Theme.LIGHT_BLUE);

        scrollPane = new JScrollPane(buttonsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void addButton(String buttonText, String directoryPath, JPanel container, Gui gui, String name) {
        JButton button = new JButton(parseFileName(buttonText));
        buttons.add(button);
        button.setPreferredSize(new Dimension(250, 100));
        button.setBackground(Theme.DARK_VIOLET);
        button.setForeground(Theme.WHITE);
        button.setFont(Theme.NORMAL_FONT.deriveFont(Font.BOLD));

        button.addActionListener(e -> {
            List<Question> questions = null;
            try {
                questions = new QuestionFileFactory(Paths.get(directoryPath + "//" + buttonText)).createQuestions();
            } catch (IOException ex) {
                Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
            }

            Gui.startTest(new Test(questions), container, gui, name);
        });

        GridBagConstraints gbc = new GridBagConstraints();

        if (buttons.size() % 2 == 0) {
            gbc.gridx = 1;
        } else {
            gbc.gridx = 0;
        }
        gbc.gridy = buttons.size() / 2 + buttons.size() % 2;
        gbc.insets = new Insets(25, 45, 25, 45);

        buttonsPanel.add(button, gbc);

        revalidate();
    }

    private String parseFileName(String fileName) {
        fileName = fileName.replace("_", " ");
        int dotIndex = fileName.lastIndexOf(".");

        if (dotIndex != -1) {
            fileName = fileName.substring(0, dotIndex);
        }

        char firstChar = Character.toUpperCase(fileName.charAt(0));

        if (fileName.length() == 1) {
            return String.valueOf(firstChar);
        }

        return firstChar + fileName.substring(1);
    }

    public File[] directoryReader(String directoryPath) {
        File directory = new File(directoryPath);
        File[] files = null;

        if (directory.isDirectory()) {
            files = directory.listFiles();
        } else {
            throw new IllegalArgumentException("The given path is not a directory.");
        }

        return files;
    }

}
