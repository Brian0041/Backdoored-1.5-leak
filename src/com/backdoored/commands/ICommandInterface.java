package com.backdoored.commands;

public interface ICommandInterface
{
    boolean execute(final String[] p0);
    
    void getHelp(final String[] p0, final String p1);
    
    boolean checkCommandWithOption(final String[] p0, final String[] p1, final String p2);
    
    String getSyntax();
}
