package com.backdoored.natives;

public class Natives
{
    private static Natives INSTANCE;
    
    public Natives() {
        super();
    }
    
    public static String decryptStr(final String txt) {
        if (Natives.INSTANCE == null) {
            Natives.INSTANCE = new Natives();
        }
        return "";
    }
    
    public static String encryptStr(final String txt) {
        if (Natives.INSTANCE == null) {
            Natives.INSTANCE = new Natives();
        }
        return "";
    }
    
    native String decryptStrNative(final String p0);
    
    native String encryptStrNative(final String p0);
}
