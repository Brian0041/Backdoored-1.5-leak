package com.backdoored;

import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.Objects;
import java.net.URL;
import java.util.Vector;
import java.lang.reflect.Field;

public class DllManager
{
    private static Field LIBRARIES;
    
    public DllManager() {
        super();
    }
    
    public static String[] getLoadedLibraries(final ClassLoader loader) {
        try {
            final Vector<String> libraries = (Vector<String>)DllManager.LIBRARIES.get(loader);
            return libraries.toArray(new String[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new String[0];
        }
    }
    
    public static void loadJarDll(final String name) throws IOException {
        final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        final File file = new File(Objects.requireNonNull(classLoader.getResource(name)).getFile());
        System.out.println(file.getAbsolutePath());
        final InputStream in = new FileInputStream(file);
        final byte[] buffer = new byte[1024];
        int read = -1;
        final File temp = File.createTempFile(name, "");
        final FileOutputStream fos = new FileOutputStream(temp);
        while ((read = in.read(buffer)) != -1) {
            fos.write(buffer, 0, read);
        }
        fos.close();
        in.close();
        System.out.println(temp.getAbsolutePath());
        System.load(temp.getAbsolutePath());
    }
    
    public static void loadBackdooredLibrary() {
        try {
            loadJarDll("com_backdoored_DllManager.dll");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String decrypt(final String s) {
        return decrypt(s, Backdoored.providedLicense);
    }
    
    public static native String encrypt(final String p0);
    
    public static native String decrypt(final String p0, final String p1);
    
    public static native String getHwid();
    
    static {
        try {
            (DllManager.LIBRARIES = ClassLoader.class.getDeclaredField("loadedLibraryNames")).setAccessible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
            DllManager.LIBRARIES = null;
        }
    }
}
