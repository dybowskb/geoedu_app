package pl.edu.pw.zes2.geoedu.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static pl.edu.pw.zes2.geoedu.core.QuestionFileFactory.readQuestionFiles;
import pl.edu.pw.zes2.geoedu.core.Test;

public class Gui implements Runnable {

    private final static String APP_NAME = "GeoEdu";

    private final static String TEST_PANEL = "TestPanel";
    private final static String RESULT_PANEL = "ResultPanel";
    private final static String VIEW_PANEL = "ViewPanel";
    private final static String ADD_PANEL = "AddPanel";
    private final static String RESOURCE_PANEL = "ResourcePanel";

    private JFrame mainFrame;
    private JPanel mainContainer;
    private JPanel sideMenuContainer;

    @Override
    public void run() {
        Theme.setup();

        mainFrame = new JFrame(APP_NAME);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        final SideMenuItem[] sideMenuItems = {
            new SideMenuItem("Rozwiązuj testy", TEST_PANEL),
            new SideMenuItem("Przeglądaj wyniki", VIEW_PANEL),
            new SideMenuItem("Dodaj testy", ADD_PANEL),
            new SideMenuItem("Przeglądaj materiały", RESOURCE_PANEL),};
        sideMenuContainer = createSideMenu(Arrays.asList(sideMenuItems));
        mainFrame.add(sideMenuContainer, BorderLayout.LINE_START);

        mainContainer = new JPanel();
        mainContainer.setLayout(new CardLayout());
        mainFrame.add(mainContainer, BorderLayout.CENTER);

        String directoryPath = "Data\\tests";
        try {
            File[] files = readQuestionFiles(directoryPath);

            TestSelectionPanel testSelectionPanel = new TestSelectionPanel();
            for (File file : files) {
                String name = file.getName();
                testSelectionPanel.addButton(name, directoryPath, mainContainer, this, TEST_PANEL);
            }
            mainContainer.add(testSelectionPanel, TEST_PANEL);
        } catch (IllegalArgumentException exception) {
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, exception);
        }

        mainContainer.add(new ViewPanel(), VIEW_PANEL);
        mainContainer.add(new AddPanel(), ADD_PANEL);
        mainContainer.add(new ResourcePanel(), RESOURCE_PANEL);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public static void startTest(Test test, JPanel container, Gui gui, String name) {
        container.add(new TestPanel(gui, test), name);

        final var layout = (CardLayout) container.getLayout();
        layout.show(container, name);
    }

    public void endTest(Test test) {
        mainContainer.add(new ResultPanel(test), RESULT_PANEL);

        final var layout = (CardLayout) mainContainer.getLayout();
        layout.show(mainContainer, RESULT_PANEL);
    }

    private class SideMenuItem {

        public final String name;
        public final String id;

        public SideMenuItem(String name, String id) {
            this.name = name;
            this.id = id;
        }
    }

    private JPanel createSideMenu(List<SideMenuItem> sideMenuItems) {
        final var sideMenu = new JPanel();
        sideMenu.setLayout(new BoxLayout(sideMenu, BoxLayout.PAGE_AXIS));
        sideMenu.setBorder(new EmptyBorder(Theme.STANDARD_UNIT,
                Theme.STANDARD_UNIT * 2,
                Theme.STANDARD_UNIT,
                Theme.STANDARD_UNIT * 2));
        sideMenu.setBackground(Theme.LIGHT_VIOLET);
        sideMenu.setForeground(Theme.WHITE);

        final var title = new JLabel("Menu", JLabel.CENTER);
        title.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        title.setFont(Theme.LARGE_FONT.deriveFont(Font.BOLD));
        sideMenu.add(title);
        sideMenu.add(Box.createRigidArea(new Dimension(0, Theme.STANDARD_UNIT)));

        for (final var item : sideMenuItems) {
            sideMenu.add(createSideMenuButton(item.name, item.id));
            sideMenu.add(Box.createRigidArea(new Dimension(0, Theme.STANDARD_UNIT)));
        }

        return sideMenu;
    }

    private JButton createSideMenuButton(String text, String id) {
        final var button = new JButton(text);
        button.setAlignmentX(JButton.CENTER_ALIGNMENT);
        button.setForeground(Theme.WHITE);
        button.setBackground(Theme.DARK_VIOLET);
        button.setBorder(new EmptyBorder(Theme.STANDARD_UNIT / 2,
                Theme.STANDARD_UNIT,
                Theme.STANDARD_UNIT / 2,
                Theme.STANDARD_UNIT));
        button.setHorizontalAlignment(JButton.LEFT);

        button.addActionListener((ActionEvent event) -> {

            String directoryPath = "Data\\tests";

            try {
                File[] files = readQuestionFiles(directoryPath);

                TestSelectionPanel testSelectionPanel = new TestSelectionPanel();
                for (File file : files) {
                    String name = file.getName();
                    testSelectionPanel.addButton(name, directoryPath, mainContainer, this, TEST_PANEL);
                }
                mainContainer.add(testSelectionPanel, TEST_PANEL);
            } catch (IllegalArgumentException exception) {
                Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, exception);
            }

            final var layout = (CardLayout) mainContainer.getLayout();
            layout.show(mainContainer, id);
            if (id==ADD_PANEL) {
                ((CardLayout) AddPanel.addPanelCardLayout.getLayout()).show(AddPanel.addPanelCardLayout, "0");
                AddPanel.title.setText("Dodaj testy");
            }
            mainFrame.pack();
        }
        );

        return button;
    }

}
