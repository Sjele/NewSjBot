package sjbot.event;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 13.05.13
 * Time: 16:43
 * To change this template use File | Settings | File Templates.
 */
public class pluginUnloadEvent {

    private final String methodName;

    public pluginUnloadEvent(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }
}
