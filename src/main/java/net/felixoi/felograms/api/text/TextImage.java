package net.felixoi.felograms.api.text;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextElement;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import static com.google.common.base.Preconditions.checkNotNull;

public class TextImage implements TextElement {

    private final static char TRANSPARENT_CHAR = ' ';
    private final static char NORMAL_CHAR = '\u2592';
    private static final TextColor[] COLORS = {
            TextColors.BLACK, TextColors.DARK_BLUE, TextColors.DARK_GREEN, TextColors.DARK_AQUA, TextColors.RED, TextColors.DARK_PURPLE,
            TextColors.GOLD, TextColors.GRAY, TextColors.DARK_GRAY, TextColors.BLUE, TextColors.GREEN, TextColors.AQUA, TextColors.RED,
            TextColors.LIGHT_PURPLE, TextColors.YELLOW, TextColors.WHITE
    };

    private BufferedImage image;
    private int height;
    private Text[] texts;

    private TextImage(BufferedImage image, int height) {
        this.image = checkNotNull(image, "The variable 'image' in TextImage#TextImage(image, height) cannot be null.");
        this.height = height;
        this.texts = this.createTexts();
    }

    public static TextImage of(BufferedImage image, int height) {
        return new TextImage(image, height);
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public int getHeight() {
        return this.height;
    }

    public Text[] toText() {
        return this.texts;
    }

    @Override
    public void applyTo(Text.Builder builder) {
        builder.append(Text.NEW_LINE);
        for (Text text : this.texts) {
            builder.append(text, Text.NEW_LINE);
        }
    }

    private Text[] createTexts() {
        TextColor[][] colors = this.createTextColorArray();

        Text[] lines = new Text[colors[0].length];

        for (int y = 0; y < colors[0].length; y++) {
            Text.Builder line = Text.builder();

            for (TextColor[] textColor : colors) {
                TextColor color = textColor[y];

                line.append(color == null ? Text.of(TRANSPARENT_CHAR) : Text.of(textColor[y], NORMAL_CHAR));
            }

            lines[y] = Text.of(line, TextColors.RESET);
        }


        return lines;
    }

    private TextColor[][] createTextColorArray() {
        double ratio = (double) this.image.getHeight() / this.image.getWidth();
        int width = (int) (this.height / ratio);
        BufferedImage resized = resizeImage(width, this.height);

        TextColor[][] chatImg = new TextColor[resized.getWidth()][resized.getHeight()];
        for (int x = 0; x < resized.getWidth(); x++) {
            for (int y = 0; y < resized.getHeight(); y++) {
                int rgb = resized.getRGB(x, y);
                TextColor closest = getClosestTextColor(new Color(rgb, true));
                chatImg[x][y] = closest;
            }
        }
        return chatImg;
    }

    private BufferedImage resizeImage(int width, int height) {
        AffineTransform af = new AffineTransform();
        af.scale(width / (double) this.image.getWidth(), height / (double) this.image.getHeight());

        AffineTransformOp operation = new AffineTransformOp(af, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return operation.filter(this.image, null);
    }

    private double getDistanceBetweenColors(Color firstColor, Color secondColor) {
        checkNotNull(firstColor, "The variable 'firstColor' in TextImage#getDistanceBetweenColors(firstColor, secondColor) cannot be null.");
        checkNotNull(secondColor, "The variable 'secondColor' in TextImage#getDistanceBetweenColors(firstColor, secondColor) cannot be null.");

        double rmean = (firstColor.getRed() + secondColor.getRed()) / 2.0;
        double r = firstColor.getRed() - secondColor.getRed();
        double g = firstColor.getGreen() - secondColor.getGreen();
        int b = firstColor.getBlue() - secondColor.getBlue();
        double weightR = 2 + rmean / 256.0;
        double weightG = 4.0;
        double weightB = 2 + (255 - rmean) / 256.0;
        return weightR * r * r + weightG * g * g + weightB * b * b;
    }

    private boolean isColorIdentical(Color firstColor, Color secondColor) {
        checkNotNull(firstColor, "The variable 'firstColor' in TextImage#isColorIdentical(firstColor, secondColor) cannot be null.");
        checkNotNull(secondColor, "The variable 'secondColor' in TextImage#isColorIdentical(firstColor, secondColor) cannot be null.");

        return Math.abs(firstColor.getRed() - secondColor.getRed()) <= 5 &&
                Math.abs(firstColor.getGreen() - secondColor.getGreen()) <= 5 &&
                Math.abs(firstColor.getBlue() - secondColor.getBlue()) <= 5;
    }

    private TextColor getClosestTextColor(Color color) {
        checkNotNull(color, "The variable 'color' in TextImage#getClosestTextColor(color) cannot be null.");

        if (color.getAlpha() < 128) return null;

        int index = 0;
        double best = -1;

        for (TextColor textColor : COLORS) {
            if (isColorIdentical(textColor.getColor().asJavaColor(), color)) {
                return textColor;
            }
        }

        for (int i = 0; i < COLORS.length; i++) {
            double distance = getDistanceBetweenColors(color, COLORS[i].getColor().asJavaColor());
            if (distance < best || best == -1) {
                best = distance;
                index = i;
            }
        }

        return COLORS[index];
    }

}
