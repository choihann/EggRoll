package eggroll.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;


public final class UIComponents {

    private UIComponents() {
    }

    // TODO: do we want rounded stuff?
    public static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color bg;

        public RoundedPanel(int radius, Color bg) {
            this.radius = radius;
            this.bg = bg;
            setOpaque(false);
        }

        public RoundedPanel(Color bg) {
            this(Theme.CORNER_RADIUS, bg);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D graphics2D = (Graphics2D) g.create();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setColor(bg);
            graphics2D.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
            graphics2D.dispose();
            super.paintComponent(g);
        }
    }

    // stat bar stuffs
    public static class StatBar extends JPanel {
        private final JLabel nameLabel;
        private final JProgressBar bar;
        private final JLabel valueLabel;

        public StatBar(String statName, Color fillColor) {
            setLayout(new BorderLayout(Theme.PAD_SM, 0));
            setOpaque(false);

            nameLabel = new JLabel(statName);
            nameLabel.setFont(Theme.FONT_CAPTION);
            nameLabel.setForeground(Theme.TEXT_SECONDARY);
            nameLabel.setPreferredSize(new Dimension(72, 16));

            bar = new JProgressBar(0, 100) {
                @Override
                protected void paintComponent(Graphics graphics) {
                    Graphics2D graphics2D = (Graphics2D) graphics.create();
                    graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    int halfHeight = getHeight() / 2;
                    graphics2D.setColor(Theme.BORDER);
                    graphics2D.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), halfHeight * 2, halfHeight * 2));
                    float frac = getValue() / (float) getMaximum();
                    int fillWidth = (int) (getWidth() * frac);
                    if (fillWidth > 0) {
                        graphics2D.setColor(fillColor);
                        graphics2D.fill(new RoundRectangle2D.Float(0, 0, fillWidth, getHeight(), halfHeight * 2, halfHeight * 2));
                    }
                    graphics2D.dispose();
                }
            };
            bar.setPreferredSize(new Dimension(0, Theme.BAR_HEIGHT));
            bar.setOpaque(false);
            bar.setBorderPainted(false);
            bar.setValue(75);

            valueLabel = new JLabel("75");
            valueLabel.setFont(Theme.FONT_CAPTION);
            valueLabel.setForeground(Theme.TEXT_MUTED);
            valueLabel.setPreferredSize(new Dimension(28, 16));
            valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            add(nameLabel, BorderLayout.WEST);
            add(bar, BorderLayout.CENTER);
            add(valueLabel, BorderLayout.EAST);
        }

        public void setValue(int value) {
            bar.setValue(Math.max(0, Math.min(100, value)));
            valueLabel.setText(String.valueOf(Math.max(0, Math.min(100, value))));
        }

        public int getValue() {
            return bar.getValue();
        }
    }

    public static class buttonStyle extends JButton {
        private final Color normalBg;
        private final Color hoverBg;
        private final Color pressBg;
        private Color currentBg;

        public buttonStyle(String text, Color background) {
            super(text);
            this.normalBg = background;
            this.hoverBg = background.brighter();
            this.pressBg = background.darker();
            this.currentBg = background;

            setFont(Theme.FONT_BUTTON);
            setForeground(Theme.TEXT_ON_ACCENT);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setMargin(new Insets(Theme.PAD_SM + 2, Theme.PAD_LG, Theme.PAD_SM + 2, Theme.PAD_LG));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent mouseEvent) {
                    currentBg = hoverBg;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent mouseEvent) {
                    currentBg = normalBg;
                    repaint();
                }

                @Override
                public void mousePressed(MouseEvent mouseEvent) {
                    currentBg = pressBg;
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent mouseEvent) {
                    currentBg = hoverBg;
                    repaint();
                }
            });
        }

        public static buttonStyle primary(String text) {
            return new buttonStyle(text, Theme.ACCENT_SAGE);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D graphics2D = (Graphics2D) graphics.create();
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics2D.setColor(currentBg);
            graphics2D.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(),
                    Theme.CORNER_RADIUS, Theme.CORNER_RADIUS));
            graphics2D.dispose();
            super.paintComponent(graphics);
        }
    }

    public static JLabel sectionHeader(String text) {
        JLabel label = new JLabel(text) {
            @Override
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                Graphics2D graphics2D = (Graphics2D) graphics.create();
                graphics2D.setColor(Theme.BORDER);
                graphics2D.setStroke(new BasicStroke(1.5f));
                int yPosition = getHeight() - 3;
                graphics2D.drawLine(0, yPosition, getWidth(), yPosition);
                graphics2D.dispose();
            }
        };
        label.setFont(Theme.FONT_HEADING);
        label.setForeground(Theme.TEXT_PRIMARY);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, Theme.PAD_SM, 0));
        return label;
    }


    public static JSeparator divider() {
        JSeparator separator = new JSeparator();
        separator.setForeground(Theme.BORDER);
        separator.setBackground(Theme.BORDER);
        return separator;
    }

    public static class CurrencyBadge extends JPanel {
        private final JLabel label;

        public CurrencyBadge(int coins) {
            setLayout(new FlowLayout(FlowLayout.LEFT, Theme.PAD_SM, 2));
            setOpaque(false);

            JLabel icon = new JLabel("C");
            icon.setFont(Theme.FONT_BODY);

            label = new JLabel(String.valueOf(coins));
            label.setFont(Theme.FONT_BUTTON);
            label.setForeground(Theme.TEXT_PRIMARY);

            add(icon);
            add(label);
        }

        public void setCoins(int coins) {
            label.setText(String.valueOf(coins));
        }
    }

    public static JLabel rarityLabel(String rarity) {
        JLabel label = new JLabel("● " + rarity);
        label.setFont(Theme.FONT_CAPTION);
        label.setForeground(Theme.rarityColour(rarity));
        return label;
    }

    public static class WrapLayout extends FlowLayout {
        public WrapLayout(int align, int horizontalGap, int verticalGap) {
            super(align, horizontalGap, verticalGap);
        }

        @Override
        public Dimension preferredLayoutSize(Container target) {
            return layoutSize(target, true);
        }

        @Override
        public Dimension minimumLayoutSize(Container target) {
            Dimension dimension = layoutSize(target, false);
            dimension.width -= (getHgap() + 1);
            return dimension;
        }

        // TODO: Fix possible magic numbers
        private Dimension layoutSize(Container target, boolean preferred) {
            synchronized (target.getTreeLock()) {
                int maxWidth = target.getWidth() - (target.getInsets().left + target.getInsets().right + getHgap() * 2);
                if (maxWidth <= 0) maxWidth = Integer.MAX_VALUE;
                Dimension dimensions = new Dimension(0, 0);
                int rowWidth = 0, rowHeight = 0;
                for (Component component : target.getComponents()) {
                    if (!component.isVisible()) continue;
                    Dimension dimension = preferred ? component.getPreferredSize() : component.getMinimumSize();
                    if (rowWidth + dimension.width > maxWidth) {
                        dimensions.width = Math.max(dimensions.width, rowWidth);
                        dimensions.height += rowHeight + getVgap();
                        rowWidth = 0;
                        rowHeight = 0;
                    }
                    rowWidth += dimension.width + getHgap();
                    rowHeight = Math.max(rowHeight, dimension.height);
                }
                Insets targetInsets = target.getInsets();
                dimensions.width = Math.max(dimensions.width, rowWidth);
                dimensions.height += rowHeight + targetInsets.top + targetInsets.bottom + getVgap() * 2;
                return dimensions;
            }
        }
    }

    public static void applyGlobalDefaults() {
        UIManager.put("Panel.background", Theme.BG_BASE);
        UIManager.put("OptionPane.background", Theme.BG_BASE);
        UIManager.put("Label.foreground", Theme.TEXT_PRIMARY);
        UIManager.put("Label.font", Theme.FONT_BODY);
        UIManager.put("ScrollPane.border", BorderFactory.createEmptyBorder());
        UIManager.put("ScrollBar.width", 8);
    }
}