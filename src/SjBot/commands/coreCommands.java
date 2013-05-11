package sjbot.commands;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 11.05.13
 * Time: 11:06
 * To change this template use File | Settings | File Templates.
 */

import com.google.common.eventbus.Subscribe;
import org.pircbotx.hooks.events.MessageEvent;
import sjbot.core.SjBot;

public class coreCommands {
    @Subscribe
    public void testCommand(MessageEvent event) {
        if(event.getMessage().equals("TEST")) {
            event.respond("This did actually work!");
        }
    }

    @Subscribe
    public void reverse(MessageEvent event) {
        if (event.getMessage().startsWith(SjBot.getPrefix())) {
            String args = utils.getArgsNoPrefix(event.getMessage());
            String[] argsArray = utils.getArgsAsArray(args);

            if (argsArray[0].equalsIgnoreCase("reverse")&&(argsArray.length >= 1)) {
                String text ="";
                for (int i = 1; i < argsArray.length; i++  ) {
                    text = text+" "+ argsArray[i];
                }

                String rev = "";
                for (int i = text.length()-1; i > 0; i--) {
                   char c = text.charAt(i);
                   rev = rev + c;
                }

                SjBot.bot.sendNotice(event.getUser(), rev);
                System.out.println("Ran command Reverse!");
            }
        }
    }


}

