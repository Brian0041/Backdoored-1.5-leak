package com.backdoored.utils.chat;

import org.apache.commons.lang3.ArrayUtils;

public class Fancy extends Mutator
{
    private static final char[] nonUnicodeChars;
    
    public Fancy() {
        super();
    }
    
    @Override
    public String getName() {
        return "Fancy";
    }
    
    @Override
    public String mutate(final String msg) {
        final StringBuilder output = new StringBuilder();
        for (final char c : msg.toCharArray()) {
            if (c < '!' || c > '\u0080') {
                output.append(c);
            }
            else if (ArrayUtils.contains(Fancy.nonUnicodeChars, c)) {
                output.append(c);
            }
            else {
                output.append(Character.toChars(c + 'ﻠ'));
            }
        }
        return output.toString();
    }
    
    static {
        nonUnicodeChars = new char[] { '(', ')', '{', '}', '[', ']', '|' };
    }
}
