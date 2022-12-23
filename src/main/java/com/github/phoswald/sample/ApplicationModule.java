package com.github.phoswald.sample;

import java.util.HashMap;
import java.util.function.Supplier;

import com.github.phoswald.sample.task.TaskRepository;
import com.github.phoswald.sample.utils.ConfigProvider;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ApplicationModule {

    static {
        // Hibernate: auto-detection falls back to JUL, slf4j is only used if logback is
        // present
        System.setProperty("org.jboss.logging.provider", "slf4j");
    }

    private static ApplicationModule module = null;
    private static Application application = null;

    private EntityManagerFactory emf = null;

    public static void setup(ApplicationModule module, Application application) {
        ApplicationModule.module = module;
        ApplicationModule.application = application;
    }

    public static ApplicationModule instance() {
        return module;
    }

    public Application getApplication() {
        return application;
    }

    public ConfigProvider getConfigProvider() {
        return new ConfigProvider();
    }

    public Supplier<TaskRepository> getTaskRepositoryFactory() {
        return () -> new TaskRepository(getEntityManagerFactory());
    }

    public EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            var config = getConfigProvider();
            var props = new HashMap<>();
            props.put("jakarta.persistence.jdbc.url", config.getConfigProperty("app.jdbc.url")
                    .orElse("jdbc:h2:mem:test" + hashCode() + ";DB_CLOSE_DELAY=-1"));
            props.put("jakarta.persistence.jdbc.user", config.getConfigProperty("app.jdbc.username").orElse("sa"));
            props.put("jakarta.persistence.jdbc.password", config.getConfigProperty("app.jdbc.password").orElse("sa"));
            emf = Persistence.createEntityManagerFactory("taskDS", props);
        }
        return emf;
    }
}
