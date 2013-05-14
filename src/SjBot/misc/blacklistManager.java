package sjbot.misc;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 13.05.13
 * Time: 21:38
 * To change this template use File | Settings | File Templates.
 */
public class blacklistManager {
    static Yaml yaml = new Yaml();

    public static void addToList(String name) {
        List<String> blacklist = getList();
        if (!blacklist.contains(name))
        blacklist.add(name);

        saveList(blacklist);
    }

    public static void removeFromList(String name) {
        List<String> blacklist  = getList();
        if (blacklist.contains(name))
            blacklist.remove(name);

        saveList(blacklist);
    }

    private static void saveList(List<String> list) {
        try {
            yaml.dump(list, new FileWriter("config/blacklist"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getList() {
        if (!new File("config/blacklist").exists()) {
            String sArray[] = new String[] { "default"};
            List<String> blacklist = Arrays.asList(sArray);
            saveList(blacklist);
            return blacklist;
        }

        try {
            return (List) yaml.load(new FileInputStream(new File("config/blacklist")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String> tmp = null;
        return tmp;

    }

    public static boolean checkStatus(String name) {
        List<String> l = getList();
        if (l.contains(name)) {
            return false;
        }else{
            return true;
        }
    }



}
