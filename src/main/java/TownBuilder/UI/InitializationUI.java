package TownBuilder.UI;

import TownBuilder.Utility;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class InitializationUI extends JPanel {
    private final JPanel mainMenuPanel;
    private final JPanel playerSelectionPanel;

    public int getPlayerCount() {
        return playerCount;
    }

    private int playerCount;

    public String getSelection() {
        return selection;
    }

    private String selection;
    public InitializationUI() {
        System.out.println("Initialize UI");
        mainMenuPanel = createMainMenuPanel();
        playerSelectionPanel = createPlayerSelectionPanel();
        add(mainMenuPanel);

    }
    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel titleLabel = new JLabel("TownBuilder");
        titleLabel.setFont(panel.getFont().deriveFont(Font.BOLD, 50f));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel promptLabel = new JLabel("What type of game would you like?");
        promptLabel.setFont(panel.getFont().deriveFont(Font.BOLD, 50f));
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
        Font buttonFont = panel.getFont().deriveFont(Font.BOLD, 30f);
        JButton defaultButton = new JButton("Default");
        defaultButton.setHorizontalAlignment(SwingConstants.CENTER);
        defaultButton.setFont(buttonFont);
        defaultButton.setToolTipText("A classic game with the normal buildings. Recommended for new players.");
        defaultButton.addActionListener(e -> {
            selection = "default";
            synchronized (Utility.getNotifier()) {
                Utility.getNotifier().notify();
            }
        });
        JButton customButton = new JButton("Custom");
        customButton.setHorizontalAlignment(SwingConstants.CENTER);
        customButton.setFont(buttonFont);
        customButton.setToolTipText("Decide what buildings you'll use for your game.");
        customButton.addActionListener(e -> {
            selection = "custom";
            synchronized (Utility.getNotifier()) {
                Utility.getNotifier().notify();
            }
        });
        JButton randomButton = new JButton("Random");
        randomButton.setHorizontalAlignment(SwingConstants.CENTER);
        randomButton.setFont(buttonFont);
        randomButton.setToolTipText("Let Math.random() decide your buildings.");
        randomButton.addActionListener(e -> {
            selection = "random";
            synchronized (Utility.getNotifier()) {
                Utility.getNotifier().notify();
            }
        });
        panel.add(titleLabel, "wrap, , align center");
        panel.add(promptLabel, " align center, wrap");
        panel.add(defaultButton, "split 3, dock center, align center");
        panel.add(customButton, "dock center, align center");
        panel.add(randomButton, "dock center, align center");

        return panel;
    }
    private JPanel createPlayerSelectionPanel() {
        JPanel panel = new JPanel(new MigLayout());
        JLabel headerLabel = new JLabel("How many players?");
        headerLabel.setFont(panel.getFont().deriveFont(Font.BOLD, 50f));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        Integer[] integers = new Integer[]{1,2,3,4,5,6};
        JComboBox<Integer> playerSelection = new JComboBox<>(integers);
        playerSelection.setSelectedIndex(0);
        playerSelection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerCount = (Integer) playerSelection.getSelectedItem();
                synchronized (Utility.getNotifier()) {
                    Utility.getNotifier().notify();
                }
            }
        });
        panel.add(headerLabel, "align center, wrap");
        panel.add(playerSelection, "align center, h 300!, w 300!");
        //panel.add(playerSelection, "align center");
        return panel;
    }
    public void promptPlayerSelection() {
        remove(mainMenuPanel);
        add(playerSelectionPanel);
        updateUI();
    }

}