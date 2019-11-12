package com.backdoored.gui;

import java.util.Arrays;
import java.util.List;

public class CategoriesInit
{
    public static final Category MOVEMENT;
    public static final Category RENDER;
    public static final Category PLAYER;
    public static final Category COMBAT;
    public static final Category MISC;
    public static final Category EXPLOIT;
    public static final Category CLIENT;
    public static final Category UI;
    public static final Category CHATBOT;
    
    public CategoriesInit() {
        super();
    }
    
    public static List<Category> getAll() {
        return Arrays.asList(CategoriesInit.MOVEMENT, CategoriesInit.RENDER, CategoriesInit.PLAYER, CategoriesInit.COMBAT, CategoriesInit.MISC, CategoriesInit.EXPLOIT, CategoriesInit.CLIENT, CategoriesInit.UI, CategoriesInit.CHATBOT);
    }
    
    static {
        MOVEMENT = new Category(25, 25, "Movement");
        RENDER = new Category(25, 46, "Render");
        PLAYER = new Category(25, 67, "Player");
        COMBAT = new Category(25, 89, "Combat");
        MISC = new Category(25, 110, "Misc");
        EXPLOIT = new Category(25, 131, "Exploits");
        CLIENT = new Category(25, 152, "Client");
        UI = new Category(25, 173, "UIs");
        CHATBOT = new Category(25, 194, "ChatBot");
    }
}
