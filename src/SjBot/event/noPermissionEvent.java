package sjbot.event;

import org.pircbotx.User;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 12.05.13
 * Time: 12:58
 * To change this template use File | Settings | File Templates.
 */
public class noPermissionEvent {

    private final String command;
    private final User user;
    private final String reqPerm;

    public noPermissionEvent(String command, User user, String reqPerm) {
        this.command = command;
        this.user = user;
        this.reqPerm = reqPerm;
    }

    public String getCommand() {
        return command;
    }

    public User getUser()  {
        return user;
    }

    public String getRequired() {
        return reqPerm;
    }

}
