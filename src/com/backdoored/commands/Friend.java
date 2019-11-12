package com.backdoored.commands;

import com.backdoored.utils.Utils;
import com.backdoored.utils.FriendUtils;

public class Friend extends CommandBase
{
    private String[] options;
    
    public Friend() {
        super(new String[] { "friend", "friends" });
        this.options = new String[] { "add", "del", "list" };
    }
    
    @Override
    public boolean execute(final String[] args) {
        if (!this.checkCommandWithOption(args, this.options, "name")) {
            return false;
        }
        if (args[0].equals("add") && !args[args.length - 1].equals("add")) {
            if (!FriendUtils.isFriend(args[1])) {
                FriendUtils.addFriend(args[1]);
                Utils.printMessage("Added '" + args[1] + "' to your friends!", "green");
            }
            else {
                Utils.printMessage("'" + args[1] + "' was already a friend", "red");
            }
            return true;
        }
        if (args[0].equals("del") && !args[args.length - 1].equals("del")) {
            if (FriendUtils.isFriend(args[1])) {
                FriendUtils.removeFriend(args[1]);
                Utils.printMessage("Removed '" + args[1] + "' from your friends!", "green");
            }
            else {
                Utils.printMessage("'" + args[1] + "' wasnt a friend", "red");
            }
            return true;
        }
        if (args[0].equals("list")) {
            final StringBuilder message = new StringBuilder("Friends: ");
            for (int x = 0; x <= FriendUtils.getFriends().size() - 1; ++x) {
                if (x == FriendUtils.getFriends().size() - 1) {
                    message.append(FriendUtils.getFriends().get(x));
                    break;
                }
                message.append(FriendUtils.getFriends().get(x)).append(", ");
            }
            Utils.printMessage(message.toString(), "red");
            return true;
        }
        return false;
    }
    
    @Override
    public String getSyntax() {
        return "-friend add cookiedragon234\n-friend del 2b2tnews\n-friend list";
    }
}
