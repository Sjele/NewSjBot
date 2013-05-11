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

public class test {
    @Subscribe
    public void testCommand(MessageEvent event) {
        if(event.getMessage().equals("TEST")) {
            event.respond("This did actually work!");
        }
    }


}

