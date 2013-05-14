package sjbot.event;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 13.05.13
 * Time: 16:26
 * To change this template use File | Settings | File Templates.
 */
public class pluginRegisterEvent {

    private final String className;
    private final String method;

    public pluginRegisterEvent(String className, String methodName) {
        this.className = className;
        this.method = methodName;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return method;
    }
}
