package sjbot.event;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 12.05.13
 * Time: 19:09
 * To change this template use File | Settings | File Templates.
 */
public class configUpdateEvent {

    private final String file;  //File that was changed
    private final String key;   //What key was changed
    private final String value; //New value
    private final String reason;//Why was this changed
    private final String user;  //Witch user performed this

    public configUpdateEvent(String file, String key, String value, String reason, String user) {
        this.file = file;
        this.key = key;
        this.value = value;
        this.reason = reason;
        this.user = user;
    }

    public String getFile() {
        return file;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getReason(){
        return reason;
    }

    public String getUser() {
        return user;
    }

}
