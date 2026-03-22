package eggroll.ui;

import java.awt.*;

public final class Theme {

    private Theme() {
    }

    // pulled this from a mood board dropper color board, feel free to change any part of this
    public static final Color BG_BASE = new Color(0xFDF6EC);
    public static final Color BG_CARD = new Color(0xF5ECD8);
    public static final Color ACCENT_SAGE = new Color(0xA8C5A0);
    public static final Color ACCENT_SAGE_DARK = new Color(0x7DA87A);
    public static final Color ACCENT_TERRA = new Color(0xD4856A);
    public static final Color ACCENT_AMBER = new Color(0xE8B84B);
    public static final Color ACCENT_ROSE = new Color(0xD4A0A0);
    public static final Color ACCENT_PERIWINKLE = new Color(0x9BAED4);
    public static final Color TEXT_PRIMARY = new Color(0x3D2B1F);
    public static final Color TEXT_SECONDARY = new Color(0x7A5C48);
    public static final Color TEXT_MUTED = new Color(0xB09880);
    public static final Color BORDER = new Color(0xD9C4A8);
    public static final Color TEXT_ON_ACCENT = new Color(0xFDF6EC);


    public static final Color RARITY_COMMON = new Color(0xA0A0A0);
    public static final Color RARITY_UNCOMMON = new Color(0x6BAE72);
    public static final Color RARITY_RARE = new Color(0x5B8DD9);
    public static final Color RARITY_EPIC = new Color(0xA06DC8);
    public static final Color RARITY_LEGENDARY = new Color(0xE8B84B);

    // pulled this from what looked bout common but in pastels
    public static Color rarityColour(String rarity) {
        return switch (rarity.toLowerCase()) {
            case "uncommon" -> RARITY_UNCOMMON;
            case "rare" -> RARITY_RARE;
            case "epic" -> RARITY_EPIC;
            case "legendary" -> RARITY_LEGENDARY;
            default -> RARITY_COMMON;
        };
    }

    public static final Font FONT_HEADING = new Font("Comic Sans MS", Font.BOLD, 22);
    public static final Font FONT_BODY = new Font("Comic Sans MS", Font.PLAIN, 13);
    public static final Font FONT_CAPTION = new Font("Comic Sans MS", Font.PLAIN, 11);
    public static final Font FONT_BUTTON = new Font("Comic Sans MS", Font.BOLD, 13);
    public static final Font FONT_PET_NAME = new Font("Comic Sans MS", Font.BOLD, 28);

    // spacing that I played with for a while

    public static final int PAD_SM = 6;
    public static final int PAD_MD = 12;
    public static final int PAD_LG = 20;
    public static final int PAD_XL = 32;

    public static final int CORNER_RADIUS = 16;
    public static final int BAR_HEIGHT = 14;

    public static Insets cardInsets() {
        return new Insets(PAD_MD, PAD_LG, PAD_MD, PAD_LG);
    }
}