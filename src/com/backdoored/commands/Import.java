package com.backdoored.commands;

import org.json.JSONObject;
import org.apache.commons.io.FileUtils;
import java.nio.charset.Charset;
import java.io.File;
import com.backdoored.utils.Utils;
import com.backdoored.utils.FriendUtils;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;

public class Import extends CommandBase
{
    public Import() {
        super(new String[] { "import", "importfriends" });
    }
    
    @Override
    public boolean execute(final String[] args) {
        if (args.length == 1) {
            try {
                if (args[0].equalsIgnoreCase("impact")) {
                    final String file = "Impact/friends.cfg";
                    final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        final String player = line.split(":")[0];
                        if (!FriendUtils.isFriend(player)) {
                            FriendUtils.addFriend(player);
                            Utils.printMessage("Added '" + player + "' to your friends!", "green");
                        }
                        else {
                            Utils.printMessage("'" + player + "' was already a friend", "red");
                        }
                    }
                    bufferedReader.close();
                    System.out.println("Successfully imported friends");
                }
                else if (args[0].equalsIgnoreCase("wwe")) {
                    final String file = "WWE/friends.txt";
                    final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        final String player = line.split(" ")[0];
                        if (!FriendUtils.isFriend(player)) {
                            FriendUtils.addFriend(player);
                            Utils.printMessage("Added '" + player + "' to your friends!", "green");
                        }
                        else {
                            Utils.printMessage("'" + player + "' was already a friend", "red");
                        }
                    }
                    bufferedReader.close();
                    System.out.println("Successfully imported friends");
                }
                else if (args[0].equalsIgnoreCase("future")) {
                    final String file = System.getProperty("user.home") + "/Future/friends.json";
                    final String json = FileUtils.readFileToString(new File(file), Charset.defaultCharset());
                    final JSONObject rootObj = new JSONObject(json);
                    final JSONObject friendsJSON = rootObj.getJSONObject("friend-label");
                    final Object[] array;
                    final Object[] friends = array = friendsJSON.keySet().toArray();
                    for (final Object playerJSON : array) {
                        final String player2 = playerJSON.toString();
                        if (!FriendUtils.isFriend(player2)) {
                            FriendUtils.addFriend(player2);
                            Utils.printMessage("Added '" + player2 + "' to your friends!", "green");
                        }
                        else {
                            Utils.printMessage("'" + player2 + "' was already a friend", "red");
                        }
                    }
                    System.out.println("Successfully imported friends");
                }
            }
            catch (Exception e) {
                System.out.println("Could not import to friends.txt: " + e.toString());
                e.printStackTrace();
                System.out.println(FriendUtils.getFriends());
            }
            return true;
        }
        return false;
    }
    
    @Override
    public String getSyntax() {
        return "-import <Impact/WWE only ones supported now>";
    }
}
