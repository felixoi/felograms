package net.felixoi.felograms.hologram.creation.processor;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.text.TextImage;
import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationBuilder;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationProcessor;
import net.felixoi.felograms.internal.message.Message;
import net.felixoi.felograms.internal.message.MessageTypes;
import net.felixoi.felograms.internal.message.MultiMessage;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Aliases({"image", "img"})
public final class AddImageHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<HologramCreationBuilder> process(HologramCreationBuilder builder, Player player, String arguments) {
        String[] args = arguments.split(" ");

        if (args.length != 2) {
            MultiMessage.builder()
                    .localizedMessage(MessageTypes.ERROR, (arguments.length() < 2 ? "command.arguments.not_enough" : "command.arguments.too_many"))
                    .localizedMessage(MessageTypes.INFO, "creation.image.usage")
                    .sendTo(player)
                    .buildAndSend();

            return Optional.empty();
        }

        String filename = args[0];
        int height;

        try {
            height = Integer.valueOf(args[1]);
        } catch (NumberFormatException e) {
            MultiMessage.builder()
                    .localizedMessage(MessageTypes.ERROR, "command.arguments.expected_integer", args[1])
                    .localizedMessage(MessageTypes.INFO, "creation.image.usage")
                    .sendTo(player)
                    .buildAndSend();

            return Optional.empty();
        }

        Path file = Felograms.getInstance().getPicturesDirectory().resolve(filename);
        if (!Files.exists(file)) {
            Message.ofLocalized(MessageTypes.ERROR, "creation.image.no_such_file", filename).sendTo(player);

            return Optional.empty();
        }

        try {
            String fileType = Files.probeContentType(file);

            if (fileType.equals("image/png") || fileType.equals("image/jpeg")) {
                BufferedImage image = ImageIO.read(file.toFile());
                Text[] lines = TextImage.of(image, height).toText();

                for (Text line : lines) {
                    builder.line(line);
                }

                Message.ofLocalized(MessageTypes.SUCCESS, "creation.image.success").sendTo(player); //todo

                return Optional.of(builder);
            } else {

            }
        } catch (IOException e) {
            Message.ofLocalized(MessageTypes.ERROR, "creation.image.failed").sendTo(player);

            return Optional.empty();
        }

        return Optional.empty();
    }

}
