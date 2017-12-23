package net.felixoi.felograms.util;

import org.spongepowered.api.text.Text;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TextUtil {

    public static Text listToText(List<Text> list) {
        checkNotNull(list, "The variable 'list' in TextUtil#listToText(list) cannot be null.");

        Iterator<Text> iterator = list.iterator();
        Text.Builder builder = Text.builder();

        while (iterator.hasNext()) {
            Text line = iterator.next();
            builder.append(line);

            if (iterator.hasNext()) {
                builder.append(Text.NEW_LINE);
            }
        }

        return builder.build();
    }

    public static Text listToText(Text[] texts) {
        return listToText(Arrays.asList(texts));
    }

}
