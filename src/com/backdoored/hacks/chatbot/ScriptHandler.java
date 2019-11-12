package com.backdoored.hacks.chatbot;

import javax.script.Invocable;
import org.apache.logging.log4j.Logger;
import javax.script.ScriptException;
import java.util.Objects;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;

public class ScriptHandler
{
    private final ScriptEngine jsEngine;
    
    public ScriptHandler() {
        super();
        Objects.requireNonNull(this.jsEngine = new ScriptEngineManager(null).getEngineByName("nashorn"));
    }
    
    public ScriptHandler eval(final String script) throws ScriptException {
        this.jsEngine.eval(script);
        return this;
    }
    
    public ScriptHandler addLogger(final Logger logger) {
        this.jsEngine.put("logger", logger);
        return this;
    }
    
    public Object invokeFunction(final String name, final Object... args) throws ScriptException, NoSuchMethodException {
        return ((Invocable)this.jsEngine).invokeFunction(name, args);
    }
}
