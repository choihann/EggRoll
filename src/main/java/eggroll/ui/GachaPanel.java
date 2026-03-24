package eggroll.ui;

import eggroll.ui.UIComponents.RoundedPanel;
import eggroll.ui.UIComponents.buttonStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GachaPanel extends JPanel {

    private static final int ROLL_ONE_COST = 50;
    private static final int ROLL_TEN_COST = 450;

    private buttonStyle rollOneBtn;
    private buttonStyle rollTenBtn;
    private JLabel resultEmoji;
    private JLabel resultName;
    private JLabel resultSpecies;
    private JLabel resultRarity;
    private JLabel resultFlavour;
    private JLabel pityLabel;
    private JPanel resultCard;

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

        rollOneBtn = rollBtn("Roll x1", ROLL_ONE_COST, Theme.ACCENT_SAGE);
        rollTenBtn = rollBtn("Roll x10", ROLL_TEN_COST, Theme.ACCENT_AMBER);

        JPanel btnRow = new JPanel(new GridLayout(1, 2, Theme.PAD_LG, 0));
        btnRow.setOpaque(false);
        btnRow.setMaximumSize(new Dimension(Short.MAX_VALUE, 80));
        btnRow.add(rollOneBtn);
        btnRow.add(rollTenBtn);

        // this is meant to be the result card
        // TODO: test this once we have implementation for the gacha system
        resultEmoji = centred("", new Font("Serif", Font.PLAIN, 72), Theme.TEXT_PRIMARY);
        resultName = centred("???", Theme.FONT_PET_NAME, Theme.TEXT_PRIMARY);
        resultSpecies = centred("", Theme.FONT_BODY, Theme.TEXT_SECONDARY);
        resultRarity = UIComponents.rarityLabel("Common");
        resultRarity.setAlignmentX(CENTER_ALIGNMENT);
        resultFlavour = centred("", new Font("Serif", Font.ITALIC, 13), Theme.TEXT_MUTED);

        resultCard = new RoundedPanel(Theme.BG_CARD);
        resultCard.setLayout(new BoxLayout(resultCard, BoxLayout.Y_AXIS));
        resultCard.setBorder(BorderFactory.createEmptyBorder(Theme.PAD_LG, Theme.PAD_XL, Theme.PAD_LG, Theme.PAD_XL));
        resultCard.setAlignmentX(CENTER_ALIGNMENT);
        resultCard.add(centred("You received!", Theme.FONT_HEADING, Theme.TEXT_PRIMARY));
        resultCard.add(Box.createVerticalStrut(Theme.PAD_SM));
        resultCard.add(resultEmoji);
        resultCard.add(Box.createVerticalStrut(Theme.PAD_SM));
        resultCard.add(resultName);
        resultCard.add(Box.createVerticalStrut(4));
        resultCard.add(resultSpecies);
        resultCard.add(Box.createVerticalStrut(4));
        resultCard.add(resultRarity);
        resultCard.add(Box.createVerticalStrut(Theme.PAD_SM));
        resultCard.add(resultFlavour);
        resultCard.setVisible(false);

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        body.add(btnRow);
        body.add(Box.createVerticalStrut(Theme.PAD_LG));
        body.add(resultCard);
        add(body, BorderLayout.CENTER);

        // TODO: replace with actual details
        pityLabel = centred("Pity: 0 / 50 pulls until guaranteed Rare+", Theme.FONT_CAPTION, Theme.TEXT_MUTED);

        // TODO: Replace with actual rates
        JLabel ratesLabel = centred("<html><center>Rates: Common 50% - Rare 35% - Epic 15% </center></html>", Theme.FONT_CAPTION, Theme.TEXT_MUTED);

        JPanel footer = new JPanel();
        footer.setLayout(new BoxLayout(footer, BoxLayout.Y_AXIS));
        footer.setOpaque(false);
        footer.add(UIComponents.divider());
        footer.add(Box.createVerticalStrut(Theme.PAD_SM));
        footer.add(pityLabel);
        footer.add(Box.createVerticalStrut(Theme.PAD_SM));
        footer.add(ratesLabel);
        add(footer, BorderLayout.SOUTH);
    }


    public void setRollOneAction(ActionListener l) {
        rollOneBtn.addActionListener(l);
    }

    public void setRollTenAction(ActionListener l) {
        rollTenBtn.addActionListener(l);
    }


    public void showResult(String emoji, String name, String species, String rarity, String flavourText) {
        resultEmoji.setText(emoji);
        resultName.setText(name);
        resultSpecies.setText(species);
        resultRarity.setText("- " + rarity);
        resultRarity.setForeground(Theme.rarityColour(rarity));
        resultFlavour.setText(flavourText != null ? "\"" + flavourText + "\"" : "");
        resultCard.setVisible(true);
        revalidate();
        repaint();
    }

    // TODO: are we doing pity?
    public void updatePity(int current, int cap) {
        pityLabel.setText("Pity: " + current + " / " + cap + " pulls until guaranteed Rare+");
    }

    public void applyAffordability(int coins) {
        rollOneBtn.setEnabled(coins >= ROLL_ONE_COST);
        rollTenBtn.setEnabled(coins >= ROLL_TEN_COST);
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