package eggroll.ui;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private static final String CARD_PET = "pet";
    private static final String CARD_COLLECTION = "collection";
    private static final String CARD_GACHA = "gacha";
    private static final String CARD_INVENTORY = "inventory";

    public final NavBarOverlay navBarOverlay;
    public final PetViewPanel petView;
    public final ActionPanel actionPanel;
    public final CollectionPanel collectionPanel;
    public final GachaPanel gachaPanel;
    public final InventoryPanel inventoryPanel;

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardContainer = new JPanel(cardLayout);

    public MainWindow() {
        super("Egg Roll");
        UIComponents.applyGlobalDefaults();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(480, 700));
        setPreferredSize(new Dimension(520, 760));
        setBackground(Theme.BG_BASE);

        navBarOverlay = new NavBarOverlay();
        petView = new PetViewPanel();
        actionPanel = new ActionPanel();
        collectionPanel = new CollectionPanel();
        gachaPanel = new GachaPanel();
        inventoryPanel = new InventoryPanel();

        JSplitPane petCard = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, petView, actionPanel);
        petCard.setDividerLocation(0.58);
        petCard.setResizeWeight(0.58);
        petCard.setBorder(BorderFactory.createEmptyBorder());
        petCard.setDividerSize(1);
        petCard.setBackground(Theme.BG_BASE);

        cardContainer.setBackground(Theme.BG_BASE);
        cardContainer.add(petCard, CARD_PET);
        cardContainer.add(collectionPanel, CARD_COLLECTION);
        cardContainer.add(gachaPanel, CARD_GACHA);
        cardContainer.add(inventoryPanel, CARD_INVENTORY);

        navBarOverlay.setTabListener(tab -> {
            String card = switch (tab) {
                case PET -> CARD_PET;
                case COLLECTION -> CARD_COLLECTION;
                case GACHA -> CARD_GACHA;
                case INVENTORY -> CARD_INVENTORY;
            };
            cardLayout.show(cardContainer, card);
        });

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(Theme.BG_BASE);
        root.add(navBarOverlay.topBar, BorderLayout.NORTH);
        root.add(cardContainer, BorderLayout.CENTER);
        root.add(navBarOverlay.navBar, BorderLayout.SOUTH);
        setContentPane(root);

        pack();
        setLocationRelativeTo(null);
    }

    public void showPetView() {
        cardLayout.show(cardContainer, CARD_PET);
        navBarOverlay.selectTab(NavBarOverlay.Tab.PET);
    }

    public void showCollection() {
        cardLayout.show(cardContainer, CARD_COLLECTION);
        navBarOverlay.selectTab(NavBarOverlay.Tab.COLLECTION);
    }

    public void showGacha() {
        cardLayout.show(cardContainer, CARD_GACHA);
        navBarOverlay.selectTab(NavBarOverlay.Tab.GACHA);
    }

    public void showInventory() {
        cardLayout.show(cardContainer, CARD_INVENTORY);
        navBarOverlay.selectTab(NavBarOverlay.Tab.INVENTORY);
    }

    static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // TODO: make sure to replace later since we're using placeholders here
            MainWindow window = new MainWindow();

            window.navBarOverlay.refreshCoins(250);
            window.navBarOverlay.refreshActivePet("Pip", "Happy");

            window.petView.updatePetIdentity("Pip", "Fluffy one", "Baby", "Uncommon", "🐣");
            window.petView.updateStats(72, 85, 60, 40);
            window.petView.updateLevel(3, 40);
            window.petView.updateStateLabel("Happy");
            window.actionPanel.applyPetState("idle");

            window.collectionPanel.refreshCollection(java.util.List.of(
                    // placeholder pets for display purposes
                    // TODO: Once we have implementation, replace this with actual pets
                    new CollectionPanel.PetCardData("1", "Pip", "Fluffy one", "Baby", "Uncommon", "🐣", true),
                    new CollectionPanel.PetCardData("2", "Glub", "Glub GLub", "Egg", "Rare", "🥚", false),
                    new CollectionPanel.PetCardData("3", "Fire", "Ash", "Adult", "Epic", "🔥", false)
            ));

            window.inventoryPanel.refreshInventory(java.util.List.of(
                    // I've put in emojis as like... a placeholder for any images we have this is just a rough idea
                    // TODO: Replace with actual inventory items
                    new InventoryPanel.ItemData("food_basic", "Basic Kibble", "🦴", "Restores 20 hunger.", 5),
                    new InventoryPanel.ItemData("treat_star", "Star Treat", "⭐", "Boosts happiness by 15.", 2),
                    new InventoryPanel.ItemData("tonic_rest", "Rest Tonic", "💤", "Instantly restores energy.", 1)
            ));

            // idk if we're gonna have a system like this, this is just a maybe for now
            // TODO: determine how we want to do our gacha system
            window.gachaPanel.updatePity(12, 50);
            window.gachaPanel.applyAffordability(250);

            window.setVisible(true);
        });
    }
}