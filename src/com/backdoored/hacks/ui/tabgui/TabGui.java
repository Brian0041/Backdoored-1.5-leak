package com.backdoored.hacks.ui.tabgui;

import java.util.Iterator;
import net.minecraft.client.gui.FontRenderer;
import com.backdoored.utils.RenderUtils;
import com.backdoored.extensions.Wrapper;
import org.lwjgl.opengl.GL11;
import java.util.ArrayList;
import java.awt.Color;
import java.util.List;

public class TabGui<T>
{
    private List<Tab<T>> tabs;
    static final int OFFSET = 3;
    static Color BACKGROUND;
    static Color BORDER;
    static Color SELECTED;
    static Color ACTIVATED;
    static Color FOREGROUND;
    private int selectedTab;
    private int selectedSubTab;
    
    public TabGui() {
        super();
        this.tabs = new ArrayList<Tab<T>>();
        this.selectedTab = 0;
        this.selectedSubTab = -1;
    }
    
    public void addTab(final Tab<T> tab) {
        this.tabs.add(tab);
    }
    
    public void render(final int x, final int y) {
        GL11.glTranslated((double)x, (double)y, 0.0);
        final FontRenderer font = Wrapper.fontRenderer;
        final int height = (font.field_78288_b + 3) * this.tabs.size();
        int width = 0;
        for (final Tab<T> tab : this.tabs) {
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
        for (final Tab<T> tab2 : this.tabs) {
            if (this.selectedTab == i) {
                RenderUtils.drawRect(7, -1, offset - 2, width, offset + font.field_78288_b + 3 - 1, TabGui.SELECTED.getRGB());
                if (this.selectedSubTab != -1) {
                    tab2.renderSubTabs(width, offset - 2, this.selectedSubTab);
                }
            }
            font.func_78276_b(tab2.getText(), 2, offset, TabGui.FOREGROUND.getRGB());
            offset += font.field_78288_b + 3;
            ++i;
        }
        GL11.glTranslated((double)(-x), (double)(-y), 0.0);
    }
    
    public void handleKey(final int keycode) {
        if (keycode == 208) {
            if (this.selectedSubTab == -1) {
                ++this.selectedTab;
                if (this.selectedTab >= this.tabs.size()) {
                    this.selectedTab = 0;
                }
            }
            else {
                ++this.selectedSubTab;
                if (this.selectedSubTab >= this.tabs.get(this.selectedTab).getSubTabs().size()) {
                    this.selectedSubTab = 0;
                }
            }
        }
        else if (keycode == 200) {
            if (this.selectedSubTab == -1) {
                --this.selectedTab;
                if (this.selectedTab < 0) {
                    this.selectedTab = this.tabs.size() - 1;
                }
            }
            else {
                --this.selectedSubTab;
                if (this.selectedSubTab < 0) {
                    this.selectedSubTab = this.tabs.get(this.selectedTab).getSubTabs().size() - 1;
                }
            }
        }
        else if (keycode == 203) {
            this.selectedSubTab = -1;
        }
        else if (this.selectedSubTab == -1 && (keycode == 28 || keycode == 205)) {
            this.selectedSubTab = 0;
        }
        else if (keycode == 28 || keycode == 205) {
            this.tabs.get(this.selectedTab).getSubTabs().get(this.selectedSubTab).press();
        }
    }
    
    static {
        TabGui.BACKGROUND = new Color(0, 0, 0, 175);
        TabGui.BORDER = new Color(0, 0, 0, 255);
        TabGui.SELECTED = new Color(38, 164, 78, 200);
        TabGui.ACTIVATED = new Color(164, 56, 55, 200);
        TabGui.FOREGROUND = Color.white;
    }
}
