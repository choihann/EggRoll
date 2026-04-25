package eggroll.ui;

import eggroll.gacha.GachaRarity;
import eggroll.ui.UIComponents.RoundedPanel;
import eggroll.ui.UIComponents.buttonStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GachaPanel extends JPanel {
    // TODO: Sync the cost here with the gacha machine
    private static final int ROLL_ONE_COST = 100;
    private static final int ROLL_TEN_COST = 950;

    private buttonStyle rollOnePetBtn;
    private buttonStyle rollTenPetBtn;
    private buttonStyle rollOnePotionBtn;
    private buttonStyle rollTenPotionBtn;
    private JLabel petEmoji;
    private JLabel petName;
    private JLabel petSpecies;
    private JLabel petFlavorText;
    private JLabel petRarity;
    private JPanel petResultCard;
    private JLabel potionEmoji;
    private JLabel potionFlavorText;
    private JLabel potionName;
    private JLabel potionEffect;
    private JLabel potionRarity;
    private JPanel potionResultCard;

    public GachaPanel() {
        setLayout(new BorderLayout(0, Theme.PAD_LG));
        setBackground(Theme.BG_BASE);
        setBorder(BorderFactory.createEmptyBorder(Theme.PAD_LG, Theme.PAD_XL, Theme.PAD_LG, Theme.PAD_XL));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);

        JLabel title = centred("The Egg Gacha", Theme.FONT_HEADING, Theme.TEXT_PRIMARY);
        JLabel subtitle = centred("Spend coins to hatch a mystery egg!", Theme.FONT_BODY, Theme.TEXT_SECONDARY);
        header.add(title);
        header.add(Box.createVerticalStrut(Theme.PAD_SM));
        header.add(subtitle);
        header.add(Box.createVerticalStrut(Theme.PAD_MD));
        header.add(UIComponents.divider());
        add(header, BorderLayout.NORTH);

        rollOnePetBtn = rollBtn("Roll Pet x1", ROLL_ONE_COST, Theme.ACCENT_SAGE);
        rollTenPetBtn = rollBtn("Roll Pet x10", ROLL_TEN_COST, Theme.ACCENT_AMBER);

        JPanel petRow = new JPanel(new GridLayout(1, 2, Theme.PAD_LG, 0));
        petRow.setOpaque(false);
        petRow.setMaximumSize(new Dimension(Short.MAX_VALUE, 80));
        petRow.add(rollOnePetBtn);
        petRow.add(rollTenPetBtn);

        rollOnePotionBtn = rollBtn("Roll Potion x1", ROLL_ONE_COST, Theme.ACCENT_SAGE);
        rollTenPotionBtn = rollBtn("Roll Potion x10", ROLL_TEN_COST, Theme.ACCENT_AMBER);

        JPanel potionRow = new JPanel(new GridLayout(1, 2, Theme.PAD_LG, 0));
        potionRow.setOpaque(false);
        potionRow.setMaximumSize(new Dimension(Short.MAX_VALUE, 80));
        potionRow.add(rollOnePotionBtn);
        potionRow.add(rollTenPotionBtn);


        // this is meant to be the result card
        petEmoji = new JLabel("", SwingConstants.CENTER);
        petEmoji.setAlignmentX(CENTER_ALIGNMENT);
        petName = centred("???", Theme.FONT_PET_NAME, Theme.TEXT_PRIMARY);
        petSpecies = centred("", Theme.FONT_BODY, Theme.TEXT_SECONDARY);
        petRarity = UIComponents.rarityLabel(GachaRarity.Common);
        petRarity.setAlignmentX(CENTER_ALIGNMENT);
        petFlavorText = centred("", new Font("Serif", Font.ITALIC, 13), Theme.TEXT_MUTED);

        petResultCard = new RoundedPanel(Theme.BG_CARD);
        petResultCard.setLayout(new BoxLayout(petResultCard, BoxLayout.Y_AXIS));
        petResultCard.setBorder(BorderFactory.createEmptyBorder(
                Theme.PAD_LG, Theme.PAD_XL, Theme.PAD_LG, Theme.PAD_XL
        ));
        petResultCard.setAlignmentX(CENTER_ALIGNMENT);

        petResultCard.add(centred("You hatched a pet!", Theme.FONT_HEADING, Theme.TEXT_PRIMARY));
        petResultCard.add(Box.createVerticalStrut(Theme.PAD_SM));
        petResultCard.add(petEmoji);
        petResultCard.add(Box.createVerticalStrut(Theme.PAD_SM));
        petResultCard.add(petName);
        petResultCard.add(Box.createVerticalStrut(4));
        petResultCard.add(petSpecies);
        petResultCard.add(Box.createVerticalStrut(4));
        petResultCard.add(petRarity);
        petResultCard.add(Box.createVerticalStrut(4));
        petResultCard.add(petFlavorText);

        petResultCard.setVisible(false);

        // potion
        potionEmoji = centred("", new Font("Serif", Font.PLAIN, 72), Theme.TEXT_PRIMARY);
        potionName = centred("???", Theme.FONT_PET_NAME, Theme.TEXT_PRIMARY);
        potionEffect = centred("", Theme.FONT_BODY, Theme.TEXT_SECONDARY);
        potionRarity = UIComponents.rarityLabel(GachaRarity.Common);
        potionRarity.setAlignmentX(CENTER_ALIGNMENT);
        potionFlavorText = centred("", new Font("Serif", Font.ITALIC, 13), Theme.TEXT_MUTED);

        potionResultCard = new RoundedPanel(Theme.BG_CARD);
        potionResultCard.setLayout(new BoxLayout(potionResultCard, BoxLayout.Y_AXIS));
        potionResultCard.setBorder(BorderFactory.createEmptyBorder(
                Theme.PAD_LG, Theme.PAD_XL, Theme.PAD_LG, Theme.PAD_XL
        ));
        potionResultCard.setAlignmentX(CENTER_ALIGNMENT);

        potionResultCard.add(centred("You found a potion!", Theme.FONT_HEADING, Theme.TEXT_PRIMARY));
        potionResultCard.add(Box.createVerticalStrut(Theme.PAD_SM));
        potionResultCard.add(potionEmoji);
        potionResultCard.add(Box.createVerticalStrut(Theme.PAD_SM));
        potionResultCard.add(potionName);
        potionResultCard.add(Box.createVerticalStrut(4));
        potionResultCard.add(potionEffect);
        potionResultCard.add(Box.createVerticalStrut(4));
        potionResultCard.add(potionRarity);
        potionResultCard.add(Box.createVerticalStrut(4));
        potionResultCard.add(potionFlavorText);

        potionResultCard.setVisible(false);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        body.add(petRow);
        body.add(Box.createVerticalStrut(Theme.PAD_LG));
        body.add(potionRow);
        body.add(Box.createVerticalStrut(Theme.PAD_LG));
        body.add(petResultCard);
        body.add(potionResultCard);
        add(body, BorderLayout.CENTER);

        // TODO: Replace with actual rates
        JLabel ratesLabel = centred("<html><center>Rates: Common 50% - Rare 35% - Epic 15% </center></html>", Theme.FONT_CAPTION, Theme.TEXT_MUTED);

        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setOpaque(false);
        footer.add(UIComponents.divider());
        footer.add(Box.createVerticalStrut(Theme.PAD_SM));
        footer.add(ratesLabel);
        add(footer, BorderLayout.SOUTH);
    }


    public void setPetRollOneAction(ActionListener actionListener) {
        rollOnePetBtn.addActionListener(actionListener);
    }

    public void setPetRollTenAction(ActionListener actionListener) {
        rollTenPetBtn.addActionListener(actionListener);
    }

    public void setPotionRollOneAction(ActionListener actionListener) {
        rollOnePotionBtn.addActionListener(actionListener);
    }

    public void setPotionRollTenAction(ActionListener actionListener) {
        rollTenPotionBtn.addActionListener(actionListener);
    }


    public void showPetResult(String emoji, String name, String species, String rarity, String flavorText) {
        ImageIcon petIcon = ImageUtils.loadPetImage(species, 96);
        if (petIcon != null) {
            petEmoji.setIcon(petIcon);
            petEmoji.setText(null);
        } else {
            petEmoji.setIcon(null);
            petEmoji.setText(emoji);
        }
        petName.setText(name);
        petSpecies.setText(species);
        petRarity.setText("- " + rarity);
        petRarity.setForeground(Theme.rarityColor(GachaRarity.Common));

        petFlavorText.setText(flavorText != null ? "\"" + flavorText + "\"" : "");

        petResultCard.setVisible(true);
        potionResultCard.setVisible(false);

        revalidate();
        repaint();
    }

    public void showPotionResult(String emoji, String name, String effect, String rarity, String flavorText) {
        potionEmoji.setText(emoji);
        potionName.setText(name);
        potionEffect.setText(effect != null ? "\"" + effect + "\"" : "");
        potionRarity.setText("- " + rarity);
        potionRarity.setForeground(Theme.rarityColor(GachaRarity.Common));
        potionFlavorText.setText(flavorText != null ? "\"" + flavorText + "\"" : "");


        potionResultCard.setVisible(true);
        petResultCard.setVisible(false);

        revalidate();
        repaint();
    }

    public void applyCanAfford(int coins) {
        rollOnePetBtn.setEnabled(coins >= ROLL_ONE_COST);
        rollTenPetBtn.setEnabled(coins >= ROLL_TEN_COST);
        rollOnePotionBtn.setEnabled(coins >= ROLL_ONE_COST);
        rollTenPotionBtn.setEnabled(coins >= ROLL_TEN_COST);
    }

    // the helpers
    private static JLabel centred(String text, Font font, Color color) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(CENTER_ALIGNMENT);
        return label;
    }

    private static buttonStyle rollBtn(String label, int cost, Color color) {
        buttonStyle btn = new buttonStyle("<html><center><b>" + label + "</b><br><small>" + cost + " coins</small></center></html>", color);
        btn.setPreferredSize(new Dimension(160, 64));
        return btn;
    }
}