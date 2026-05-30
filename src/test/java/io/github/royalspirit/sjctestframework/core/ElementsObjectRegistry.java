package io.github.royalspirit.sjctestframework.core;

import io.github.royalspirit.sjctestframework.core.annotations.ElementTitle;
import io.github.royalspirit.sjctestframework.core.annotations.PageTitle;
import org.openqa.selenium.By;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

public class ElementsObjectRegistry {

    private static final Set<Object> pageObjects;

    static {
        Reflections reflections = new Reflections("io.github.royalspirit.sjctestframework.pages");
        Set<Class<?>> pages = reflections.getTypesAnnotatedWith(PageTitle.class);

        pageObjects = pages.stream()
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .map(clazz -> {
                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Unable to create instance of PageObject: " + clazz.getSimpleName(), e);
                    }
                })
                .collect(Collectors.toSet());
    }

    /**
     * Returns the set of page objects registered in the framework.
     * @return set of page objects
     */
    public static Set<Object> getPageObjects() {
        return pageObjects;
    }

    /**
     * Finds a By locator for an element by its title annotation.
     * @param elementTitle the title of the element
     * @return By locator for the element
     * @throws IllegalArgumentException if the element with the specified title is not found
     */
    public static By getElementByTitle(String elementTitle) {
        for (Object page : pageObjects) {
            for (Field field : page.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(ElementTitle.class)
                        && field.getAnnotation(ElementTitle.class).value().equalsIgnoreCase(elementTitle)
                        && field.getType().equals(By.class)) {
                    try {
                        field.setAccessible(true);
                        return (By) field.get(page);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Error accessing element: " + elementTitle, e);
                    }
                }
            }
        }
        throw new IllegalArgumentException("Element with title '" + elementTitle + "' not found.");
    }
}