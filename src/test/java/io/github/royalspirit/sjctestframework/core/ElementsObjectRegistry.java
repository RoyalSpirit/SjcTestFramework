package io.github.royalspirit.sjctestframework.core;

import io.github.royalspirit.sjctestframework.core.annotations.ElementTitle;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.util.Collection;

public class ElementsObjectRegistry {

    /**
     * Returns the set of page objects registered in the framework.
     * @return registered page objects
     */
    public static Collection<FrameworkPage> getPageObjects() {
        return PageContextRegistry.getRegisteredPages();
    }

    /**
     * Finds a By locator for an element by its title annotation.
     * @param elementTitle the title of the element
     * @return By locator for the element
     * @throws IllegalArgumentException if the element with the specified title is not found
     */
    public static By getElementByTitle(String elementTitle) {
        for (FrameworkPage page : getPageObjects()) {
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
