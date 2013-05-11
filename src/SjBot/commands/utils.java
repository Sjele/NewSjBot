package sjbot.commands;

import sjbot.core.SjBot;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 11.05.13
 * Time: 15:20
 * To change this template use File | Settings | File Templates.
 */
public class utils {

    public static String getArgsNoPrefix(String args) {
        String argsNoPrefix = args.substring(SjBot.getPrefix().length());
        return argsNoPrefix;
    }

    public static String[] getArgsAsArray(String args) {
        return args.split(" ");
    }


}
