package eggroll.ui;

import eggroll.ui.UIComponents.RoundedPanel;
import eggroll.ui.UIComponents.buttonStyle;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InventoryPanel extends JPanel {

    public interface ItemUseListener {
        void onUseItem(String itemId);
    }

    public record ItemData(
            String id,
            String name,
            String emoji,
            String description,
            int quantity
    ) {
    }

    private final JPanel gridPanel;
    private final JLabel emptyLabel;
    private final JLabel countLabel;
    private ItemUseListener useListener;

    public InventoryPanel() {
        setLayout(new BorderLayout(0, Theme.PAD_MD));
        setBackground(Theme.BG_BASE);
        setBorder(BorderFactory.createEmptyBorder(Theme.PAD_MD, Theme.PAD_LG, Theme.PAD_MD, Theme.PAD_LG));

        JPanel headerRow = new JPanel(new BorderLayout());
        headerRow.setOpaque(false);
        headerRow.add(UIComponents.sectionHeader("Inventory"), BorderLayout.WEST);

        countLabel = new JLabel("0 items");
        countLabel.setFont(Theme.FONT_CAPTION);
        countLabel.setForeground(Theme.TEXT_MUTED);
        headerRow.add(countLabel, BorderLayout.EAST);
        add(headerRow, BorderLayout.NORTH);

        // item grid
        gridPanel = new JPanel();
        gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.Y_AXIS));
        gridPanel.setBackground(Theme.BG_BASE);

        emptyLabel = new JLabel("Your bag is empty!", SwingConstants.CENTER);
        emptyLabel.setFont(Theme.FONT_BODY);
        emptyLabel.setForeground(Theme.TEXT_MUTED);
        emptyLabel.setBorder(BorderFactory.createEmptyBorder(Theme.PAD_XL, 0, Theme.PAD_XL, 0));
        emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gridPanel.add(emptyLabel);

        JScrollPane scroll = new JScrollPane(gridPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        add(scroll, BorderLayout.CENTER);
    }


    public void setUseListener(ItemUseListener itemUseListener) {
        this.useListener = itemUseListener;
    }

    public void refreshInventory(List<ItemData> items) {
        gridPanel.removeAll();

        if (items == null || items.isEmpty()) {
            gridPanel.add(emptyLabel);
            countLabel.setText("0 items");
        } else {
            int total = items.stream().mapToInt(ItemData::quantity).sum();
            countLabel.setText(total + " item" + (total == 1 ? "" : "s"));
            for (ItemData item : items) {
                gridPanel.add(buildItemRow(item));
                gridPanel.add(Box.createVerticalStrut(Theme.PAD_SM));
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private JPanel buildItemRow(ItemData item) {
        RoundedPanel row = new RoundedPanel(Theme.BG_CARD);
        row.setLayout(new BorderLayout(Theme.PAD_MD, 0));
        row.setBorder(BorderFactory.createEmptyBorder(Theme.PAD_SM, Theme.PAD_MD, Theme.PAD_SM, Theme.PAD_MD));
        row.setMaximumSize(new Dimension(Short.MAX_VALUE, 68));

        // TODO: Replace emojis with images
        JLabel icon = new JLabel(item.emoji(), SwingConstants.CENTER);
        icon.setFont(new Font("Comic Sans MD", Font.PLAIN, 32));
        icon.setPreferredSize(new Dimension(44, 44));

        JPanel textCol = new JPanel();
        textCol.setLayout(new BoxLayout(textCol, BoxLayout.Y_AXIS));
        textCol.setOpaque(false);

        JLabel name = new JLabel(item.name());
        name.setFont(Theme.FONT_BUTTON);
        name.setForeground(Theme.TEXT_PRIMARY);

        JLabel desc = new JLabel(item.description());
        desc.setFont(Theme.FONT_CAPTION);
        desc.setForeground(Theme.TEXT_SECONDARY);

        textCol.add(name);
        textCol.add(desc);

        JPanel rightCol = new JPanel(new FlowLayout(FlowLayout.RIGHT, Theme.PAD_SM, 0));
        rightCol.setOpaque(false);

        JLabel quantity = new JLabel("×" + item.quantity());
        quantity.setFont(Theme.FONT_BODY);
        quantity.setForeground(Theme.TEXT_MUTED);

        buttonStyle useBtn = buttonStyle.primary("Use");
        useBtn.setPreferredSize(new Dimension(64, 32));
        useBtn.setEnabled(item.quantity() > 0);
        useBtn.addActionListener(e -> {
            if (useListener != null) useListener.onUseItem(item.id());
        });

        rightCol.add(quantity);
        rightCol.add(useBtn);

        row.add(icon, BorderLayout.WEST);
        row.add(textCol, BorderLayout.CENTER);
        row.add(rightCol, BorderLayout.EAST);

        return row;
    }
}
