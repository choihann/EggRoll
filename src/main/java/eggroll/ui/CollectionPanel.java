package eggroll.ui;

import eggroll.ui.UIComponents.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// tried to make this a scrollable grid
// I'm using emojis instead of actual images since we don't have any and the emojis do a good job of basics
public class CollectionPanel extends JPanel {

    private final JPanel gridPanel;
    private final JLabel emptyLabel;
    private final JLabel countLabel;
    private PetSelectListener selectListener;

    // this is just to keep panel and game logic decoupled
    public interface PetSelectListener {
        void onPetSelected(String petId);
    }

    public record PetCardData(
            String id,
            String name,
            String species,
            String stage,
            String rarity,
            String emoji,
            boolean isActive
    ) {
    }

    public CollectionPanel() {
        setLayout(new BorderLayout(0, Theme.PAD_MD));
        setBackground(Theme.BG_BASE);
        setBorder(BorderFactory.createEmptyBorder(Theme.PAD_MD, Theme.PAD_LG, Theme.PAD_MD, Theme.PAD_LG));

        // header
        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setOpaque(false);
        headerRow.add(UIComponents.sectionHeader("Collection"), BorderLayout.WEST);

        countLabel = new JLabel("0 pets");
        countLabel.setFont(Theme.FONT_CAPTION);
        countLabel.setForeground(Theme.TEXT_MUTED);
        headerRow.add(countLabel, BorderLayout.EAST);
        add(headerRow, BorderLayout.NORTH);

        // scrollable grid
        gridPanel = new JPanel(new UIComponents.WrapLayout(FlowLayout.LEFT, Theme.PAD_SM, Theme.PAD_SM));
        gridPanel.setBackground(Theme.BG_BASE);

        emptyLabel = new JLabel("No pets yet; roll the gacha to get started!", SwingConstants.CENTER);
        emptyLabel.setFont(Theme.FONT_BODY);
        emptyLabel.setForeground(Theme.TEXT_MUTED);
        emptyLabel.setBorder(BorderFactory.createEmptyBorder(Theme.PAD_XL, 0, Theme.PAD_XL, 0));
        gridPanel.add(emptyLabel);

        JScrollPane scroll = new JScrollPane(gridPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);
    }

    public void setSelectListener(PetSelectListener petSelectListener) {
        this.selectListener = petSelectListener;
    }

    public void refreshCollection(List<PetCardData> pets) {
        gridPanel.removeAll();

        if (pets == null || pets.isEmpty()) {
            gridPanel.add(emptyLabel);
            countLabel.setText("0 pets");
        } else {
            countLabel.setText(pets.size() + " pet" + (pets.size() == 1 ? "" : "s"));
            for (PetCardData data : pets) {
                gridPanel.add(buildPetCard(data));
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    // building the cards for aesthetics

    private JPanel buildPetCard(PetCardData data) {
        Color cardBg = data.isActive() ? Theme.ACCENT_SAGE.brighter().brighter() : Theme.BG_CARD;

        RoundedPanel card = new RoundedPanel(cardBg);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(Theme.PAD_SM, Theme.PAD_MD, Theme.PAD_SM, Theme.PAD_MD));
        card.setPreferredSize(new Dimension(110, 130));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setToolTipText("Click to make " + data.name() + " your active pet");

        // this is to see which pet is active
        if (data.isActive()) {
            JLabel activeBadge = new JLabel("★ Active", SwingConstants.CENTER);
            activeBadge.setFont(Theme.FONT_CAPTION);
            activeBadge.setForeground(Theme.ACCENT_SAGE_DARK);
            activeBadge.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(activeBadge);
            card.add(Box.createVerticalStrut(2));
        }

        // TODO: replace with actual pet image and not emojis
        JLabel emoji = new JLabel(data.emoji(), SwingConstants.CENTER);
        emoji.setFont(new Font("Serif", Font.PLAIN, 36));
        emoji.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(emoji);

        card.add(Box.createVerticalStrut(Theme.PAD_SM));

        JLabel name = new JLabel(data.name(), SwingConstants.CENTER);
        name.setFont(Theme.FONT_BUTTON);
        name.setForeground(Theme.TEXT_PRIMARY);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(name);

        JLabel species = new JLabel(data.species() + " · " + data.stage(), SwingConstants.CENTER);
        species.setFont(Theme.FONT_CAPTION);
        species.setForeground(Theme.TEXT_SECONDARY);
        species.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(species);

        card.add(Box.createVerticalStrut(2));

        JLabel rarity = UIComponents.rarityLabel(data.rarity());
        rarity.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(rarity);

        // TODO: implement this functionality
        card.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (selectListener != null) selectListener.onPetSelected(data.id());
            }
        });

        return card;
    }
}
