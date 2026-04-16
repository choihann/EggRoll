package eggroll.ui;

import eggroll.observer.PetEvent;
import eggroll.observer.PetObserver;
import eggroll.pet.Pet;
import eggroll.ui.UIComponents.RoundedPanel;
import eggroll.ui.UIComponents.StatBar;

import javax.swing.*;
import java.awt.*;

public class PetViewPanel extends JPanel implements PetObserver {

    private JLabel petNameLabel;
    private JLabel speciesLabel;
    private JLabel rarityLabel;
    private JLabel stageLabel;
    private JLabel stateLabel;

    // TODO: replace with actual image and not emoji
    private JPanel spriteArea;
    private JLabel spriteEmoji;

    private StatBar hungerBar;
    private StatBar happinessBar;
    private StatBar energyBar;
    private StatBar hygieneBar;

    private JLabel levelLabel;

    public PetViewPanel() {
        setLayout(new BorderLayout(0, Theme.PAD_MD));
        setBackground(Theme.BG_BASE);
        setBorder(BorderFactory.createEmptyBorder(Theme.PAD_LG, Theme.PAD_LG, Theme.PAD_MD, Theme.PAD_LG));

        JPanel identityPanel = buildIdentityPanel();
        add(identityPanel, BorderLayout.NORTH);

        spriteEmoji = new JLabel("🥚", SwingConstants.CENTER);
        spriteEmoji.setFont(new Font("Serif", Font.PLAIN, 96));
        spriteEmoji.setToolTipText("Pet sprite (image placeholder)");

        spriteArea = new RoundedPanel(Theme.BG_CARD);
        spriteArea.setLayout(new BorderLayout());
        spriteArea.setBorder(BorderFactory.createEmptyBorder(Theme.PAD_LG, Theme.PAD_XL, Theme.PAD_LG, Theme.PAD_XL));
        spriteArea.setPreferredSize(new Dimension(260, 260));
        spriteArea.add(spriteEmoji, BorderLayout.CENTER);

        stateLabel = new JLabel("Waiting to hatch…", SwingConstants.CENTER);
        stateLabel.setFont(Theme.FONT_BODY);
        stateLabel.setForeground(Theme.TEXT_SECONDARY);
        stateLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PAD_SM, 0));
        spriteArea.add(stateLabel, BorderLayout.SOUTH);

        JPanel spriteCentre = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        spriteCentre.setOpaque(false);
        spriteCentre.add(spriteArea);
        add(spriteCentre, BorderLayout.CENTER);

        add(buildStatsPanel(), BorderLayout.SOUTH);

        // TODO: must a pet be selected?
        petNameLabel.setText("—");
        speciesLabel.setText("No pet selected");
    }

    // helpers
    private JPanel buildIdentityPanel() {
        // TODO: Figure out what information will be displayed and remove placeholders
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        petNameLabel = new JLabel("—");
        petNameLabel.setFont(Theme.FONT_PET_NAME);
        petNameLabel.setForeground(Theme.TEXT_PRIMARY);
        petNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel subRow = new JPanel(new FlowLayout(FlowLayout.CENTER, Theme.PAD_MD, 0));
        subRow.setOpaque(false);

        speciesLabel = new JLabel("Species unknown");
        speciesLabel.setFont(Theme.FONT_BODY);
        speciesLabel.setForeground(Theme.TEXT_SECONDARY);

        JLabel sep1 = new JLabel("·");
        sep1.setForeground(Theme.TEXT_MUTED);

        stageLabel = new JLabel("Egg");
        stageLabel.setFont(Theme.FONT_BODY);
        stageLabel.setForeground(Theme.TEXT_SECONDARY);

        JLabel sep2 = new JLabel("·");
        sep2.setForeground(Theme.TEXT_MUTED);

        rarityLabel = UIComponents.rarityLabel("Common");

        subRow.add(speciesLabel);
        subRow.add(sep1);
        subRow.add(stageLabel);
        subRow.add(sep2);
        subRow.add(rarityLabel);

        panel.add(petNameLabel);
        panel.add(subRow);
        return panel;
    }

    private JPanel buildStatsPanel() {
        RoundedPanel panel = new RoundedPanel(Theme.BG_CARD);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(Theme.PAD_MD, Theme.PAD_LG, Theme.PAD_MD, Theme.PAD_LG));

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 0, 4, 0);
        gridBagConstraints.gridx = 0;

        // TODO: replace emojis
        hungerBar = new StatBar("🍖 Hunger", Theme.ACCENT_TERRA);
        happinessBar = new StatBar("😊 Happy", Theme.ACCENT_AMBER);
        energyBar = new StatBar("⚡ Energy", Theme.ACCENT_PERIWINKLE);
        hygieneBar = new StatBar("✨ Hygiene", Theme.ACCENT_ROSE);

        JPanel hygieneRow = new JPanel(new BorderLayout(Theme.PAD_SM, 0));
        hygieneRow.setOpaque(false);
        levelLabel = new JLabel("Lv.1");
        levelLabel.setFont(Theme.FONT_CAPTION);
        levelLabel.setForeground(Theme.TEXT_MUTED);
        hygieneRow.add(hygieneBar, BorderLayout.CENTER);
        hygieneRow.add(levelLabel, BorderLayout.EAST);

        gridBagConstraints.gridy = 0;
        panel.add(UIComponents.sectionHeader("Stats"), gridBagConstraints);
        gridBagConstraints.gridy = 1;
        panel.add(hungerBar, gridBagConstraints);
        gridBagConstraints.gridy = 2;
        panel.add(happinessBar, gridBagConstraints);
        gridBagConstraints.gridy = 3;
        panel.add(energyBar, gridBagConstraints);
        gridBagConstraints.gridy = 4;
        panel.add(hygieneRow, gridBagConstraints);

        return panel;
    }


    public void updatePetIdentity(String name, String species, String stage, String rarity, String emoji) {
        petNameLabel.setText(name);
        speciesLabel.setText(species);
        stageLabel.setText(stage);
        rarityLabel.setText("● " + rarity);
        rarityLabel.setForeground(Theme.rarityColour(rarity));
        spriteEmoji.setText(emoji);
    }

    public void updateStats(int hunger, int happiness, int energy, int hygiene) {
        hungerBar.setValue(hunger);
        happinessBar.setValue(happiness);
        energyBar.setValue(energy);
        hygieneBar.setValue(hygiene);
    }

    // TODO: Are we going to have levels or just evolve?
//    public void updateLevel(int level, int xp) {
//        levelLabel.setText("Lv." + level);
//        hygieneBar.setValue(xp % 100);
//    }

    public void updateStateLabel(String stateText) {
        stateLabel.setText(stateText);
    }

    @Override
    public void onPetEvent(PetEvent event, Pet pet) {
        SwingUtilities.invokeLater(() -> {
            switch (event) {
                case FULLNESS_CHANGED, HAPPINESS_CHANGED, ENERGY_CHANGED, FITNESS_CHANGED, HYGIENE_CHANGED ->
                        updateStats(
                        scaledToHundred(pet.getFullnessStat()),
                        scaledToHundred(pet.getHappinessStat()),
                        scaledToHundred(pet.getEnergyStat()),
                        scaledToHundred(pet.getHygieneStat())
                );
                case STATE_CHANGED ->
                        updateStateLabel(pet.getPetState().getClass().getSimpleName().replace("State", ""));
                // case AGE_CHANGED -> updateLevel(pet.getAge(), 0); // TODO: swap 0 for real XP when you have it
            }
        });
    }

    private int scaledToHundred(int raw) {
        return (int) (raw * 100.0 / Pet.getDefaultMaxStat());
    }
}
