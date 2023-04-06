# sample-javafx

Experiments with JavaFX

## Run Standalone

Linux / MacOS:

~~~
$ mvn clean verify

$ java \
  -p target/sample-javafx-*-dist/lib/ \
  --add-modules ALL-MODULE-PATH \
  -Dapp.jdbc.url=jdbc:h2:./databases/task-db \
  com.github.phoswald.sample.Application

$ java \
  -cp $(echo target/sample-javafx-*-dist/lib)/"*" \
  -Dapp.jdbc.url=jdbc:h2:./databases/task-db \
  com.github.phoswald.sample.ApplicationLauncher

$ APP_JDBC_URL=jdbc:h2:./databases/task-db \
  target/sample-javafx-*-dist/bin/run.sh

$ APP_JDBC_URL=jdbc:h2:./databases/task-db \
  target/sample-javafx-*-dist/bin/run-cp.sh
~~~

Windows:

~~~
$ mvn clean verify

$ java ^
  -p target\sample-javafx-1.0.0-SNAPSHOT-dist\lib ^
  --add-modules ALL-MODULE-PATH ^
  -Dapp.jdbc.url=jdbc:h2:%USERPROFILE%\Documents\databases\task-db ^
  com.github.phoswald.sample.Application

$ java ^
  -cp target\sample-javafx-1.0.0-SNAPSHOT-dist\lib\* ^
  -Dapp.jdbc.url=jdbc:h2:%USERPROFILE%\Documents\databases\task-db ^
  com.github.phoswald.sample.ApplicationLauncher

$ set APP_JDBC_URL=jdbc:h2:%USERPROFILE%\Documents\databases\task-db
$ target\sample-javafx-1.0.0-SNAPSHOT-dist\bin\run.cmd
$ target\sample-javafx-1.0.0-SNAPSHOT-dist\bin\run-cp.cmd
$ set APP_JDBC_URL=
~~~

## Run JRE created by JLink 

Linux / MacOS:

~~~
$ mvn clean verify && ./build-jlink.sh

$ target/manual-jlink/bin/java --list-modules

$ APP_JDBC_URL=jdbc:h2:./databases/task-db \
  target/manual-jlink/bin/run.sh
~~~

## Run from Eclipse

The application can be started from Eclipse in a non-modular way.

## Module Path vs. Class Path

- By default, the `org.openjfx:*` dependencies should be specified as modules on the module path
  (using the `-p` und `--add-modules` options).
- It is possible, although not officially supported, to run the application in a non-modular way from the class path 
  (using only the `-cp` option). For that, the main class must not extend `javafx.application.Application` 
  (see the additional main class `ApplicationLauncher`, which simply delegates to `Application`).
