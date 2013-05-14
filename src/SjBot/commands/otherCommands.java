package sjbot.commands;

import com.google.api.GoogleAPI;
import com.google.api.GoogleAPIException;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
import com.google.common.eventbus.Subscribe;
import org.pircbotx.hooks.events.MessageEvent;
import sjbot.core.SjBot;
import sjbot.commands.utils;
import sjbot.permission.PermissionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 11.05.13
 * Time: 22:17
 * To change this template use File | Settings | File Templates.
 */
public class otherCommands {



    public void translate(MessageEvent event) {
        if (event.getMessage().startsWith(SjBot.getPrefix())) {
            if (utils.getArgsAsArray(utils.getArgsNoPrefix(event.getMessage()))[0].equalsIgnoreCase("translate")) {
                String[] args = utils.getArgsAsArray(utils.getArgsNoPrefix(event.getMessage()));
                if (PermissionManager.checkPerm(event.getUser().getNick(),"translate")){
                    GoogleAPI.setHttpReferrer("https://code.google.com/p/google-api-translate-java/");
                    GoogleAPI.setKey("AIzaSyBVv9tzwzZeLkA04I6eS2gmWtOAp18TcEE");

                    try {
                        SjBot.bot.sendNotice(event.getUser(), Translate.DEFAULT.execute(args[1],Language.ENGLISH, Language.NORWEGIAN));
                    } catch (GoogleAPIException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    @Subscribe
    public void getLatestPost(MessageEvent event) {


        /*
        * Pattern p = Pattern.compile(
                    ".*?((?:http|https)(?::\\/{2}[\\w]+)(?:[\\/|\\.]?)(?:[^\\s\"]*))",
                    Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(txt);
        if (m.find()) {
            String url = m.group(1);
        }
        *
        *
        * */
        if (!event.getMessage().equalsIgnoreCase("sjeletest")) return;
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;

        try {
            url = new URL("http://www.computercraft.info/forums2/index.php?app=core&module=search&do=viewNewContent&search_app=forums");
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));
            String name = "No match";
            Pattern p = Pattern.compile(
                    "<h4><a href='http://www.computercraft.info/forums2/index.php?/topic//\\d+(//s+)",
                    Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            while ((line = br.readLine()) != null) {

                    Matcher m = p.matcher(line);
                    if (m.find()) {
                        System.out.println("Got match, saved as name");
                        name = m.group(1);
                        System.out.println(name);
                    }
            }
            System.out.println(name);
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }

    }

   // @command(name = "cmdTest", help = "This command is to test the new implemention of interfaces", permission = "")
    public void cmdTest() {
        System.out.println("This test worked");
    }

}
