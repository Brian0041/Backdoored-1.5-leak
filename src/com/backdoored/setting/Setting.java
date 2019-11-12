package com.backdoored.setting;

import com.backdoored.gui.Button;
import com.backdoored.hacks.BaseHack;

public class Setting
{
    private String name;
    private String mode;
    public Class type;
    private String[] options;
    private double min;
    private double max;
    public BaseHack hack;
    private String uniqueId;
    private Object value;
    public boolean held;
    public Button button;
    
    public Setting(final String name, final BaseHack hack) {
        super();
        this.name = name;
        this.type = String.class;
        this.hack = hack;
        this.mode = "bind";
        this.uniqueId = hack.name + "-bind";
        this.held = false;
        this.value = "NONE";
        this.button = new Button(0, 0, hack.category.width, hack.category.height / 3 * 2, "Bind: " + this.value, false, false, new float[] { 1.0f, 0.4f, 0.0f, 1.0f });
        SettingsManager.settings.add(this);
    }
    
    public Setting(final String name, final BaseHack hack, final String defaultVal, final String[] options) {
        super();
        this.name = name;
        this.type = String.class;
        this.hack = hack;
        this.options = options;
        this.mode = "mode";
        this.uniqueId = hack.name + "-" + name;
        this.value = defaultVal;
        this.button = new Button(0, 0, hack.category.width, hack.category.height / 3 * 2, name + ": " + this.value, false, false, new float[] { 1.0f, 0.4f, 0.0f, 1.0f });
        SettingsManager.settings.add(this);
    }
    
    public Setting(final String name, final BaseHack hack, final boolean defaultVal) {
        super();
        this.name = name;
        this.type = Boolean.class;
        this.hack = hack;
        this.mode = "toggle";
        this.uniqueId = hack.name + "-" + name;
        this.value = defaultVal;
        this.button = new Button(0, 0, hack.category.width, hack.category.height / 3 * 2, name + ": " + this.value, false, false, new float[] { 1.0f, 0.4f, 0.0f, 1.0f });
        SettingsManager.settings.add(this);
    }
    
    public Setting(final String name, final BaseHack hack, final double dval, final double min, final double max) {
        super();
        this.name = name;
        this.type = Double.class;
        this.hack = hack;
        this.value = dval;
        this.min = min;
        this.max = max;
        this.mode = "slider";
        this.uniqueId = hack.name + "-" + name;
        this.held = false;
        this.value = dval;
        this.button = new Button(0, 0, hack.category.width, hack.category.height / 3 * 2, name + ": " + this.value, false, false, new float[] { 1.0f, 0.4f, 0.0f, 1.0f });
        SettingsManager.settings.add(this);
    }
    
    public Setting(final String name, final BaseHack hack, final int ival, final int min, final int max) {
        super();
        this.name = name;
        this.type = Integer.class;
        this.hack = hack;
        this.value = ival;
        this.min = min;
        this.max = max;
        this.mode = "sliderINT";
        this.uniqueId = hack.name + "-" + name;
        this.held = false;
        this.value = ival;
        this.button = new Button(0, 0, hack.category.width, hack.category.height / 3 * 2, name + ": " + this.value, false, false, new float[] { 1.0f, 0.4f, 0.0f, 1.0f });
        SettingsManager.settings.add(this);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getUniqueId() {
        return this.uniqueId;
    }
    
    public void setVal(final Object in) {
        this.value = in;
    }
    
    public Object getVal() {
        return this.value;
    }
    
    public String getValString() {
        return String.valueOf(this.value);
    }
    
    public String[] getOptions() {
        return this.options;
    }
    
    public boolean getValBoolean() {
        return (boolean)this.value;
    }
    
    public double getValDouble() {
        if (this.value instanceof Integer) {
            return (double)this.value;
        }
        return (double)this.value;
    }
    
    public int getValInt() {
        if (this.value instanceof Integer) {
            return (int)this.value;
        }
        return (int)this.value;
    }
    
    public double getMin() {
        return this.min;
    }
    
    public double getMax() {
        return this.max;
    }
    
    public boolean isMode() {
        return this.mode.equalsIgnoreCase("mode");
    }
    
    public boolean isToggle() {
        return this.mode.equalsIgnoreCase("toggle");
    }
    
    public boolean isSliderD() {
        return this.mode.equalsIgnoreCase("slider");
    }
    
    public boolean isSliderINT() {
        return this.mode.equalsIgnoreCase("sliderINT");
    }
    
    public boolean isSlider() {
        return this.mode.equalsIgnoreCase("SliderINT") || this.mode.equalsIgnoreCase("SliderD");
    }
    
    public boolean isBind() {
        return this.mode.equalsIgnoreCase("bind");
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.type.cast(this.value));
    }
}
