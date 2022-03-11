#!/bin/bash
java \
  -p  'target/libs-mp' \
  -cp 'target/libs-cp/*:target/sample-javafx.jar' \
  --add-modules ALL-MODULE-PATH \
  -Dapp.jdbc.url=jdbc:h2:./databases/task-db \
  com.github.phoswald.sample.javafx.JavaFxApplication
