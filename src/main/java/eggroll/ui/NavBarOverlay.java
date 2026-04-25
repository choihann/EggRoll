package eggroll.ui;

import eggroll.ui.UIComponents.CurrencyBadge;

import javax.swing.*;
import java.awt.*;

public class NavBarOverlay {

    public enum Tab {PET, COLLECTION, GACHA, INVENTORY}

    public interface TabListener {
        void onTabSelected(Tab tab);
    }

    // this is on the top
    private final CurrencyBadge currencyBadge;
    private final JLabel activePetLabel;
    private final JLabel moodLabel;
    private final JLabel notificationLabel;

    // bottom navigation tabs
    private final JToggleButton petTab;
    private final JToggleButton collectionTab;
    private final JToggleButton gachaTab;
    private final JToggleButton inventoryTab;

    // actual panels added to JFrame
    public final JPanel topBar;
    public final JPanel navBar;

    private TabListener tabListener;

    public NavBarOverlay() {
        // top bar
        topBar = new JPanel(new BorderLayout(Theme.PAD_MD, 0));
        topBar.setBackground(Theme.BG_CARD);
        topBar.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER), BorderFactory.createEmptyBorder(Theme.PAD_SM, Theme.PAD_LG, Theme.PAD_SM, Theme.PAD_LG)));

        JLabel logo = new JLabel("Egg Roll");
        logo.setFont(Theme.FONT_HEADING);
        logo.setForeground(Theme.TEXT_PRIMARY);

        JPanel centre = new JPanel(new FlowLayout(FlowLayout.CENTER, Theme.PAD_SM, 0));
        centre.setOpaque(false);
        activePetLabel = styledLabel("No active pet", Theme.FONT_HEADING, Theme.TEXT_PRIMARY);
        moodLabel = styledLabel("", Theme.FONT_BODY, Theme.TEXT_SECONDARY);
        notificationLabel = styledLabel("", Theme.FONT_CAPTION, Theme.ACCENT_TERRA);
        centre.add(activePetLabel);
        centre.add(moodLabel);
        centre.add(notificationLabel);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, Theme.PAD_MD, 0));
        right.setOpaque(false);
        currencyBadge = new CurrencyBadge(0);
        right.add(currencyBadge);

        topBar.add(logo, BorderLayout.WEST);
        topBar.add(centre, BorderLayout.CENTER);
        topBar.add(right, BorderLayout.EAST);

        // bottom navigation bar
        navBar = new JPanel(new GridLayout(1, 4));
        navBar.setBackground(Theme.BG_CARD);
        navBar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER));
        navBar.setPreferredSize(new Dimension(0, 56));

        ButtonGroup group = new ButtonGroup();
        petTab = buildTab("Pet", Tab.PET);
        collectionTab = buildTab("Collection", Tab.COLLECTION);
        gachaTab = buildTab("Gacha", Tab.GACHA);
        inventoryTab = buildTab("Inventory", Tab.INVENTORY);
        for (JToggleButton toggleButton : new JToggleButton[]{petTab, collectionTab, gachaTab, inventoryTab}) {
            group.add(toggleButton);
            navBar.add(toggleButton);
        }
        petTab.setSelected(true);
    }

    // top bar refresh

    public void refreshCoins(int coins) {
        currencyBadge.setCoins(coins);
    }

    public void refreshActivePet(String petName, String moodEmoji) {
        if (petName == null || petName.isBlank()) {
            activePetLabel.setText("No active pet");
            moodLabel.setText("");
        } else {
            activePetLabel.setText(petName);
            moodLabel.setText("  ·  " + moodEmoji);
        }
    }


    // navbar refresh

    public void setTabListener(TabListener tabListener) {
        this.tabListener = tabListener;
    }

    public void selectTab(Tab tab) {
        switch (tab) {
            case PET -> petTab.setSelected(true);
            case COLLECTION -> collectionTab.setSelected(true);
            case GACHA -> gachaTab.setSelected(true);
            case INVENTORY -> inventoryTab.setSelected(true);
        }
    }

    // helper stuffs

    private static JLabel styledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private JToggleButton buildTab(String label, Tab tab) {
        JToggleButton toggleButton = new JToggleButton(label) {
            @Override
            protected void paintComponent(Graphics graphics) {
                Graphics2D graphics2D = (Graphics2D) graphics.create();
                graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (isSelected()) {
                    graphics2D.setColor(Theme.ACCENT_SAGE.brighter().brighter());
                    graphics2D.fillRect(0, 0, getWidth(), getHeight());
                    graphics2D.setColor(Theme.ACCENT_SAGE_DARK);
                    graphics2D.setStroke(new BasicStroke(3f));
                    graphics2D.drawLine(4, getHeight() - 2, getWidth() - 4, getHeight() - 2);
                } else if (getModel().isRollover()) {
                    graphics2D.setColor(Theme.BORDER);
                    graphics2D.fillRect(0, 0, getWidth(), getHeight());
                }
                graphics2D.dispose();
                super.paintComponent(graphics);
            }
        };
        toggleButton.setFont(Theme.FONT_BUTTON);
        toggleButton.setForeground(Theme.TEXT_PRIMARY);
        toggleButton.setFocusPainted(false);
        toggleButton.setBorderPainted(false);
        toggleButton.setContentAreaFilled(false);
        toggleButton.setOpaque(false);
        toggleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        toggleButton.addActionListener(e -> {
            if (tabListener != null) tabListener.onTabSelected(tab);
        });
        return toggleButton;
    }
}
