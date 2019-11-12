package com.backdoored.commands;

import com.backdoored.utils.Utils;
import net.minecraftforge.common.MinecraftForge;
import java.util.Arrays;
import net.minecraft.client.Minecraft;
import java.util.List;
import java.util.ArrayList;

public abstract class CommandBase implements ICommandInterface
{
    public static ArrayList<CommandBase> objects;
    protected List<String> aliases;
    public Minecraft mc;
    
    CommandBase(final String name) {
        this(new String[] { name });
    }
    
    CommandBase(final String... aliases) {
        this(Arrays.asList(aliases));
    }
    
    CommandBase(final List<String> aliases) {
        super();
        this.mc = Minecraft.func_71410_x();
        this.aliases = aliases;
        CommandBase.objects.add(this);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public abstract boolean execute(final String[] p0);
    
    @Override
    public void getHelp(final String[] args, final String paramName) {
        Utils.printMessage("Usage: " + this.getSyntax(), "yellow");
    }
    
    @Override
    public boolean checkCommandWithOption(final String[] args, final String[] options, final String paramName) {
        if (args[0].equals("")) {
            this.getHelp(options, paramName);
            return false;
        }
        for (int x = 0; x <= options.length; ++x) {
            if (options[x].equals(args[0])) {
                return true;
            }
            if (!options[x].equals(args[0]) && x == options.length - 1) {
                this.getHelp(options, paramName);
                return false;
            }
        }
        return true;
    }
    
    @Override
    public abstract String getSyntax();
    
    static {
        CommandBase.objects = new ArrayList<CommandBase>();
    }
}
