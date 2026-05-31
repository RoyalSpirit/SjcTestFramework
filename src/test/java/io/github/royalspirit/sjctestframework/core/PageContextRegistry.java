package io.github.royalspirit.sjctestframework.core;

import io.github.royalspirit.sjctestframework.core.annotations.PageTitle;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.*;

public class PageContextRegistry {


    private static final Map<String, FrameworkPage> pageTitleMap = new HashMap<>();
    private static FrameworkPage currentPage;

    /**
     * Automatically registers all pages annotated with @PageTitle in the given package.
     * @param basePackage the base package to scan
     */
    public static void autoRegisterPages(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        Set<Class<?>> pageClasses = reflections.getTypesAnnotatedWith(PageTitle.class);

        pageTitleMap.clear();
        currentPage = null;

        for (Class<?> pageClass : pageClasses) {
            if (Modifier.isAbstract(pageClass.getModifiers())) {
                continue;
            }
            try {
                FrameworkPage pageInstance = (FrameworkPage) pageClass.getDeclaredConstructor().newInstance();
                PageTitle annotation = pageClass.getAnnotation(PageTitle.class);
                pageTitleMap.put(annotation.title(), pageInstance);
            } catch (Exception e) {
                throw new RuntimeException("Unable to create instance for page: " + pageClass.getSimpleName(), e);
            }
        }
    }

    /**
     * Gets all registered page instances.
     * @return registered framework pages
     */
    public static Collection<FrameworkPage> getRegisteredPages() {
        if (pageTitleMap.isEmpty()) {
            throw new IllegalStateException("Pages are not registered. Call autoRegisterPages before accessing page objects.");
        }
        return Collections.unmodifiableCollection(pageTitleMap.values());
    }

    /**
     * Gets a registered page instance by its title.
     * @param title the title of the page
     * @return the FrameworkPage instance
     * @throws IllegalArgumentException if the page is not found
     */
    public static FrameworkPage getPageByTitle(String title) {
        FrameworkPage page = pageTitleMap.get(title);
        if (page == null) {
            throw new IllegalArgumentException("Page with title: '" + title + "' not found. Maybe it does not exist.");
        }
        return page;
    }

    /**
     * Gets the current page instance.
     * @return the current FrameworkPage
     * @throws IllegalStateException if the current page is not set
     */
    public static FrameworkPage getCurrentPage() {
        if (currentPage == null) {
            throw new IllegalStateException("Current page is not set");
        }
        return currentPage;
    }

    /**
     * Sets the current page instance.
     * @param page the FrameworkPage to set as current
     */
    public static void setCurrentPage(FrameworkPage page) {
        currentPage = page;
    }

    public static void setCurrentPageByTitle(String title) {
        FrameworkPage page = getPageByTitle(title);
        setCurrentPage(page);
    }

}
