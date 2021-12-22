package presentation.frame;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;

import static presentation.common.GuiConstants.HOME_SCREEN_TITLE;

public class HomeScreen {

    private final JFrame frame;
    private static final Logger LOGGER = Logger.getLogger(HomeScreen.class.getName());
    private JTabbedPane basePanel;

    public HomeScreen() {
        setLookAndFeel();
        frame = new JFrame(HOME_SCREEN_TITLE);
        frame.setPreferredSize(new Dimension(550, 250));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createComponents();
        frame.pack();
    }

    public void setVisible(){
        frame.setVisible(true);
    }

    private void setLookAndFeel() {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            setUIFont(new javax.swing.plaf.FontUIResource("CustomFont", Font.PLAIN,12));
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, "Error at setting up look and feel", e);
        }
    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, f);
        }
    }

    private void createComponents() {
        basePanel = new JTabbedPane();
        frame.getContentPane().add(basePanel, BorderLayout.CENTER);
        frame.getContentPane().add(new JLabel("Developed by Nuno Pereira, ISEL, 2022"), BorderLayout.SOUTH);
    }

    public JTabbedPane getBasePanel() {
        if(basePanel == null) {
            basePanel = new JTabbedPane();
            frame.getContentPane().add(basePanel, BorderLayout.CENTER);
        }
        return basePanel;
    }

    public JFrame getFrame() {
        return frame;
    }
}
