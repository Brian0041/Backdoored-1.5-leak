package com.backdoored.utils;

import com.backdoored.natives.EncryptedStringPool;
import com.backdoored.Globals;
import net.minecraft.entity.player.EntityPlayer;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FriendUtils
{
    private static final String FILENAME;
    private static ArrayList<String> friends;
    
    public FriendUtils() {
        super();
    }
    
    public static boolean save() {
        if (FriendUtils.friends == null) {
            FriendUtils.friends = new ArrayList<String>() {
                FriendUtils$1() {
                    super();
                }
            };
        }
        try {
            final PrintWriter writer = new PrintWriter(FriendUtils.FILENAME);
            writer.print("");
            writer.close();
            final BufferedWriter outputWriter = new BufferedWriter(new FileWriter(FriendUtils.FILENAME));
            for (final String friend : FriendUtils.friends) {
                outputWriter.write(friend);
                outputWriter.newLine();
            }
            outputWriter.flush();
            outputWriter.close();
            return true;
        }
        catch (Exception e) {
            System.out.println("Could not write friends.txt: " + e.toString());
            e.printStackTrace();
            System.out.println(FriendUtils.friends);
            return false;
        }
    }
    
    public static boolean read() {
        try {
            try {
                FriendUtils.friends = new ArrayList<String>();
                final BufferedReader bufferedReader = new BufferedReader(new FileReader(FriendUtils.FILENAME));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    FriendUtils.friends.add(line);
                }
                bufferedReader.close();
                System.out.println("Successfully read friends: " + FriendUtils.friends.toString());
                return true;
            }
            catch (FileNotFoundException e2) {
                final File file = new File(FriendUtils.FILENAME);
                file.createNewFile();
                FriendUtils.friends = new ArrayList<String>() {
                    FriendUtils$2() {
                        super();
                    }
                };
                return true;
            }
        }
        catch (Exception e) {
            System.out.println("Could not read friends: " + e.toString());
            e.printStackTrace();
            return false;
        }
    }
    
    public static ArrayList<String> getFriends() {
        return FriendUtils.friends;
    }
    
    public static void addFriend(final String friend) {
        if (FriendUtils.friends == null) {
            return;
        }
        FriendUtils.friends.add(friend);
    }
    
    public static void removeFriend(final String friend) {
        if (FriendUtils.friends == null) {
            return;
        }
        FriendUtils.friends.remove(friend);
    }
    
    public static boolean isFriend(final String player) {
        return FriendUtils.friends != null && FriendUtils.friends.contains(player);
    }
    
    public static boolean isFriend(final EntityPlayer player) {
        return FriendUtils.friends != null && (Globals.mc.field_71439_g.func_110124_au().equals(player.func_110124_au()) || isFriend(player.func_70005_c_()));
    }
    
    static {
        FILENAME = EncryptedStringPool.poolGet(42);
    }
}
