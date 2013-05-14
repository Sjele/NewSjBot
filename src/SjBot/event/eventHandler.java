package sjbot.event;

import com.google.common.eventbus.Subscribe;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import sjbot.api.commandHandler;
import sjbot.core.SjBot;
import sjbot.commands.utils;
import sjbot.api.pluginRegistry;
import sjbot.misc.blacklistManager;
import sjbot.permission.PermissionManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 12.05.13
 * Time: 13:15
 * To change this template use File | Settings | File Templates.
 */
public class eventHandler {

    @Subscribe
    public void onPluginLoadEvent(pluginLoadEvent event) {
        System.out.println("Loaded plugin: "+event.getMethodName().toUpperCase());
    }

    @Subscribe
    public void onPluginLoadEvent(pluginUnloadEvent event) {
        System.out.println("Unloaded plugin: "+event.getMethodName().toUpperCase());
    }

    @Subscribe
    public void onPluginRegisterEvent(pluginRegisterEvent event) {
        System.out.println("Registred a plugin/command! name = "+event.getMethodName().toUpperCase()+" from class "+event.getClassName().toUpperCase());
    }


    @Subscribe
    public void onNoPermissionEvent(noPermissionEvent event) {
        //Handles no permission events:
        //event/noPermissionEvent
        SjBot.bot.sendNotice(event.getUser(),"Innsuficient permission to run command: "+event.getCommand()+ ". You need permission: "+event.getRequired());
        System.out.println(event.getUser().getNick()+ " tried to run command "+event.getCommand() + " and was denied due to lack of permission. Perm required: "+event.getRequired());
    }

    @Subscribe
    public  void onConfigUpdateEvent(configUpdateEvent event) {
        //Handles config update events
        //event/configUpdateEvent
        System.out.println("Config was updated! file = "+ event.getFile().toUpperCase()+" key = " + event.getKey().toUpperCase()+" new value = "+event.getValue() + " reason = " + event.getReason() + " user = " + event.getUser());
        SjBot.bot.sendNotice(SjBot.bot.getUser(event.getUser()),"Config was updated! file = "+ event.getFile().toUpperCase()+" key = " + event.getKey().toUpperCase()+" new value = "+event.getValue() + " reason = " + event.getReason() + " user = " + event.getUser() );
    }

    @Subscribe
    public void onMessage(MessageEvent event){
       if (event.getMessage().startsWith(SjBot.getPrefix())){
           System.out.println("Run run run!");
           run(event);
       }
    }



    private void run(MessageEvent event){

        String[] args = utils.getArgsAsArray(utils.getArgsNoPrefix(event.getMessage()));
            String name = args[0];

            if (pluginRegistry.getLoadedPlugins().containsKey(name)) {
                if (!PermissionManager.checkPerm(event.getUser().getNick(), pluginRegistry.getPluginInfo(name).get("perm"))){
                    System.out.println(name+ "  " + pluginRegistry.getPluginInfo(name).toString());
                    SjBot.eventBus.post(new noPermissionEvent(name, event.getUser(), pluginRegistry.getPluginInfo(name).get("perm")));
                    return;
                }

                if (!blacklistManager.checkStatus(event.getUser().getNick())) return;

                Object obj = new Object();
                //Command handler sends data too the command upon launching the commands method
                commandHandler handler = new commandHandler();
                handler.setData(event.getUser(), args, name);
                Method method = pluginRegistry.getMethod(name);
                Class<?> clazz = method.getDeclaringClass();
                try {
                    obj = clazz.newInstance();
                } catch (InstantiationException e) {
                    System.out.println("Exception1 "+ name);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    System.out.println("Exception2 "+name);
                }

                try {
                    System.out.println("Trying to run: Method = \""+name+"\" Class = "+ obj.getClass().getName());
                    pluginRegistry.getMethod(name).invoke(obj,handler);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
    }
}
