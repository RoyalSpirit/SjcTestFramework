package io.github.royalspirit.sjctestframework.core;

import io.github.royalspirit.sjctestframework.core.annotations.ActionTitle;
import io.github.royalspirit.sjctestframework.core.annotations.ActionsTitle;
import io.github.royalspirit.sjctestframework.core.annotations.ElementTitle;
import io.github.royalspirit.sjctestframework.core.annotations.PageTitle;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Validates framework annotation registries before scenarios start.
 * <p>
 * The validator fails fast when page, element, or action metadata is ambiguous.
 * This keeps Cucumber steps from resolving to an unexpected page object, locator,
 * or action method during scenario execution.
 */
public final class FrameworkRegistryValidator {

    private FrameworkRegistryValidator() {
    }

    /**
     * Checks that every non-abstract page object has a globally unique {@link PageTitle}.
     *
     * @param pageClasses classes discovered by the page scanner
     * @throws IllegalStateException if a page title is empty or duplicated
     */
    public static void validateUniquePageTitles(Set<Class<?>> pageClasses) {
        Map<String, Class<?>> registeredTitles = new LinkedHashMap<>();

        for (Class<?> pageClass : pageClasses) {
            if (Modifier.isAbstract(pageClass.getModifiers())) {
                continue;
            }

            PageTitle annotation = pageClass.getAnnotation(PageTitle.class);
            String title = annotation.title();
            String normalizedTitle = normalizeTitle(title, "@PageTitle", pageClass.getSimpleName());
            Class<?> existingPageClass = registeredTitles.putIfAbsent(normalizedTitle, pageClass);

            if (existingPageClass != null) {
                throw new IllegalStateException("Duplicate @PageTitle '" + title + "' found in "
                        + existingPageClass.getSimpleName() + " and " + pageClass.getSimpleName());
            }
        }
    }

    /**
     * Checks element title definitions for all registered page objects.
     * <p>
     * Element titles are required to be unique only inside a single page object.
     * The same readable title may be reused on different pages because element
     * resolution can be page-context dependent.
     *
     * @param pages registered framework page instances
     * @throws IllegalStateException if an element title is empty, duplicated inside one page,
     *                               or used on a field that is not a {@link By}
     */
    public static void validateUniqueElementTitles(Collection<FrameworkPage> pages) {
        for (FrameworkPage page : pages) {
            Class<?> pageClass = page.getClass();
            Map<String, ElementDefinition> registeredElements = new LinkedHashMap<>();

            for (Field field : pageClass.getDeclaredFields()) {
                if (!field.isAnnotationPresent(ElementTitle.class)) {
                    continue;
                }

                if (!field.getType().equals(By.class)) {
                    throw new IllegalStateException("@ElementTitle can only be used on By fields. Invalid field: "
                            + pageClass.getSimpleName() + "." + field.getName());
                }

                String title = field.getAnnotation(ElementTitle.class).value();
                String normalizedTitle = normalizeTitle(title, "@ElementTitle", pageClass.getSimpleName() + "." + field.getName());
                ElementDefinition existingElement = registeredElements.putIfAbsent(
                        normalizedTitle,
                        new ElementDefinition(field.getName())
                );

                if (existingElement != null) {
                    throw new IllegalStateException("Duplicate @ElementTitle '" + title + "' found in "
                            + pageClass.getSimpleName() + "." + existingElement.fieldName
                            + " and " + pageClass.getSimpleName() + "." + field.getName());
                }
            }
        }
    }

    /**
     * Checks action titles available for each registered page object.
     * <p>
     * Actions are validated per page, including actions inherited from parent page classes.
     *
     * @param pages registered framework page instances
     * @throws IllegalStateException if an action title is empty or duplicated for the same page
     */
    public static void validateUniqueActionTitles(Collection<FrameworkPage> pages) {
        for (FrameworkPage page : pages) {
            validateUniqueActionTitles(page.getClass());
        }
    }

    /**
     * Scans action annotations declared on the page class and its framework parent classes.
     *
     * @param pageClass page object class to validate
     */
    private static void validateUniqueActionTitles(Class<?> pageClass) {
        Map<String, Method> registeredActions = new LinkedHashMap<>();
        Class<?> currentClass = pageClass;

        while (currentClass != null && FrameworkPage.class.isAssignableFrom(currentClass)) {
            for (Method method : currentClass.getDeclaredMethods()) {
                registerActionTitle(pageClass, registeredActions, method);
            }
            currentClass = currentClass.getSuperclass();
        }
    }

    /**
     * Registers all action titles declared on a method.
     *
     * @param pageClass page object class being validated
     * @param registeredActions action titles already found for this page
     * @param method method that may contain action annotations
     */
    private static void registerActionTitle(Class<?> pageClass, Map<String, Method> registeredActions, Method method) {
        if (method.isAnnotationPresent(ActionTitle.class)) {
            registerActionTitle(pageClass, registeredActions, method, method.getAnnotation(ActionTitle.class).value());
        }

        if (method.isAnnotationPresent(ActionsTitle.class)) {
            for (ActionTitle annotation : method.getAnnotation(ActionsTitle.class).value()) {
                registerActionTitle(pageClass, registeredActions, method, annotation.value());
            }
        }
    }

    /**
     * Registers a single action title and fails if the same title is already registered.
     *
     * @param pageClass page object class being validated
     * @param registeredActions action titles already found for this page
     * @param method method associated with the action title
     * @param title readable action title from the annotation
     */
    private static void registerActionTitle(Class<?> pageClass, Map<String, Method> registeredActions, Method method, String title) {
        String normalizedTitle = normalizeTitle(title, "@ActionTitle", pageClass.getSimpleName() + "." + method.getName());
        Method existingMethod = registeredActions.putIfAbsent(normalizedTitle, method);

        if (existingMethod != null) {
            throw new IllegalStateException("Duplicate @ActionTitle '" + title + "' found on page "
                    + pageClass.getSimpleName() + " in methods "
                    + existingMethod.getDeclaringClass().getSimpleName() + "." + existingMethod.getName()
                    + " and " + method.getDeclaringClass().getSimpleName() + "." + method.getName());
        }
    }

    /**
     * Normalizes annotation values for case-insensitive duplicate checks.
     *
     * @param title annotation value to normalize
     * @param annotationName annotation name used in error messages
     * @param source class, method, or field where the value was declared
     * @return lower-case title suitable for map keys
     * @throws IllegalStateException if the title is blank
     */
    private static String normalizeTitle(String title, String annotationName, String source) {
        if (title == null || title.isBlank()) {
            throw new IllegalStateException(annotationName + " value can not be empty in " + source);
        }
        return title.toLowerCase(Locale.ROOT);
    }

    private record ElementDefinition(String fieldName) {
    }
}
