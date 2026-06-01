package io.github.royalspirit.sjctestframework.core;

import io.github.royalspirit.sjctestframework.core.annotations.ElementTitle;
import org.openqa.selenium.By;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Optional;

public class ElementsObjectRegistry {

    /**
     * Returns page objects registered in the framework.
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
        FrameworkPage currentPage = PageContextRegistry.getCurrentPage();
        return findElementByTitle(currentPage, elementTitle)
                .orElseThrow(() -> new IllegalArgumentException("Element with title '" + elementTitle
                        + "' not found on page " + currentPage.getClass().getSimpleName() + "."));
    }

    private static Optional<By> findElementByTitle(FrameworkPage page, String elementTitle) {
        Class<?> currentClass = page.getClass();

        while (currentClass != null && FrameworkPage.class.isAssignableFrom(currentClass)) {
            Optional<By> element = findDeclaredElementByTitle(page, currentClass, elementTitle);
            if (element.isPresent()) {
                return element;
            }
            currentClass = currentClass.getSuperclass();
        }

        return Optional.empty();
    }

    /**
     * Finds an element title among fields declared directly in the given page class.
     * <p>
     * This method does not inspect parent classes; class hierarchy traversal is handled by
     * {@link #findElementByTitle(FrameworkPage, String)} so page-specific elements have priority
     * over inherited common elements.
     *
     * @param page page instance used to read the locator field value
     * @param pageClass class whose declared fields should be inspected
     * @param elementTitle readable element title declared in {@link ElementTitle}
     * @return locator when a matching {@link ElementTitle} field is found
     */
    private static Optional<By> findDeclaredElementByTitle(FrameworkPage page, Class<?> pageClass, String elementTitle) {
        for (Field field : pageClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ElementTitle.class)
                    && field.getAnnotation(ElementTitle.class).value().equalsIgnoreCase(elementTitle)
                    && field.getType().equals(By.class)) {
                try {
                    field.setAccessible(true);
                    return Optional.of((By) field.get(page));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Error accessing element: " + elementTitle, e);
                }
            }
        }

        return Optional.empty();
    }
}
