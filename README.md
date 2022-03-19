# sample-javafx

Experiments with JavaFX

## Run Standalone

~~~
$ mvn clean verify
$ ./run-mp.sh
$ ./run-cp.sh
~~~

## Run JRE created by JLink 

~~~
$ mvn clean verify
$ ./build-jre.sh
$ ./target/jre/bin/run
~~~

## Run from Eclipse

The application can be started from Eclipse in a non-modular way.

## Module Path vs. Class Path

- By default, the `org.openjfx:*` dependencies should be specified as modules on the module path
  (using the `-p` und `--add-modules` options).
- It is possible, although not officially supported, to run the application in a non-modular way from the class path 
  (using only the `-cp` option). For that, the main class must not extend `javafx.application.Application` 
  (see the additional main class `JavaFxApplicationLauncher`, which simply delegates to `JavaFxApplication`).
