package com.backdoored.api;

import com.backdoored.Globals;
import com.backdoored.subguis.MainMenuGui;
import javax.net.ssl.HttpsURLConnection;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.net.MalformedURLException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import com.google.common.collect.HashBiMap;

public class MojangWebApi
{
    public static HashBiMap<String, String> userbase;
    private static final Boolean[] statuses;
    
    public MojangWebApi() {
        super();
    }
    
    public static String grabRealName(final String UUID) {
        if (MojangWebApi.userbase.containsKey((Object)UUID)) {
            System.out.println("Grabbing username from hash map");
            return (String)MojangWebApi.userbase.get((Object)UUID);
        }
        System.out.println("Grabbing username from Mojang Web Api");
        return QueryWebApi(UUID, true);
    }
    
    public static String grabRealUUID(final String NAME) {
        if (MojangWebApi.userbase.containsValue((Object)NAME)) {
            System.out.println("Grabbing UUID from hash map");
            return (String)MojangWebApi.userbase.inverse().get((Object)NAME);
        }
        System.out.println("Grabbing UUID from Mojang Web Api");
        return QueryWebApi(NAME, false);
    }
    
    private static String QueryWebApi(final String id, final Boolean isUUID) {
        if (isUUID) {
            final String untrimmedUUID = id;
            try {
                final String mojangUrl = "https://api.mojang.com/user/profiles/" + id.replace("-", "") + "/names";
                final URL WebApi = new URL(mojangUrl);
                final BufferedReader in = new BufferedReader(new InputStreamReader(WebApi.openStream()));
                String username = "Popbob";
                final String rawInput = in.readLine();
                System.out.println(rawInput);
                in.close();
                if (rawInput != null) {
                    final JSONParser parseRaw = new JSONParser();
                    final JSONArray namesJSON = (JSONArray)parseRaw.parse(rawInput);
                    final JSONObject usernameSlot = (JSONObject)namesJSON.get(namesJSON.size() - 1);
                    username = usernameSlot.get((Object)"name").toString();
                }
                in.close();
                MojangWebApi.userbase.put((Object)untrimmedUUID, (Object)username);
                return username;
            }
            catch (MalformedURLException e) {
                System.out.println("MALIGNED URL, CARBOLEMONS IS DUMB IF YOU ARE READING THIS, BECAUSE, WHAT, IMPOSSIBLE... LITCHERALLLY...");
                return "";
            }
            catch (IOException e2) {
                System.out.println("uh, something went horribly wrong if you are seeing this in your log.");
                return "";
            }
            catch (ParseException e3) {
                System.out.println("JSON userdata was parsed wrong, shit.");
                return "";
            }
        }
        try {
            final String mojangUrl2 = "https://api.mojang.com/users/profiles/minecraft/" + id;
            final URL WebApi2 = new URL(mojangUrl2);
            final BufferedReader in2 = new BufferedReader(new InputStreamReader(WebApi2.openStream()));
            String trimmedUUID = "00000000000000000000000000000000";
            String UUID = "00000000-0000-0000-0000-000000000000";
            final String name = "Popbob";
            final String rawInput2 = in2.readLine();
            in2.close();
            if (rawInput2 != null) {
                final JSONParser parseRaw2 = new JSONParser();
                final JSONObject rawJSON = (JSONObject)parseRaw2.parse(rawInput2);
                trimmedUUID = rawJSON.get((Object)"id").toString();
                final String __untrimUUID = new String(trimmedUUID);
                final StringBuilder untrimUUID = new StringBuilder(__untrimUUID);
                untrimUUID.insert(8, '-');
                untrimUUID.insert(13, '-');
                untrimUUID.insert(18, '-');
                untrimUUID.insert(23, '-');
                UUID = untrimUUID.toString();
            }
            MojangWebApi.userbase.put((Object)UUID, (Object)id);
            return UUID;
        }
        catch (MalformedURLException e4) {
            System.out.println("MALIGNED URL, CARBOLEMONS IS DUMB IF YOU ARE READING THIS, BECAUSE, WHAT, IMPOSSIBLE... LITCHERALLLY...");
            return "";
        }
        catch (IOException e5) {
            System.out.println("uh, something went horribly wrong if you are seeing this in your log.");
            return "";
        }
        catch (ParseException e6) {
            System.out.println("JSON userdata was parsed wrong, shit.");
            return "";
        }
    }
    
    private static boolean isAuthUpImpl() {
        return isWebsiteUp("https://authserver.mojang.com/authenticate");
    }
    
    private static boolean isSeshUpImpl() {
        return isWebsiteUp("https://sessionserver.mojang.com/");
    }
    
    public static boolean isAuthUp() {
        synchronized (MojangWebApi.statuses) {
            return MojangWebApi.statuses[0];
        }
    }
    
    public static boolean isSeshUp() {
        synchronized (MojangWebApi.statuses) {
            return MojangWebApi.statuses[1];
        }
    }
    
    private static boolean isWebsiteUp(final String urlString) {
        try {
            final URL url = new URL(urlString);
            final HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.connect();
            connection.disconnect();
            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private static /* synthetic */ void lambda$static$0() {
        while (true) {
            try {
                while (true) {
                    if (Globals.mc.field_71462_r instanceof MainMenuGui) {
                        final boolean auth = isAuthUpImpl();
                        final boolean sesh = isSeshUpImpl();
                        synchronized (MojangWebApi.statuses) {
                            MojangWebApi.statuses[0] = auth;
                            MojangWebApi.statuses[1] = sesh;
                        }
                    }
                    Thread.sleep(7500L);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            break;
        }
    }
    
    static {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: putstatic       com/backdoored/api/MojangWebApi.userbase:Lcom/google/common/collect/HashBiMap;
        //     6: iconst_2       
        //     7: anewarray       Ljava/lang/Boolean;
        //    10: dup            
        //    11: iconst_0       
        //    12: iconst_1       
        //    13: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    16: aastore        
        //    17: dup            
        //    18: iconst_1       
        //    19: iconst_1       
        //    20: invokestatic    java/lang/Boolean.valueOf:(Z)Ljava/lang/Boolean;
        //    23: aastore        
        //    24: putstatic       com/backdoored/api/MojangWebApi.statuses:[Ljava/lang/Boolean;
        //    27: new             Ljava/lang/Thread;
        //    30: dup            
        //    31: invokedynamic   BootstrapMethod #0, run:()Ljava/lang/Runnable;
        //    36: ldc_w           "Status checker"
        //    39: invokespecial   java/lang/Thread.<init>:(Ljava/lang/Runnable;Ljava/lang/String;)V
        //    42: invokevirtual   java/lang/Thread.start:()V
        //    45: return         
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
