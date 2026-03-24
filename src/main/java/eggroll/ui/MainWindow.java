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
            MainWindow window = new MainWindow();
            new eggroll.game.GameController(window).startGame();
        });
    }
}