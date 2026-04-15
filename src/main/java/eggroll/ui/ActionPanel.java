package eggroll.ui;

import eggroll.observer.PetEvent;
import eggroll.observer.PetObserver;
import eggroll.pet.Pet;
import eggroll.pet.petstate.HungryState;
import eggroll.pet.petstate.PetState;
import eggroll.pet.petstate.TiredState;
import eggroll.pet.petstate.UnbornState;
import eggroll.ui.UIComponents.buttonStyle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

// I'm thinking buttons can be enabled or disabled based on the state pattern
// TODO: each button should map to a command object like feeding, playing, resting, etc
public class ActionPanel extends JPanel implements PetObserver {

    private final buttonStyle feedBtn;
    private final buttonStyle playBtn;
    private final buttonStyle restBtn;
    private final buttonStyle batheBtn;
    private final buttonStyle healBtn;
    private final buttonStyle trainBtn;

    // this is for our theoretical feedback
    private final JLabel feedbackLabel;

    public ActionPanel() {
        setLayout(new BorderLayout(0, Theme.PAD_MD));
        setBackground(Theme.BG_BASE);
        setBorder(BorderFactory.createEmptyBorder(Theme.PAD_MD, Theme.PAD_LG, Theme.PAD_LG, Theme.PAD_LG));

        add(UIComponents.sectionHeader("Actions"), BorderLayout.NORTH);

        // button grid
        JPanel primaryGrid = new JPanel(new GridLayout(2, 2, Theme.PAD_MD, Theme.PAD_MD));
        primaryGrid.setOpaque(false);

        // for now, I use emojis in place of actual images because it feels strangely empty
        feedBtn = buildActionButton("🍖  Feed", Theme.ACCENT_TERRA, "Feed your pet to restore hunger.");
        playBtn = buildActionButton("🎾  Play", Theme.ACCENT_SAGE, "Play with your pet to boost happiness.");
        restBtn = buildActionButton("🌙  Rest", Theme.ACCENT_PERIWINKLE, "Put your pet to sleep to restore energy.");
        batheBtn = buildActionButton("🛁  Bathe", Theme.ACCENT_ROSE, "Give your pet a bath for a happiness boost.");

        primaryGrid.add(feedBtn);
        primaryGrid.add(playBtn);
        primaryGrid.add(restBtn);
        primaryGrid.add(batheBtn);

        // TODO: merge into one grid instead of two
        // I left it at two cause I don't know how many actions we want yet
        JPanel secondaryRow = new JPanel(new GridLayout(1, 2, Theme.PAD_MD, 0));
        secondaryRow.setOpaque(false);

        healBtn = buildActionButton("💊  Heal", Theme.ACCENT_AMBER, "Use a medicine item to heal your pet.");

        trainBtn = buildActionButton("📚  Train", Theme.ACCENT_SAGE_DARK, "Train your pet to gain bonus XP.");

        secondaryRow.add(healBtn);
        secondaryRow.add(trainBtn);

        // this is for feedback stuffs
        feedbackLabel = new JLabel(" ", SwingConstants.CENTER);
        feedbackLabel.setFont(Theme.FONT_CAPTION);
        feedbackLabel.setForeground(Theme.TEXT_SECONDARY);

        // putting everything together
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        body.add(primaryGrid);
        body.add(Box.createVerticalStrut(Theme.PAD_SM));
        body.add(secondaryRow);
        body.add(Box.createVerticalStrut(Theme.PAD_SM));
        body.add(feedbackLabel);

        add(body, BorderLayout.CENTER);
    }

    // TODO: bind to the commands
    public void setFeedAction(ActionListener feedListener) {
        feedBtn.addActionListener(feedListener);
    }

    public void setPlayAction(ActionListener playListener) {
        playBtn.addActionListener(playListener);
    }

    public void setNapAction(ActionListener napListener) {
        restBtn.addActionListener(napListener);
    }

    public void setBatheAction(ActionListener batheListener) {
        batheBtn.addActionListener(batheListener);
    }

    // TODO: configure which buttons given pet state
    public void applyPetState(PetState state) {
        setAllEnabled(true);
        switch (state) {
            case TiredState tiredState -> {
                feedBtn.setEnabled(false);
                playBtn.setEnabled(false);
                trainBtn.setEnabled(false);
                showFeedback("Your pet is resting…");
            }
            case UnbornState unbornState -> {
                setAllEnabled(false);
                feedBtn.setEnabled(true);
                showFeedback("It will hatch soon!");
            }
            case HungryState hungrystate -> {
                playBtn.setEnabled(false);
                trainBtn.setEnabled(false);
                showFeedback("Your pet is hungry!");
            }
            default -> showFeedback("");
        }
    }

    // feedback message? maybe?
    public void showFeedback(String message) {
        feedbackLabel.setText(message == null || message.isBlank() ? " " : message);
    }

    // the helpers

    private buttonStyle buildActionButton(String label, Color color, String tooltip) {
        buttonStyle btn = new buttonStyle(label, color);
        btn.setToolTipText(tooltip);
        btn.setPreferredSize(new Dimension(0, 48));
        return btn;
    }

    private void setAllEnabled(boolean enabled) {
        feedBtn.setEnabled(enabled);
        playBtn.setEnabled(enabled);
        restBtn.setEnabled(enabled);
        batheBtn.setEnabled(enabled);
        healBtn.setEnabled(enabled);
        trainBtn.setEnabled(enabled);
    }

    @Override
    public void onPetEvent(PetEvent event, Pet pet) {
        if (event == PetEvent.STATE_CHANGED) {
            SwingUtilities.invokeLater(() ->
                    applyPetState(pet.getPetState())
            );
        }
    }
}