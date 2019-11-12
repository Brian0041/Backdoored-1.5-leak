package com.backdoored.hacks.player;

import net.minecraft.client.Minecraft;
import java.util.Random;
import net.minecraft.util.ChatAllowedCharacters;
import com.backdoored.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.backdoored.gui.CategoriesInit;
import javax.swing.Timer;
import com.backdoored.setting.Setting;
import java.io.File;
import com.backdoored.hacks.BaseHack;

public class Spammer extends BaseHack
{
    private static final char[] chars;
    private static final File FILE;
    private String lastKnownMode;
    private Setting mode;
    private Setting delay;
    private Timer fileTimer;
    private Timer spamTimer;
    private Timer emptyTimer;
    
    public Spammer() {
        super("Spammer", CategoriesInit.PLAYER, "Spam the chat");
        this.mode = new Setting("Mode", this, "File", new String[] { "File", "Spam" });
        this.delay = new Setting("Delay", this, 2, 1, 60);
        try {
            Spammer.FILE.createNewFile();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void onEnabled() {
        if (this.delay == null) {
            this.delay = new Setting("Delay", this, 2, 1, 60);
        }
        try {
            final String valString = this.mode.getValString();
            switch (valString) {
                case "File": {
                    final String[] lines = new String(Files.readAllBytes(Paths.get(Spammer.FILE.getCanonicalPath(), new String[0]))).split("\n");
                    final int numLines = lines.length;
                    if (numLines == 0 || lines[0].isEmpty()) {}
                    (this.fileTimer = new Timer(this.delay.getValInt() * 1000, new ActionListener() {
                        int index = 0;
                        final /* synthetic */ int val$numLines;
                        final /* synthetic */ String[] val$lines;
                        final /* synthetic */ Spammer this$0;
                        
                        Spammer$1() {
                            this.this$0 = this$0;
                            super();
                        }
                        
                        @Override
                        public void actionPerformed(final ActionEvent e) {
                            if (this.index < numLines) {
                                Spammer.mc.field_71439_g.func_71165_d(lines[this.index]);
                                ++this.index;
                                if (this.index == numLines) {
                                    this.index = 0;
                                }
                            }
                        }
                    })).start();
                    break;
                }
                case "Spam": {
                    (this.spamTimer = new Timer(this.delay.getValInt() * 1000, e -> Spammer.mc.field_71439_g.func_71165_d(this.getRandomCharacters()))).start();
                    break;
                }
                default: {
                    this.emptyTimer = new Timer(this.delay.getValInt() * 1000, e -> Spammer.mc.field_71439_g.func_71165_d(StringUtils.repeat(Spammer.chars[195], 256)));
                    break;
                }
            }
        }
        catch (Exception e) {
            Utils.printMessage("Disabled spammer due to error: " + e.toString(), "red");
            e.printStackTrace();
            this.setEnabled(false);
        }
    }
    
    public void onUpdate() {
        if (this.getEnabled()) {
            if (this.lastKnownMode == null) {
                this.lastKnownMode = this.mode.getValString();
                return;
            }
            if (!this.lastKnownMode.equals(this.mode.getValString())) {
                this.onDisabled();
                this.onEnabled();
            }
            this.lastKnownMode = this.mode.getValString();
        }
    }
    
    public void onDisabled() {
        for (final Timer timer : new Timer[] { this.fileTimer, this.spamTimer, this.emptyTimer }) {
            try {
                timer.stop();
            }
            catch (Exception ex) {}
        }
    }
    
    private String getRandomCharacters() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 256; ++i) {
            char s;
            for (s = ' '; s == ' ' || !ChatAllowedCharacters.func_71566_a(s); s = Spammer.chars[new Random().nextInt(Spammer.chars.length)]) {}
            builder.append(s);
        }
        return builder.toString();
    }
    
    private static /* synthetic */ void lambda$onEnabled$1(final ActionEvent e) {
        Spammer.mc.field_71439_g.func_71165_d(StringUtils.repeat(Spammer.chars[195], 256));
    }
    
    private /* synthetic */ void lambda$onEnabled$0(final ActionEvent e) {
        Spammer.mc.field_71439_g.func_71165_d(this.getRandomCharacters());
    }
    
    static /* synthetic */ Minecraft access$000() {
        return Spammer.mc;
    }
    
    static {
        chars = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~âŒ‚Ã‡Ã¼Ã©Ã¢Ã¤Ã Ã¥Ã§ÃªÃ«Ã¨Ã¯Ã®Ã¬Ã„Ã…Ã‰Ã¦Ã†Ã´Ã¶Ã²Ã»Ã¹Ã¿Ã–ÃœÃ¸Â£Ã˜Ã—Æ’Ã¡Ã\u00adÃ³ÃºÃ±Ã‘ÂªÂº".toCharArray();
        FILE = new File("Backdoored/spammer.txt");
    }
}
