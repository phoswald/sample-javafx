module sample {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    opens com.github.phoswald.sample to javafx.fxml;

    exports com.github.phoswald.sample;
}
