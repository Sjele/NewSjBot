package sjbot.permission;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 11.05.13
 * Time: 12:15
 * To change this template use File | Settings | File Templates.
 */


public class PermissionManager {

    private HashMap<String, LinkedList<String>> permlist = new HashMap<String, LinkedList<String>>();

    public void addPerm(String user, String perm) {
        if (!permlist.containsKey(user)) {
            permlist.put(user, new LinkedList<String>());
        }

        if (permlist.containsKey(user)) {
           if (!permlist.get(user).contains(perm)) {
                permlist.get(user).add(perm);
           }
        }

    }

    private boolean checkPerm(String nick, String perm) {
        if (!permlist.containsKey(nick)) {
            return false;
        }
        return permlist.get(nick).contains(perm);
    }
}

