package sjc.aft.framework.core;

import org.reflections.Reflections;
import sjc.aft.framework.core.annotations.PageTitle;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PageContextRegistry {


    private static final Map<String, FrameworkPage> pageTitleMap = new HashMap<>();
    private static FrameworkPage currentPage;

    public static void autoRegisterPages(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> pageClasses = reflections.getTypesAnnotatedWith(PageTitle.class);

        for (Class<?> pageClass : pageClasses) {
            if (Modifier.isAbstract(pageClass.getModifiers())) {
                continue;
            }
            try {
                FrameworkPage pageInstance = (FrameworkPage) pageClass.getDeclaredConstructor().newInstance();
                PageTitle annotation = pageClass.getAnnotation(PageTitle.class);
                pageTitleMap.put(annotation.title(), pageInstance);
            } catch (Exception e) {
                throw new RuntimeException("Не удалось создать экземпляр для страницы: " + pageClass.getSimpleName(), e);
            }
        }
    }

    public static FrameworkPage getPageByTitle(String title) {
        FrameworkPage page = pageTitleMap.get(title);
        if (page == null) {
            throw new IllegalArgumentException("Страница с заголовком: '" + title + "' не найдена. Возможно она не существует.");
        }
        return page;
    }

    public static FrameworkPage getCurrentPage() {
        if (currentPage == null) {
            throw new IllegalStateException("Текущая страница не установлена");
        }
        return currentPage;
    }

    public static void setCurrentPage(FrameworkPage page) {
        currentPage = page;
    }

    public static void setCurrentPageByTitle(String title) {
        FrameworkPage page = getPageByTitle(title);
        setCurrentPage(page);
    }


}