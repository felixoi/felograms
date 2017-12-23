package net.felixoi.felograms.hologram.creation;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.api.hologram.HologramCreationProcessor;
import net.felixoi.felograms.api.message.Message;
import net.felixoi.felograms.api.message.MessageTypes;
import net.felixoi.felograms.util.ImageToTextUtil;
import net.felixoi.felograms.util.TextUtil;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class AddImageHologramCreationProcessor implements HologramCreationProcessor {

    @Override
    public String getID() {
        return "ADD_IMAGE";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("image", "img");
    }

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder currentBuilder, UUID uuid, MessageReceiver creator, String arguments, Location<World> location) {
        // format: <(img|image) <filename> <height>

        String[] args = arguments.split(" ");

        if (args.length != 2) {
            Message.builder().messageType(MessageTypes.ERROR).localizedContent("creation.image_usage").sendTo(creator).build();

            return Optional.empty();
        }

        String filename = args[0];
        int height;

        if (!filename.matches("(.*).(png|jpg|jpeg)")) {
            Message.builder().messageType(MessageTypes.ERROR).localizedContent("creation.image.invalid_file").sendTo(creator).build();

            return Optional.empty();
        }

        try {
            height = Integer.valueOf(args[1]);
        } catch (NumberFormatException e) {
            Message.builder().messageType(MessageTypes.ERROR).localizedContent("creation.image.height_no_number").sendTo(creator).build();

            return Optional.empty();
        }

        Path file = Felograms.getInstance().getPicturesDirectory().resolve(filename);
        if (!Files.exists(file)) {
            Message.builder().messageType(MessageTypes.ERROR).localizedContent("creation.image.no_file").sendTo(creator).build();
            return Optional.empty();
        }

        try {
            BufferedImage image = ImageIO.read(file.toFile());
            Text[] lines = ImageToTextUtil.createTextFromImage(image, height);

            for (Text line : lines) {
                currentBuilder.line(line);
            }

            Message.builder().messageType(MessageTypes.SUCCESS).localizedContent("creation.image.added").hoverContentText(TextUtil.listToText(lines)).sendTo(creator).build();

            return Optional.of(currentBuilder);
        } catch (IOException e) {
            Message.builder().messageType(MessageTypes.ERROR).localizedContent("creation.image.failed_to_convert").sendTo(creator).build();

            return Optional.empty();
        }
    }

}
