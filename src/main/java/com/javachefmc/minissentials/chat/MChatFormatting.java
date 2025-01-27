//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.javachefmc.minissentials.chat;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.minecraft.ChatFormatting;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public enum MChatFormatting implements StringRepresentable {
    BLACK("BLACK", '0', 0, 0),
    DARK_BLUE("DARK_BLUE", '1', 1, 170),
    DARK_GREEN("DARK_GREEN", '2', 2, 43520),
    DARK_AQUA("DARK_AQUA", '3', 3, 43690),
    DARK_RED("DARK_RED", '4', 4, 11141120),
    DARK_PURPLE("DARK_PURPLE", '5', 5, 11141290),
    GOLD("GOLD", '6', 6, 16755200),
    GRAY("GRAY", '7', 7, 11184810),
    DARK_GRAY("DARK_GRAY", '8', 8, 5592405),
    BLUE("BLUE", '9', 9, 5592575),
    GREEN("GREEN", 'a', 10, 5635925),
    AQUA("AQUA", 'b', 11, 5636095),
    RED("RED", 'c', 12, 16733525),
    LIGHT_PURPLE("LIGHT_PURPLE", 'd', 13, 16733695),
    YELLOW("YELLOW", 'e', 14, 16777045),
    WHITE("WHITE", 'f', 15, 16777215),

    OBFUSCATED("OBFUSCATED", 'k', true),
    BOLD("BOLD", 'l', true),
    STRIKETHROUGH("STRIKETHROUGH", 'm', true),
    UNDERLINE("UNDERLINE", 'n', true),
    ITALIC("ITALIC", 'o', true),

    RESET("RESET", 'r', -1, null);

    public static final Codec<MChatFormatting> CODEC = StringRepresentable.fromEnum(MChatFormatting::values);
    public static final char PREFIX_CODE = '&';
    private static final Map FORMATTING_BY_NAME = Arrays.stream(values()).collect(Collectors.toMap((chatFormatting) -> cleanName(chatFormatting.name), (chatFormatting) -> chatFormatting));
    private static final Pattern STRIP_FORMATTING_PATTERN = Pattern.compile("(?i)&[0-9A-FK-OR]");
    private final String name;
    private final char code;
    private final boolean isFormat;
    private final String toString;
    private final int id;
    @Nullable
    private final Integer color;

    private static String cleanName(String string) {
        return string.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
    }

    MChatFormatting(final String string2, final char c, final int j, @Nullable final Integer integer) {
        this(string2, c, false, j, integer);
    }

    MChatFormatting(final String string2, final char c, final boolean bl) {
        this(string2, c, bl, -1, null);
    }

    MChatFormatting(final String string2, final char c, final boolean bl, final int j, @Nullable final Integer integer) {
        this.name = string2;
        this.code = c;
        this.isFormat = bl;
        this.id = j;
        this.color = integer;
        this.toString = "&" + c;
    }

    public char getChar() {
        return this.code;
    }

    public int getId() {
        return this.id;
    }

    public boolean isFormat() {
        return this.isFormat;
    }

    public boolean isColor() {
        return !this.isFormat && this != RESET;
    }

    @Nullable
    public Integer getColor() {
        return this.color;
    }

    public String getName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public String toString() {
        return this.toString;
    }

    @Nullable
    @Contract("!null->!null;_->_")
    public static String stripFormatting(@Nullable String string) {
        return string == null ? null : STRIP_FORMATTING_PATTERN.matcher(string).replaceAll("");
    }

    @Nullable
    public static MChatFormatting getByName(@Nullable String string) {
        return string == null ? null : (MChatFormatting)FORMATTING_BY_NAME.get(cleanName(string));
    }

    @Nullable
    public static MChatFormatting getById(int i) {
        if (i < 0) {
            return RESET;
        } else {
            MChatFormatting[] var1 = values();

            for (MChatFormatting chatFormatting : var1) {
                if (chatFormatting.getId() == i) {
                    return chatFormatting;
                }
            }

            return null;
        }
    }

    @Nullable
    public static MChatFormatting getByCode(char c) {
        char d = Character.toLowerCase(c);
        MChatFormatting[] var2 = values();

        for (MChatFormatting chatFormatting : var2) {
            if (chatFormatting.code == d) {
                return chatFormatting;
            }
        }

        return null;
    }

    public static Collection<String> getNames(boolean bl, boolean bl2) {
        List<String> list = Lists.newArrayList();
        MChatFormatting[] var3 = values();

        for (MChatFormatting chatFormatting : var3) {
            if ((!chatFormatting.isColor() || bl) && (!chatFormatting.isFormat() || bl2)) {
                list.add(chatFormatting.getName());
            }
        }

        return list;
    }

    public String getSerializedName() {
        return this.getName();
    }


    ////// ADDED BY ME

    public static ChatFormatting getStyle(char code){
        return switch (code) {
            case '1' -> ChatFormatting.AQUA;
            default -> ChatFormatting.WHITE;
        };
    }
}
