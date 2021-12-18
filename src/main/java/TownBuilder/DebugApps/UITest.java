package TownBuilder.DebugApps;

import TownBuilder.UI.BoardUILayer;


public class UITest {
    public static void main(String[] args) {
        BoardUILayer layer = new BoardUILayer();
        layer.setVisible(true);
//        TileUI buttonTest = new TileUI(1, 2);
//        buttonTest.setBounds(50, 50, 100, 100);
//        layer.add(buttonTest);

        //layer.triggerResourcePlacementPrompt();

        layer.listenForTilePress();
//        layer.clearErrorLabel();
//        layer.failedResourcePlacement(1);
    }
}
//import java.awt.BorderLayout;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.GridLayout;
//
//import javax.swing.BorderFactory;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JTextArea;
//import javax.swing.SwingUtilities;
//
//public class UITest implements Runnable {
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new UITest());
//    }
//
//    @Override
//    public void run() {
//        JFrame frame = new JFrame("Random Game");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        frame.add(createMainPanel(), BorderLayout.CENTER);
//        frame.add(createManualPanel(), BorderLayout.EAST);
//
//        frame.pack();
//        frame.setLocationByPlatform(true);
//        frame.setVisible(true);
//    }
//
//    private JPanel createMainPanel() {
//        JPanel panel = new JPanel(new BorderLayout());
//
//        JPanel upperPanel = createTurnButtonPanel();
//        JPanel lowerPanel = createResourcePanel();
//        JPanel innerPanel = createUpperPanel(upperPanel, lowerPanel);
//        panel.add(innerPanel, BorderLayout.NORTH);
//        panel.add(lowerPanel, BorderLayout.SOUTH);
//
//        return panel;
//    }
//
//    private JPanel createUpperPanel(JPanel upperPanel, JPanel lowerPanel) {
//        Dimension upperSize = upperPanel.getPreferredSize();
//        Dimension lowerSize = lowerPanel.getPreferredSize();
//
//        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
//        int difference = lowerSize.width - upperSize.width;
//        int left = difference / 2;
//        int right = difference - left;
//        panel.setBorder(BorderFactory.createEmptyBorder(0, left, 0, right));
//        panel.add(upperPanel);
//
//        return panel;
//    }
//
//    private JPanel createTurnButtonPanel() {
//        JPanel panel = new JPanel(new BorderLayout());
//
//        panel.add(createTurnPanel(), BorderLayout.NORTH);
//        panel.add(createButtonGrid(), BorderLayout.SOUTH);
//
//        return panel;
//    }
//
//    private JPanel createTurnPanel() {
//        JPanel panel = new JPanel(new FlowLayout());
//        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//
//        Font font = panel.getFont().deriveFont(Font.BOLD, 36f);
//
//        JLabel playerLabel = new JLabel("Player's Turn");
//        playerLabel.setFont(font);
//        panel.add(playerLabel);
//
//        return panel;
//    }
//
//    private JPanel createButtonGrid() {
//        JPanel panel = new JPanel(new GridLayout(0, 4, 2, 2));
//        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
//
//        JButton[] buttonArray = new JButton[16];
//        Dimension buttonSize = new Dimension(100, 100);
//        for (int index = 0; index < buttonArray.length; index++) {
//            buttonArray[index] = new JButton("Empty");
//            buttonArray[index].setPreferredSize(buttonSize);
//            panel.add(buttonArray[index]);
//        }
//
//        return panel;
//    }
//
//    private JPanel createResourcePanel() {
//        JPanel panel = new JPanel(new BorderLayout());
//
//        panel.add(createResourceLabelPanel(), BorderLayout.NORTH);
//        panel.add(createResourceButtonPanel(), BorderLayout.CENTER);
//
//        return panel;
//    }
//
//    private JPanel createResourceLabelPanel() {
//        JPanel panel = new JPanel(new FlowLayout());
//        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//
//        Font font = panel.getFont().deriveFont(Font.BOLD, 36f);
//
//        JLabel label = new JLabel("Select a Resource");
//        label.setFont(font);
//        panel.add(label);
//
//        return panel;
//    }
//
//    private JPanel createResourceButtonPanel() {
//        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
//        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//
//        JButton[] resourceButton = new JButton[5];
//        Dimension buttonSize = new Dimension(100, 100);
//        for (int index = 0; index < resourceButton.length; index++) {
//            resourceButton[index] = new JButton("Empty");
//            resourceButton[index].setPreferredSize(buttonSize);
//            panel.add(resourceButton[index]);
//        }
//
//        return panel;
//    }
//
//    private JPanel createManualPanel() {
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 300, 20));
//
//        Font font = panel.getFont().deriveFont(Font.BOLD, 36f);
//
//        JLabel label = new JLabel("Manual");
//        label.setHorizontalAlignment(JLabel.CENTER);
//        label.setFont(font);
//        panel.add(label, BorderLayout.NORTH);
//
//        JTextArea textArea = new JTextArea(5, 20);
//        panel.add(textArea, BorderLayout.CENTER);
//
//        JPanel eastButtonPanel = createEastButtonPanel();
//        JPanel innerPanel = createOuterEastButtonPanel(textArea, eastButtonPanel);
//
//        panel.add(innerPanel, BorderLayout.SOUTH);
//
//        return panel;
//    }
//
//    private JPanel createOuterEastButtonPanel(JTextArea textArea,
//                                              JPanel eastButtonPanel) {
//        Dimension eastButtonPanelSize = eastButtonPanel.getPreferredSize();
//        Dimension textAreaSize = textArea.getPreferredSize();
//
//        int difference = textAreaSize.width = eastButtonPanelSize.width;
//        int left = difference / 2;
//        int right = difference - left;
//
//        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
//        panel.setBorder(BorderFactory.createEmptyBorder(0, left, 0, right));
//        panel.add(eastButtonPanel);
//
//        return panel;
//    }
//
//    private JPanel createEastButtonPanel() {
//        JPanel panel = new JPanel(new GridLayout(0, 2, 2, 2));
//        panel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
//
//        JButton[] resourceButton = new JButton[4];
//        Dimension buttonSize = new Dimension(50, 50);
//        for (int index = 0; index < resourceButton.length; index++) {
//            resourceButton[index] = new JButton();
//            resourceButton[index].setPreferredSize(buttonSize);
//            panel.add(resourceButton[index]);
//        }
//
//        return panel;
//    }
//
//}