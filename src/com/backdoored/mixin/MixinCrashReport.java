package com.backdoored.mixin;

import org.spongepowered.asm.mixin.injection.Inject;
import javax.swing.JOptionPane;
import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import com.google.common.base.Throwables;
import com.backdoored.api.HasteBinApi;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.JPanel;
import java.awt.Frame;
import com.backdoored.Globals;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import java.io.File;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import com.backdoored.DrmManager;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ CrashReport.class })
public class MixinCrashReport
{
    @Final
    @Shadow
    private Throwable field_71511_b;
    
    public MixinCrashReport() {
        super();
    }
    
    @Redirect(method = { "getCompleteReport" }, at = @At(value = "INVOKE", target = "Ljava/lang/StringBuilder;toString()Ljava/lang/String;"))
    public String interceptReport(final StringBuilder stringBuilder) {
        try {
            return DrmManager.handleCrash(stringBuilder);
        }
        catch (Throwable t) {
            return stringBuilder.toString();
        }
    }
    
    @Inject(method = { "saveToFile" }, at = { @At("RETURN") })
    private void showDialog(final File toFile, final CallbackInfoReturnable<Boolean> cir) {
        if (Globals.mc.func_71372_G()) {
            Globals.mc.func_71352_k();
        }
        final Frame frame = new Frame();
        frame.setAlwaysOnTop(true);
        frame.setState(1);
        final JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        panel.setLayout(new BorderLayout(0, 0));
        final String fullReport = ((CrashReport)this).func_71502_e();
        String hasteBinUrl;
        try {
            hasteBinUrl = HasteBinApi.uploadImpl("https://paste.dimdev.org", "mccrash", fullReport);
        }
        catch (Exception e) {
            hasteBinUrl = e.getMessage();
        }
        final JTextArea comp = new JTextArea("Uploaded crash report: " + hasteBinUrl + "\n" + Throwables.getStackTraceAsString(this.field_71511_b));
        comp.setEditable(false);
        final JScrollPane scroll = new JScrollPane(comp, 22, 32);
        panel.add(scroll);
        StackTraceElement trace;
        if (this.field_71511_b.getStackTrace().length > 0) {
            trace = this.field_71511_b.getStackTrace()[0];
        }
        else {
            trace = new StackTraceElement("", "", "", -1);
        }
        JOptionPane.showMessageDialog(frame, panel, "ERROR encountered at " + trace.toString(), 0);
        frame.requestFocus();
    }
}
