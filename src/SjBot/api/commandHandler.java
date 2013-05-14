package sjbot.api;

import org.pircbotx.User;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 13.05.13
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public class commandHandler {

    protected User user;
    protected String[] args;
    private String command;

    public void setData(User user, String args[], String command) {
        this.user = user;
        this.args = args;
        this.command = command;
    }

    public User getUser() {
        return user;
    }

    public String[] getArgs()  {
        return args;
    }

    public String getCommand() {
        return command;
    }

}
