package com.library.util;

public class BookUtils {

    /**
     * Returns a formatted message by substituting placeholders in the message
     * template with the provided arguments.
     *
     * <p>The message template may contain placeholders in the format {@code %s},
     * which will be replaced by the string representation of the corresponding
     * argument in the order they appear. For example:
     * <pre>
     *     String template = "Hello, %s! You have %d new messages.";
     *     getFormattedMessage(template, "Alice", 5);
     * </pre>
     * produces:
     * <pre>
     *     "Hello, Alice! You have 5 new messages."
     * </pre>
     *
     * @param template the message template containing placeholders, not null
     * @param args the arguments to replace the placeholders, may be empty
     * @return the formatted message with placeholders replaced by arguments
     * @throws IllegalArgumentException if the number of placeholders in the
     *         template does not match the number of arguments
     */
    public static String getFormattedMessage(String template, Object... args) {
        return String.format(template, args);
    }

}
