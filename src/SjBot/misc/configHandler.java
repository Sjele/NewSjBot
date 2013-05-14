package sjbot.misc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 12.05.13
 * Time: 19:08
 * To change this template use File | Settings | File Templates.
 */
public class configHandler {

    public void add(String key, String value, String reason, String user, String file) {



    }

    public void run(String file) {

        Properties prop = new Properties();
        if (!new File("config/commandConfig/"+file).exists()) {
            try {
                prop.setProperty("help", "Useage: help <command>");
                prop.setProperty("perm", "Useage: perm <flag> user permission");
                prop.setProperty("reverse", "Useage:");
                prop.store(new FileOutputStream("config/commandConfig/"+file),null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
