package sjc.aft.framework.core;

import org.openqa.selenium.By;
import org.reflections.Reflections;
import sjc.aft.framework.core.annotations.ElementTitle;
import sjc.aft.framework.core.annotations.PageTitle;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Set;
import java.util.stream.Collectors;

public class ElementsObjectRegistry {

    private static final Set<Object> pageObjects;

    static {
        Reflections reflections = new Reflections("sjc.aft.framework.pages");
        Set<Class<?>> pages = reflections.getTypesAnnotatedWith(PageTitle.class);

        pageObjects = pages.stream()
                .filter(clazz -> !Modifier.isAbstract(clazz.getModifiers()))
                .map(clazz -> {
                    try {
                        return clazz.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException("Не удалось создать экземпляр PageObject: " + clazz.getSimpleName(), e);
                    }
                })
                .collect(Collectors.toSet());
    }

    public static Set<Object> getPageObjects() {
        return pageObjects;
    }

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
                        throw new RuntimeException("Ошибка доступа к элементу: " + elementTitle, e);
                    }
                }
            }
        }
        throw new IllegalArgumentException("Элемент с заголовком '" + elementTitle + "' не найден.");
    }
}