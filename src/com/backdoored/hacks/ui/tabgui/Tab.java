package com.backdoored.hacks.ui.tabgui;

import com.backdoored.hacks.BaseHack;
import com.backdoored.Globals;
import java.util.Iterator;
import net.minecraft.client.gui.FontRenderer;
import com.backdoored.utils.RenderUtils;
import com.backdoored.extensions.Wrapper;
import org.lwjgl.opengl.GL11;
import java.util.ArrayList;
import java.util.List;

public class Tab<T>
{
    private List<SubTab<T>> subTabs;
    private String text;
    
    public Tab(final String text) {
        super();
        this.subTabs = new ArrayList<SubTab<T>>();
        this.text = text;
    }
    
    public void addSubTab(final SubTab<T> subTab) {
        this.subTabs.add(subTab);
    }
    
    public List<SubTab<T>> getSubTabs() {
        return this.subTabs;
    }
    
    public void renderSubTabs(final int x, final int y, final int selectedSubTab) {
        GL11.glTranslated((double)x, (double)y, 0.0);
        final FontRenderer font = Wrapper.fontRenderer;
        final int height = (font.field_78288_b + 3) * this.subTabs.size();
        int width = 0;
        for (final SubTab<T> tab : this.subTabs) {
            if (font.func_78256_a(tab.getText()) > width) {
                width = font.func_78256_a(tab.getText());
            }
        }
        width += 4;
        RenderUtils.drawRect(7, 0, 0, width, height, TabGui.BACKGROUND.getRGB());
        GL11.glLineWidth(1.0f);
        RenderUtils.drawRect(2, 0, 0, width, height, TabGui.BORDER.getRGB());
        int offset = 2;
        int i = 0;
        for (final SubTab<T> tab2 : this.subTabs) {
            if (isHackEnabled(tab2.getText())) {
                RenderUtils.drawRect(7, 0, offset - 2, width, offset + font.field_78288_b + 3 - 1, TabGui.ACTIVATED.getRGB());
            }
            else if (selectedSubTab == i) {
                RenderUtils.drawRect(7, 0, offset - 2, width, offset + font.field_78288_b + 3 - 1, TabGui.SELECTED.getRGB());
            }
            font.func_78276_b(tab2.getText(), 2, offset, TabGui.FOREGROUND.getRGB());
            offset += font.field_78288_b + 3;
            ++i;
        }
        GL11.glTranslated((double)(-x), (double)(-y), 0.0);
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
    
    private static boolean isHackEnabled(final String name) {
        for (final BaseHack hack : Globals.hacks) {
            if (hack.name.equals(name)) {
                return hack.getEnabled();
            }
        }
        return false;
    }
}
