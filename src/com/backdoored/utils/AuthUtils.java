package com.backdoored.utils;

import com.mojang.authlib.exceptions.AuthenticationException;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import com.backdoored.Globals;
import net.minecraft.client.Minecraft;
import com.backdoored.natives.EncryptedStringPool;

public class AuthUtils
{
    public AuthUtils() {
        super();
    }
    
    public static boolean mcLogin(final String email, final String password) throws AuthenticationException {
        System.out.println(EncryptedStringPool.poolGet(13) + email);
        final YggdrasilAuthenticator auth = new YggdrasilAuthenticator(email, password);
        if (auth.login()) {
            try {
                ObfuscationReflectionHelper.setPrivateValue((Class)Minecraft.class, (Object)Globals.mc, (Object)auth.getSession(), new String[] { EncryptedStringPool.poolGet(14), EncryptedStringPool.poolGet(15) });
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            System.out.println(EncryptedStringPool.poolGet(16));
            System.out.println(EncryptedStringPool.poolGet(17) + Globals.mc.func_110432_I().func_111286_b());
            System.out.println(EncryptedStringPool.poolGet(18) + getUsername());
            return true;
        }
        return false;
    }
    
    public static String getUsername() {
        return Globals.mc.func_110432_I().func_111285_a();
    }
    
    public static String getUUID() {
        return Globals.mc.func_110432_I().func_148256_e().getId().toString();
    }
}
