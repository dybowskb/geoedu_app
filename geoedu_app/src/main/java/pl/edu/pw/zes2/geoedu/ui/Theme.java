package pl.edu.pw.zes2.geoedu.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.FlatLightLaf;

class Theme {
    public static Color LIGHT_VIOLET = new Color(239, 227, 255);
    public static Color DARK_VIOLET = new Color(151, 71, 255);
    public static Color LIGHT_BLUE = new Color(219, 240, 255);
    public static Color DARK_BLUE = new Color(13, 153, 255);
    public static Color GREEN = new Color(122, 199, 79);
    public static Color RED = new Color(242, 87, 87);
    public static Color WHITE = Color.WHITE;

    public static int STANDARD_UNIT = 16;
    public static Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 16);
    public static Font LARGE_FONT = NORMAL_FONT.deriveFont(20f);

    public static void setup() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new IllegalStateException("Cannot set FlatLightLaf as Look&Feel", e);
        }

        JFrame.setDefaultLookAndFeelDecorated(true);
        UIManager.put("defaultFont", NORMAL_FONT);
        UIManager.put("TitlePane.font", NORMAL_FONT.deriveFont(12f));
        System.setProperty("flatlaf.uiScale.enabled", "false");
    }
}
