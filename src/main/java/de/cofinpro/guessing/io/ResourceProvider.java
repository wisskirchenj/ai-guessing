package de.cofinpro.guessing.io;

import java.text.MessageFormat;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * ResourceProvider as singleton using Enum Pattern.
 */
public enum ResourceProvider {
    INSTANCE;

    private static final String ARRAY_SEPARATOR = "\f";
    private static final Random RANDOM = new Random();

    private final List<ResourceBundle> resourceBundles;

    ResourceProvider() {
        resourceBundles = Stream.of("messages", "patterns").map(ResourceBundle::getBundle).toList();
    }

    /**
     * return the value associated to given key from any resource bundle.
     * @throws IllegalArgumentException if key is not assigned to any bundle.
     */
    public String get(String key) {
        return resourceBundles.stream()
                .filter(rb -> rb.containsKey(key))
                .map(rb -> rb.getString(key))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("key not found %s".formatted(key)));
    }

    /**
     * return a random value for a splittable valiues list associated to given key.
     * @throws IllegalArgumentException if key is not assigned to any bundle.
     */
    public String getRandom(String key) {
        var choices = get(key).split(ARRAY_SEPARATOR);
        return choices[RANDOM.nextInt(choices.length)];
    }

    /**
     * returns a formatted value - using the varargs parameters given - for a key associated to a positional format string.
     */
    public String getFormatted(String key, Object... params) {
        var messageFormat = new MessageFormat(get(key));
        return messageFormat.format(params);
    }

    /**
     * matches the input versus several pattern variants 'keyPrefix.index.pattern' (as specified in patterns.properties bundle)
     * and replaces the match with the corresponding replace regex 'keyPrefix.index.replace'.
     * The variants given have to cover all possible input values - otherwise an IllegalArgumentExc. is thrown if none matches.
     */
    public String getReplaced(String keyPrefix, String input) {
        var index = 1;
        while (ResourceProvider.INSTANCE.containsKey("%s.%d.pattern".formatted(keyPrefix, index))) {
            var regex = get("%s.%d.pattern".formatted(keyPrefix, index));
            var replace = get("%s.%d.replace".formatted(keyPrefix, index));
            if (input.matches(regex)) {
                return input.replaceAll(regex, replace);
            }
            index++;
        }
        throw new IllegalArgumentException("pattern mismatch in resource bundles");
    }

    private boolean containsKey(String key) {
        return resourceBundles.stream().anyMatch(rb -> rb.containsKey(key));
    }

    public static String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
