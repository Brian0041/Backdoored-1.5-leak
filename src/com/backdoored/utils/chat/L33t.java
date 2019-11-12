package com.backdoored.utils.chat;

import java.util.HashMap;
import java.util.Map;

public class L33t extends Mutator
{
    private Map<String, String> replaceMap;
    
    public L33t() {
        super();
        this.replaceMap = new HashMap<String, String>() {
            final /* synthetic */ L33t this$0;
            
            L33t$1() {
                this.this$0 = this$0;
                super();
            }
            
            {
                this.put("a", "4");
                this.put("b", "8");
                this.put("e", "3");
                this.put("i", "1");
                this.put("l", "1");
                this.put("o", "0");
                this.put("s", "5");
                this.put("t", "7");
                this.put("z", "5");
            }
        };
    }
    
    @Override
    public String getName() {
        return "L33t";
    }
    
    @Override
    public String mutate(final String msg) {
        final StringBuilder sb = new StringBuilder();
        for (final String ch4r : msg.split("")) {
            sb.append(this.replaceMap.getOrDefault(ch4r.toLowerCase(), ch4r));
        }
        return sb.toString();
    }
}
