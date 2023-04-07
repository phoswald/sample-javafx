module com.github.phoswald.sample {
    requires jakarta.persistence;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.slf4j;
    //requires org.slf4j.simple;
    //requires org.hibernate.orm.core; // TODO (review): required? (for JPA entity proxies only)

    opens com.github.phoswald.sample to javafx.graphics, javafx.fxml;
    opens com.github.phoswald.sample.javafx to javafx.graphics, javafx.fxml;
    opens com.github.phoswald.sample.sample to javafx.graphics, javafx.fxml;
    opens com.github.phoswald.sample.task to javafx.graphics, javafx.fxml, org.hibernate.orm.core;
}
