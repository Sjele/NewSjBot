package sjbot.core; /**
 * Created with IntelliJ IDEA.
 * User: Sjele
 * Date: 10.05.13
 * Time: 20:02
 */

import org.pircbotx.PircBotX;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.Listener;

import com.google.common.eventbus.EventBus;

import sjbot.api.pluginRegistry;
import sjbot.commands.coreCommands;
import sjbot.commands.otherCommands;
import sjbot.event.eventHandler;
import sjbot.permission.PermissionManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SjBot extends PircBotX{

    private String botname;
    private String service;
    private String channel;
    private static String owner;
    private static String prefix;

    public static PircBotX bot;

    public String[] channels;

    public static EventBus eventBus;

    public SjBot() {

        eventBus = new EventBus();
        eventBus.register(this);
        if (!new File("config").exists()) {
            new File("config").mkdir();
            System.out.println("Config directory made!");
        }else{
            System.out.println("Found config directory");
        }
        Properties prop = new Properties();

        if (! new File("config/mainConfig.properties").exists()) {

            try {
                prop.setProperty("botname", "sjbot");
                prop.setProperty("service", "irc.esper.net");
                prop.setProperty("channel", "#ccbots");
                prop.setProperty("owner", "Sjele");
                prop.setProperty("prefix", "<>");

                prop.store(new FileOutputStream("config/mainConfig.properties"), null);

                System.out.println("The bot recommends you to go edit mainConfig!");
            }catch(IOException e) {
                e.printStackTrace();
            }
        }

        try {
            prop.load(new FileInputStream("config/mainConfig.properties"));

            botname = prop.getProperty("botname");
            service = prop.getProperty("service");
            channel = prop.getProperty("channel");
            owner = prop.getProperty("owner");
            prefix = prop.getProperty("prefix");


            System.out.println("Loaded config!");

        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("Started sjbot!");

        bot = new PircBotX();

        bot.getListenerManager().addListener(new Listener() {
            @Override
            public void onEvent(Event event) throws Exception {
                eventBus.post(event);
            }
        });
        try {
            bot.setName(botname);
            bot.connect(service);
            bot.joinChannel(channel);


        }catch(Exception e) {
            e.printStackTrace();
        }
        eventBus.register(new coreCommands());
        eventBus.register(new otherCommands());
        eventBus.register(new eventHandler());

        PermissionManager.addPerm(owner, "o+");
        PermissionManager.addDefault();

        pluginRegistry.registerPluginClass(sjbot.commands.test.class);
        pluginRegistry.registerPluginClass(sjbot.commands.coreCommands.class);
    }

    public static String getOwner(){
        return owner;
    }

    public static String getPrefix() {
        return prefix;
    }
}
