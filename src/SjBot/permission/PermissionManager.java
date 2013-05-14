package sjbot.permission;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 11.05.13
 * Time: 12:15
 * To change this template use File | Settings | File Templates.
 */

import org.yaml.snakeyaml.Yaml;
public class PermissionManager {

    private static Map<String, List<String>> permlist = new HashMap<String, List<String>>();
    private static Yaml yaml = new Yaml();
    public static void addPerm(String user, String perm) {
        user = user.toLowerCase();
        if(!new File("config/permission").exists()) {
            try{
                yaml.dump(permlist, new FileWriter(new File("config/permission")));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        try{
            permlist = (Map) yaml.load(new FileInputStream(new File("config/permission")));
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!permlist.containsKey(user)) {
            permlist.put(user, new LinkedList<String>());
        }

        if (permlist.containsKey(user)) {
           if (!permlist.get(user).contains(perm)) {
               permlist.get(user).add(perm);
               try{
                   yaml.dump(permlist, new FileWriter(new File("config/permission")));
               }catch(IOException e){
                   e.printStackTrace();
               }
           }
        }

    }

    public static void removePerm(String user, String perm) {
        user = user.toLowerCase();
        if(!new File("config/permission").exists()) {
            try{
                yaml.dump(permlist, new FileWriter(new File("config/permission")));
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        try{
            permlist = (Map) yaml.load(new FileInputStream(new File("config/permission")));
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        if (!permlist.containsKey(user)) {
            permlist.put(user, new LinkedList<String>());
        }
        if (permlist.containsKey(user)) {
            if (permlist.get(user).contains(perm)) {
                permlist.get(user).remove(perm);
                try{
                    yaml.dump(permlist, new FileWriter(new File("config/permission")));
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }



    }

    public static boolean checkPerm(String nick, String perm) {
        nick = nick.toLowerCase();
        try{
            permlist = (Map) yaml.load(new FileInputStream(new File("config/permission")));
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        if (!permlist.containsKey(nick)) {
            if (checkPerm("default", perm)) {
                return true;
            }else{
                return false;
            }
        }
        if (permlist.containsKey(nick)) {
             if (permlist.get(nick).contains("o+")) {
                 System.out.println("Gave running accesess for command to owner!");
                 return true;
             }
        }

        return permlist.get(nick).contains(perm);
    }

    public static void addDefault() {
        addDefault("default");
    }

    public static void addDefault(String nick) {
        nick = nick.toLowerCase();
        addPerm(nick, "reverse");
        addPerm(nick, "help");
    }
}

