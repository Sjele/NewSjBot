package sjbot.commands;

import sjbot.api.Command;
import sjbot.api.commandHandler;
import sjbot.core.SjBot;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 13.05.13
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class test {

    @Command(name = "testCommand", help = "a test command for the new plugin system", permission = "testCommand")
    public void testCommand(commandHandler handler) {
        System.out.println("Hi from testCommand");
        SjBot.bot.sendNotice(handler.getUser(),"This did work!");
    }

}
