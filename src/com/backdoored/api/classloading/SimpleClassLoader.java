package com.backdoored.api.classloading;

import net.minecraftforge.fml.common.FMLLog;
import java.util.HashSet;
import java.util.Set;

public class SimpleClassLoader
{
    private Class[] clazzes;
    private Set<Class> erroredClasses;
    
    public SimpleClassLoader() {
        super();
    }
    
    public SimpleClassLoader build(final Class[] clazzes) {
        this.clazzes = clazzes;
        this.erroredClasses = new HashSet<Class>(clazzes.length);
        return this;
    }
    
    public SimpleClassLoader initialise() {
        for (final Class clazz : this.clazzes) {
            try {
                clazz.newInstance();
            }
            catch (Exception e) {
                this.erroredClasses.add(clazz);
                FMLLog.log.info("Error initialising class " + clazz.getName());
                e.printStackTrace();
            }
        }
        return this;
    }
    
    public Set<Class> getErroredClasses() {
        return this.erroredClasses;
    }
}
