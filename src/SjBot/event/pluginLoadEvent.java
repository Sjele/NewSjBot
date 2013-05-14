package sjbot.event;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 13.05.13
 * Time: 15:41
 * To change this template use File | Settings | File Templates.
 */
public class pluginLoadEvent {

    private final String method;

    public pluginLoadEvent(String methodName) {
        this.method = methodName;
    }

    public String getMethodName() {
        return method;
    }
}
