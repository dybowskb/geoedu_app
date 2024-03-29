package pl.edu.pw.zes2.geoedu.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class ViewPanel extends JPanel {


    public ViewPanel() {
        super(new BorderLayout());
        setBackground(Theme.LIGHT_BLUE);

        final var title = new JLabel("Wyniki testów", JLabel.CENTER);
        title.setBorder(new EmptyBorder(Theme.STANDARD_UNIT / 2,
                Theme.STANDARD_UNIT,
                Theme.STANDARD_UNIT / 2,
                Theme.STANDARD_UNIT));
        title.setForeground(Theme.WHITE);
        title.setBackground(Theme.DARK_BLUE);
        title.setOpaque(true);
        title.setFont(Theme.LARGE_FONT.deriveFont(Font.BOLD));
        add(title, BorderLayout.PAGE_START);

        
        
    }
        
}