package sjc.aft.framework.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sjc.aft.framework.core.annotations.ActionTitle;
import sjc.aft.framework.core.annotations.ActionsTitle;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public abstract class FrameworkPage {

    public final Logger logger = LoggerFactory.getLogger(getClass());

    private static final Map<Class<?>, Map<String, Method>> actionsCache = new HashMap<>();

    public void executeMethodByTitle(String actionTitle, Object... params) {
        Method method = findActionMethod(actionTitle);

        if (method == null) {
            throw new IllegalArgumentException("Can't find method '" + actionTitle + "' on the page " + this.getClass().getSimpleName());
        }

        try {
            if (method.getParameterCount() == params.length) {
                method.setAccessible(true);
                method.invoke(this, (Object[]) params);
            } else {
                throw new IllegalArgumentException("Method " + method.getName() + " awaits "
                        + method.getParameterCount() + " parameters, but received " + params.length);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error calling method with action: " + actionTitle, e.getCause());
        }
    }

    private Method findActionMethod(String actionTitle) {
        Map<String, Method> cachedMethods = actionsCache.computeIfAbsent(this.getClass(), this::scanActions);
        return cachedMethods.get(actionTitle.toLowerCase());
    }

    private Map<String, Method> scanActions(Class<?> pageClass) {
        Map<String, Method> actions = new HashMap<>();
        Class<?> currentClass = pageClass;

        while (currentClass != null && FrameworkPage.class.isAssignableFrom(currentClass)) {
            for (Method method : currentClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(ActionTitle.class)) {
                    ActionTitle annotation = method.getAnnotation(ActionTitle.class);
                    String key = annotation.value().toLowerCase();

                    if (actions.containsKey(key)) {
                        throw new IllegalStateException("Duplicate @ActionTitle: '" + key + "' found in " + currentClass.getSimpleName());
                    }
                    method.setAccessible(true);
                    actions.put(key, method);
                }
                if (method.isAnnotationPresent(ActionsTitle.class)) {
                    for (ActionTitle annotation : method.getAnnotation(ActionsTitle.class).value()) {
                        String key = annotation.value().toLowerCase();

                        if (actions.containsKey(key)) {
                            throw new IllegalStateException("Duplicate @ActionTitle: '" + key + "' found in " + currentClass.getSimpleName());
                        }
                        method.setAccessible(true);
                        actions.put(key, method);
                    }
                }
            }
            currentClass = currentClass.getSuperclass();
        }
        return actions;
    }

    public abstract void assertIsOpen();

}