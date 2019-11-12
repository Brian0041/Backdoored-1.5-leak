package com.backdoored.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.TrayIcon;
import java.awt.SystemTray;

public class TrayUtils
{
    private static SystemTray tray;
    private static TrayIcon trayIcon;
    
    public TrayUtils() {
        super();
        try {
            TrayUtils.tray = SystemTray.getSystemTray();
            (TrayUtils.trayIcon = new TrayIcon(new BufferedImage(20, 20, 1), "Tray Demo")).setImageAutoSize(true);
            TrayUtils.trayIcon.setToolTip("Backdoored");
            TrayUtils.tray.add(TrayUtils.trayIcon);
        }
        catch (Exception e) {
            e.printStackTrace();
            Utils.printMessage("Could not send notification due to error: " + e.toString());
        }
    }
    
    public static void sendMessage(final String msg) {
        sendMessage("Backdoored", msg);
    }
    
    public static void sendMessage(final String title, final String msg) {
        if (TrayUtils.trayIcon == null) {
            new TrayUtils();
        }
        TrayUtils.trayIcon.displayMessage(title, msg, TrayIcon.MessageType.INFO);
    }
}
