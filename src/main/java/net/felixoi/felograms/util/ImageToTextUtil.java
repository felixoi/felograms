package net.felixoi.felograms.util;


import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import static com.google.common.base.Preconditions.checkNotNull;

public final class ImageToTextUtil {

    private final static char TRANSPARENT_CHAR = ' ';

    private static final TextColor[] colors = {
            TextColors.BLACK,
            TextColors.DARK_BLUE,
            TextColors.DARK_GREEN,
            TextColors.DARK_AQUA,
            TextColors.RED,
            TextColors.DARK_PURPLE,
            TextColors.GOLD,
            TextColors.GRAY,
            TextColors.DARK_GRAY,
            TextColors.BLUE,
            TextColors.GREEN,
            TextColors.AQUA,
            TextColors.RED,
            TextColors.LIGHT_PURPLE,
            TextColors.YELLOW,
            TextColors.WHITE
    };

    private ImageToTextUtil() {
    }

    public static Text[] createTextFromImage(BufferedImage image, int height) {
        checkNotNull(image, "The variable 'image' in ImageToTextUtil#createTextFromImage(image, height) cannot be null.");

        TextColor[][] TextColors = createTextColorArray(image, height);
        return imageToText(TextColors);
    }

    private static TextColor[][] createTextColorArray(BufferedImage image, int height) {
        checkNotNull(image, "The variable 'image' in ImageToTextUtil#createTextColorArray(image, height) cannot be null.");

        double ratio = (double) image.getHeight() / image.getWidth();
        int width = (int) (height / ratio);
        BufferedImage resized = resizeImage(image, width, height);

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

    private static Text[] imageToText(TextColor[][] colors) {
        checkNotNull(colors, "The variable 'colors' in ImageToTextUtil#imageToText(colors) cannot be null.");
        Text[] lines = new Text[colors[0].length];

        for (int y = 0; y < colors[0].length; y++) {
            Text.Builder line = Text.builder();

            for (TextColor[] textColor : colors) {
                TextColor color = textColor[y];

                line.append(color == null ? Text.of(TRANSPARENT_CHAR) : Text.of(textColor[y], '\u2592'));
            }

            lines[y] = Text.of(line, TextColors.RESET);
        }


        return lines;
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        checkNotNull(originalImage, "The variable 'originalImage' in ImageToTextUtil#resizeImage(originalImage, width, height) cannot be null.");

        AffineTransform af = new AffineTransform();
        af.scale(width / (double) originalImage.getWidth(), height / (double) originalImage.getHeight());

        AffineTransformOp operation = new AffineTransformOp(af, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return operation.filter(originalImage, null);
    }

    private static double getDistanceBetweenColors(Color color1, Color color2) {
        checkNotNull(color1, "The variable 'color1' in ImageToTextUtil#getDistanceBetweenColors(color1, color2) cannot be null.");
        checkNotNull(color2, "The variable 'color2' in ImageToTextUtil#getDistanceBetweenColors(color1, color2) cannot be null.");

        double rmean = (color1.getRed() + color2.getRed()) / 2.0;
        double r = color1.getRed() - color2.getRed();
        double g = color1.getGreen() - color2.getGreen();
        int b = color1.getBlue() - color2.getBlue();
        double weightR = 2 + rmean / 256.0;
        double weightG = 4.0;
        double weightB = 2 + (255 - rmean) / 256.0;
        return weightR * r * r + weightG * g * g + weightB * b * b;
    }

    private static boolean isColorIdentical(Color color1, Color color2) {
        checkNotNull(color1, "The variable 'color1' in ImageToTextUtil#isColorIdentical(color1, color2) cannot be null.");
        checkNotNull(color2, "The variable 'color2' in ImageToTextUtil#isColorIdentical(color1, color2) cannot be null.");

        return Math.abs(color1.getRed() - color2.getRed()) <= 5 &&
                Math.abs(color1.getGreen() - color2.getGreen()) <= 5 &&
                Math.abs(color1.getBlue() - color2.getBlue()) <= 5;
    }

    private static TextColor getClosestTextColor(Color color) {
        checkNotNull(color, "The variable 'color' in ImageToTextUtil#getClosestTextColor(color) cannot be null.");

        if (color.getAlpha() < 128) return null;

        int index = 0;
        double best = -1;

        for (TextColor textColor : colors) {
            if (isColorIdentical(textColor.getColor().asJavaColor(), color)) {
                return textColor;
            }
        }

        for (int i = 0; i < colors.length; i++) {
            double distance = getDistanceBetweenColors(color, colors[i].getColor().asJavaColor());
            if (distance < best || best == -1) {
                best = distance;
                index = i;
            }
        }

        return colors[index];
    }

}
