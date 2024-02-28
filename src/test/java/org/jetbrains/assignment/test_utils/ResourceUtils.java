package org.jetbrains.assignment.test_utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@UtilityClass
public class ResourceUtils {

    public static String readAsStringFromFile(Class<?> clazz, String path) {
        try {
            return IOUtils.toString(Objects.requireNonNull(clazz.getResourceAsStream(path)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String readAsStringFromSourcePath(String resourcePath) {
        try {
            return IOUtils.toString(Objects.requireNonNull(ResourceUtils.class.getResource(resourcePath)),
                    StandardCharsets.UTF_8);
        } catch (IOException exception) {
            return "";
        }
    }

    public static String readAsStringWithPlaceholders(String resourcePath, Object... placeholders) {
        String content = readAsStringFromSourcePath(resourcePath);
        for (Object placeholder : placeholders) {
            content = StringUtils.replaceOnce(content, "PLACEHOLDER", placeholder.toString());
        }
        return content;
    }
}
