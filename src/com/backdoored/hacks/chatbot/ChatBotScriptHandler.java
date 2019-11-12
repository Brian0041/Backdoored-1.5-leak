package com.backdoored.hacks.chatbot;

import org.apache.logging.log4j.LogManager;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.script.ScriptException;
import com.backdoored.utils.Utils;
import org.apache.logging.log4j.Logger;

public class ChatBotScriptHandler
{
    static final String FILENAME = "Backdoored/chatbot.js";
    ScriptHandler scriptHandler;
    static final Logger logger;
    
    ChatBotScriptHandler() throws Exception {
        super();
        this.scriptHandler = new ScriptHandler().eval(getScriptContents("Backdoored/chatbot.js")).addLogger(ChatBotScriptHandler.logger);
        Utils.printMessage("Successfully read and evaluated chatbot script", "green");
    }
    
    String onChatRecieved(final String msg, final String type) throws ScriptException, NoSuchMethodException, IllegalStateException {
        return (String)this.scriptHandler.invokeFunction("onChatRecieved", msg, type);
    }
    
    public static String getScriptContents(final String fileName) throws IOException {
        try {
            final StringBuilder sb = new StringBuilder();
            final BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            ChatBotScriptHandler.logger.info("Successfully read chatbot script");
            return sb.toString();
        }
        catch (FileNotFoundException e) {
            final File file = new File(fileName);
            try {
                file.createNewFile();
            }
            catch (Exception ex) {}
            throw new IllegalStateException("Could not find chatbot script file");
        }
    }
    
    static {
        logger = LogManager.getLogger("BackdooredChatBot");
    }
}
