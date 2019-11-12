package com.backdoored.hacks.client;

import com.backdoored.gui.CategoriesInit;
import com.backdoored.hacks.BaseHack;

public class CameraClip extends BaseHack
{
    private static CameraClip theInstance;
    
    public CameraClip() {
        super("Camera Clip", CategoriesInit.CLIENT, "Clip through walls");
        CameraClip.theInstance = this;
    }
    
    public static boolean isEnabled() {
        return CameraClip.theInstance == null || CameraClip.theInstance.getEnabled();
    }
}
