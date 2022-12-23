package com.github.phoswald.sample;

import java.util.HashMap;
import java.util.function.Supplier;

import com.github.phoswald.sample.task.TaskRepository;
import com.github.phoswald.sample.utils.ConfigProvider;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public interface ApplicationModule {

    static void setup(ApplicationModule module, Application application) {
        Global.module = module;
        Global.application = application;
    }

    static ApplicationModule instance() {
        return Global.module;
    }

    default Application getApplication() {
        return Global.application;
    }

    default ConfigProvider getConfigProvider() {
        return new ConfigProvider();
    }

    default Supplier<TaskRepository> getTaskRepositoryFactory() {
        return () -> new TaskRepository(getEntityManagerFactory());
    }

    default EntityManagerFactory getEntityManagerFactory() {
        if (Global.emf == null) {
            var config = getConfigProvider();
            var props = new HashMap<>();
            config.getConfigProperty("app.jdbc.driver") //
                    .ifPresent(v -> props.put("jakarta.persistence.jdbc.driver", v));
            config.getConfigProperty("app.jdbc.url") //
                    .ifPresent(v -> props.put("jakarta.persistence.jdbc.url", v));
            config.getConfigProperty("app.jdbc.username") //
                    .ifPresent(v -> props.put("jakarta.persistence.jdbc.user", v));
            config.getConfigProperty("app.jdbc.password") //
                    .ifPresent(v -> props.put("jakarta.persistence.jdbc.password", v));
            Global.emf = Persistence.createEntityManagerFactory("taskDS", props);
        }
        return Global.emf;
    }

    public static class Global {

        static {
            // Hibernate: auto-detection falls back to JUL, slf4j is only used if logback is present
            System.setProperty("org.jboss.logging.provider", "slf4j");
        }

        private static ApplicationModule module = null;
        private static Application application = null;
        private static EntityManagerFactory emf = null;
    }
}
