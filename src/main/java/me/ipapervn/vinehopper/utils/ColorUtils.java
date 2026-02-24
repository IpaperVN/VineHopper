package me.ipapervn.vinehopper.utils;

import net.kyori.adventure.text.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private static final Pattern GRADIENT_PATTERN = Pattern.compile("<gradient:#([A-Fa-f0-9]{6}):#([A-Fa-f0-9]{6})>(.*?)</gradient>");

    public static Component colorize(String text) {
        text = applyGradient(text);
        text = applyHex(text);
        return Component.text(text);
    }

    public static void sendMultiline(org.bukkit.command.ConsoleCommandSender sender, String... lines) {
        for (String line : lines) {
            sender.sendMessage(line);
        }
    }

    private static String applyHex(String text) {
        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuilder buffer = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, "§x§" + 
                matcher.group(1).charAt(0) + "§" + matcher.group(1).charAt(1) + "§" +
                matcher.group(1).charAt(2) + "§" + matcher.group(1).charAt(3) + "§" +
                matcher.group(1).charAt(4) + "§" + matcher.group(1).charAt(5));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private static String applyGradient(String text) {
        Matcher matcher = GRADIENT_PATTERN.matcher(text);
        StringBuilder buffer = new StringBuilder();
        while (matcher.find()) {
            String start = matcher.group(1);
            String end = matcher.group(2);
            String content = matcher.group(3);
            matcher.appendReplacement(buffer, createGradient(content, start, end));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private static String createGradient(String text, String startHex, String endHex) {
        int length = text.length();
        if (length == 0) return text;

        int startR = Integer.parseInt(startHex.substring(0, 2), 16);
        int startG = Integer.parseInt(startHex.substring(2, 4), 16);
        int startB = Integer.parseInt(startHex.substring(4, 6), 16);

        int endR = Integer.parseInt(endHex.substring(0, 2), 16);
        int endG = Integer.parseInt(endHex.substring(2, 4), 16);
        int endB = Integer.parseInt(endHex.substring(4, 6), 16);

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            float ratio = (float) i / (length - 1);
            int r = (int) (startR + ratio * (endR - startR));
            int g = (int) (startG + ratio * (endG - startG));
            int b = (int) (startB + ratio * (endB - startB));

            String hex = String.format("%02x%02x%02x", r, g, b);
            result.append("§x§").append(hex.charAt(0)).append("§").append(hex.charAt(1))
                  .append("§").append(hex.charAt(2)).append("§").append(hex.charAt(3))
                  .append("§").append(hex.charAt(4)).append("§").append(hex.charAt(5))
                  .append(text.charAt(i));
        }
        return result.toString();
    }
}
