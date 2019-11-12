package com.backdoored.utils.chat;

import java.util.HashMap;
import java.util.Map;

public class Chav extends Mutator
{
    private Map<String, String> replaceMap;
    
    public Chav() {
        super();
        this.replaceMap = new HashMap<String, String>() {
            final /* synthetic */ Chav this$0;
            
            Chav$1() {
                this.this$0 = this$0;
                super();
            }
            
            {
                this.put("the", "te");
                this.put("check", "czech");
                this.put("your", "ur");
                this.put("who ", "hoo");
                this.put("me", " i");
                this.put("i", " me");
                this.put("ok", "k");
                this.put("inter", "intre");
                this.put("family", "fam");
                this.put("crystal", "cyrtal");
                this.put("i'm", "me is");
                this.put("im", "me is");
                this.put("racist", "rasist");
                this.put("to", "2");
                this.put("have", "av");
                this.put("32k", "32kay");
                this.put("32ks", "32kays");
                this.put("hause", "horse");
                this.put("house", "horse");
                this.put("hausemaster", "horsemaster");
                this.put("housemaster", "horsemaster");
                this.put("jesus", "jebus");
                this.put("spawn", "spwan");
                this.put("cookiedragon", "cookiewagon");
                this.put("cookiedragon234", "cookiewagon234");
                this.put("tigermouthbear", "tigressmouthbear");
                this.put("carbolemons", "carbonlenons");
                this.put("give", "gib");
            }
        };
    }
    
    @Override
    public String getName() {
        return "Chav";
    }
    
    @Override
    public String mutate(final String msg) {
        final StringBuilder sb = new StringBuilder();
        final String[] split;
        final String[] words = split = msg.split(" ");
        for (final String word : split) {
            sb.append(this.replaceMap.getOrDefault(word.toLowerCase(), word)).append(" ");
        }
        return sb.toString();
    }
}
