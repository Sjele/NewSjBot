package sjbot.api;

import sjbot.core.SjBot;
import sjbot.event.pluginLoadEvent;
import sjbot.event.pluginRegisterEvent;
import sjbot.event.pluginUnloadEvent;
import sjbot.event.unknownCommandEvent;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Sjele
 * Date: 13.05.13
 * Time: 15:07
 * To change this template use File | Settings | File Templates.
 */


public class pluginRegistry {

   private static HashMap<String, HashMap<String, String>> methods = new HashMap<String, HashMap<String, String>>();
   private static HashMap<String, Method> loadedPlugins = new HashMap<String, Method>();
   private static HashMap<String, Method> unloadedPlugins = new HashMap<String, Method>();

    public static void registerPluginClass(Class<?> clazz) {
        Method[] Clazzmethods;
        for (Method method : clazz.getDeclaredMethods()) {
            boolean anno = method.isAnnotationPresent(Command.class);
            if (anno) {
                Command command = method.getAnnotation(Command.class);
                String name = command.name();
                String help = command.help();
                String perm = command.permission();
                methods.put(name, new HashMap<String, String>());
                loadedPlugins.put(name,method);
                if (methods.containsKey(name)) {
                    methods.get(name).put("help", help);
                    methods.get(name).put("perm", perm);
                }
                SjBot.eventBus.post(new pluginRegisterEvent(clazz.getName(), method.getName()));
            }
        }
    }

    public static void loadPlugin(String name) {
        if (unloadedPlugins.containsKey(name)) {
            loadedPlugins.put(name,unloadedPlugins.get(name));
            unloadedPlugins.remove(name);
            SjBot.eventBus.post(new pluginLoadEvent(loadedPlugins.get(name).getName()));
        }
    }

    public static void unLoadPlugin(String name) {
        if (loadedPlugins.containsKey(name)) {
            unloadedPlugins.put(name,loadedPlugins.get(name));
            loadedPlugins.remove(name);
            SjBot.eventBus.post(new pluginUnloadEvent(unloadedPlugins.get(name).getName()));
        }
    }

    public static HashMap<String, Method> getLoadedPlugins() {
        return loadedPlugins;
    }

    public static Method getMethod(String name) {
        if (loadedPlugins.containsKey(name)) {
            return loadedPlugins.get(name);
        }else{
            return  null;
        }
    }

    public static HashMap<String, HashMap<String, String>> getPluginsInfo() {
        return methods;
    }

    public static HashMap<String, String> getPluginInfo(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }else{
            return null;
        }
    }
}
