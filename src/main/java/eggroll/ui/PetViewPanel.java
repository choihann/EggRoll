package eggroll.ui;

import eggroll.gacha.GachaRarity;
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
    private JLabel spriteImage;

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

        spriteImage = new JLabel(String.valueOf(SwingConstants.CENTER));
        spriteImage.setToolTipText("Pet sprite");
        ImageIcon eggIcon = ImageUtils.loadPetImage("egg", 180);
        if (eggIcon != null) {
            spriteImage.setIcon(eggIcon);
            spriteImage.setText(null);
        } else {
            spriteImage.setText("🥚");
            spriteImage.setFont(new Font("Serif", Font.PLAIN, 96));
        }

        spriteArea = new RoundedPanel(Theme.BG_CARD);
        spriteArea.setLayout(new BorderLayout());
        spriteArea.setBorder(BorderFactory.createEmptyBorder(Theme.PAD_LG, Theme.PAD_XL, Theme.PAD_LG, Theme.PAD_XL));
        spriteArea.setMaximumSize(new Dimension(260, 260));
        spriteArea.add(spriteImage, BorderLayout.CENTER);

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

        petNameLabel.setText("—");
        speciesLabel.setText("No pet selected");
    }

    // helpers
    private JPanel buildIdentityPanel() {
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

        rarityLabel = UIComponents.rarityLabel(GachaRarity.Common);

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

        gridBagConstraints.gridy = 0;
        panel.add(hygieneBar, gridBagConstraints);
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


    public void updatePetIdentity(String name, String species, String stage, GachaRarity rarity, String emoji) {
        petNameLabel.setText(name);
        speciesLabel.setText(species);
        stageLabel.setText(stage);
        rarityLabel.setText("● " + rarity);
        rarityLabel.setForeground(Theme.rarityColor(rarity));
        ImageIcon icon = ImageUtils.loadPetImage(stage.equalsIgnoreCase("UNBORN") ? "egg" : species, 180);
        if (icon != null) {
            spriteImage.setIcon(icon);
            spriteImage.setText(null);
        } else {
            spriteImage.setIcon(null);
            spriteImage.setText(emoji); // fall back, just in case it doesn't load
            spriteImage.setFont(new Font("Serif", Font.PLAIN, 96));
        }
    }

    public void updateStats(int hunger, int happiness, int energy, int hygiene) {
        hungerBar.setValue(hunger);
        happinessBar.setValue(happiness);
        energyBar.setValue(energy);
        hygieneBar.setValue(hygiene);
    }

    public void updateStateLabel(String stateText) {
        stateLabel.setText(stateText);
    }

    @Override
    public void onPetEvent(PetEvent event, Pet pet) {
        SwingUtilities.invokeLater(() -> {
            switch (event) {
                case FULLNESS_CHANGED, HAPPINESS_CHANGED, ENERGY_CHANGED, HYGIENE_CHANGED ->
                        updateStats(
                                scaledToHundred(pet.getFullnessStat(), pet),
                                scaledToHundred(pet.getHappinessStat(), pet),
                                scaledToHundred(pet.getEnergyStat(), pet),
                                scaledToHundred(pet.getHygieneStat(), pet)
                );
                case EVOLUTION_OCCURRED -> {
                    updateStats(
                            scaledToHundred(pet.getFullnessStat(), pet),
                            scaledToHundred(pet.getHappinessStat(), pet),
                            scaledToHundred(pet.getEnergyStat(), pet),
                            scaledToHundred(pet.getHygieneStat(), pet)
                    );
                    stageLabel.setText(pet.getEvolutionStage().name());
                    updatePetIdentity(
                            pet.getName(),
                            pet.getSpecies(),
                            pet.getEvolutionStage().name(),
                            pet.getRarity(),
                            ""
                    );
                }
                case STATE_CHANGED ->
                        updateStateLabel(pet.getPetState().getClass().getSimpleName().replace("State", ""));
            }
        });
    }

    private int scaledToHundred(int raw, Pet pet) {
        return (int) (raw * 100.0 / pet.getMaxStat());
    }
}
