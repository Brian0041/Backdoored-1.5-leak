package com.backdoored.api.classloading;

import net.minecraft.launchwrapper.LaunchClassLoader;
import net.minecraft.launchwrapper.Launch;
import java.net.URL;

public class BackdooredUrlClassLoader
{
    public BackdooredUrlClassLoader(final URL classUrl) {
        super();
        final LaunchClassLoader classLoader = Launch.classLoader;
        classLoader.addURL(classUrl);
    }
}
