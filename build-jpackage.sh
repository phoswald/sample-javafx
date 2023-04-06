#!/bin/bash
# Unfortunately, jpackage cannot process automatic modules (`jpackage ... --add-modules ALL-MODULE-PATH`).
# Therefore, we have to add some modules and pass the remaining stuff to `--input`.
jpackage \
  -d target/manual-jpackage -n sample-javafx \
  -t app-image \
  --runtime-image target/manual-jlink \
  --input target/manual-jlink/lib/jars \
  --main-class com.github.phoswald.sample.Application \
  --main-jar sample-javafx-1.0.0-SNAPSHOT.jar
rm -rf target/manual-jpackage/sample-javafx/lib/runtime/lib/jars
