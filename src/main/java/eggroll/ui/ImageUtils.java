package eggroll.ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageUtils {
    public static ImageIcon loadPetImage(String name, int maxSize) {
        String filename = switch (name == null ? "" : name.toLowerCase()) {
            case "dog" -> "dog.png";
            case "cat" -> "cat.png";
            default -> "egg.png";
        };

        URL url = ImageUtils.class.getResource("/images/" + filename);
        if (url == null) return null;

        ImageIcon rawImage = new ImageIcon(url);
        int width = rawImage.getIconWidth();
        int height = rawImage.getIconHeight();

        // scale to fit within maxSize x maxSize, for correct aspect ratio
        float scale = Math.min((float) maxSize / width, (float) maxSize / height);
        int scaledWidth = Math.round(width * scale);
        int scaledHeight = Math.round(height * scale);

        Image scaledImage = rawImage.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}